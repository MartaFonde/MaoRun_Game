package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.widget.Toast;

public class Escena3 extends Escena {
    Bitmap fondo;
    float velocidadCoches;

    public Escena3(Context context, int anchoPantalla, int altoPantalla, int numPantalla, Gato gato) {
        super(context, anchoPantalla, altoPantalla, numPantalla, gato);
        //fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.mapa_nivel3);
        fondo = Pantalla.getBitmapFromAssets(context, "mapas/mapa_nivel3.png");
        setFondo(fondo);
        arbolesRect = new RectF[19];
        setArbolesRect();
        setPosicionMonedas();
        velocidadCoches = (anchoPantalla /(32 * 10))*1.5f;
        setCoches();
        gato.setVelocidad(anchoPantalla / (32*2));
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
            if(gato.getPosicionFutura(mov).intersect(new RectF(anchoPantalla/32 *18, 0,
                    anchoPantalla/32*22, altoPantalla/16 * 0.5f))){
                JuegoSV.pantallaActual = new PantallaFinPartida(context, anchoPantalla, altoPantalla, 9, false, gato.puntos);
            }else {
                gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                gato.moverArriba();
                colisionMonedas();
            }
        }

        return -1;
    }

    public void setArbolesRect(){      //TODO retocar bordes
        //fila1
        arbolesRect[0] = new RectF(0, propH  * 12 * 1.03f, propW  * 6, altoPantalla);
        arbolesRect[1] = new RectF(propW  * 6, propH  * 13 * 1.02f, propW * 10 * 1.01f, altoPantalla);
        arbolesRect[2] = new RectF(propW  * 12 * 1.03f, propH  * 12 * 1.02f, propW  * 13 , propH * 13);
        arbolesRect[3] = new RectF(propW  * 19 * 1.015f, propH  * 13 * 1.02f, propW  * 20 , propH * 14);
        arbolesRect[4] = new RectF(propW  * 22 * 1.015f, propH  * 14 * 1.02f, propW * 24 * 1.01f, altoPantalla);
        arbolesRect[5] = new RectF(propW  * 24 *1.005f, propH  * 13 * 1.02f, propW * 27, altoPantalla);
        arbolesRect[6] = new RectF(propW  * 27 *1.005f, propH  * 12 * 1.02f, anchoPantalla, altoPantalla);

        //fila2
        arbolesRect[7] = new RectF(0 , propH  * 6 * 1.05f, propW * 4 / 1.04f, propH * 8 );
        arbolesRect[8] = new RectF(propW * 6 * 1.025f, propH  * 7 * 1.03f, propW  * 9 , propH  * 8);
        arbolesRect[9] = new RectF(propW  * 11 * 1.03f, propH  * 6 * 1.03f, propW * 14  , propH  * 7);
        arbolesRect[10] = new RectF(propW * 15 *1.015f, propH * 7 * 1.03f, propW * 20 / 1.015f, propH * 8 );
        arbolesRect[11] = new RectF(propW * 21 *1.015f, propH  * 6 * 1.03f, propW * 24 / 1.015f  , propH * 7);
        arbolesRect[12] = new RectF(propW * 26 *1.015f, propH  * 7 *1.03f, propW * 28, propH * 8 );
        arbolesRect[13] = new RectF(propW * 28 * 1.015f, propH * 6 * 1.03f, anchoPantalla, propH * 8 / 1.03f);

        //fila3
        arbolesRect[14] = new RectF(0, 0, propW * 6 , propH * 2 / 1.03f );
        arbolesRect[15] = new RectF(propW  * 6 * 1.02f, 0, propW * 9, propH / 1.03f);
        arbolesRect[16] = new RectF(propW  * 12 * 1.025f, 0, propW * 17 , propH * 2 / 1.03f);
        arbolesRect[17] = new RectF(propW * 23 * 1.015f, 0, propW * 25 / 1.015f, propH / 1.07f);
        arbolesRect[18] = new RectF(propW * 25* 1.01f, 0, anchoPantalla, propH * 2);
    }

    public void setCoches(){
        trafico.coches[0] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 2, altoPantalla / 16 * 11, velocidadCoches);
        trafico.coches[1] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla / 5, altoPantalla / 16 * 10, velocidadCoches);
        trafico.coches[2] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], -trafico.anchoCoche, altoPantalla / 16 * 11, velocidadCoches);
        trafico.coches[3] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 10, velocidadCoches);

        trafico.coches[4] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 7, altoPantalla / 16 * 9, velocidadCoches);
        trafico.coches[5] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 8, velocidadCoches);
        trafico.coches[6] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla, altoPantalla / 16 * 9, velocidadCoches);
        trafico.coches[7] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 8, velocidadCoches);

        trafico.coches[8] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], -trafico.anchoCoche, altoPantalla / 16 * 5, velocidadCoches);
        trafico.coches[9] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla, altoPantalla /16 * 4, velocidadCoches);
        trafico.coches[10] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 5, velocidadCoches);
        trafico.coches[11] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/3, altoPantalla /16 * 4, velocidadCoches);

        trafico.coches[12] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla / 16, altoPantalla /16 *3, velocidadCoches);
        trafico.coches[13] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], -trafico.anchoCoche, altoPantalla / 16 * 2, velocidadCoches);
        trafico.coches[14] = new Coche(trafico.imgCochesRight[(int)(Math.random()*5)], anchoPantalla/3, altoPantalla / 16 * 3, velocidadCoches);
        trafico.coches[15] = new Coche(trafico.imgCochesLeft[(int)(Math.random()*5)], anchoPantalla/2, altoPantalla / 16 * 2, velocidadCoches);
    }
}
