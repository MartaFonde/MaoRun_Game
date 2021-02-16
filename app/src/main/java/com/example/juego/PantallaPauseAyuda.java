package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.Toast;

public class PantallaPauseAyuda extends Pantalla{
    Bitmap atrasbtmp;
    RectF rectAtras;

    public PantallaPauseAyuda(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        rectAtras = new RectF(anchoPantalla / 32 *8, altoPantalla/16*2, anchoPantalla/32*10, altoPantalla/16*4);
        atrasbtmp = Pantalla.escala(context, "menu/menu_atras.png", anchoPantalla/32*2, altoPantalla/16 *2);
    }

    @Override
    public void dibuja(Canvas c) {
        c.drawBitmap(atrasbtmp, anchoPantalla/32*8, altoPantalla/16*2, null);

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
    int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int aux = super.onTouchEvent(event);        //return 0 se toca algun rect son, mus, vib, -1 se non
        if(aux == -1){
            if(rectAtras.contains(x,y)){
                return 1;
            }
        }
        return -1;
    }
}
