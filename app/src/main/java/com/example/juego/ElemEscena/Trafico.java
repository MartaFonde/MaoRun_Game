package com.example.juego.ElemEscena;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.juego.ElemEscena.Coche;
import com.example.juego.Pantalla;

public class Trafico {
    Context context;
    public Coche[] coches;

    public Bitmap[] imgCochesRight;
    public Bitmap[] imgCochesLeft;

    int anchoPantalla;
    int altoPantalla;
    public int anchoCoche;
    public int altoCoche;

    public Trafico(Context context, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        this.anchoCoche = anchoPantalla/32;
        this.altoCoche = altoPantalla/16;

        coches = new Coche[16];

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
     * Dibuja las imágenes del array coches en sus respectivas posiciones. El array coches se
     * instancia en cada clase Escenario, ya que sus posiciones varían dependiendo del Escenario
     * @param c lienzo
     */
    public void dibujaCoches(Canvas c){
        for (Coche coche : coches) {
            c.drawBitmap(coche.imagen, coche.getX(), coche.getY(), null);
            //c.drawRect(coche.rectangulo,p);
        }
    }
}
