package com.example.juego;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class MenuOpciones extends Menu{

    RectF sonAct;
    RectF sonDesact;
    RectF musicaAct;
    RectF musicaDesact;
    RectF vibracionAct;
    RectF vibracionDesact;

    Paint pAct;
    Paint pDesact;

    public MenuOpciones(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        pAct = new Paint();
        pAct.setColor(Color.GREEN);
        pDesact = new Paint();
        pDesact.setColor(Color.RED);
        setRect();
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTextSize(altoPantalla/10);
        c.drawText("OPCIONES", anchoPantalla/2, altoPantalla/16 * 2, tp);

        tp.setTextAlign(Paint.Align.LEFT);
        tp.setTextSize(altoPantalla/12);
        c.drawText("SONIDO", anchoPantalla/32 * 10, altoPantalla / 16 * 6, tp);
        c.drawRect(sonAct, pAct);
        c.drawRect(sonDesact, pDesact);

        c.drawText("MÚSICA", anchoPantalla/32 * 10, altoPantalla / 16 * 10, tp);
        c.drawRect(musicaAct, pAct);
        c.drawRect(musicaDesact, pDesact);

        c.drawText("VIBRACIÓN", anchoPantalla/32 * 10, altoPantalla / 16 * 14, tp);
        c.drawRect(vibracionAct, pAct);
        c.drawRect(vibracionDesact, pDesact);
    }

    @Override
    int onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();
        int accion = event.getAction();
        switch (accion){
            case MotionEvent.ACTION_DOWN:
                int aux=super.onTouchEvent(event);
                if (aux!=this.numPantalla && aux!=-1){
                    return aux;
                }else{
                    if(sonAct.contains(x,y)){

                    } else if(sonDesact.contains(x,y)) {

                    } else if(musicaAct.contains(x,y)) {

                    } else if(musicaDesact.contains(x,y)) {

                    } else if(vibracionAct.contains(x,y)) {

                    } else if(vibracionDesact.contains(x,y)) {
                    }
                }
            break;
        }
        return -1;
    }

    public void setRect(){
        sonAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 20, altoPantalla/16 * 6.5f);
        sonDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 24, altoPantalla/16 * 6.5f);
        musicaAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 8.5f, anchoPantalla/32 * 20, altoPantalla/16 * 10.5f);
        musicaDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 8.5f, anchoPantalla/32 * 24, altoPantalla/16 * 10.5f);
        vibracionAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 12.5f, anchoPantalla/32 * 20, altoPantalla/16 * 14.5f);
        vibracionDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 12.5f, anchoPantalla/32 * 24, altoPantalla/16 * 14.5f);
    }
}
