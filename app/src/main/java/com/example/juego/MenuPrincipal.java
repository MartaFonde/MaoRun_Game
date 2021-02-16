package com.example.juego;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.MotionEvent;

public class MenuPrincipal extends Menu{

    RectF btnJugar;
    RectF btnRecords;
    RectF btnCreditos;
    RectF btnAyuda;
    RectF btnOpciones;
    RectF btnSalir;


    public MenuPrincipal(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        setBotonesRect();
        paintBotones(c);
    }

    @Override
    int onTouchEvent(MotionEvent event) {
        float x= event.getX();
        float y= event.getY();

        int accion = event.getAction();
        switch (accion){
            case MotionEvent.ACTION_DOWN:
                if(btnJugar.contains(x,y)){
                    return 6;
                }else if(btnCreditos.contains(x,y)){
                    return 3;
                }else if(btnOpciones.contains(x,y)){
                    return 5;
                }else if(btnAyuda.contains(x,y)){
                    return 4;
                }
        }
        return super.onTouchEvent(event);       //-1
    }

    public void cambioPantallaMenu(int nuevaPantallaMenu){
        switch (nuevaPantallaMenu){

        }
    }

    public void setBotonesRect(){
        btnJugar = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16, anchoPantalla / 32 * 24, altoPantalla / 16 * 3);
        btnRecords = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 3.5f, anchoPantalla / 32 * 24, altoPantalla / 16 * 5.5f);
        btnCreditos = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 6, anchoPantalla / 32 * 24, altoPantalla / 16 * 8);
        btnAyuda = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 8.5f, anchoPantalla / 32 * 24, altoPantalla / 16 * 10.5f);
        btnOpciones = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 *11, anchoPantalla / 32 * 24, altoPantalla / 16 * 13);
        btnSalir = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 13.5f, anchoPantalla / 32 * 24, altoPantalla / 16 * 15.5f);
    }

    public void paintBotones(Canvas c){
        c.drawRect(btnJugar, p);
        c.drawText("JUGAR", anchoPantalla / 2, altoPantalla / 16 * 2.5f, tp);
        c.drawRect(btnRecords, p);
        c.drawText("RECORDS", anchoPantalla/2, altoPantalla / 16 * 5, tp);
        c.drawRect(btnCreditos, p);
        c.drawText("CRÉDITOS", anchoPantalla/2, altoPantalla /16 * 7.5f , tp);
        c.drawRect(btnAyuda, p);
        c.drawText("AYUDA", anchoPantalla/2, altoPantalla /16 * 10, tp);
        c.drawRect(btnOpciones, p);
        c.drawText("OPCIONES", anchoPantalla/2, altoPantalla /16 * 12.5f, tp);
        c.drawRect(btnSalir, p);
        c.drawText("SALIR", anchoPantalla/2, altoPantalla /16 * 15 , tp);
    }


}
