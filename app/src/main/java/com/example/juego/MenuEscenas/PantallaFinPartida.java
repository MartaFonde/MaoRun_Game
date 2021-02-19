package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.Menu;
import com.example.juego.Pantalla;

public class PantallaFinPartida extends Menu {
    RectF btnRepetir;
    RectF btnMenuPpal;
    boolean sinVidas;
    int puntos;
    Bitmap monedas;

    public PantallaFinPartida(Context context, int anchoPantalla, int altoPantalla, int numPantalla, boolean sinVidas, int puntos) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        this.sinVidas = sinVidas;
        this.puntos = puntos;
        this.monedas = Pantalla.escala(context, "moneda/monedas_controles.png", anchoPantalla/32*2, altoPantalla/16*2);
    }

    @Override
    public void dibuja(Canvas c) {
        tp.setTextSize(altoPantalla/10);
        tp.setTextAlign(Paint.Align.CENTER);
        c.drawText("FIN DE LA PARTIDA", anchoPantalla/2, altoPantalla/16*3, tp);
        tp.setTextSize(altoPantalla/12);
        if(sinVidas){
            tp.setTextSize(altoPantalla/14);
            c.drawText("Has perdido todas las vidas", anchoPantalla/2, altoPantalla/16*5, tp);
        }else{
            tp.setTextSize(altoPantalla/14);
            c.drawText("Has superado todos los niveles", anchoPantalla/2, altoPantalla/16*5, tp);
        }
        c.drawBitmap(monedas, anchoPantalla/32 * 13, altoPantalla/16*5.5f, null);
        c.drawText(puntos+"", anchoPantalla/32*17.5f, altoPantalla/16*7, tp);

        tp.setTextSize(altoPantalla/10);
        btnRepetir = new RectF(anchoPantalla / 32 *  9.5f, altoPantalla/16*8, anchoPantalla / 32 *22.5f, altoPantalla/16*10);
        c.drawRect(btnRepetir, p);
        c.drawText("Volver a jugar", anchoPantalla / 2, altoPantalla/16 * 9.5f, tp);
        btnMenuPpal = new RectF(anchoPantalla / 32 *  9.5f, altoPantalla/16*12, anchoPantalla / 32 *22.5f, altoPantalla/16*14);
        c.drawRect(btnMenuPpal, p);
        c.drawText("Menú principal", anchoPantalla / 2, altoPantalla/16 * 13.5f, tp);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(btnRepetir.contains(x, y)){
            return 6;
        }else if(btnMenuPpal.contains(x,y)){
            return 1;
        }
        return super.onTouchEvent(event);
    }
}
