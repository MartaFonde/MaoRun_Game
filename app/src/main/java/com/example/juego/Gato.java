package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class Gato {
    private Bitmap imagenes; // Bitmap con todas las imágenes
    public PointF posicion;
    private int anchoImagenes; //Ancho del bitmap
    private int altoImagenes; //alto del bitmap
    public int fila = 2, col = 1; //Fila y columna de la imagen a representar
    Bitmap imagen;
    Random g;
    RectF rectangulo;
    int anchoImagen;
    int altoImagen;
    Bitmap[][] img = new Bitmap[4][3];
    boolean puedeMoverse = true;
    int velocidad = 30;

    RectF posicionFutura;

    public Gato(Bitmap imagenes, float x, float y) {
        this.imagenes = imagenes;
        this.posicion = new PointF(x, y);

        anchoImagenes = imagenes.getWidth();
        altoImagenes = imagenes.getHeight();

        anchoImagen = anchoImagenes/3;
        altoImagen = altoImagenes /4;

        for (int i = 0; i < img.length; i++) {     
            for (int j = 0; j < img[i].length; j++) {
                img[i][j] = Bitmap.createBitmap(imagenes, (anchoImagenes/3)*j,(altoImagenes / 4)*i, anchoImagen,altoImagen);
            }
        }

        this.imagen = Bitmap.createBitmap(imagenes, anchoImagenes/3,(altoImagenes / 4)*3, anchoImagen,altoImagen);
        g = new Random();
        this.setRectangulo();
    }

    public void setRectangulo() {
        float x = posicion.x;
        float y = posicion.y;
        rectangulo = new RectF(x+anchoImagen*0.3f, y+altoImagen*0.6f,  x+anchoImagen*0.7f, y+altoImagen);
        //posicionFutura = new RectF(x+anchoImagen*0.3f, y+altoImagen*0.6f,  x+anchoImagen*0.7f, y+altoImagen);

    }

    public void moverAbajo(int anchoPantalla, int altoPantalla, int velocidad) { // Mueve la nave a la izquierda
        fila = 0;
        velocidad = anchoPantalla / 72;
        if (puedeMoverse && posicion.y<= altoPantalla-imagen.getHeight()) {
            posicion.y += velocidad;
            this.setRectangulo();
        }
        actualizaImagen();
    }

    public void moverIzquierda(int anchoPantalla, int altoPantalla, int velocidad) { // Mueve la nave a la izquierda
        fila = 1;
        velocidad = anchoPantalla / 72;

        if (puedeMoverse && posicion.x >= 0) {
            posicion.x -= velocidad;
            this.setRectangulo();
        }
        actualizaImagen();
    }

    public void moverDerecha(int anchoPantalla, int altoPantalla,int velocidad) { // Mueve la nave a la derecha
        fila = 2;
        velocidad = anchoPantalla / 72;

        if (puedeMoverse &&  posicion.x <= anchoPantalla-imagen.getWidth()) {
            posicion.x += velocidad;
            this.setRectangulo();
        }
        actualizaImagen();
    }

    public void moverArriba(int anchoPantalla, int altoPantalla,int velocidad) { // Mueve la nave a la izquierda
        fila = 3;
        velocidad = anchoPantalla / 72;

        if (puedeMoverse && posicion.y >= -40) {
            posicion.y -= velocidad;
            this.setRectangulo();
        }else{
            posicionFutura = rectangulo;
        }
        actualizaImagen();
    }

    public RectF getPosicionFutura(int mov){
        float posX = posicion.x;
        float posY= posicion.y;

        switch (mov){
            case 0: posY += velocidad;
            case 1: posX -= velocidad;
            case 2: posX += velocidad;
            case 3: posY -= velocidad;
        }
        PointF pos= new PointF(posX, posY);
        posicionFutura = new RectF(posX+anchoImagen*0.3f, posY+altoImagen*0.6f,  posX+anchoImagen*0.7f, posY+altoImagen);
        //Log.i("pos", posicion.x +", "+posicion.y);
        //Log.i("posFutura", pos.x +", "+pos.y);
        return posicionFutura;
    }

    public void actualizaImagen() {
        if(col < img[fila].length){
            this.imagen = img[fila][col];
            col++;
        }else{
            col = 0;
        }
    }

    public void parado(){
        this.imagen = img[fila][1];
    }


//    public void actualizarFisicaGato(){
//        switch (gato.fila){
//            case 0: gato.moverAbajo(anchoPantalla, altoPantalla, velGato);
//            break;
//            case 1: gato.moverIzquierda(anchoPantalla, altoPantalla, velGato);
//            break;
//            case 2: gato.moverDerecha(anchoPantalla, altoPantalla, velGato);
//            break;
//            case 3: gato.moverArriba(anchoPantalla, altoPantalla, velGato);
//            break;
//        }
//    }
}
