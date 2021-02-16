package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.Toast;

public class Escena1 extends Escena {
    Bitmap fondo;
    int numEscena;
    float velocidadCoches;

    public Escena1(Context context, int anchoPantalla, int altoPantalla, int numPantalla, Gato gato) {
        super(context, anchoPantalla, altoPantalla, numPantalla, gato);

        fondo = Pantalla.getBitmapFromAssets(context, "mapas/mapa_nivel1.png");
        //fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.mapa_nivel1);
        setFondo(fondo);
        arbolesRect = new RectF[25];
        setArbolesRect();
        setPosicionMonedas();

        this.velocidadCoches = anchoPantalla /(32 * 15);
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
                    anchoPantalla/32*17, altoPantalla/16 * 0.5f))){
                gato.moverArriba();
                colisionMonedas();
                gato.setY(altoPantalla/16*15);
                return this.numPantalla+1;
            } else {
                gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                gato.moverArriba();
                colisionMonedas();
            }
        }
        return super.onTouchEvent(event);
    }

    public void setArbolesRect(){
        //fila1
        arbolesRect[0] = new RectF(0, propH  * 12, propW  *4*1.005f, altoPantalla );
        arbolesRect[1] = new RectF(propW * 4, propH  * 13 * 1.02f, propW *7 *0.99f, altoPantalla);
        arbolesRect[2] = new RectF(propW  * 7, propH  * 14 * 1.015f, propW  *8 * 0.99f, altoPantalla);
        arbolesRect[3] = new RectF(propW  * 8, propH  * 15 * 1.015f, propW *11, altoPantalla);
        arbolesRect[4] = new RectF(propW  * 11 * 1.005f, propH  * 13 * 1.02f, propW  * 12 , propH  * 14);
        arbolesRect[5] = new RectF(propW  * 14 * 1.015f, propH  * 15 * 1.02f, propW *15, altoPantalla);
        arbolesRect[6] = new RectF(propW  * 18 *1.015f, propH  * 15 * 1.02f, propW * 20, altoPantalla);
        arbolesRect[7] = new RectF(propW  * 23*1.005f, propH  * 14 * 1.02f, propW *25, altoPantalla);
        arbolesRect[8] = new RectF(propW  * 25 * 1.015f , propH  * 13 * 1.02f, propW *29, altoPantalla);
        arbolesRect[9] = new RectF(propW  * 29*1.02f , propH  * 12 * 1.02f, anchoPantalla, altoPantalla);
        //fila2
        arbolesRect[10] = new RectF(propW * 1 * 1.05f, propH  * 9 * 1.03f, propW  *2 , propH  * 10 / 1.015f);
        arbolesRect[11] = new RectF(propW  * 10 * 1.025f, propH  * 9*1.03f, propW  *12, propH  * 10 / 1.015f);
        arbolesRect[12] = new RectF(propW * 29*1.015f, propH * 9 *1.03f, propW *30, propH * 10/ 1.015f);
        //fila3
        arbolesRect[13] = new RectF(0, propH  * 6*1.03f, propW *1 * 1.03f, propH  * 7 / 1.02f);
        arbolesRect[14] = new RectF(propW * 3*1.015f, propH  * 5*1.05f, propW  * 4 * 0.99f, propH  * 6/ 1.02f);
        arbolesRect[15] = new RectF(propW  * 8*1.03f, propH * 6 * 1.03f, propW * 10 *1.015f, propH * 7/ 1.015f);
        arbolesRect[16] = new RectF(propW  * 18 *1.015f, propH  * 5*1.05f, propW*19*1.005f, propH  * 6/ 1.03f);
        arbolesRect[17] = new RectF(propW  * 21 * 1.007f, propH * 6 * 1.05f, propW *22 , propH * 7/ 1.01f);
        arbolesRect[18] = new RectF(propW  * 29 * 1.005f, propH  * 5*1.05f, propW *30, propH  * 6/ 1.03f);
        arbolesRect[19] = new RectF(propW  * 31 * 1.015f, propH  * 6 *1.03f, anchoPantalla, propH  * 7/ 1.015f);
        //fila4
        arbolesRect[20] =  new RectF(0, 0, propW* 7 *1.015f, propH/ 1.03f);
        arbolesRect[21] = new RectF(propW * 11 * 1.01f, 0, propW* 12 *1.005f, propH   / 1.06f);
        arbolesRect[22] = new RectF(propW * 21 *1.015f, 0, propW  * 22, propH   / 1.06f);
        arbolesRect[23] = new RectF(propW  * 25 * 1.01f, 0, propW  * 26, propH  / 1.06f);
        arbolesRect[24] = new RectF(propW  * 29 * 1.02f, 0, anchoPantalla, propH   / 1.03f);
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

        trafico.coches[8] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], -trafico.anchoCoche, altoPantalla / 16 * 4, velocidadCoches);
        trafico.coches[9] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 3, velocidadCoches);
        trafico.coches[10] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 4, velocidadCoches);
        trafico.coches[11] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/3, altoPantalla / 16 * 3, velocidadCoches);

        trafico.coches[12] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 16, altoPantalla / 16 * 2, velocidadCoches);
        trafico.coches[13] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], -trafico.anchoCoche, altoPantalla / 16 * 1, velocidadCoches);
        trafico.coches[14] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla/3, altoPantalla / 16 * 2, velocidadCoches);
        trafico.coches[15] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 1, velocidadCoches);
    }
}
