package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MenuCreditos extends Menu {

    /**
     * Construye la pantalla de la opción de menú Créditos a partir de unas dimensiones ancho y alto de
     * pantalla y de un número identificativo. Define el tamaño del texto que se usará en esta pantalla
     * y su alineación.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     */
    public MenuCreditos(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        tpBeige.setTextSize(altoPantalla/15);
        tpBeige.setTextAlign(Paint.Align.LEFT);
    }

    /**
     * Dibuja sobre el lienzo texto que indica los autores de las imágenes, la fuente de letra y
     * la música que se utilizaron para realizar el juego. También dibuja el fondo negro y el botón
     * de retroceso.
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
        c.drawText("IMÁGENES", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 5.5f, tpVerde);
        c.drawText("D.Paige",anchoPantalla/32 * 4.5f, altoPantalla / 16 * 7, tpBeige);
        c.drawText("La Red Games", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 8.5f, tpBeige);
        c.drawText("Kyrise", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 10, tpBeige);
        c.drawText("Robert Brooks", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 11.5f, tpBeige);
        c.drawText("CarterArt", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 13, tpBeige);
        c.drawText("www.icon-icons.com", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 14.5f, tpBeige);

        c.drawText("ESTILO DE FUENTE", anchoPantalla/32 * 16, altoPantalla / 16 * 5, tpVerde);
        c.drawText("The Fontry", anchoPantalla/32 * 16, altoPantalla / 16 * 6.5f, tpBeige);

        c.drawText("MÚSICA", anchoPantalla/32 * 16, altoPantalla / 16 * 9, tpVerde);
        c.drawText("www.mixkit.co", anchoPantalla/32 * 16, altoPantalla / 16 * 10.5f, tpBeige);
        c.drawText("www.freesoundslibrary.com", anchoPantalla/32 * 16, altoPantalla / 16 * 12, tpBeige);
        c.drawText("www.soundeffectsplus.com", anchoPantalla/32 * 16, altoPantalla / 16 * 13.5f, tpBeige);
        c.drawText("www.bensound.com", anchoPantalla/32 * 16, altoPantalla / 16 * 15, tpBeige);
    }
}
