package com.example.maorungame.ElemEscena;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.maorungame.Escenas.Escena;

public class Gato {
    private Bitmap imagenes; // Bitmap con todas las imágenes

    public PointF posicion;
    RectF posicionFutura;
    float x;
    float y;

    private int anchoImagenes; //Ancho bitmap todas imagenes
    private int altoImagenes; //alto del bitmap todas imagenes
    int anchoImagen;
    int altoImagen;
    public int fila = 2;
    int col = 1; //Fila y columna de la imagen a representar
    Bitmap imagenActual;      //imagen actual del gato
    Bitmap[][] imgGato;

    public RectF rectangulo;
    public boolean puedeMoverse = true;

    float velocidad;

    public int numVidas;
    public int puntos;

    public Gato(Bitmap imagenes, float x, float y, float velocidad) {
        this.imagenes = imagenes;
        this.posicion = new PointF(x, y);
        setX(x);
        setY(y);
        setVelocidad(velocidad);

        this.anchoImagenes = imagenes.getWidth();
        this.altoImagenes = imagenes.getHeight();
        this.anchoImagen = anchoImagenes/3;     //3 columnas
        this.altoImagen = altoImagenes/4; // 4 filas (movimientos)

        imgGato = new Bitmap[4][3];
        setImgGato(imagenes);

        //por defecto estará direccion arriba:
        this.imagenActual = Bitmap.createBitmap(imagenes, anchoImagenes/3,(altoImagenes / 4)*3, anchoImagen,altoImagen);

        numVidas = 7;
        puntos = 0;
    }

    /**
     * Devuelve la propiedad x que representa la coordenada x de gato
     * @return coordenada x
     */
    public float getX() {
        return x;
    }

    /**
     * Establece la propiedad x de gato, que representa su coordenada x,
     * y actualiza el rect, que utiliza este valor en su definición
     * @param x x
     */
    public void setX(float x) {
        this.x = x;
        posicion.x = x;
        setRectangulo();
    }

    /**
     * Devuelve la propiedad y que representa la coordenada y de gato
     * @return coordenada y
     */
    public float getY() {
        return y;
    }

    /**
     * Establece la propiedad y de gato, que representa su coordenada y,
     * y actualiza el rect, que utiliza este valor en su definición
     * @param y y
     */
    public void setY(float y) {
        this.y = y;
        posicion.y = y;
        setRectangulo();
    }

    /**
     * Devuelve la propiedad velocidad de gato
     * @return velocidad
     */
    public float getVelocidad() {
        return velocidad;
    }

    /**
     * Establece la propiedad velocidad de gato
     * @param velocidad
     */
    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Devuelve el array bidimensional que contiene los sprites animados de gato
     * @return sprites de gato
     */
    public Bitmap[][] getImgGato() {
        return imgGato;
    }

    /**
     * Establece el array bidimensional que contiene los sprites animados de gato
     * @param img imagen que contiene los sprites animados de gato
     */
    public void setImgGato(Bitmap img) {
        for (int i = 0; i < imgGato.length; i++) {
            for (int j = 0; j < imgGato[i].length; j++) {
                imgGato[i][j] = Bitmap.createBitmap(img, (anchoImagenes/3)*j,(altoImagenes / 4)*i, anchoImagen,altoImagen);
            }
        }
    }

    /**
     * Establece el rect de gato a partir de su posición, y el rect de su posición futura tomando
     * como referencia su su último movimiento
     */
    public void setRectangulo() {
        rectangulo = new RectF(posicion.x + anchoImagen*0.3f, posicion.y + altoImagen*0.7f,
                posicion.x +anchoImagen * 0.7f, posicion.y+altoImagen);
        posicionFutura = getPosicionFutura(Escena.mov);
    }

    /**
     * Incrementa la propiedad y en velocidad si el gato se puede mover (el rect no colisiona con
     * obstáculos) y si su imagen no supera el alto de pantalla. Actualiza la imagen
     * a partir de los sprites animados
     * @param altoPantalla alto de la pantalla
     */
    public void moverAbajo(int altoPantalla) {
        fila = 0;
        if (puedeMoverse && posicion.y + altoImagen <= altoPantalla) {
            setY(posicion.y + velocidad);
        }
        actualizaImagen();
    }

    /**
     * Decrementa la propiedad x en velocidad si el gato puede moverse (el rect no colisiona con
     * obstáculos) y si su imagen no se va fuera de la pantalla por el lado izquierdo.
     * Actualiza la imagen a partir de los sprites animados
     */
    public void moverIzquierda() {
        fila = 1;
        if (puedeMoverse && posicion.x >= 0) {
            setX(posicion.x - velocidad);
        }
        actualizaImagen();
    }

    /**
     * Incrementa la propiedad x en velocidad si el gato puede moverse (el rect no colisiona con
     * obstáculos) y si su imagen no se va fuera de la pantalla por el lado derecho.
     * Actualiza la imagen a partir de los sprites animados
     * @param anchoPantalla ancho de pantalla
     */
    public void moverDerecha(int anchoPantalla) {
        fila = 2;
        if (puedeMoverse &&  posicion.x <= anchoPantalla- imagenActual.getWidth()) {
            setX(posicion.x + velocidad);
        }
        actualizaImagen();
    }

    /**
     * Decrementa la propiedad y en velocidad si el gato puede moverse (el rect no colisiona con
     * obstáculos) y si su imagen completa no se va fuera de la pantalla por la parte superior.
     * Actualiza la imagen a partir de los sprites animados
     */
    public void moverArriba() {
        fila = 3;
        if (puedeMoverse && posicion.y + velocidad  >= 0) {
            setY(posicion.y - velocidad);
        }
        actualizaImagen();
    }

    /**
     * Establece y devuelve el rect de posición futura de gato en base al movimiento pasado como
     * parámetro (dirección), su posición actual y la velocidad. Su cálculo es útil para determinar
     * si, dadas estas variables, el gato se puede mover o no (detectar colisiones)
     * @param mov movimiento futuro de gato
     * @return rect de la posición futura de gato
     */
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

    /**
     * Actualiza la propiedad imagenActual dependiendo de la dirección, con sprites animados.
     */
    public void actualizaImagen() {
        if(col < imgGato[fila].length){
            this.imagenActual = imgGato[fila][col];
            col++;
        }else{
            col = 0;
        }
    }

    /**
     * Actualiza la propiedad imagenActual dependiendo del movimiento anterior si el gato se para
     */
    public void parado(){
        col = 1;
        this.imagenActual = imgGato[fila][col];
    }

    /**
     * Dibuja la propiedad imagenActual en la posición x,y de gato
     * @param c lienzo
     */
    public void dibujaGato(Canvas c){
        c.drawBitmap(imagenActual, posicion.x, posicion.y, null);
    }
}
