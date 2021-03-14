package com.example.maorungame.ElemEscena;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;

import com.example.maorungame.Pantalla;

public class Puntuacion{

    /**
     * Contexto
     */
    Context context;

    /**
     * Ancho de la pantalla
     */
    int anchoPantalla;

    /**
     * Alto de la pantalla
     */
    int altoPantalla;

    /**
     * Fondo sobre el que se dibujan las vidas y la puntuación
     */
    RectF puntuacionRect;

    /**
     * Estilo del fondo sobre el que se dibujan las vidas y la puntuación
     */
    Paint pPuntuacion;

    /**
     * Imagen de una vida
     */
    Bitmap vidaBitmap;

    /**
     * Imagen de monedas
     */
    Bitmap monedasPuntuacionBitmap;

    /**
     * Estilo del número de puntuación
     */
    TextPaint tpPuntos;


    /**
     * Llama a la función que inicializa los elementos de puntuación.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public Puntuacion(Context context, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        setPuntuacion();
    }

    /**
     * Crea el fondo del recuadro puntuación y vidas y el textPaint que se usará para pintar el número
     * de puntuación. Inicializa las imágenes redimensionadas de puntuación y vidas.
     */
    public void setPuntuacion(){
        pPuntuacion = new Paint();
        pPuntuacion.setColor(Color.GRAY);
        pPuntuacion.setStyle(Paint.Style.FILL);
        pPuntuacion.setAlpha(150);

        tpPuntos = new TextPaint();
        tpPuntos.setTextSize(altoPantalla / 20);
        tpPuntos.setColor(Color.BLACK);

        vidaBitmap = Pantalla.escala(context, "gato/heart.png",
                anchoPantalla / 32, altoPantalla / 16);
        monedasPuntuacionBitmap = Pantalla.escala(context, "moneda/monedas_controles.png",
                anchoPantalla / 32, altoPantalla / 16);
    }

    /**
     * Dibuja el rect de fondo de la puntuación, tantos corazones como vidas tenga el gato, el
     * número correspondiente a la puntuación y una imagen de monedas a la derecha del número.
     * @param c lienzo
     */
    public void dibujaPuntuacion(Canvas c, int numVidas, int puntos){
        puntuacionRect = new RectF(anchoPantalla/32*24, 0, anchoPantalla, altoPantalla/16*2.5f);

        c.drawRect(puntuacionRect, pPuntuacion);
        for (int i = 0; i < numVidas; i++) {
            c.drawBitmap(vidaBitmap, anchoPantalla/32 * (31 - i), altoPantalla / 16 * 0.5f, null);
        }
        c.drawText(""+puntos, anchoPantalla/32*27.5f,  altoPantalla / 16 *2.25f, tpPuntos);
        c.drawBitmap(monedasPuntuacionBitmap, anchoPantalla /32 *30, altoPantalla/16 * 1.5f, null);
    }
}
