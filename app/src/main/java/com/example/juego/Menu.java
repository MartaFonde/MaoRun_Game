package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.MotionEvent;

abstract public class Menu extends Pantalla{
    int altoPantalla;
    int anchoPantalla;
    Context context;
    Paint p;
    Rect menu;
    int pantallaMenu;
    RectF btnAtras;
    Bitmap atrasBitmap;

    //TODO fondo, fonte de letra, colores

    public Menu(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        this.context = context;

        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;

        p = new Paint();
        p.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.CYAN);
        p.setStyle(Paint.Style.FILL);

        super.tp.setTextAlign(Paint.Align.CENTER);

        btnAtras = new RectF(0, altoPantalla/16 * 13, anchoPantalla / 32 * 3, altoPantalla);
        atrasBitmap = Pantalla.escala(context, "menu/menu_atras.png", anchoPantalla / 32 * 3, altoPantalla/16*3);
    }

    @Override
    public void dibuja(Canvas c){
        super.dibuja(c);
        if(numPantalla != 1){
            //c.drawRect(btnAtras, p);
            c.drawBitmap(atrasBitmap, 0, altoPantalla/16*13, null);
        }
    }

    int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();

        if(numPantalla != 1 && btnAtras.contains(x,y)) {
            return 1;
        }

//        if (botonDerecha.contains(x,y)){
//            if (numEscena<6) return numEscena+1;
//        }else if(botonIz.contains(x,y)){
//            if (numEscena>1)return numEscena-1;
//        }
//        if (numEscena!=1)   if (menu.contains(x,y)) return 1;

        return -1;
    }


}