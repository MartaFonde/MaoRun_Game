package com.example.maorungame.ElemEscena;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.example.maorungame.Pantalla;

import java.util.ArrayList;

public class Moneda {

    /**
     * Ancho de pantalla del dispositivo
     */
    int anchoPantalla;

    /**
     * Alto de pantalla del dispositivo
     */
    int altoPantalla;

    /**
     * Ancho de la moneda
     */
    int anchoMoneda;

    /**
     * Alto de la moneda
     */
    int altoMoneda;

    /**
     * Imagen que contiene todos los sprites de moneda
     */
    Bitmap imagenMoneda;

    /**
     * Array con los sprites de moneda
     */
    Bitmap[] monedas;

    /**
     * Columna del array de los sprites
     */
    int col = 0;

    /**
     * Contador que calcula cuando la imagen de la moneda se tiene que actualizar
     */
    int cont;

    /**
     * Imagen actual de la moneda
     */
    Bitmap monedaActual;

    /**
     * Posiciones de las monedas en la escena
     */
    ArrayList<PointF> posicionMonedas;

    /**
     * Número total de monedas que se crearán en la escena
     */
    int totalMonedas = 10;

    /**
     * Colección de la superficie que ocupa cada una de las monedas de la escena
     */
    public ArrayList<RectF> monedasRect;

    /**
     * Crea el array de los sprites animados de la moneda a partir de un ancho y alto de pantalla
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public Moneda(Context context, int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        posicionMonedas = new ArrayList<>();
        this.anchoMoneda = anchoPantalla/32;
        this.altoMoneda = altoPantalla/16;
        this.imagenMoneda = Pantalla.escala(context, "moneda/moneda.png" , anchoMoneda*5, altoMoneda);
        setBitmapMoneda(imagenMoneda);
        monedasRect = new ArrayList<>();
    }

    /**
     * Crea el array de imágenes de moneda a partir de la imagen que las contiene todas para crear
     * la animación, y asigna a monedaActual la imagen en la que la moneda está estática.
     * @param conjuntoMonedas imagen que contiene todas los sprites de moneda
     */
    public void setBitmapMoneda(Bitmap conjuntoMonedas){
        monedas = new Bitmap[5];
        for (int i = 0; i < monedas.length; i++) {
            Log.i("aaa2",  conjuntoMonedas.getWidth()/5+ " - "+conjuntoMonedas.getWidth());
            monedas[i] = Bitmap.createBitmap(conjuntoMonedas, conjuntoMonedas.getWidth()/5*i,
                    0, conjuntoMonedas.getWidth()/5, altoMoneda);
        }
        this.monedaActual = monedas[0];
    }

    /**
     * Si la escena no está en pause, actualiza el valor de monedaActual según la animación y recrea
     * y reasigna los rect de monedas según sus coordenadas left, top y sus medidas.
     * Dibuja la imagen monedaActual en cada una de las posiciones de monedasRect
     * @param c lienzo
     */
    public void dibujaMonedas(Canvas c, boolean pause){
        if(!pause){
            if(++cont % 10 == 0){
                monedaActual = actualizaImagenMoneda();     //bitmap de la animacion de moneda que se va a mostrar
            }
        }
        for (int i = 0; i < monedasRect.size(); i++) {
            monedasRect.set(i, new RectF(monedasRect.get(i).left, monedasRect.get(i).top,
                    monedasRect.get(i).left+anchoMoneda, monedasRect.get(i).top + altoMoneda));
            c.drawBitmap(monedaActual, monedasRect.get(i).left, monedasRect.get(i).top, null);
        }
    }

    /**
     * Actualiza la imagen de moneda apartir del array de imágenes de moneda.
     * @return imagen actual de moneda
     */
    public Bitmap actualizaImagenMoneda() {
        if(col < monedas.length){
            this.imagenMoneda = monedas[col];
            col++;
        }else{
            col = 0;
        }
        return imagenMoneda;
    }

    /**
     * Sitúa las monedas en pantalla. Comprueba que la posición no interseque ni estea contenida en
     * ningún rect de Arboles para que el gato pueda colisionar con ella.
     * Hace la misma comprobación con respecto a los rect de monedas, para que no se solapen.
     * Si la posición cumple las condiciones, se añade el rect con esas posiciones a la coleccion
     * monedasRect.
     * Para que las monedas no se situen muy en los extremos de la pantalla se limita la superficie
     * en la que pueden aparecer las monedas en con respecto a ancho y alto pantalla.
     * Se calculan posiciones hasta que el tamaño de la colección monedasRect se corresponda con
     * el valor de la variable totalMonedas.
     */
    public void setPosicionMonedas(RectF[] arbolesRect){
        float x;
        float y;

        while(monedasRect.size() < totalMonedas) {
            x = (float)Math.random()*(anchoPantalla-anchoMoneda*4)+anchoMoneda*2;
            y = (float)Math.random()*(altoPantalla-altoMoneda*4)+altoMoneda*2;
            boolean posValida = true;

            for (int i = 0; i < arbolesRect.length; i++) {
                if(new RectF(arbolesRect[i].left, arbolesRect[i].top, arbolesRect[i].right,
                        arbolesRect[i].bottom).contains(new RectF(x, y, x+anchoMoneda, y+altoMoneda))
                        || new RectF(arbolesRect[i].left, arbolesRect[i].top, arbolesRect[i].right,
                        arbolesRect[i].bottom).intersect(new RectF(x, y, x+anchoMoneda, y+altoMoneda))) {
                    posValida = false;
                    break;
                }
            }

            if(posValida){
                for (int i = 0; i < monedasRect.size(); i++) {
                    if(new RectF(monedasRect.get(i).left, monedasRect.get(i).top, monedasRect.get(i).right,
                            monedasRect.get(i).bottom).intersect(new RectF(x, y, x+anchoMoneda, y+altoMoneda))
                            || new RectF(monedasRect.get(i).left, monedasRect.get(i).top, monedasRect.get(i).right,
                            monedasRect.get(i).bottom).contains(new RectF(x, y, x+anchoMoneda, y+altoMoneda))){
                        posValida = false;
                        break;
                    }
                }
            }

            if(posValida){
                monedasRect.add(new RectF(x, y, x+anchoMoneda, y+altoMoneda));
            }
        }
    }
}
