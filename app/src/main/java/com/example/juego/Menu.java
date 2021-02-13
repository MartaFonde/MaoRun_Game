package com.example.juego;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.MotionEvent;

abstract public class Menu extends Pantalla{
    int altoPantalla;
    int anchoPantalla;
    Context context;
    Paint p;
    Rect menu;
    int pantallaMenu;

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
    }

    @Override
    public void dibuja(Canvas c){
        super.dibuja(c);
    }

    int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();

//        if (botonDerecha.contains(x,y)){
//            if (numEscena<6) return numEscena+1;
//        }else if(botonIz.contains(x,y)){
//            if (numEscena>1)return numEscena-1;
//        }
//        if (numEscena!=1)   if (menu.contains(x,y)) return 1;

        return -1;
    }


}
