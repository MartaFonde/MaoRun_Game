package com.example.juego;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class PantallaPause extends Pantalla{

    RectF rectFondo;
    RectF btnVolver;
    RectF btnOpciones;
    RectF btnAyuda;
    RectF btnMenuPpal;

    Paint pFondo;

    static boolean opciones = false;
    static boolean ayuda = false;
    Pantalla pauseOpciones;
    Pantalla pauseAyuda;

    public PantallaPause(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        pFondo = new Paint();
        pFondo.setColor(Color.argb(210, 50,50,50));

        setRectBotones();
        tp.setARGB(250,233,217,168);
        tp.setTextSize(altoPantalla/12);
        tp.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawColor(Color.TRANSPARENT); //fondo transparente
        c.drawRect(rectFondo, pFondo);
        if(opciones){
            pauseOpciones.dibuja(c);
        }else if(ayuda){
            pauseAyuda.dibuja(c);
        }else{
            setRectBotones();
            drawBotones(c);
        }
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
    int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(!opciones && !ayuda){
            if(btnVolver.contains(x,y)){
                return 1;
            }else if(btnOpciones.contains(x,y)){
                opciones = true;
                pauseOpciones = new PantallaPauseOpciones(context, anchoPantalla, altoPantalla, 11);
            }else if(btnAyuda.contains(x,y)){
                ayuda = true;
                pauseAyuda = new PantallaPauseAyuda(context, anchoPantalla, altoPantalla, 12);
            }else if(btnMenuPpal.contains(x,y)){
                JuegoSV.cambiaPantalla(1);
            }
        }else if(opciones && !ayuda) {
            int aux = pauseOpciones.onTouchEvent(event);
            if(aux == 1){
                pauseOpciones = null;
                opciones = false;
                //Toast.makeText(context, "opcFALSE", Toast.LENGTH_SHORT).show();
            }
        }else {
            int aux = pauseAyuda.onTouchEvent(event);
            if(aux == 1){
                pauseAyuda = null;
                ayuda = false;
            }
        }

        return super.onTouchEvent(event);
    }
}
