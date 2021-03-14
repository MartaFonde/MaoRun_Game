package com.example.maorungame.Escenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.maorungame.ElemEscena.Coche;
import com.example.maorungame.ElemEscena.Gato;
import com.example.maorungame.Pantalla;

public class Escena2 extends Escena {

    /**
     * Imagen de fondo
     */
    Bitmap fondo;

    /**
     * Velocidad a la que circulan los coches
     */
    float velocidadCoches;

    /**
     * Posiciones del eje y en las que circulan coches
     */
    int[] posicionesCoches = {2,3,5,6,7,8,10,11};

    /**
     * Construye el segundo nivel del juego a partir de las dimensiones de ancho y alto de la pantalla,
     * del número identificativo y del personaje gato. Inicializa el fondo y llama a la función
     * que crea los rect de árboles, la que establece la posición de las monedas y la que fija la
     * posición inicial de los coches.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     * @param gato personaje del jugador
     */
    public Escena2(Context context, int anchoPantalla, int altoPantalla, int numPantalla, Gato gato) {
        super(context, anchoPantalla, altoPantalla, numPantalla, gato);
        fondo = Pantalla.getBitmapFromAssets(context, "mapas/mapa_nivel2.png");
        setFondo(fondo);
        setArbolesRect();
        moneda.setPosicionMonedas(arbolesRect);
        gato.setVelocidad(anchoPantalla / (32*2.5f));
        velocidadCoches = anchoPantalla / (32*10);
        trafico.setCoches(posicionesCoches, velocidadCoches);
    }

    /**
     * Dibuja en lienzo dibuja de clase Escena.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
    }


    /**
     * Gestiona el movimiento hacia arriba (decremento de posición y) de gato.
     * Si el rect de gato interseca el rect marcado para el cambio de escena, se cambia de escena:
     * se reposiciona gato y se devuelve el número de la nueva escena
     * @param event evento
     * @return incremento de numPantalla a 8 si se supera el nivel. En caso contrario, se retorna
     * el numPantalla de esta escena (7) y no se hace cambio de pantalla.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        int aux = super.onTouchEvent(event);
        if(aux == -3){
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

    /**
     * Crea los rect de arboles según las posiciones determinadas por los árboles de fondo y los ajusta.
     */
    public void setArbolesRect(){
        arbolesRect = new RectF[25];
        //fila1
        arbolesRect[0] = new RectF(0, propH  * 13 * 1.02f, propW  * 6 , altoPantalla);
        arbolesRect[1] = new RectF(propW * 6, propH  * 14 * 1.02f, propW * 7 *0.99f, altoPantalla);
        arbolesRect[2] = new RectF(propW  * 7, propH  * 15 * 1.02f, propW  * 14 * 0.99f, altoPantalla);
        arbolesRect[3] = new RectF(propW  * 9 * 1.015f, propH  * 13 * 1.007f, propW * 11 * 1.01f, propH * 14);
        arbolesRect[4] = new RectF(propW  * 19 * 1.015f, propH  * 13 * 1.02f, propW  * 21 , propH * 14);
        arbolesRect[5] = new RectF(propW  * 17 * 1.025f, propH  * 15 * 1.02f, propW  * 23 , altoPantalla);
        arbolesRect[6] = new RectF(propW  * 23 * 1.005f, propH  * 14 * 1.02f, propW * 27 * 1.01f, altoPantalla);
        arbolesRect[7] = new RectF(propW  * 27 *1.005f, propH  * 13 * 1.02f, anchoPantalla, altoPantalla);
        arbolesRect[8] = new RectF(propW  * 30 *1.005f, propH  * 12 * 1.02f, anchoPantalla, propH * 13);

        //fila2
        arbolesRect[9] = new RectF(0 , propH * 9 * 1.02f, propW * 2, propH * 10 );
        arbolesRect[10] = new RectF(propW  * 4 *1.05f , propH  * 9 * 1.03f, propW * 5 / 1.04f, propH * 10 );
        arbolesRect[11] = new RectF(propW * 9 * 1.015f, propH  * 9 * 1.03f, propW  * 10 , propH  * 10 );
        arbolesRect[12] = new RectF(propW  * 14 * 1.015f, propH  * 9 * 1.03f, propW *15 / 1.02f , propH  * 10 );
        arbolesRect[13] = new RectF(propW * 22 *1.015f, propH * 9 * 1.03f, propW * 23, propH * 10 );
        arbolesRect[14] = new RectF(propW * 28 *1.015f, propH  * 9 * 1.03f, propW * 29 , propH * 10 );
        arbolesRect[15] = new RectF(propW * 31 *1.015f, propH  * 9 *1.03f, anchoPantalla, propH * 10 );

        //fila3
        arbolesRect[16] = new RectF(0, propH * 4 * 1.035f, propW * 5 *1.015f, propH * 5 / 1.03f);
        arbolesRect[17] = new RectF(propW  * 8 *1.02f, propH * 4 * 1.035f, propW * 9 / 1.005f, propH * 5 / 1.03f);
        arbolesRect[18] = new RectF(propW  * 13 *1.015f * 1.007f, propH * 4 * 1.035f, propW * 14/1.005f , propH * 5 / 1.03f);
        arbolesRect[19] = new RectF(propW  * 20 * 1.015f, propH * 4 * 1.035f, propW * 21, propH * 5 / 1.03f);
        arbolesRect[20] = new RectF(propW  * 27 * 1.015f, propH * 4 * 1.035f, anchoPantalla, propH * 5 / 1.03f);
        //fila4
        arbolesRect[21] =  new RectF(0, 0, propW * 4 / 1.015f, propH *2 / 1.07f);
        arbolesRect[22] = new RectF(propW * 4 * 1.015f, 0, propW* 9 / 1.015f, propH / 1.07f);
        arbolesRect[23] = new RectF(propW  * 22 * 1.01f, 0, propW  * 28, propH / 1.06f);
        arbolesRect[24] = new RectF(propW  * 28 * 1.015f, 0, anchoPantalla, propH *2 / 1.03f);
    }
}
