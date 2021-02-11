package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.io.IOException;
import java.io.InputStream;

public class Trafico {
    Coche[] coches;

    Bitmap[] imgCochesLeft;
    Bitmap[] imgCochesRight;

    PointF[] posicionesCoches;

    Bitmap bitmapCoche;
    int anchoPantalla;
    int altoPantalla;

    int anchoCoche;
    int altoCoche;
    float velocidadCoche;

    Paint p;

    public Trafico(Bitmap[] imgCochesRight, Bitmap[] imgCochesLeft, float velocidadCoche, int anchoPantalla, int altoPantalla) {
        coches = new Coche[16];

        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        this.anchoCoche = anchoPantalla/32;
        this.altoCoche = altoPantalla/16;

        this.velocidadCoche = velocidadCoche;

        setImgCochesRight(imgCochesRight);
        setImgCochesLeft(imgCochesLeft);

        posicionesCoches = new PointF[16];

        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setAlpha(150);

        setCoches(0, velocidadCoche);
    }

    public PointF[] getPosicionesCoches() {
        return posicionesCoches;
    }

    public void setCoches(int numFondo, float vel) {
        if(numFondo == 0){
            coches[0] = new Coche(imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 2, altoPantalla / 16 * 11, vel);
            coches[1] = new Coche(imgCochesLeft[(int)(Math.random()*5)], anchoPantalla / 5, altoPantalla / 16 * 10, vel);
            coches[2] = new Coche(imgCochesRight[(int)(Math.random()*5)], -anchoCoche, altoPantalla / 16 * 11, vel);
            coches[3] = new Coche(imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 10, vel);

            coches[4] = new Coche(imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 7, altoPantalla / 16 * 8, vel);
            coches[5] = new Coche(imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 7, vel);
            coches[6] = new Coche(imgCochesRight[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 8, vel);
            coches[7] = new Coche(imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 7, vel);

            coches[8] = new Coche(imgCochesRight[(int)(Math.random()*5)], -anchoCoche, altoPantalla / 16 * 4, vel);
            coches[9] = new Coche(imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 3, vel);
            coches[10] = new Coche(imgCochesRight[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 4, vel);
            coches[11] = new Coche(imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/3, altoPantalla / 16 * 3, vel);

            coches[12] = new Coche(imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 16, altoPantalla / 16 * 2, vel);
            coches[13] = new Coche(imgCochesLeft[(int)(Math.random()*5)], -anchoCoche, altoPantalla / 16 * 1, vel);
            coches[14] = new Coche(imgCochesRight[(int)(Math.random()*5)], anchoPantalla/3, altoPantalla / 16 * 2, vel);
            coches[15] = new Coche(imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 1, vel);

        }else if(numFondo == 1){
            coches[8].setY(altoPantalla / 16 * 6);
            coches[9].setY(altoPantalla /16 * 5);
            coches[10].setY(altoPantalla / 16 * 6);
            coches[11].setY(altoPantalla / 16 * 5);

            coches[12].setY(altoPantalla /16 *3);
            coches[13].setY(altoPantalla / 16 * 2);
            coches[14].setY(altoPantalla/16 * 3);
            coches[15].setY(altoPantalla/16 * 2);

            for (int i = 0; i < coches.length; i++) {
                if(i % 2 == 0){
                    coches[i].setImagen(imgCochesRight[(int)(Math.random()*5)]);
                }else{
                    coches[i].setImagen(imgCochesLeft[(int)(Math.random()*5)]);
                }
                coches[i].setVelocidad(vel);
            }

        } else if(numFondo == 2){
            coches[4].setY(altoPantalla / 16 * 9);
            coches[5].setY(altoPantalla / 16 * 8);
            coches[6].setY(altoPantalla / 16 * 9);
            coches[7].setY(altoPantalla / 16 * 8);

            coches[8].setY(altoPantalla / 16 * 5);
            coches[9].setY(altoPantalla /16 * 4);
            coches[10].setY(altoPantalla / 16 * 5);
            coches[11].setY(altoPantalla / 16 * 4);

            for (int i = 0; i < coches.length; i++) {
                if(i % 2 == 0){
                    coches[i].setImagen(imgCochesRight[(int)(Math.random()*5)]);
                }else{
                    coches[i].setImagen(imgCochesLeft[(int)(Math.random()*5)]);
                }
                coches[i].setVelocidad(vel);
            }
        }
    }

    public Bitmap[] getImgCochesLeft() {
        return imgCochesLeft;
    }

    public void setImgCochesLeft(Bitmap[] imgCochesLeft) {
        this.imgCochesLeft = imgCochesLeft;
    }

    public Bitmap[] getImgCochesRight() {
        return imgCochesRight;
    }

    public void setImgCochesRight(Bitmap[] imgCochesRight) {
        this.imgCochesRight = imgCochesRight;
    }

    public void setCoches() {
        coches[0] = new Coche(getImgCochesRight()[(int)(Math.random()*5)], anchoPantalla / 2, altoPantalla / 16 * 11, velocidadCoche);
        coches[1] = new Coche(getImgCochesLeft()[(int)(Math.random()*5)], anchoPantalla / 5, altoPantalla / 16 * 10, velocidadCoche);
        coches[2] = new Coche(getImgCochesRight()[(int)(Math.random()*5)], anchoPantalla / 7, altoPantalla / 16 * 8, velocidadCoche);
        coches[3] = new Coche(getImgCochesLeft()[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 7, velocidadCoche);
        coches[4] = new Coche(getImgCochesRight()[(int)(Math.random()*5)], -anchoCoche, altoPantalla / 16 * 4, velocidadCoche);
        coches[5] = new Coche(getImgCochesLeft()[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 3, velocidadCoche);
        coches[6] = new Coche(getImgCochesRight()[4], anchoPantalla / 16, altoPantalla / 16 * 2, velocidadCoche);
        coches[7] = new Coche(getImgCochesLeft()[1], -anchoCoche, altoPantalla / 16 * 1, velocidadCoche);
        coches[8] = new Coche(getImgCochesRight()[3], -anchoCoche, altoPantalla / 16 * 11, velocidadCoche);
        coches[9] = new Coche(getImgCochesLeft()[2], anchoPantalla, altoPantalla / 16 * 10, velocidadCoche);
        coches[10] = new Coche(getImgCochesRight()[4], anchoPantalla, altoPantalla / 16 * 8, velocidadCoche);
        coches[11] = new Coche(getImgCochesLeft()[2], anchoPantalla/2, altoPantalla / 16 * 7, velocidadCoche);
        coches[12] = new Coche(getImgCochesRight()[3], anchoPantalla/2, altoPantalla / 16 * 4, velocidadCoche);
        coches[13] = new Coche(getImgCochesLeft()[0], anchoPantalla/3, altoPantalla / 16 * 3, velocidadCoche);
        coches[14] = new Coche(getImgCochesRight()[4], anchoPantalla/3, altoPantalla / 16 * 2, velocidadCoche);
        coches[15] = new Coche(getImgCochesLeft()[1], anchoPantalla/2, altoPantalla / 16 * 1, velocidadCoche);
    }

    public Coche[] getCoches() {
        return coches;
    }

    public void dibujaCoches(Canvas c){
        for (Coche coche : getCoches()) {
            c.drawBitmap(coche.imagen, coche.posicion.x, coche.posicion.y, null);
            c.drawRect(coche.rectangulo,p);
        }
    }

}
