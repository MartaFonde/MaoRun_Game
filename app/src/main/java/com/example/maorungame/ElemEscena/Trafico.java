package com.example.maorungame.ElemEscena;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.maorungame.Pantalla;

public class Trafico {

    /**
     * Contexto
     */
    Context context;

    /**
     * Conjunto de coches que circulan en la escena
     */
    public Coche[] coches;

    /**
     * Imágenes de los coches que circulan hacia la derecha
     */
    public Bitmap[] imgCochesRight;

    /**
     * Imágenes de los coches que circulan hacia la izquierda
     */
    public Bitmap[] imgCochesLeft;

    /**
     * Ancho de la pantalla
     */
    int anchoPantalla;

    /**
     * Alto de la pantalla
     */
    int altoPantalla;

    /**
     * Ancho del coche
     */
    public int anchoCoche;

    /**
     * Alto del coche
     */
    public int altoCoche;

    /**
     * Inicializa las dimensiones de las imágenes de coches a partir de un ancho y alto de pantalla.
     * Llama a las funciones encargadas de crear los array de imágenes de coches.
     * @param context contexto
     * @param anchoPantalla ancho de pantalla
     * @param altoPantalla alto de pantalla
     */
    public Trafico(Context context, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.anchoCoche = anchoPantalla/32;
        this.altoCoche = altoPantalla/16;

        setImgCochesRight();
        setImgCochesLeft();
    }

    /**
     * Devuelve array de las imágenes de coches que circulan hacia la izquierda
     * @return imágenes de coches que circulan hacia la izquierda
     */
    public Bitmap[] getImgCochesLeft() {
        return imgCochesLeft;
    }

    /**
     * Escala las imágenes de los coches que circulan hacia la izquierda y crea el array de imágenes
     */
    public void setImgCochesLeft() {
        imgCochesLeft = new Bitmap[5];
        imgCochesLeft[0] = Pantalla.escala(context,"coches/blue_car_left.png", anchoCoche, altoCoche);
        imgCochesLeft[1] = Pantalla.escala(context, "coches/green_car_left.png", anchoCoche, altoCoche);
        imgCochesLeft[2] = Pantalla.escala(context, "coches/red_car_left.png", anchoCoche, altoCoche);
        imgCochesLeft[3] = Pantalla.escala(context,  "coches/white_car_left.png", anchoCoche, altoCoche);
        imgCochesLeft[4] = Pantalla.escala(context, "coches/orange_car_left.png", anchoCoche, altoCoche);
    }

    /**
     * Devuelve array de las imágenes de coches que circulan hacia la derecha
     * @return imágenes de coches que circulan hacia la derecha
     */
    public Bitmap[] getImgCochesRight() {
        return imgCochesRight;
    }

    /**
     * Escala las imágenes de los coches que circulan hacia la derecha e inicializa el array de imágenes
     */
    public void setImgCochesRight() {
        imgCochesRight = new Bitmap[5];
        imgCochesRight[0] = Pantalla.escala(context, "coches/blue_car_right.png", anchoCoche, altoCoche);
        imgCochesRight[1] = Pantalla.escala(context, "coches/green_car_right.png", anchoCoche, altoCoche);
        imgCochesRight[2] = Pantalla.escala(context, "coches/red_car_right.png", anchoCoche, altoCoche);
        imgCochesRight[3] = Pantalla.escala(context, "coches/white_car_right.png", anchoCoche, altoCoche);
        imgCochesRight[4] = Pantalla.escala(context, "coches/orange_car_right.png", anchoCoche, altoCoche);
    }


    /**
     * Dibuja las imágenes del array coches en sus respectivas posiciones.
     * @param c lienzo
     */
    public void dibujaCoches(Canvas c){
        for (Coche coche : coches) {
            c.drawBitmap(coche.imagen, coche.getX(), coche.getY(), null);
        }
    }

    /**
     * Crea el array de coches a partir de las coordenadas y pasadas como parámetro.
     * La imagen del coche será una imagen aleatoria del array de imágenes correspondiente a la
     * dirección del coche.
     * Los índices pares son coches que circulan hacia la derecha, y los impares coches que circulan
     * hacia la izquierda.
     * @param pos posiciones en eje y
     * @param velocidad velocidad de los coches
     */
    public void setCoches(int[] pos, float velocidad){
        coches = new Coche[16];
        coches[0] = new Coche(imgCochesRight[(int)(Math.random()*5)],
                anchoPantalla / 2, altoPantalla / 16 * pos[6], velocidad);
        coches[1] = new Coche(imgCochesLeft[(int)(Math.random()*5)],
                anchoPantalla / 5, altoPantalla / 16 * pos[7], velocidad);
        coches[2] = new Coche(imgCochesRight[(int)(Math.random()*5)],
                -anchoCoche, altoPantalla / 16 * pos[6], velocidad);
        coches[3] = new Coche(imgCochesLeft[(int)(Math.random()*5)],
                anchoPantalla, altoPantalla / 16 * pos[7], velocidad);

        coches[4] = new Coche(imgCochesRight[(int)(Math.random()*5)],
                anchoPantalla / 7, altoPantalla / 16 * pos[4], velocidad);
        coches[5] = new Coche(imgCochesLeft[(int)(Math.random()*5)],
                anchoPantalla, altoPantalla / 16 * pos[5], velocidad);
        coches[6] = new Coche(imgCochesRight[(int)(Math.random()*5)],
                anchoPantalla, altoPantalla / 16 * pos[4], velocidad);
        coches[7] = new Coche(imgCochesLeft[(int)(Math.random()*5)],
                anchoPantalla/2, altoPantalla / 16 * pos[5], velocidad);

        coches[8] = new Coche(imgCochesRight[(int)(Math.random()*5)],
                -anchoCoche, altoPantalla / 16 * pos[2], velocidad);
        coches[9] = new Coche(imgCochesLeft[(int)(Math.random()*5)],
                anchoPantalla, altoPantalla / 16 * pos[3], velocidad);
        coches[10] = new Coche(imgCochesRight[(int)(Math.random()*5)],
                anchoPantalla/2, altoPantalla / 16 * pos[2], velocidad);
        coches[11] = new Coche(imgCochesLeft[(int)(Math.random()*5)],
                anchoPantalla/3, altoPantalla / 16 * pos[3], velocidad);

        coches[12] = new Coche(imgCochesRight[(int)(Math.random()*5)],
                anchoPantalla / 16, altoPantalla / 16 * pos[0], velocidad);
        coches[13] = new Coche(imgCochesLeft[(int)(Math.random()*5)],
                -anchoCoche, altoPantalla / 16 * pos[1], velocidad);
        coches[14] = new Coche(imgCochesRight[(int)(Math.random()*5)],
                anchoPantalla/3, altoPantalla / 16 * pos[0], velocidad);
        coches[15] = new Coche(imgCochesLeft[(int)(Math.random()*5)],
                anchoPantalla/2, altoPantalla / 16 * pos[1], velocidad);

    }
}
