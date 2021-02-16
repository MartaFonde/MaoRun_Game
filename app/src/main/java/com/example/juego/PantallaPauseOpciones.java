package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.Toast;

public class PantallaPauseOpciones extends MenuOpciones{
    Bitmap atrasbtmp;
    RectF rectAtras;

    public PantallaPauseOpciones(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        atrasbtmp = Pantalla.escala(context, "menu/menu_atras.png", anchoPantalla/32*2, altoPantalla/16 *2);
    }

    @Override
    public void dibuja(Canvas c) {
        c.drawRect(rectAtras, pDesact);
        c.drawBitmap(atrasbtmp, anchoPantalla/32*8, altoPantalla/16*2, null);

        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTextSize(altoPantalla/15);
        c.drawText("OPCIONES", anchoPantalla/2, altoPantalla/16 * 3.5f, tp);

        tp.setTextAlign(Paint.Align.LEFT);
        tp.setTextSize(altoPantalla/18);
        c.drawText("SONIDO", anchoPantalla/32 * 10, altoPantalla / 16 * 6, tp);
        c.drawRect(rectSonAct, pAct);
        c.drawRect(rectSonDesact, pDesact);

        c.drawText("MÚSICA", anchoPantalla/32 * 10, altoPantalla / 16 * 9, tp);
        c.drawRect(rectMusicaAct, pAct);
        c.drawRect(rectMusicaDesact, pDesact);

        c.drawText("VIBRACIÓN", anchoPantalla/32 * 10, altoPantalla / 16 * 12, tp);
        c.drawRect(rectVibracionAct, pAct);
        c.drawRect(rectVibracionDesact, pDesact);
    }

    @Override
    public void setRect() {
        rectAtras = new RectF(anchoPantalla / 32 *8, altoPantalla/16*2, anchoPantalla/32*10, altoPantalla/16*4);

        rectSonAct = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 18, altoPantalla/16 * 6.5f);
        rectSonDesact = new RectF(anchoPantalla/32 * 20, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 22, altoPantalla/16 * 6.5f);
        rectMusicaAct = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 7.5f, anchoPantalla/32 * 18, altoPantalla/16 * 9.5f);
        rectMusicaDesact = new RectF(anchoPantalla/32 * 20, altoPantalla / 16 * 7.5f, anchoPantalla/32 * 22, altoPantalla/16 * 9.5f);
        rectVibracionAct = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 10.5f, anchoPantalla/32 * 18, altoPantalla/16 * 12.5f);
        rectVibracionDesact = new RectF(anchoPantalla/32 * 20, altoPantalla / 16 * 10.5f, anchoPantalla/32 * 22, altoPantalla/16 * 12.5f);
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
