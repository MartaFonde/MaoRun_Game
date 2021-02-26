package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;

public class MenuAyuda extends Menu {
    public MenuAyuda(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
    }

    /**
     * Dibuja sobre el lienzo texto que indica como se juega al juego, el fondo negro y el botón de
     * retroceso.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);        //fondo + btnAtras

        tpVerde.setTextSize(altoPantalla/10);
        c.drawText("CÓMO JUGAR", anchoPantalla/2, altoPantalla/16 * 2, tpVerde);

        tpVerde.setTextSize(altoPantalla/14);
        c.drawText("El objetivo del juego es guiar el gato para ", anchoPantalla/2, altoPantalla / 16 * 4, tpVerde);
        c.drawText("conseguir el mayor número de monedas posible. ", anchoPantalla/2, altoPantalla / 16 * 5, tpVerde);
        c.drawText("El juego consta de tres niveles, con sus respectivos ", anchoPantalla/2, altoPantalla / 16 * 6, tpVerde);
        c.drawText("escenarios, en los que los árboles son obstáculos", anchoPantalla/2, altoPantalla / 16 * 7, tpVerde);
        c.drawText("y por los que circulan coches. En cada colisión", anchoPantalla/2, altoPantalla / 16 * 8, tpVerde);
        c.drawText("con un coche el gato perderá una vida.", anchoPantalla/2, altoPantalla / 16 * 9, tpVerde);
        c.drawText("Como buen gato, inicialmente contará con siete vidas.", anchoPantalla/2, altoPantalla / 16 * 10, tpVerde);
        c.drawText("Para superar cada nivel, el gato debe atravesar ", anchoPantalla/2, altoPantalla / 16 * 11, tpVerde);
        c.drawText("el camino de tierra marcado.", anchoPantalla/2, altoPantalla / 16 * 12, tpVerde);
    }
}
