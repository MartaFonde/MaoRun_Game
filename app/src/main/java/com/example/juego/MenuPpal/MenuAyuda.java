package com.example.juego.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.juego.Menu;

public class MenuAyuda extends Menu {
    public MenuAyuda(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        tpBeige.setARGB(225,129,157,80);
    }

    /**
     * Dibuja sobre el lienzo texto que indica como se juega al juego
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);        //fondo + btnAtras
        tpBeige.setTextAlign(Paint.Align.CENTER);
        tpBeige.setTextSize(altoPantalla/10);
        c.drawText("CÓMO JUGAR", anchoPantalla/2, altoPantalla/16 * 2, tpBeige);

        tpBeige.setTextSize(altoPantalla/14);
        c.drawText("El objetivo del juego es guiar el gato para ", anchoPantalla/2, altoPantalla / 16 * 4, tpBeige);
        c.drawText("conseguir el mayor número de monedas posible. ", anchoPantalla/2, altoPantalla / 16 * 5, tpBeige);
        c.drawText("El juego consta de tres niveles, con sus respectivos ", anchoPantalla/2, altoPantalla / 16 * 6, tpBeige);
        c.drawText("escenarios, en los que los árboles son obstáculos", anchoPantalla/2, altoPantalla / 16 * 7, tpBeige);
        c.drawText("y por los que circulan coches. En cada colisión", anchoPantalla/2, altoPantalla / 16 * 8, tpBeige);
        c.drawText("con un coche el gato perderá una vida.", anchoPantalla/2, altoPantalla / 16 * 9, tpBeige);
        c.drawText("Como buen gato, inicialmente contará con siete vidas.", anchoPantalla/2, altoPantalla / 16 * 10, tpBeige);
        c.drawText("Para superar cada nivel, el gato debe atravesar ", anchoPantalla/2, altoPantalla / 16 * 11, tpBeige);
        c.drawText("el camino de tierra marcado.", anchoPantalla/2, altoPantalla / 16 * 12, tpBeige);
    }
}
