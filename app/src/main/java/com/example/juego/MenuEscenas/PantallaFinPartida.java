package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Menu;
import com.example.juego.Pantalla;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PantallaFinPartida extends Menu {
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
        btnAvanzar = new RectF(anchoPantalla/32 * 29, altoPantalla/16 * 13, anchoPantalla, altoPantalla);
        avanzaBitmap = Pantalla.escala(context, "menu/menu_avance.png",
                anchoPantalla / 32 * 3, altoPantalla/16*3);

        if(JuegoSV.musica){
            JuegoSV.mediaPlayer.start();
        }

    }

    /**
     * Dibuja el texto correspondiente al final de la partida, el motivo de finalización.
     * Dibuja dos botones (rect) como opciones a realizar y el texto indicativo
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
     * Obtiene las coordenadas de pulsación. Si los rect de los botones de opciones los contienen:
     * si los contiene btnRepetir se hace un cambio de pantalla a Escena1; si los contiene btnMenuPpal
     * se hace un cambio de pantalla a pantalla MenuPrincipal
     * @param event
     * @return 5 número de pantalla de Escena1, 1 número de pantalla de MenuPrincipal. Si los rect
     * no contienen las coordenadas de la pulsación devuelve número de la pantalla actual (8) y no
     * se hace cambio de pantalla
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

    public boolean guardaRecord(){
        ArrayList<String> rec = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput("records.txt");
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader buffer = new BufferedReader(reader)) {
            String linea = "";
            while((linea = buffer.readLine()) != null){
                rec.add(linea+("\n"));
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
