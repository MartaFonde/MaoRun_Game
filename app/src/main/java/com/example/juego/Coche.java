package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

public class Coche {

    public PointF posicion;
    public Bitmap imagen;
    private Random g;
    public RectF rectangulo;
    int ancho;
    int alto;

    public Coche(Bitmap imagen, float x, float y) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        g = new Random();
        ancho = imagen.getWidth();
        alto = imagen.getHeight();
        this.setRectangulo();
    }

    //Establece el movimiento de un enemigo en una pantalla definida por alto y ancho y cierta velocidad
    public void moverCoche(int alto,int ancho, int velocidad) {
        //posicion.x += velocidad;
        //moverDerecha(alto, ancho, velocidad);
        moverIzquierda(alto, ancho, velocidad);
        if (posicion.x > ancho) {
            posicion.x = 0;
            posicion.y= g.nextFloat() * (alto - imagen.getHeight());
        }
    }

    public void setRectangulo() {
        float x = posicion.x;
        float y = posicion.y;
        //rectangulo = new Rect((int)x+ancho/6, (int)(y+alto/2.8), (int)(x+ancho/1.2), (int)(y+alto/1.5));
        rectangulo = new RectF(x, y+alto*0.3f, x+ancho, y+alto);
    }

    public void moverDerecha(int ancho, int alto,  int velocidad) { // Mueve la nave a la derecha
        if (posicion.x <= ancho) {
            posicion.x += velocidad;
            this.setRectangulo();
        }else{
            posicion.x = -imagen.getWidth();
        }
    }
    public void moverIzquierda(int ancho, int alto,  int velocidad) { // Mueve la nave a la izquierda
        if (posicion.x>= 0) {
            posicion.x -= velocidad;
            this.setRectangulo();
        }else{
            posicion.x = ancho;
        }
    }

    public PointF getPosicion() {
        return posicion;
    }

    public void setPosicion(PointF posicion) {
        this.posicion = posicion;
    }


}
