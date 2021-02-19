package com.example.juego.ElemEscena;

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
    float velocidad;

    public Coche(Bitmap imagen, float x, float y, float velocidad) {
        setImagen(imagen);
        this.posicion = new PointF(x, y);
        setX(x);
        setY(y);
        this.anchoCoche = imagen.getWidth();
        this.altoCoche = imagen.getHeight();
        setVelocidad(velocidad);
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

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
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
