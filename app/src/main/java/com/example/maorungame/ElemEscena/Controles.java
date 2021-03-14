package com.example.maorungame.ElemEscena;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.maorungame.Pantalla;

public class Controles {

    /**
     * Contexto
     */
    Context context;

    /**
     * Ancho de pantalla del dispositivo
     */
    int anchoPantalla;

    /**
     * Alto de pantalla del dispositivo
     */
    int altoPantalla;

    /**
     * Imágenes de los botones de controles
     */
    Bitmap[] bitmapControles;

    /**
     * Botón para mover a la derecha
     */
    public RectF der;

    /**
     * Botón para mover a la izquierda
     */
    public RectF izq;

    /**
     * Botón para mover hacia arriba
     */
    public RectF arriba;

    /**
     * Botón para mover hacia abajo
     */
    public RectF abajo;

    /**
     * Estilo de los botones de controles
     */
    Paint pControles;

    /**
     * Llama a la función que crea los rect de los botones de controles y a la función que escala las
     * imágenes de los mismos. Para ello toma las dimensiones de ancho y alto de pantalla pasadas como
     * parámetro. Establece el paint de los controles.
     * @param context contexto
     * @param anchoPantalla ancho de pantalla
     * @param altoPantalla alto de pantalla
     */
    public Controles(Context context, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        escalaControles();
        pControles = new Paint();
        pControles.setAlpha(200);
        setRectControles();
    }

    /**
     * Crea los rect que representan los botones de los controles.
     */
    public void setRectControles(){
        abajo = new RectF(anchoPantalla / 32 * 24 , altoPantalla / 16 * 13, anchoPantalla / 32 * 27 , altoPantalla);
        izq = new RectF(anchoPantalla / 32 , altoPantalla / 16 * 13, anchoPantalla / 32 * 4, altoPantalla);
        der = new RectF(anchoPantalla / 32 * 5, altoPantalla / 16 * 13, anchoPantalla / 32 * 8, altoPantalla);
        arriba = new RectF(anchoPantalla / 32 * 28, altoPantalla / 16 * 13, anchoPantalla/32 * 31, altoPantalla);
    }

    /**
     * Dibuja las imágenes asociadas a cada botón de los controles.
     * @param c lienzo
     */
    public void dibujaControles(Canvas c) {
        c.drawBitmap(bitmapControles[0], anchoPantalla/32 * 24, altoPantalla / 16 * 13,pControles);
        c.drawBitmap(bitmapControles[1], anchoPantalla / 32, altoPantalla / 16 * 13, pControles);
        c.drawBitmap(bitmapControles[2], anchoPantalla / 32 * 5, altoPantalla / 16 * 13, pControles);
        c.drawBitmap(bitmapControles[3], anchoPantalla / 32 * 28, altoPantalla / 16 * 13, pControles);
    }

    /**
     * Escala las imágenes correspondientes a cada botón de controles.
     */
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
