package com.example.juego.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.R;

import java.io.IOException;

public class MenuPrincipal extends Menu {

    RectF btnJugar;
    RectF btnRecords;
    RectF btnCreditos;
    RectF btnAyuda;
    RectF btnOpciones;
    RectF btnSalir;

    public MenuPrincipal(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        setBotonesRect();
        if(JuegoSV.musica  && JuegoSV.restartMusica) {
            JuegoSV.mediaPlayer = MediaPlayer.create(context, R.raw.bensound_highoctane);
            //JuegoSV.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            JuegoSV.mediaPlayer.setLooping(true);
            JuegoSV.volumen = JuegoSV.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            JuegoSV.mediaPlayer.setVolume(JuegoSV.volumen, JuegoSV.volumen );
            JuegoSV.mediaPlayer.start();
        }
    }

    /**
     * Dibuja botones (rect) de las opciones de menú y el texto asociado, y el fondo negro.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawRect(btnJugar, pBotonVerde);
        c.drawText("JUGAR", anchoPantalla / 2, altoPantalla / 16 * 2.5f, tpBeige);
        c.drawRect(btnRecords, pBotonVerde);
        c.drawText("RÉCORDS", anchoPantalla/2, altoPantalla / 16 * 5, tpBeige);
        c.drawRect(btnCreditos, pBotonVerde);
        c.drawText("CRÉDITOS", anchoPantalla/2, altoPantalla /16 * 7.5f , tpBeige);
        c.drawRect(btnAyuda, pBotonVerde);
        c.drawText("AYUDA", anchoPantalla/2, altoPantalla /16 * 10, tpBeige);
        c.drawRect(btnOpciones, pBotonVerde);
        c.drawText("OPCIONES", anchoPantalla/2, altoPantalla /16 * 12.5f, tpBeige);
        c.drawRect(btnSalir, pBotonVerde);
        c.drawText("SALIR", anchoPantalla/2, altoPantalla /16 * 15 , tpBeige);
    }

    /**
     * Obtiene las coordenadas de la pulsación y comprueba si algún rect de las opciones las contiene.
     * Si hay alguno que las contenga, en JuegoSV se llevará a cabo el cambioPantalla a la pantalla
     * correspondiente al rect.
     * @param event
     * @return número de la nueva pantalla o -1 si ningún rect contiene las coordenadas y no se cambia
     * pantalla.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x= event.getX();
        float y= event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(btnRecords.contains(x,y)){
                return 2;
            } else if(btnCreditos.contains(x,y)){
                return 3;
            } else if(btnAyuda.contains(x,y)){
                return 4;
            } else if(btnOpciones.contains(x,y)){
                return 5;
            } else if(btnJugar.contains(x,y)){
                JuegoSV.mediaPlayer.stop();
                return 6;
            } else if(btnSalir.contains(x,y)){
                JuegoSV.mediaPlayer.stop();
                return 0;
            }
        }
        return super.onTouchEvent(event);       //-1
    }

    /**
     * Instancia los rect de los botones que representan los botones de las opciones de menú.
     */
    public void setBotonesRect(){
        btnJugar = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 3);
        btnRecords = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 3.5f,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 5.5f);
        btnCreditos = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 6,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 8);
        btnAyuda = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 8.5f,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 10.5f);
        btnOpciones = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 *11,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 13);
        btnSalir = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 13.5f,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 15.5f);
    }
}
