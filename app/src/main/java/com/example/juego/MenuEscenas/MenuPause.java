package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Pantalla;

public class MenuPause extends Pantalla {

    RectF rectFondo;
    RectF btnVolver;
    RectF btnOpciones;
    RectF btnAyuda;
    RectF btnMenuPpal;

    public MenuPause(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        setRectBotones();
        tp.setARGB(250,233,217,168);
        tp.setTextSize(altoPantalla/12);
        tp.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void dibuja(Canvas c) {
        drawBotones(c);
    }

    public void setRectBotones(){
        rectFondo = new RectF(anchoPantalla/32 * 8, altoPantalla/16 * 2, anchoPantalla/32*24, altoPantalla/16 * 14);
        btnVolver = new RectF(anchoPantalla/32 * 11, altoPantalla/16 * 2.5f, anchoPantalla/32*21, altoPantalla/16 * 4.5f);
        btnOpciones = new RectF(anchoPantalla/32 * 11, altoPantalla/16 * 5.5f, anchoPantalla/32*21, altoPantalla/16 * 7.5f);
        btnAyuda = new RectF(anchoPantalla/32 * 11, altoPantalla/16 * 8.5f, anchoPantalla/32*21, altoPantalla/16 * 10.5f);
        btnMenuPpal = new RectF(anchoPantalla/32 * 11, altoPantalla/16 * 11.5f, anchoPantalla/32*21, altoPantalla/16 * 13.5f);
    }

    public void drawBotones(Canvas c){
        c.drawRect(btnVolver, pBotonesVerdes);
        c.drawText("Volver", anchoPantalla/2, altoPantalla/16*4, tp);
        c.drawRect(btnOpciones, pBotonesVerdes);
        c.drawText("Opciones", anchoPantalla/2, altoPantalla/16*7, tp);
        c.drawRect(btnAyuda, pBotonesVerdes);
        c.drawText("Ayuda", anchoPantalla/2, altoPantalla/16*10, tp);
        c.drawRect(btnMenuPpal, pBotonesVerdes);
        c.drawText("Menú principal", anchoPantalla/2, altoPantalla/16*13, tp);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int accion = event.getAction();

        if(accion == MotionEvent.ACTION_DOWN){
            if(btnVolver.contains(x,y)){
                return 0;
            }else if(btnOpciones.contains(x,y)){
                return 11;

            }else if(btnAyuda.contains(x,y)){
                return 12;

            }else if(btnMenuPpal.contains(x,y)){
                JuegoSV.cambiaPantalla(1);
            }
        }
        return -1;
    }
}
