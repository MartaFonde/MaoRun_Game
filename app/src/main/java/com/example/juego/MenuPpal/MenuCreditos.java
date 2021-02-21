package com.example.juego.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.juego.Menu;

public class MenuCreditos extends Menu {


    public MenuCreditos(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        tp.setARGB(225,129,157,80);
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
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTextSize(altoPantalla/10);
        c.drawText("AGRADECIMIENTOS", anchoPantalla/2, altoPantalla/16 * 2, tp);

        tp.setTextAlign(Paint.Align.LEFT);

        tp.setTextSize(altoPantalla/12);
        c.drawText("IMÁGENES", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 5, tp);
        tp.setTextSize(altoPantalla/15);
        c.drawText("D.Paige",anchoPantalla/32 * 4.5f, altoPantalla / 16 * 6.5f, tp);
        c.drawText("La Red Games", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 8, tp);
        c.drawText("Kyrise", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 9.5f, tp);
        c.drawText("Robert Brooks", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 11, tp);
        c.drawText("CarterArt", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 12.5f, tp);
        c.drawText("www.icon-icons.com", anchoPantalla/32 * 4.5f, altoPantalla / 16 * 14, tp);

        tp.setTextSize(altoPantalla/12);
        c.drawText("ESTILO DE FUENTE", anchoPantalla/32 * 16, altoPantalla / 16 * 5, tp);
        tp.setTextSize(altoPantalla/15);
        c.drawText("Heaven castro", anchoPantalla/32 * 16, altoPantalla / 16 * 6.5f, tp);

        tp.setTextSize(altoPantalla/12);
        c.drawText("MÚSICA", anchoPantalla/32 * 16, altoPantalla / 16 * 9, tp);
        tp.setTextSize(altoPantalla/15);
        c.drawText("www.mixkit.co", anchoPantalla/32 * 16, altoPantalla / 16 * 10.5f, tp);
        c.drawText("www.freesoundslibrary.com", anchoPantalla/32 * 16, altoPantalla / 16 * 12, tp);
        c.drawText("www.soundeffectsplus.com", anchoPantalla/32 * 16, altoPantalla / 16 * 13.5f, tp);
        c.drawText("www.bensound.com", anchoPantalla/32 * 16, altoPantalla / 16 * 15, tp);

    }
}
