package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.maorungame.R;

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
     * la música que se utilizaron para realizar el juego. Dibuja el fondo y el botón de retroceso.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);

        c.drawRect(rectFondo, pFondo);

        tpNaranja.setTextAlign(Paint.Align.CENTER);
        tpNaranja.setTextSize(altoPantalla/10);
        c.drawText(context.getResources().getText(R.string.agradecimientos).toString(), anchoPantalla/2, altoPantalla/16 * 2, tpNaranja);

        tpNaranja.setTextAlign(Paint.Align.LEFT);

        tpNaranja.setTextSize(altoPantalla/12);
        c.drawText(context.getResources().getText(R.string.imagenes).toString(), anchoPantalla/32 * 4.5f, altoPantalla / 16 * 4.75f, tpNaranja);
        c.drawText(context.getResources().getText(R.string.credImg1).toString(),anchoPantalla/32 * 4.5f, altoPantalla / 16 * 6.25f, tpBeige);
        c.drawText(context.getResources().getText(R.string.credImg2).toString(), anchoPantalla/32 * 4.5f, altoPantalla / 16 * 7.75f, tpBeige);
        c.drawText(context.getResources().getText(R.string.credImg3).toString(), anchoPantalla/32 * 4.5f, altoPantalla / 16 * 9.25f, tpBeige);
        c.drawText(context.getResources().getText(R.string.credImg4).toString(), anchoPantalla/32 * 4.5f, altoPantalla / 16 * 10.75f, tpBeige);
        c.drawText(context.getResources().getText(R.string.credImg5).toString(), anchoPantalla/32 * 4.5f, altoPantalla / 16 * 12.25f, tpBeige);
        c.drawText(context.getResources().getText(R.string.credImg6).toString(), anchoPantalla/32 * 4.5f, altoPantalla / 16 * 13.75f, tpBeige);
        c.drawText(context.getResources().getText(R.string.credImg7).toString(), anchoPantalla/32 * 4.5f, altoPantalla / 16 * 15.25f, tpBeige);


        c.drawText(context.getResources().getText(R.string.estiloFuente).toString(), anchoPantalla/32 * 16, altoPantalla / 16 * 5, tpNaranja);
        c.drawText(context.getResources().getText(R.string.credFont).toString(), anchoPantalla/32 * 16, altoPantalla / 16 * 6.5f, tpBeige);

        c.drawText(context.getResources().getText(R.string.musica).toString(), anchoPantalla/32 * 16, altoPantalla / 16 * 9, tpNaranja);
        c.drawText(context.getResources().getText(R.string.credMusic1).toString(), anchoPantalla/32 * 16, altoPantalla / 16 * 10.5f, tpBeige);
        c.drawText(context.getResources().getText(R.string.credMusic2).toString(), anchoPantalla/32 * 16, altoPantalla / 16 * 12, tpBeige);
        c.drawText(context.getResources().getText(R.string.credMusic3).toString(), anchoPantalla/32 * 16, altoPantalla / 16 * 13.5f, tpBeige);
        c.drawText(context.getResources().getText(R.string.credMusic4).toString(), anchoPantalla/32 * 16, altoPantalla / 16 * 15, tpBeige);
    }
}
