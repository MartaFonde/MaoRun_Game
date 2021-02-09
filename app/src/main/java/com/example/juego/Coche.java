package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

public class Coche {
    public PointF posicion;
    public Bitmap imagen;
    public RectF rectangulo;
    int anchoCoche;
    int altoCoche;
    int velocidad;

    public Coche(Bitmap imagen, float x, float y, int velocidad) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        this.anchoCoche = imagen.getWidth();
        this.altoCoche = imagen.getHeight();
        this.velocidad = velocidad;
        this.setRectangulo();
    }

    public void setRectangulo() {
        float x = posicion.x;
        float y = posicion.y;
        //rectangulo = new Rect((int)x+ancho/6, (int)(y+alto/2.8), (int)(x+ancho/1.2), (int)(y+alto/1.5));
        rectangulo = new RectF(x, y+ altoCoche *0.3f, x+ anchoCoche, y+ altoCoche);
    }

    public void moverDerecha(int anchoPantalla) {
        if (posicion.x <= anchoPantalla) {
            posicion.x += velocidad;
            this.setRectangulo();
        }else{
            posicion.x = -imagen.getWidth();
        }
    }
    public void moverIzquierda(int anchoPantalla) {
        if (posicion.x>= 0) {
            posicion.x -= velocidad;
            this.setRectangulo();
        }else{
            posicion.x = anchoPantalla;
        }
    }

    public PointF getPosicion() {
        return posicion;
    }

    public void setPosicion(PointF posicion) {
        this.posicion = posicion;
    }


}
