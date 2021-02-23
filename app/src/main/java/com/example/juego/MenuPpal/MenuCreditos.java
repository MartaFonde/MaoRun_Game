package com.example.juego.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.juego.Menu;

public class MenuCreditos extends Menu {

    public MenuCreditos(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        tpBeige.setARGB(225,129,157,80);
    }

    /**
     * Dibuja sobre el lienzo texto que indica los autores de las imágenes, la fuente de letra y
     * la música que se utilizaron para realizar el juego. Todos los recursosutilizados son de
     * libre distribución.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c); //fondo + btnAtras
        tpBeige.setTextAlign(Paint.Align.CENTER);
        tpBeige.setTextSize(altoPantalla/10);
        c.drawText("AGRADECIMIENTOS", anchoPantalla/2, altoPantalla/16 * 2, tpBeige);

        tpBeige.setTextAlign(Paint.Align.LEFT);

        tpBeige.setTextSize(altoPantalla/12);
        c.drawText("IMÁGENES", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 5, tpBeige);
        tpBeige.setTextSize(altoPantalla/15);
        c.drawText("D.Paige",anchoPantalla/32 * 4.5f, altoPantalla / 16 * 6.5f, tpBeige);
        c.drawText("La Red Games", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 8, tpBeige);
        c.drawText("Kyrise", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 9.5f, tpBeige);
        c.drawText("Robert Brooks", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 11, tpBeige);
        c.drawText("CarterArt", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 12.5f, tpBeige);
        c.drawText("www.icon-icons.com", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 14, tpBeige);

        tpBeige.setTextSize(altoPantalla/12);
        c.drawText("ESTILO DE FUENTE", anchoPantalla/32 * 16, altoPantalla / 16 * 5, tpBeige);
        tpBeige.setTextSize(altoPantalla/15);
        c.drawText("Heaven castro", anchoPantalla/32 * 16, altoPantalla / 16 * 6.5f, tpBeige);

        tpBeige.setTextSize(altoPantalla/12);
        c.drawText("MÚSICA", anchoPantalla/32 * 16, altoPantalla / 16 * 9, tpBeige);
        tpBeige.setTextSize(altoPantalla/15);
        c.drawText("www.mixkit.co", anchoPantalla/32 * 16, altoPantalla / 16 * 10.5f, tpBeige);
        c.drawText("www.freesoundslibrary.com", anchoPantalla/32 * 16, altoPantalla / 16 * 12, tpBeige);
        c.drawText("www.soundeffectsplus.com", anchoPantalla/32 * 16, altoPantalla / 16 * 13.5f, tpBeige);
        c.drawText("www.bensound.com", anchoPantalla/32 * 16, altoPantalla / 16 * 15, tpBeige);

    }
}
