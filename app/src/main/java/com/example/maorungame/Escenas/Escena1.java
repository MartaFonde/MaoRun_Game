package com.example.maorungame.Escenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.maorungame.ElemEscena.Coche;
import com.example.maorungame.ElemEscena.Gato;
import com.example.maorungame.Pantalla;

public class Escena1 extends Escena {

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
    int[] posicionesCoches = {2,1,4,3,8,7,11,10};

    /**
     * Construye el primer nivel del juego a partir de las dimensiones de ancho y alto de la pantalla,
     * del número identificativo y del personaje gato. Inicializa el fondo y llama a la función
     * que crea los rect de árboles, la que establece la posición de las monedas y la que fija la
     * posición inicial de los coches.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     * @param gato personaje del jugador
     */
    public Escena1(Context context, int anchoPantalla, int altoPantalla, int numPantalla, Gato gato) {
        super(context, anchoPantalla, altoPantalla, numPantalla, gato);
        fondo = Pantalla.getBitmapFromAssets(context, "mapas/mapa_nivel1.png");
        setFondo(fondo);
        setArbolesRect();
        moneda.setPosicionMonedas(arbolesRect);
        velocidadCoches = anchoPantalla /(32 * 15);
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
     * @param event
     * @return incremento de numPantalla a 7 si se supera el nivel. En caso contrario, se retorna
     * el numPantalla de esta escena (6) y no se hace cambio de pantalla.
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
                if(gato.puedeMoverse) colisionMonedas();
            }
        }
        return super.onTouchEvent(event);   //numPantalla
    }

    /**
     * Crea los rect de arboles según las posiciones determinadas por los árboles de fondo y los ajusta.
     */
    public void setArbolesRect(){
        arbolesRect = new RectF[25];
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
}
