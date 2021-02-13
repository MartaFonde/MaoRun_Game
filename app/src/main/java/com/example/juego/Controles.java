package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;

public class Controles {
    Context context;
    int anchoPantalla;
    int altoPantalla;

    Bitmap[] bitmapControles;

    RectF der;
    RectF izq;
    RectF arriba;
    RectF abajo;

    RectF puntuacionRect;

    Paint pDer;
    Paint pIzq;
    Paint pArriba;
    Paint pAbajo;

    Paint pControles;

    int puntos;
    int vidas;

    Paint pPuntuacion;
    TextPaint textPaint;
    Typeface face;

    public Controles(Context context, int anchoPantalla, int altoPantalla) {
        this.context = context;

        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        bitmapControles = new Bitmap[6];
        setBitmapControles();

        this.puntos = 0;
        this.vidas = 7;

        face=Typeface.createFromAsset(context.getAssets(),"fonts/PolandCannedIntoFuture-OxE3.ttf");

        setRectControles();
        setPaintControles();
    }

    public Bitmap[] getBitmapControles() {
        return bitmapControles;
    }

    public void setBitmapControles() {
        escalaControles();
    }

    public void setRectControles(){
        abajo = new RectF(anchoPantalla / 32 * 24 , altoPantalla / 16 * 13, anchoPantalla / 32 * 27 , altoPantalla);
        izq = new RectF(anchoPantalla / 32 , altoPantalla / 16 * 13, anchoPantalla / 32 * 4, altoPantalla);
        der = new RectF(anchoPantalla / 32 * 5, altoPantalla / 16 * 13, anchoPantalla / 32 * 8, altoPantalla);
        arriba = new RectF(anchoPantalla / 32 * 28, altoPantalla / 16 * 13, anchoPantalla/32 * 31, altoPantalla);
    }

    public void setPaintControles(){
        pDer = new Paint();
        pDer.setColor(Color.BLUE);
        pDer.setAlpha(125);

        pIzq = new Paint();
        pIzq.setColor(Color.RED);
        pIzq.setAlpha(125);

        pArriba = new Paint();
        pArriba.setColor(Color.GREEN);
        pArriba.setAlpha(125);

        pAbajo = new Paint();
        pAbajo.setColor(Color.YELLOW);
        pAbajo.setAlpha(125);

        pControles = new Paint();
        pControles.setAlpha(200);

        pPuntuacion = new Paint();
        pPuntuacion.setColor(Color.GRAY);
        pPuntuacion.setStyle(Paint.Style.FILL);
        pPuntuacion.setAlpha(150);

        textPaint = new TextPaint();
        textPaint.setTypeface(face);
        textPaint.setTextSize(altoPantalla / 20); // tamaño del texto en pixels
        textPaint.setTextAlign(Paint.Align.CENTER); // Alineación del texto
        textPaint.setColor(Color.BLACK); // C
    }

    public void dibujaControles(Canvas c) {
        c.drawBitmap(bitmapControles[0], anchoPantalla/32 * 24, altoPantalla / 16 * 13,pControles);
        c.drawBitmap(bitmapControles[1], anchoPantalla / 32, altoPantalla / 16 * 13, pControles);
        c.drawBitmap(bitmapControles[2], anchoPantalla / 32 * 5, altoPantalla / 16 * 13, pControles);
        //arriba = new RectF(anchoPantalla / 32 * 28, altoPantalla / 16 * 13, anchoPantalla/32 * 31, altoPantalla);
        c.drawBitmap(bitmapControles[3], anchoPantalla / 32 * 28, altoPantalla / 16 * 13, pControles);

        puntuacionRect = new RectF(anchoPantalla/32*24, 0, anchoPantalla, altoPantalla/16*2.5f);
        c.drawRect(puntuacionRect, pPuntuacion);

        //Log.i("vidas", vidas+"");
        for (int i = 0; i < vidas; i++) {
            c.drawBitmap(bitmapControles[4], anchoPantalla/32 * (31 - i), altoPantalla / 16 * 0.5f, null);
        }

        c.drawText(""+puntos, anchoPantalla/32*27.5f,  altoPantalla / 16 *2.25f, textPaint);
        c.drawBitmap(bitmapControles[5], anchoPantalla /32 *30, altoPantalla/16 * 1.5f, null);
    }


    public void escalaControles(){

        int anchoControles = anchoPantalla/32 * 3;
        int altoControles = altoPantalla /16 * 3;

        int anchoPuntuacion = anchoPantalla / 32;
        int altoPuntuacion = altoPantalla / 16 ;

        bitmapControles[0] = Pantalla.escala(context, R.drawable.arrow_down, anchoControles, altoControles);
        bitmapControles[1] = Pantalla.escala(context, R.drawable.arrow_left, anchoControles, altoControles);
        bitmapControles[2] = Pantalla.escala(context, R.drawable.arrow_right, anchoControles, altoControles);
        bitmapControles[3] = Pantalla.escala(context, R.drawable.arrow_up, anchoControles, altoControles);

        bitmapControles[4] = Pantalla.escala(context, R.drawable.heart, anchoPuntuacion, altoPuntuacion);
        bitmapControles[5] = Pantalla.escala(context, R.drawable.monedas_controles, anchoPuntuacion, altoPuntuacion);
    }
}
