package com.example.maorungame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;

abstract public class Pantalla {

    /**
     * Número identificativo
     */
    public int numPantalla;

    /**
     * Alto de pantalla del dispositivo
     */
    protected int altoPantalla;

    /**
     * Ancho de pantalla del dispositivo
     */
    protected int anchoPantalla;

    /**
     * Contexto
     */
    protected Context context;

    /**
     * Estilo de texto beige
     */
    protected TextPaint tpBeige;

    /**
     * Estilo de texto naranja
     */
    protected TextPaint tpNaranja;

    /**
     * Estilo de fuente
     */
    protected Typeface face;

    /**
     * Estilo para botón naranja
     */
    public Paint pBotonNaranja;

    /**
     * Estilo para botón beige
     */
    public Paint pBotonBeige;

    /**
     * Fondo del menú principal y sus opciones y de las pantallas de fin de partida
     */
    public Bitmap fondoMenu;

    /**
     * Fondo negro para las pantallas de las opciones del menú
     */
    public RectF rectFondo;

    /**
     * Estilo del recuadro del fondo negro
     */
    public Paint pFondo;


    /**
     * Crea una pantalla a partir de unas dimensiones ancho y alto y de un número identificativo.
     * También crea el paint de los botones naranja y beige, el tipo de fuente y los estilo del texto
     * que se usarán en la clase.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número que identifica la pantalla
     */
    public Pantalla(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.context = context;
        this.numPantalla = numPantalla;

        pBotonNaranja = new Paint();
        pBotonNaranja.setColor(context.getResources().getColor(R.color.naranjaClaro));
        pBotonNaranja.setAlpha(225);
        pBotonNaranja.setStyle(Paint.Style.FILL);

        pBotonBeige = new Paint();
        pBotonBeige.setColor(context.getResources().getColor(R.color.beigeClaro));
        pBotonBeige.setAlpha(250);

        face=Typeface.createFromAsset(context.getAssets(),"fonts/FtyStrategycideNcv-elGl.ttf");

        tpBeige = new TextPaint();
        tpBeige.setTextSize(altoPantalla/10);
        tpBeige.setColor(pBotonBeige.getColor());
        tpBeige.setAlpha(250);
        tpBeige.setTypeface(face);
        tpBeige.setTextAlign(Paint.Align.CENTER);

        tpNaranja = new TextPaint();
        tpNaranja.setTextSize(altoPantalla/10);
        tpNaranja.setColor(pBotonNaranja.getColor());
        tpNaranja.setAlpha(225);
        tpNaranja.setTypeface(face);
        tpNaranja.setTextAlign(Paint.Align.CENTER);

        this.fondoMenu = Pantalla.getBitmapFromAssets(context, "menu/fondoMenu.jpg");
        this.fondoMenu = Bitmap.createScaledBitmap(fondoMenu, anchoPantalla, altoPantalla, true);

        rectFondo = new RectF(anchoPantalla/32 * 3, altoPantalla / 16 * 0.5f,
                anchoPantalla/32 * 29, altoPantalla/16*15.5f);
        pFondo = new Paint();
        pFondo.setColor(context.getResources().getColor(R.color.rectFondo));
        pFondo.setAlpha(150);
    }

    /**
     * Establece el tono de los colores de los botones y del texto dependiendo del nivel de luminosidad.
     * Ante fallo, se asignan los tonos claros.
     * @param c lienzo
     */
    public void dibuja(Canvas c){
        try{
            pBotonNaranja.setColor(context.getResources().getColor(MainActivity.luz > 80? R.color.naranjaClaro : R.color.naranjaOscuro));
            pBotonBeige.setColor(context.getResources().getColor(MainActivity.luz > 80? R.color.beigeClaro: R.color.beigeOscuro));
            tpBeige.setColor(pBotonBeige.getColor());
            tpNaranja.setColor(pBotonNaranja.getColor());
        }catch(Exception e){
            pBotonNaranja.setColor(context.getResources().getColor(R.color.naranjaClaro));
            pBotonBeige.setColor(context.getResources().getColor(R.color.beigeClaro));
            tpBeige.setColor(pBotonBeige.getColor());
            tpNaranja.setColor(pBotonNaranja.getColor());
        }
    }

    /**
     * Actualiza el movimiento en las escenas.
     */
    public void actualizaFisica(){
    }

    /**
     * Gestiona las pulsaciones.
     * @param event evento
     * @return -1 por defecto
     */
    public int onTouchEvent(MotionEvent event){
        return -1;
    }

    /**
     * Crea un bitmap con un ancho y un alto determinados a partir de una imagen.
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

