package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.Log;

public class Controles {
    int anchoPantalla;
    int altoPantalla;

    Bitmap[] imgControles;

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
    TextPaint tpaint;

    public Controles(Bitmap[] imgControles, int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.puntos = 0;
        this.vidas = 7;

        setPaintControles();
        setImgControles(imgControles);
    }

    public Bitmap[] getImgControles() {
        return imgControles;
    }

    public void setImgControles(Bitmap[] imgControles) {
        this.imgControles = imgControles;
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

        tpaint = new TextPaint();
        tpaint.setTextSize(50); // tamaño del texto en pixels
        //tpaint.setTextAlign(Paint.Align.CENTER); // Alineación del texto
        tpaint.setColor(Color.BLACK); // Color del texto
    }

    public void dibujaControles(Canvas c, int anchoPantalla, int altoPantalla) {
        abajo = new RectF(anchoPantalla / 32 * 24 , altoPantalla / 16 * 13, anchoPantalla / 32 * 27 , altoPantalla);
        //c.drawRect(abajo, pAbajo);
        c.drawBitmap(imgControles[0], anchoPantalla/32 * 24, altoPantalla / 16 * 13,pControles);

        izq = new RectF(anchoPantalla / 32 , altoPantalla / 16 * 13, anchoPantalla / 32 * 4, altoPantalla);
        //c.drawRect(izq, pIzq);
        c.drawBitmap(imgControles[1], anchoPantalla / 32, altoPantalla / 16 * 13, pControles);

        der = new RectF(anchoPantalla / 32 * 5, altoPantalla / 16 * 13, anchoPantalla / 32 * 8, altoPantalla);
        //c.drawRect(der, pDer);
        c.drawBitmap(imgControles[2], anchoPantalla / 32 * 5, altoPantalla / 16 * 13, pControles);

        arriba = new RectF(anchoPantalla / 32 * 28, altoPantalla / 16 * 13, anchoPantalla/32 * 31, altoPantalla);
        //c.drawRect(arriba, pArriba);
        c.drawBitmap(imgControles[3], anchoPantalla / 32 * 28, altoPantalla / 16 * 13, pControles);

        puntuacionRect = new RectF(anchoPantalla/32*24, 0, anchoPantalla, altoPantalla/16*2.5f);
        c.drawRect(puntuacionRect, pPuntuacion);

        //Log.i("vidas", vidas+"");
        for (int i = 0; i < vidas; i++) {
            c.drawBitmap(imgControles[4], anchoPantalla/32 * (31 - i), altoPantalla / 16 * 0.5f, null);
        }

        c.drawText(""+puntos, anchoPantalla/32*27.5f,  altoPantalla / 16 *2.25f, tpaint);
        c.drawBitmap(imgControles[5], anchoPantalla /32 *30, altoPantalla/16 * 1.5f, null);
    }
}
