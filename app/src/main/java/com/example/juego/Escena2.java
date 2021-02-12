package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Escena2 extends Escena {

    Bitmap fondo;
    int numEscena;
    float velocidadCoches;

    public Escena2(Context context, int numEscena, int anchoPantalla, int altoPantalla) {
        super(context, numEscena, anchoPantalla, altoPantalla);

        this.numEscena = numEscena;

        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.mapa_nivel2);
        super.setFondo(fondo);

        super.arbolesRect = new RectF[25];
        setArbolesRect();

        super.setPosicionMonedas();

        velocidadCoches = (anchoPantalla / (32*10))*1.5f;
        setCoches();
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibujaFondo(c);
        dibujaArboles(c);
        super.dibuja(c);
    }

    @Override
    public void dibujaArboles(Canvas c) {
        setArbolesRect();
        super.dibujaArboles(c);
    }

    @Override
    int onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if(mov == 3){
            if(gato.getPosicionFutura(mov).intersect(new RectF(anchoPantalla/32 *14, 0,
                    anchoPantalla/32*17, 0))){
                int nuevaEscena = this.numEscena+1;
                gato.setX(gato.getX());
                gato.setY(altoPantalla/16*15);
                return nuevaEscena;
            }
        }

        return -1;
    }


    public void setArbolesRect(){
        //fila1
        arbolesRect[0] = new RectF(0, propH  * 13, propW  * 6 , altoPantalla);
        arbolesRect[1] = new RectF(propW * 6, propH  * 14 * 1.02f, propW * 7 *0.99f, altoPantalla);
        arbolesRect[2] = new RectF(propW  * 7, propH  * 15 * 1.015f, propW  * 14 * 0.99f, altoPantalla);
        arbolesRect[3] = new RectF(propW  * 9 * 1.015f, propH  * 13 * 1.007f, propW * 11 * 1.01f, propH * 14);
        arbolesRect[4] = new RectF(propW  * 19 * 1.015f, propH  * 13 * 1.02f, propW  * 21 , propH * 14);
        arbolesRect[5] = new RectF(propW  * 17 * 1.015f, propH  * 15 * 1.02f, propW  * 23 , altoPantalla);
        arbolesRect[6] = new RectF(propW  * 23 * 1.005f, propH  * 14 * 1.02f, propW * 27 * 1.01f, altoPantalla);
        arbolesRect[7] = new RectF(propW  * 27 *1.005f, propH  * 13 * 1.02f, anchoPantalla, altoPantalla);
        arbolesRect[8] = new RectF(propW  * 30 *1.005f, propH  * 12 * 1.02f, anchoPantalla, propH * 13);

        //fila2
        arbolesRect[9] = new RectF(0 , propH * 9 * 1.02f, propW * 2, propH * 10 );
        arbolesRect[10] = new RectF(propW  * 4 *1.05f , propH  * 9, propW * 5 / 1.04f, propH * 10 );
        arbolesRect[11] = new RectF(propW * 9 * 1.05f, propH  * 9 * 1.03f, propW  * 10 , propH  * 10 );
        arbolesRect[12] = new RectF(propW  * 14 * 1.015f, propH  * 9* 1.03f, propW *15 , propH  * 10 );
        arbolesRect[13] = new RectF(propW * 22 *1.015f, propH * 9 * 1.03f, propW * 23, propH * 10 );
        arbolesRect[14] = new RectF(propW * 28 *1.015f, propH  * 9 * 1.03f, propW * 29 , propH * 10 );
        arbolesRect[15] = new RectF(propW * 31 *1.015f, propH  * 9 *1.03f, anchoPantalla, propH * 10 );

        //fila3
        arbolesRect[16] = new RectF(0, propH * 4 * 1.015f, propW * 5 *1.015f, propH * 5 / 1.03f);
        arbolesRect[17] = new RectF(propW  * 8 *1.02f, propH * 4 * 1.015f, propW * 9 / 1.005f, propH * 5 / 1.03f);
        arbolesRect[18] = new RectF(propW  * 13 *1.015f * 1.007f, propH * 4 * 1.015f, propW * 14/1.005f , propH * 5 / 1.03f);
        arbolesRect[19] = new RectF(propW  * 20 * 1.015f, propH * 4 * 1.015f, propW * 21, propH * 5 / 1.03f);
        arbolesRect[20] = new RectF(propW  * 27 * 1.015f, propH * 4 * 1.015f, anchoPantalla, propH * 5 / 1.03f);
        //fila4
        arbolesRect[21] =  new RectF(0, 0, propW * 4 / 1.015f, propH *2 / 1.07f);
        arbolesRect[22] = new RectF(propW * 4 * 1.015f, 0, propW* 9 / 1.015f, propH / 1.07f);
        arbolesRect[23] = new RectF(propW  * 22 * 1.01f, 0, propW  * 28, propH / 1.06f);
        arbolesRect[24] = new RectF(propW  * 28 * 1.015f, 0, anchoPantalla, propH *2 / 1.03f);
    }

    public void setCoches(){
        trafico.coches[0] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 2, altoPantalla / 16 * 11, velocidadCoches);
        trafico.coches[1] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla / 5, altoPantalla / 16 * 10, velocidadCoches);
        trafico.coches[2] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], -trafico.anchoCoche, altoPantalla / 16 * 11, velocidadCoches);
        trafico.coches[3] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 10, velocidadCoches);

        trafico.coches[4] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 7, altoPantalla / 16 * 8, velocidadCoches);
        trafico.coches[5] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 7, velocidadCoches);
        trafico.coches[6] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 8, velocidadCoches);
        trafico.coches[7] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 7, velocidadCoches);

        trafico.coches[8] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], -trafico.anchoCoche, altoPantalla / 16 * 6, velocidadCoches);
        trafico.coches[9] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla /16 * 5, velocidadCoches);
        trafico.coches[10] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla /16 * 6, velocidadCoches);
        trafico.coches[11] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/3, altoPantalla / 16 * 5, velocidadCoches);

        trafico.coches[12] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 16, altoPantalla /16 *3, velocidadCoches);
        trafico.coches[13] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], -trafico.anchoCoche, altoPantalla / 16 * 2, velocidadCoches);
        trafico.coches[14] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla/3, altoPantalla / 16 * 3, velocidadCoches);
        trafico.coches[15] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 2, velocidadCoches);
    }
}
