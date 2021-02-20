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

    /**
     * Devuelve la propiedad x del coche, que representa su coordenada x
     * @return coordenada x
     */
    public float getX() {
        return x;
    }

    /**
     * Establece la propiedad x del coche, que representa su coordenada x,
     * y actualiza el rect, que utiliza este valor en su definición
     * @param x coordenada x
     */
    public void setX(float x) {
        this.x = x;
        posicion.x = x;
        setRectangulo();
    }

    /**
     * Devuelve la propiedad y del coche, que representa su coordenada y
     * @return propiedad y
     */
    public float getY() {
        return y;
    }

    /**
     * Establece la propiedad y del coche, que representa su coordenada y,
     * y actualiza el rect, que utiliza este valor en su definición
     * @param y coordenada y
     */
    public void setY(float y) {
        this.y = y;
        posicion.y = y;
        setRectangulo();
    }

    /**
     * Devuelve la propiedad velocidad del coche
     * @return velocidad
     */
    public float getVelocidad() {
        return velocidad;
    }

    /**
     * Establece la propiedad velocidad del coche
     * @param velocidad
     */
    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Devuelve la propiedad imagen del coche
     * @return imagen
     */
    public Bitmap getImagen() {
        return imagen;
    }

    /**
     * Establece la propiedad imagen del coche
     * @param imagen
     */
    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    /**
     * Establece el rect del coche a partir de su posición
     */
    public void setRectangulo() {
        rectangulo = new RectF(posicion.x, posicion.y+ altoCoche *0.3f, posicion.x+ anchoCoche, posicion.y+ altoCoche);
    }

    /**
     * Incrementa la propiedad x en velocidad, si x es menor que el parámetro anchoPantalla.
     * En caso contrario, el valor de x pasa a ser el negativo del ancho de la propiedad imagen.
     * @param anchoPantalla ancho de la pantalla
     */
    public void moverDerecha(int anchoPantalla) {
        if (posicion.x <= anchoPantalla) {
            setX(posicion.x + velocidad);
        }else{
            setX(-imagen.getWidth());
        }
    }

    /**
     * Decrementa propiedad x en velocidad, si x es mayor o igual que 0.
     * En caso contrario, x será igual al parámetro ancho pantalla.
     * @param anchoPantalla ancho de la pantalla
     */
    public void moverIzquierda(int anchoPantalla) {
        if (posicion.x>= 0) {
            setX(posicion.x - velocidad);
        }else{
            setX(anchoPantalla);
        }
    }

    /**
     * Devuelve la propiedad posición de coche
     * @return posición
     */
    public PointF getPosicion() {
        return posicion;
    }

    /**
     * Establece propiedad posición de coche
     * @param posicion
     */
    public void setPosicion(PointF posicion) {
        this.posicion = posicion;
    }
}
