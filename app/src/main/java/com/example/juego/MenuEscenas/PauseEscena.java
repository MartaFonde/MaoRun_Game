package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.Pantalla;

public class PauseEscena extends Pantalla {

    Pantalla pantallaActual;
    int numPantallaNueva;
    Paint pFondo;
    RectF rectFondo;
    Bitmap atrasbtmp;
    RectF rectAtras;

    public PauseEscena(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        pFondo = new Paint();
        pFondo.setColor(Color.argb(210, 50,50,50));

        tp.setARGB(250,233,217,168);
        tp.setTextSize(altoPantalla/12);
        tp.setTextAlign(Paint.Align.CENTER);

        rectFondo = new RectF(anchoPantalla/32 * 8, altoPantalla/16 * 2, anchoPantalla/32*24, altoPantalla/16 * 14);
        rectAtras = new RectF(anchoPantalla / 32 *8, altoPantalla/16*2, anchoPantalla/32*10, altoPantalla/16*4);
        atrasbtmp = Pantalla.escala(context, "menu/menu_atras.png", anchoPantalla/32*2, altoPantalla/16 *2);

        pantallaActual = new MenuPause(context, anchoPantalla, altoPantalla, 10);
    }

    @Override
    public void dibuja(Canvas c) {
        c.drawColor(Color.TRANSPARENT); //fondo transparente
        c.drawRect(rectFondo, pFondo);
        if(pantallaActual.numPantalla != 10){
            c.drawBitmap(atrasbtmp, anchoPantalla/32*8, altoPantalla/16*2, null);
        }
        pantallaActual.dibuja(c);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        int accion = event.getAction(); // Solo gestiona la pulsación de un dedo.
        int x=(int)event.getX();
        int y=(int)event.getY();

        switch (accion){
            case MotionEvent.ACTION_DOWN:
                numPantallaNueva = pantallaActual.onTouchEvent(event);
                if(numPantallaNueva == 0) return 0;         //volve a escena
                if(numPantallaNueva != -1 ) cambiaPantalla(numPantallaNueva);
        }

        return -1;
    }

    private void cambiaPantalla(int num){
        switch (num){
            case 10 : pantallaActual = new MenuPause(context, anchoPantalla, altoPantalla, 10);
                break;
            case 11 : pantallaActual = new PauseOpciones(context, anchoPantalla, altoPantalla, 11);
                break;
            case 12:pantallaActual = new PauseAyuda(context, anchoPantalla, altoPantalla, 12);
                break;

        }
    }
}
