package com.example.maorungame.Escenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.maorungame.ElemEscena.Coche;
import com.example.maorungame.ElemEscena.Gato;
import com.example.maorungame.JuegoSV;
import com.example.maorungame.Pantalla;

public class Escena3 extends Escena {

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
    int[] posicionesCoches = {2,3,4,5,8,9,10,11};

    /**
     * Construye el tercero y último nivel del juego a partir de las dimensiones de ancho y alto de la
     * pantalla, del número identificativo y del personaje gato. Inicializa el fondo y llama a la función
     * que crea los rect de árboles, la que establece la posición de las monedas y la que fija la
     * posición inicial de los coches.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     * @param gato personaje del jugador
     */
    public Escena3(Context context, int anchoPantalla, int altoPantalla, int numPantalla, Gato gato) {
        super(context, anchoPantalla, altoPantalla, numPantalla, gato);
        fondo = Pantalla.getBitmapFromAssets(context, "mapas/mapa_nivel3.png");
        setFondo(fondo);
        setArbolesRect();
        moneda.setPosicionMonedas(arbolesRect);
        gato.setVelocidad(anchoPantalla / (32*2));
        velocidadCoches = (anchoPantalla /(32 * 10))*1.5f;
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
     * Si el rect de gato interseca el rect marcado para el cambio de escena, se finaliza el juego.
     * @param event evento
     * @return 9 si supera el nivel, que lleva a la pantalla de fin de partida. En caso contrario,
     * devuelve el numPantalla de esta escena (8) y no se hace cambio de pantalla.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        int aux = super.onTouchEvent(event);
        if(aux == -3){
            if(gato.getPosicionFutura(mov).intersect(new RectF(anchoPantalla/32 *18, 0,
                    anchoPantalla/32*22, altoPantalla/16 * 0.5f))){
                JuegoSV.mediaPlayer.stop();
                return 9;
            }else {
                gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                gato.moverArriba();
                colisionMonedas();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * Crea los rect de arboles según las posiciones determinadas por el fondo y los ajusta.
     */
    public void setArbolesRect(){
        arbolesRect = new RectF[19];
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
}
