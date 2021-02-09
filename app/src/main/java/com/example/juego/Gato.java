package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

public class Gato {
    private Bitmap imagenes; // Bitmap con todas las imágenes
    public PointF posicion;
    private int anchoImagenes; //Ancho del bitmap
    private int altoImagenes; //alto del bitmap
    public int fila = 2;
    int col = 1; //Fila y columna de la imagen a representar
    Bitmap imagen;      //imagen actual del gato
    RectF rectangulo;
    int anchoImagen;
    int altoImagen;

    Bitmap[][] imgGato;
    boolean puedeMoverse = true;
    int velocidad;
    Paint p;
    Paint p2;

    RectF posicionFutura;

    public Gato(Bitmap imagenes, float x, float y, int velocidad) {
        this.imagenes = imagenes;
        this.posicion = new PointF(x, y);

        this.velocidad = velocidad;

        anchoImagenes = imagenes.getWidth();
        altoImagenes = imagenes.getHeight();

        anchoImagen = anchoImagenes/3;
        altoImagen = altoImagenes /4;

        imgGato = new Bitmap[4][3];
        setImgGato(imagenes);

        //por defecto estará direccion arriba:
        this.imagen = Bitmap.createBitmap(imagenes, anchoImagenes/3,(altoImagenes / 4)*3, anchoImagen,altoImagen);
        posicionFutura = new RectF(x+anchoImagen*0.3f, y+altoImagen-velocidad*0.6f,  x+anchoImagen*0.7f, y+altoImagen);

        this.setRectangulo();

        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setAlpha(150);

        p2 = new Paint();
        p2.setColor(Color.BLUE);
        p2.setStyle(Paint.Style.STROKE);
        p2.setStrokeWidth(5);
        p2.setAlpha(150);
    }

    public Bitmap[][] getImgGato() {
        return imgGato;
    }

    public void setImgGato(Bitmap img) {
        for (int i = 0; i < imgGato.length; i++) {
            for (int j = 0; j < imgGato[i].length; j++) {
                imgGato[i][j] = Bitmap.createBitmap(img, (anchoImagenes/3)*j,(altoImagenes / 4)*i, anchoImagen,altoImagen);
            }
        }
    }

    public void setRectangulo() {
        float x = posicion.x;
        float y = posicion.y;
        rectangulo = new RectF(x+anchoImagen*0.3f, y+altoImagen*0.6f,  x+anchoImagen*0.7f, y+altoImagen);
        posicionFutura = getPosicionFutura(Pantalla.mov);
        //posicionFutura = new RectF(x+anchoImagen*0.3f, y+altoImagen-velocidad*0.6f,  x+anchoImagen*0.7f, y+altoImagen);

    }

    public void moverAbajo(int anchoPantalla, int altoPantalla) { // Mueve la nave a la izquierda
        fila = 0;
        if (puedeMoverse && posicion.y<= altoPantalla-imagen.getHeight()) {
            posicion.y += velocidad;
            this.setRectangulo();
        }
        actualizaImagen();
    }

    public void moverIzquierda(int anchoPantalla, int altoPantalla) { // Mueve la nave a la izquierda
        fila = 1;
        if (puedeMoverse && posicion.x >= 0) {
            posicion.x -= velocidad;
            this.setRectangulo();
        }
        actualizaImagen();
    }

    public void moverDerecha(int anchoPantalla, int altoPantalla) { // Mueve la nave a la derecha
        fila = 2;

        if (puedeMoverse &&  posicion.x <= anchoPantalla-imagen.getWidth()) {
            posicion.x += velocidad;
            this.setRectangulo();
        }
        actualizaImagen();
    }

    public void moverArriba(int anchoPantalla, int altoPantalla) { // Mueve la nave a la izquierda
        fila = 3;

        if (puedeMoverse && posicion.y >= -40) {
            posicion.y -= velocidad;
            this.setRectangulo();
        }
        actualizaImagen();
    }

    public RectF getPosicionFutura(int mov){
        float posX = posicion.x;
        float posY= posicion.y;

        switch (mov){
            case 0: posY += velocidad;
                    break;
            case 1: posX -= velocidad;
                    break;
            case 2: posX += velocidad;
                    break;
            case 3: posY -= velocidad;
        }
        PointF pos= new PointF(posX, posY);
        posicionFutura = new RectF(posX+anchoImagen*0.3f, posY+altoImagen*0.6f,  posX+anchoImagen*0.7f, posY+altoImagen);
        //Log.i("pos", posicion.x +", "+posicion.y);
        //Log.i("posFutura", pos.x +", "+pos.y);
        return posicionFutura;
    }

    public void actualizaImagen() {
        if(col < imgGato[fila].length){
            this.imagen = imgGato[fila][col];
            col++;
        }else{
            col = 0;
        }
    }

    public void parado(){
        this.imagen = imgGato[fila][1];
    }

    public void dibujaGato(Canvas c){
        c.drawBitmap(imagen, posicion.x, posicion.y, null);
        c.drawRect(rectangulo, p);
        c.drawRect(posicionFutura, p2);
    }
}
