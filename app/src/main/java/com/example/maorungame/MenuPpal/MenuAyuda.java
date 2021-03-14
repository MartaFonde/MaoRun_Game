package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;

import com.example.maorungame.R;

public class MenuAyuda extends Menu {

    /**
     * Construye la pantalla de la opción de menú Ayuda a partir de unas dimensiones ancho y alto de
     * pantalla y de un número identificativo. Define el tamaño del texto que se usará en esta pantalla.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     */
    public MenuAyuda(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        tpNaranja.setTextSize(altoPantalla/10);
        tpBeige.setTextSize(altoPantalla/14);
    }

    /**
     * Dibuja sobre el lienzo texto que indica como se juega al juego, el fondo y el botón de
     * retroceso.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawRect(rectFondo, pFondo);

        c.drawText(context.getResources().getText(R.string.ayudaTitulo).toString(), anchoPantalla/2, altoPantalla/16 * 2, tpNaranja);

        c.drawText(context.getResources().getText(R.string.ayuda1).toString(), anchoPantalla/2, altoPantalla / 16 * 4, tpBeige);
        c.drawText(context.getResources().getText(R.string.ayuda2).toString(), anchoPantalla/2, altoPantalla / 16 * 5.25f, tpBeige);
        c.drawText(context.getResources().getText(R.string.ayuda3).toString(), anchoPantalla/2, altoPantalla / 16 * 6.5f, tpBeige);
        c.drawText(context.getResources().getText(R.string.ayuda4).toString(), anchoPantalla/2, altoPantalla / 16 * 7.75f, tpBeige);
        c.drawText(context.getResources().getText(R.string.ayuda5).toString(), anchoPantalla/2, altoPantalla / 16 * 9, tpBeige);
        c.drawText(context.getResources().getText(R.string.ayuda6).toString(), anchoPantalla/2, altoPantalla / 16 * 10.25f, tpBeige);
        c.drawText(context.getResources().getText(R.string.ayuda7).toString(), anchoPantalla/2, altoPantalla / 16 * 11.5f, tpBeige);
        c.drawText(context.getResources().getText(R.string.ayuda8).toString(), anchoPantalla/2, altoPantalla / 16 * 12.75f, tpBeige);
        c.drawText(context.getResources().getText(R.string.ayuda9).toString(), anchoPantalla/2, altoPantalla / 16 * 14, tpBeige);
    }
}
