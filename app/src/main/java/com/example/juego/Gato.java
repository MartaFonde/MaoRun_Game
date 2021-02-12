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
    RectF posicionFutura;
    float x;
    float y;

    private int anchoImagenes; //Ancho del bitmap
    private int altoImagenes; //alto del bitmap
    int anchoImagen;
    int altoImagen;

    public int fila = 2;
    int col = 1; //Fila y columna de la imagen a representar

    Bitmap imagen;      //imagen actual del gato
    RectF rectangulo;

    Bitmap[][] imgGato;
    boolean puedeMoverse = true;
    int velocidad;
    Paint p;
    Paint p2;

    public Gato(Bitmap imagenes, float x, float y, int velocidad) {
        this.imagenes = imagenes;
        this.posicion = new PointF(x, y);
        setX(x);
        setY(y);

        this.velocidad = velocidad;

        this.anchoImagenes = imagenes.getWidth();
        this.altoImagenes = imagenes.getHeight();

        this.anchoImagen = anchoImagenes/3;     //3 columnas
        this.altoImagen = altoImagenes/4; // 4 filas (movimientos)

        imgGato = new Bitmap[4][3];
        setImgGato(imagenes);

        //por defecto estará direccion arriba:
        this.imagen = Bitmap.createBitmap(imagenes, anchoImagenes/3,(altoImagenes / 4)*3, anchoImagen,altoImagen);

      //  posicionFutura = new RectF(x+anchoImagen*0.3f, y+altoImagen-velocidad*0.6f,  x+anchoImagen*0.7f, y+altoImagen);
       // this.setRectangulo();

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
        rectangulo = new RectF(posicion.x + anchoImagen*0.3f, posicion.y + altoImagen*0.7f,
                posicion.x +anchoImagen * 0.7f, posicion.y+altoImagen);
        posicionFutura = getPosicionFutura(Escena.mov);
    }

    public void moverAbajo(int altoPantalla) {
        fila = 0;
        if (puedeMoverse && posicion.y <= altoPantalla-imagen.getHeight()) {
            setY(posicion.y + velocidad);
        }
        actualizaImagen();
    }

    public void moverIzquierda() {
        fila = 1;
        if (puedeMoverse && posicion.x >= 0) {
            setX(posicion.x - velocidad);
        }
        actualizaImagen();
    }

    public void moverDerecha(int anchoPantalla) {
        fila = 2;
        if (puedeMoverse &&  posicion.x <= anchoPantalla-imagen.getWidth()) {
            setX(posicion.x + velocidad);
        }
        actualizaImagen();
    }

    public void moverArriba() {
        fila = 3;
        if (puedeMoverse && posicion.y >= -40) {
            setY(posicion.y - velocidad);
        }
        actualizaImagen();
    }

    public RectF getPosicionFutura(int mov){
        float posFuturaX = posicion.x;
        float posFuturaY= posicion.y;

        switch (mov){
            case 0: posFuturaY += velocidad;
                    break;
            case 1: posFuturaX -= velocidad;
                    break;
            case 2: posFuturaX += velocidad;
                    break;
            case 3: posFuturaY -= velocidad;
        }
        posicionFutura = new RectF(posFuturaX + anchoImagen*0.3f, posFuturaY + altoImagen*0.7f,
                posFuturaX+anchoImagen*0.7f, posFuturaY+altoImagen);
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
