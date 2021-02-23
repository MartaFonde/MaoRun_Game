package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

abstract public class Menu extends Pantalla{
    protected Context context;
    protected Paint pBotonMenu;
    protected RectF btnAtras;
    protected Bitmap atrasBitmap;

    //TODO fondo, colores

    public Menu(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        this.context = context;

        pBotonMenu = new Paint();
        pBotonMenu.setTextAlign(Paint.Align.CENTER);
        pBotonMenu.setColor(Color.argb(225,129,157,80)); //verde
        pBotonMenu.setStyle(Paint.Style.FILL);

        tpBeige.setTextAlign(Paint.Align.CENTER);
        tpBeige.setARGB(250,233,217,168);    //beige

        if(numPantalla != 1 || numPantalla != 9){
            btnAtras = new RectF(0, altoPantalla/16 * 13, anchoPantalla / 32 * 3,
                    altoPantalla);
            atrasBitmap = Pantalla.escala(context, "menu/menu_atras.png",
                    anchoPantalla / 32 * 3, altoPantalla/16*3);
        }

        if(numPantalla == 1 || numPantalla == 9){
            JuegoSV.mediaPlayer = MediaPlayer.create(context, R.raw.bensound_highoctane);
            JuegoSV.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            JuegoSV.mediaPlayer.setLooping(true);
            JuegoSV.volumen = JuegoSV.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            JuegoSV.mediaPlayer.setVolume(JuegoSV.volumen / 3, JuegoSV.volumen / 3);
        }
    }

    /**
     * Dibuja en el lienzo un fondo negro y un bitmap de retroceso,
     * excepto en las pantallas Menú principa y Fin Partida
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c){
        c.drawColor(Color.BLACK);
        if(numPantalla != 1 && numPantalla != 9){       //1 menú ppal 9 fin partida
            c.drawBitmap(atrasBitmap, 0, altoPantalla/16*13, null);
        }
    }

    /**
     * Obtiene coordenadas de pulsaciones al tocar pantalla.
     * @param event
     * @return devuelve 1 si el rect btnAtras contiene las coordenadas x,y y el número de pantalla
     * no se corresponde con la pantalla Menú principal(1) ni en pantalla Fin Partida(9)
     * En caso contrario, devuelve -1
     */
    public int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();

        if(numPantalla != 1 && numPantalla != 9 && btnAtras.contains(x,y)) {
            JuegoSV.restartMusica = false;
            return 1;       //vuelve a menu principal
        }
        return -1;
    }
}
