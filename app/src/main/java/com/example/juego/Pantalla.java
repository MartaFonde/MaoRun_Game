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

import java.io.IOException;
import java.io.InputStream;

abstract public class Pantalla {
    public int numPantalla;
    protected int altoPantalla;
    protected int anchoPantalla;
    protected Context context;
    protected TextPaint tpBeige;
    protected TextPaint tpVerde;
    protected Typeface face;

    public Paint pBotonVerde;
    public Paint pBotonBeige;

    public Pantalla( Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.context = context;
        this.numPantalla = numPantalla;

        pBotonVerde = new Paint();
        pBotonVerde.setColor(Color.argb(225,129,157,80));

        pBotonBeige = new Paint();
        pBotonBeige.setColor(Color.argb(250,233,217,168));


        face=Typeface.createFromAsset(context.getAssets(),"fonts/PolandCannedIntoFuture-OxE3.ttf");
        tpBeige = new TextPaint();
        tpBeige.setTextSize(altoPantalla/10);
        tpBeige.setColor(Color.argb(250,233,217,168));
        tpBeige.setTypeface(face);

        tpVerde = new TextPaint();
        tpVerde.setTextSize(altoPantalla/10);
        tpVerde.setColor(Color.argb(225,129,157,80));
        tpVerde.setTypeface(face);
    }

    public void dibuja(Canvas c){
    }

    public void actualizaFisica(){
    }

    public int onTouchEvent(MotionEvent event){
        return -1;
    }

    /**
     * Crea un bitmap con un ancho y un alto determinados a partir de una imagen
     * @param context contexto
     * @param fichero ruta de la imagen
     * @param nuevoAncho nuevo ancho del bitmap
     * @param nuevoAlto nuevo alto del bitmap
     * @return bitmap con las nuevas dimensiones
     */
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

    /**
     * Obtiene bitmap de una imagen ubicada en el directorio assets
     * @param context contexto
     * @param fichero ruta de la imagen
     * @return bitmap de la imagen
     */
    public static Bitmap getBitmapFromAssets(Context context, String fichero) {
        try {
            InputStream is=context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch(IOException e) {
            return null;
        }
    }


}

