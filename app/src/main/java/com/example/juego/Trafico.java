package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Trafico {
    Coche[] coches;

    Bitmap[] imgCochesLeft;
    Bitmap[] imgCochesRight;

    Bitmap bitmapCoche;
    int anchoPantalla;
    int altoPantalla;

    int anchoCoche;
    int altoCoche;

    public Trafico(Bitmap[] imgCochesRight, Bitmap[] imgCochesLeft, int anchoPantalla, int altoPantalla) {
        coches = new Coche[16];

        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        this.anchoCoche = anchoPantalla/32;
        this.altoCoche = altoPantalla/16;

        setImgCochesRight(imgCochesRight);
        setImgCochesLeft(imgCochesLeft);

        setCoches();
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
        coches[0] = new Coche(getImgCochesRight()[0], anchoPantalla / 2, altoPantalla / 16 * 11);
        coches[1] = new Coche(getImgCochesLeft()[1], anchoPantalla / 5, altoPantalla / 16 * 10);
        coches[2] = new Coche(getImgCochesRight()[2], anchoPantalla / 7, altoPantalla / 16 * 8);
        coches[3] = new Coche(getImgCochesLeft()[3], anchoPantalla, altoPantalla / 16 * 7);
        coches[4] = new Coche(getImgCochesRight()[4], -anchoCoche, altoPantalla / 16 * 4);
        coches[5] = new Coche(getImgCochesLeft()[0], anchoPantalla, altoPantalla / 16 * 3);
        coches[6] = new Coche(getImgCochesRight()[4], anchoPantalla / 16, altoPantalla / 16 * 2);
        coches[7] = new Coche(getImgCochesLeft()[1], -anchoCoche, altoPantalla / 16 * 1);
        coches[8] = new Coche(getImgCochesRight()[3], -anchoCoche, altoPantalla / 16 * 11);
        coches[9] = new Coche(getImgCochesLeft()[2], anchoPantalla, altoPantalla / 16 * 10);
        coches[10] = new Coche(getImgCochesRight()[4], anchoPantalla, altoPantalla / 16 * 8);
        coches[11] = new Coche(getImgCochesLeft()[2], anchoPantalla/2, altoPantalla / 16 * 7);
        coches[12] = new Coche(getImgCochesRight()[3], anchoPantalla/2, altoPantalla / 16 * 4);
        coches[13] = new Coche(getImgCochesLeft()[0], anchoPantalla/3, altoPantalla / 16 * 3);
        coches[14] = new Coche(getImgCochesRight()[4], anchoPantalla/3, altoPantalla / 16 * 2);
        coches[15] = new Coche(getImgCochesLeft()[1], anchoPantalla/2, altoPantalla / 16 * 1);
    }

    public Coche[] getCoches() {
        return coches;
    }

}
