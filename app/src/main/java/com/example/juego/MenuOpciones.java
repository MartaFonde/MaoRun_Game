package com.example.juego;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.Toast;

public class MenuOpciones extends Menu{

    RectF rectSonAct;
    RectF rectSonDesact;
    RectF rectMusicaAct;
    RectF rectMusicaDesact;
    RectF rectVibracionAct;
    RectF rectVibracionDesact;

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
        c.drawRect(rectSonAct, pAct);
        c.drawRect(rectSonDesact, pDesact);

        c.drawText("MÚSICA", anchoPantalla/32 * 10, altoPantalla / 16 * 10, tp);
        c.drawRect(rectMusicaAct, pAct);
        c.drawRect(rectMusicaDesact, pDesact);

        c.drawText("VIBRACIÓN", anchoPantalla/32 * 10, altoPantalla / 16 * 14, tp);
        c.drawRect(rectVibracionAct, pAct);
        c.drawRect(rectVibracionDesact, pDesact);
    }

    @Override
    int onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();
        int accion = event.getAction();
        switch (accion){
            case MotionEvent.ACTION_DOWN:
                int aux=super.onTouchEvent(event);  //se se pulsa btnAtras volve a mnu ppal -- menu return 1
                if (aux == 1){
                    return aux;
                }else{
                //if(aux!= 1){        //non touch btnAtras
                    if(rectSonAct.contains(x,y)){
                        if(!JuegoSV.sonidoAct) JuegoSV.sonidoAct = true;
                        Toast.makeText(context, "sonidoAct"+JuegoSV.sonidoAct, Toast.LENGTH_SHORT).show();
                            return 0;
                    } else if(rectSonDesact.contains(x,y)) {
                        if(JuegoSV.sonidoAct) JuegoSV.sonidoAct = false;
                        Toast.makeText(context, "sonidoAct"+JuegoSV.sonidoAct, Toast.LENGTH_SHORT).show();
                        return 0;
                    } else if(rectMusicaAct.contains(x,y)) {    //TODO
                        if(!JuegoSV.musicaAct) JuegoSV.musicaAct = true;
                        Toast.makeText(context, "musicaAct"+JuegoSV.musicaAct, Toast.LENGTH_SHORT).show();
                        return 0;
                    } else if(rectMusicaDesact.contains(x,y)) {     //TODO
                        if(JuegoSV.musicaAct) JuegoSV.musicaAct = false;
                        Toast.makeText(context, "musicaAct"+JuegoSV.musicaAct, Toast.LENGTH_SHORT).show();
                        return 0;
                    } else if(rectVibracionAct.contains(x,y)) {
                        if(!JuegoSV.vibracionAct) JuegoSV.vibracionAct = true;
                        Toast.makeText(context, "vibracionAct"+JuegoSV.vibracionAct, Toast.LENGTH_SHORT).show();
                        return 0;
                    } else if(rectVibracionDesact.contains(x,y)) {
                        if(JuegoSV.vibracionAct) JuegoSV.vibracionAct = false;
                        Toast.makeText(context, "vibracionAct"+JuegoSV.vibracionAct, Toast.LENGTH_SHORT).show();
                        return 0;
                    }
                }
            break;
        }
        return -1;
    }

    public void setRect(){
        rectSonAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 20, altoPantalla/16 * 6.5f);
        rectSonDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 24, altoPantalla/16 * 6.5f);
        rectMusicaAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 8.5f, anchoPantalla/32 * 20, altoPantalla/16 * 10.5f);
        rectMusicaDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 8.5f, anchoPantalla/32 * 24, altoPantalla/16 * 10.5f);
        rectVibracionAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 12.5f, anchoPantalla/32 * 20, altoPantalla/16 * 14.5f);
        rectVibracionDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 12.5f, anchoPantalla/32 * 24, altoPantalla/16 * 14.5f);
    }
}
