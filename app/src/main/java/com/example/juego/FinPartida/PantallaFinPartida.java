package com.example.juego.FinPartida;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Pantalla;
import com.example.juego.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PantallaFinPartida extends Pantalla {
    RectF btnAvanzar;
    Bitmap avanzaBitmap;
    boolean sinVidas;
    int puntos;
    Bitmap monedas;

    public PantallaFinPartida(Context context, int anchoPantalla, int altoPantalla, int numPantalla, boolean sinVidas, int puntos) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        this.sinVidas = sinVidas;
        this.puntos = puntos;
        this.monedas = Pantalla.escala(context, "moneda/monedas_controles.png", anchoPantalla/32*2, altoPantalla/16*2);
        btnAvanzar = new RectF(anchoPantalla/32 * 28, altoPantalla/16 * 13, anchoPantalla, altoPantalla);
        avanzaBitmap = Pantalla.escala(context, "menu/menu_avance.png",
                anchoPantalla / 32 * 3, altoPantalla/16*3);

        if(JuegoSV.musica){
            JuegoSV.mediaPlayer = MediaPlayer.create(context, R.raw.bensound_highoctane);
            JuegoSV.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            JuegoSV.mediaPlayer.setLooping(true);
            JuegoSV.volumen = JuegoSV.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            JuegoSV.mediaPlayer.setVolume(JuegoSV.volumen / 3, JuegoSV.volumen / 3);
            JuegoSV.mediaPlayer.start();
        }
    }

    /**
     * Dibuja el texto indicando final de la partida, el motivo de finalización y la puntuación
     * lograda con una imagen de monedas.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        tpBeige.setTextSize(altoPantalla/8);
        tpBeige.setTextAlign(Paint.Align.CENTER);
        c.drawText("FIN DE LA PARTIDA", anchoPantalla/2, altoPantalla/16*5, tpBeige);
        tpBeige.setTextSize(altoPantalla/12);
        if(sinVidas){
            tpBeige.setTextSize(altoPantalla/10);
            c.drawText("Has perdido todas las vidas", anchoPantalla/2, altoPantalla/16*8, tpBeige);
        }else{
            tpBeige.setTextSize(altoPantalla/14);
            c.drawText("Has superado todos los niveles", anchoPantalla/2, altoPantalla/16*8, tpBeige);
        }
        c.drawBitmap(monedas, anchoPantalla/32 * 13, altoPantalla/16*10.5f, null);
        c.drawText(puntos+"", anchoPantalla/32*17.5f, altoPantalla/16*12, tpBeige);
        c.drawBitmap(avanzaBitmap,anchoPantalla/32 * 29, altoPantalla/16 * 13, null );
    }

    /**
     * Si las coordenadas de pulsación están contenidas en el rect del botón avanzar, llama a la
     * función que comprueba si se logra superar algún récord.
     * @param event evento
     * @return 13 si se logra superar algún récord para avanzar a la pantalla GuardarRecord, en la que
     * se pide el nombre y guarda el récord. Si no se consigue entrar en la lista de récords, devuelve
     * 14, pantalla que muestra récords. Devuelve -1 si el rect del botón avanzar no contiene las coordenadas.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(btnAvanzar.contains(x, y)){
                if(guardaRecord()){
                    return 13;
                }else{
                    return 14;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * Comprueba si en el fichero records están registrados menos de 5 récords o si el último récord
     * es menor o igual que la variable puntos. Si se cumple alguna de esas condiciones, el récord
     * puede ser guardado.
     * @return true si la lista no está completa o si el récord supera o iguala la menor puntuación
     * guardada, false en caso contrario.
     */
    public boolean guardaRecord(){
        ArrayList<String> rec = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput("records.txt");
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader buffer = new BufferedReader(reader)) {
            String linea = "";
            while((linea = buffer.readLine()) != null){
                rec.add(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rec.size() < 5){
            return true;
        }else if(Integer.parseInt(rec.get(rec.size()-1).split(" ")[0]) <= puntos){
            return true;
        }
        return false;
    }
}
