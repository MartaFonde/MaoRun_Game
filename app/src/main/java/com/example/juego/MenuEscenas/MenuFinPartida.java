package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Pantalla;

public class MenuFinPartida extends Pantalla {

    RectF btnRepetir;
    RectF btnMenuPpal;

    public MenuFinPartida(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        tpBeige.setTextAlign(Paint.Align.CENTER);
        tpBeige.setARGB(250,233,217,168);
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        tpBeige.setTextSize(altoPantalla/10);
        btnRepetir = new RectF(anchoPantalla / 32 *  9.5f, altoPantalla/16*5, anchoPantalla / 32 *22.5f, altoPantalla/16*7);
        c.drawRect(btnRepetir, pBotonVerde);
        c.drawText("Volver a jugar", anchoPantalla / 2, altoPantalla/16 * 6.5f, tpBeige);
        btnMenuPpal = new RectF(anchoPantalla / 32 *  9.5f, altoPantalla/16*9, anchoPantalla / 32 *22.5f, altoPantalla/16*11);
        c.drawRect(btnMenuPpal, pBotonVerde);
        c.drawText("Menú principal", anchoPantalla / 2, altoPantalla/16 * 10.5f, tpBeige);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(btnRepetir.contains(x, y)){
            return 6;
        }else if(btnMenuPpal.contains(x,y)){
            JuegoSV.restartMusica = false;
            return 1;
        }
        return super.onTouchEvent(event);
    }
}
