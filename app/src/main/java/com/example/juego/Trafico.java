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
    Context context;
    Coche[] coches;

    Bitmap[] imgCochesRight;
    Bitmap[] imgCochesLeft;

    //PointF[] posicionesCoches;

    //Bitmap bitmapCoche;
    int anchoPantalla;
    int altoPantalla;

    int anchoCoche;
    int altoCoche;

    Paint p;

    public Trafico(Context context, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        this.anchoCoche = anchoPantalla/32;
        this.altoCoche = altoPantalla/16;

        coches = new Coche[16];

        imgCochesRight = new Bitmap[5];
        imgCochesLeft = new Bitmap[5];

        setImgCochesRight();
        setImgCochesLeft();

        //posicionesCoches = new PointF[16];

        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setAlpha(150);

    }

    public Bitmap[] getImgCochesLeft() {
        return imgCochesLeft;
    }

    public void setImgCochesLeft() {
        imgCochesLeft[0] = Pantalla.escala(context,"coches/blue_car_left.png", anchoCoche, altoCoche);
        imgCochesLeft[1] = Pantalla.escala(context, "coches/green_car_left.png", anchoCoche, altoCoche);
        imgCochesLeft[2] = Pantalla.escala(context, "coches/red_car_left.png", anchoCoche, altoCoche);
        imgCochesLeft[3] = Pantalla.escala(context,  "coches/white_car_left.png", anchoCoche, altoCoche);
        imgCochesLeft[4] = Pantalla.escala(context, "coches/orange_car_left.png", anchoCoche, altoCoche);

    }

    public Bitmap[] getImgCochesRight() {
        return imgCochesRight;
    }

    public void setImgCochesRight() {
        imgCochesRight[0] = Pantalla.escala(context, "coches/blue_car_right.png", anchoCoche, altoCoche);
        imgCochesRight[1] = Pantalla.escala(context, "coches/green_car_right.png", anchoCoche, altoCoche);
        imgCochesRight[2] = Pantalla.escala(context, "coches/red_car_right.png", anchoCoche, altoCoche);
        imgCochesRight[3] = Pantalla.escala(context, "coches/white_car_right.png", anchoCoche, altoCoche);
        imgCochesRight[4] = Pantalla.escala(context, "coches/orange_car_right.png", anchoCoche, altoCoche);
    }


    public void dibujaCoches(Canvas c){
        for (Coche coche : coches) {
            c.drawBitmap(coche.imagen, coche.posicion.x, coche.posicion.y, null);
            c.drawRect(coche.rectangulo,p);
        }
    }
}
