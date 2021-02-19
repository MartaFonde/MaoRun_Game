package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class PauseAyuda extends PauseEscena {

    public PauseAyuda(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
    }

    @Override
    public void dibuja(Canvas c) {
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTextSize(altoPantalla/15);
        c.drawText("CÓMO JUGAR", anchoPantalla/2, altoPantalla/16 * 3.5f, tp);

        tp.setTextSize(altoPantalla/18);
        c.drawText("Consigue el mayor número de monedas", anchoPantalla/2, altoPantalla / 16 * 5, tp);
        c.drawText("posible. Tendrás que esquivar a los", anchoPantalla/2, altoPantalla / 16 * 6, tp);
        c.drawText("coches para no perder vidas. ", anchoPantalla/2, altoPantalla / 16 * 7, tp);
        c.drawText("Los árboles son obstáculos.  ", anchoPantalla/2, altoPantalla / 16 * 8, tp);
        c.drawText("Para superar cada nivel, dirige el ", anchoPantalla/2, altoPantalla / 16 * 9, tp);
        c.drawText("gato hacia el camino de tierra.", anchoPantalla/2, altoPantalla / 16 * 10, tp);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(rectAtras.contains(x,y)){
            return 10;
        }
        return -1;
    }
}
