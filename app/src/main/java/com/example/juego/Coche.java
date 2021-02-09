package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

public class Coche {
    public PointF posicion;
    float x;
    float y;
    public Bitmap imagen;
    public RectF rectangulo;
    int anchoCoche;
    int altoCoche;
    int velocidad;

    public Coche(Bitmap imagen, float x, float y, int velocidad) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        setX(x);
        setY(y);
        this.anchoCoche = imagen.getWidth();
        this.altoCoche = imagen.getHeight();
        this.velocidad = velocidad;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        posicion.x = x;
        setRectangulo();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        posicion.y = y;
        setRectangulo();
    }

    public void setRectangulo() {
        rectangulo = new RectF(posicion.x, posicion.y+ altoCoche *0.3f, posicion.x+ anchoCoche, posicion.y+ altoCoche);
    }

    public void moverDerecha(int anchoPantalla) {
        if (posicion.x <= anchoPantalla) {
            setX(posicion.x + velocidad);
        }else{
            setX(-imagen.getWidth());
        }
    }
    public void moverIzquierda(int anchoPantalla) {
        if (posicion.x>= 0) {
            setX(posicion.x - velocidad);
        }else{
            setX(anchoPantalla);
        }
    }

    public PointF getPosicion() {
        return posicion;
    }

    public void setPosicion(PointF posicion) {
        this.posicion = posicion;
    }


}
