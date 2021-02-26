package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MenuCreditos extends Menu {

    public MenuCreditos(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
    }

    /**
     * Dibuja sobre el lienzo texto que indica los autores de las imágenes, la fuente de letra y
     * la música que se utilizaron para realizar el juego. Todos los recursos utilizados son de
     * libre distribución. También dibuja el fondo negro y el botón de retroceso.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c); //fondo + btnAtras
        tpVerde.setTextAlign(Paint.Align.CENTER);
        tpVerde.setTextSize(altoPantalla/10);
        c.drawText("AGRADECIMIENTOS", anchoPantalla/2, altoPantalla/16 * 2, tpVerde);

        tpVerde.setTextAlign(Paint.Align.LEFT);

        tpVerde.setTextSize(altoPantalla/12);
        c.drawText("IMÁGENES", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 5, tpVerde);
        tpVerde.setTextSize(altoPantalla/15);
        c.drawText("D.Paige",anchoPantalla/32 * 4.5f, altoPantalla / 16 * 6.5f, tpVerde);
        c.drawText("La Red Games", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 8, tpVerde);
        c.drawText("Kyrise", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 9.5f, tpVerde);
        c.drawText("Robert Brooks", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 11, tpVerde);
        c.drawText("CarterArt", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 12.5f, tpVerde);
        c.drawText("www.icon-icons.com", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 14, tpVerde);

        tpVerde.setTextSize(altoPantalla/12);
        c.drawText("ESTILO DE FUENTE", anchoPantalla/32 * 16, altoPantalla / 16 * 5, tpVerde);
        tpVerde.setTextSize(altoPantalla/15);
        c.drawText("Heaven castro", anchoPantalla/32 * 16, altoPantalla / 16 * 6.5f, tpVerde);

        tpVerde.setTextSize(altoPantalla/12);
        c.drawText("MÚSICA", anchoPantalla/32 * 16, altoPantalla / 16 * 9, tpVerde);
        tpVerde.setTextSize(altoPantalla/15);
        c.drawText("www.mixkit.co", anchoPantalla/32 * 16, altoPantalla / 16 * 10.5f, tpVerde);
        c.drawText("www.freesoundslibrary.com", anchoPantalla/32 * 16, altoPantalla / 16 * 12, tpVerde);
        c.drawText("www.soundeffectsplus.com", anchoPantalla/32 * 16, altoPantalla / 16 * 13.5f, tpVerde);
        c.drawText("www.bensound.com", anchoPantalla/32 * 16, altoPantalla / 16 * 15, tpVerde);
    }
}
