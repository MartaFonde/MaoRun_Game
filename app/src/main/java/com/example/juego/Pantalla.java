package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.MotionEvent;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class Pantalla {
    public int numPantalla;
    protected int altoPantalla;
    protected int anchoPantalla;
    protected Context context;
    protected TextPaint tp;

    public Paint pBotonesVerdes;

    public Pantalla( Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.context = context;
        this.numPantalla = numPantalla;

        pBotonesVerdes = new Paint();
        pBotonesVerdes.setColor(Color.argb(225,129,157,80));

        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/PolandCannedIntoFuture-OxE3.ttf");
        tp = new TextPaint();
        tp.setTextSize(altoPantalla/10);
        tp.setColor(Color.WHITE);
        tp.setTypeface(face);

    }

    public void dibuja(Canvas c){
    }


    public void actualizaFisica(){
    }

    public int onTouchEvent(MotionEvent event){
        return -1;
    }


    public static Bitmap escala(Context context, String fichero, int nuevoAncho, int nuevoAlto) {
        //Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        Bitmap bitmapAux = getBitmapFromAssets(context, fichero);
        if (nuevoAncho == bitmapAux.getWidth() && nuevoAlto == bitmapAux.getHeight())
            return bitmapAux;
        //Ajuste a nuevo ancho
        if(nuevoAncho != bitmapAux.getWidth() && nuevoAlto == bitmapAux.getHeight())
            return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                    bitmapAux.getWidth(), true);
        //Ajuste a nuevo alto
        if(nuevoAncho == bitmapAux.getWidth() && nuevoAlto != bitmapAux.getHeight())
            return bitmapAux.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
                    bitmapAux.getHeight(), nuevoAlto, true);
        //Ajuste a nuevo alto y a nuevo ancho
        if(nuevoAncho != bitmapAux.getWidth() && nuevoAlto != bitmapAux.getHeight())
            bitmapAux = bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                    bitmapAux.getWidth(), true);
            bitmapAux = bitmapAux.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
                    bitmapAux.getHeight(), nuevoAlto, true);
            return bitmapAux;
    }

    public static Bitmap getBitmapFromAssets(Context context, String fichero) {
        try {
            InputStream is=context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch(IOException e) {
            return null;
        }
    }
}
