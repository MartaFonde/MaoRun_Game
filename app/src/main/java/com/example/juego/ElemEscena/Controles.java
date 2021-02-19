package com.example.juego.ElemEscena;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;

import com.example.juego.Pantalla;

public class Controles {
    Context context;
    int anchoPantalla;
    int altoPantalla;

    Bitmap[] bitmapControles;

    public RectF der;
    public RectF izq;
    public RectF arriba;
    public RectF abajo;

    Paint pControles;

    public Controles(Context context, int anchoPantalla, int altoPantalla) {
        this.context = context;

        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        escalaControles();

        pControles = new Paint();
        pControles.setAlpha(200);

        setRectControles();
    }

    public void setRectControles(){
        abajo = new RectF(anchoPantalla / 32 * 24 , altoPantalla / 16 * 13, anchoPantalla / 32 * 27 , altoPantalla);
        izq = new RectF(anchoPantalla / 32 , altoPantalla / 16 * 13, anchoPantalla / 32 * 4, altoPantalla);
        der = new RectF(anchoPantalla / 32 * 5, altoPantalla / 16 * 13, anchoPantalla / 32 * 8, altoPantalla);
        arriba = new RectF(anchoPantalla / 32 * 28, altoPantalla / 16 * 13, anchoPantalla/32 * 31, altoPantalla);
    }


    public void dibujaControles(Canvas c) {
        c.drawBitmap(bitmapControles[0], anchoPantalla/32 * 24, altoPantalla / 16 * 13,pControles);
        c.drawBitmap(bitmapControles[1], anchoPantalla / 32, altoPantalla / 16 * 13, pControles);
        c.drawBitmap(bitmapControles[2], anchoPantalla / 32 * 5, altoPantalla / 16 * 13, pControles);
        c.drawBitmap(bitmapControles[3], anchoPantalla / 32 * 28, altoPantalla / 16 * 13, pControles);
    }


    public void escalaControles(){
        int anchoControles = anchoPantalla/32 * 3;
        int altoControles = altoPantalla /16 * 3;
        bitmapControles = new Bitmap[4];

        bitmapControles[0] = Pantalla.escala(context, "controles/abajo_ctrl.jpg", anchoControles, altoControles);
        bitmapControles[1] = Pantalla.escala(context, "controles/izq_ctrl.jpg", anchoControles, altoControles);
        bitmapControles[2] = Pantalla.escala(context,  "controles/der_ctrl.jpg", anchoControles, altoControles);
        bitmapControles[3] = Pantalla.escala(context,  "controles/arriba_ctrl.jpg", anchoControles, altoControles);
    }
}
