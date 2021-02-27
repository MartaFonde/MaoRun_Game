package com.example.maorungame.MenuEscenas;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class PauseAyuda extends PauseEscena {

    /**
     * Construye la pantalla de la opción Ayuda del menú de pausa de Escena a partir de unas dimensiones
     * alto y ancho de pantalla y de un número identificativo.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     */
    public PauseAyuda(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
    }

    /**
     * Dibuja el texto explicativo sobre cómo se juega al juego.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        tpBeige.setTextSize(altoPantalla/15);
        c.drawText("CÓMO JUGAR", anchoPantalla/2, altoPantalla/16 * 3.5f, tpBeige);

        tpBeige.setTextSize(altoPantalla/20);
        c.drawText("Consigue el mayor número de monedas", anchoPantalla/2, altoPantalla / 16 * 6, tpBeige);
        c.drawText("posible. Tendrás que esquivar a los", anchoPantalla/2, altoPantalla / 16 * 7, tpBeige);
        c.drawText("coches para no perder vidas. ", anchoPantalla/2, altoPantalla / 16 * 8, tpBeige);
        c.drawText("Los árboles son obstáculos.  ", anchoPantalla/2, altoPantalla / 16 * 9, tpBeige);
        c.drawText("Para superar cada nivel, dirige el ", anchoPantalla/2, altoPantalla / 16 * 10, tpBeige);
        c.drawText("gato hacia el camino de tierra.", anchoPantalla/2, altoPantalla / 16 * 11, tpBeige);
    }

    /**
     * Obtiene las coordenadas de pulsación y si el rect del botón retroceso las contiene se vuelve a
     * la pantalla de menú de pausa.
     * @param event evento
     * @return devuelve 10 para volver a pantalla PauseMenu o -1 en caso contrario (no se hace
     * cambio de pantalla)
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(rectAtras.contains(x,y)){
            return 10;
        }
        return -1;
    }
}
