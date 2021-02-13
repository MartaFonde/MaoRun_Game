package com.example.juego;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Creditos extends Menu{


    public Creditos(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
    }


    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);

        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTextSize(altoPantalla/10);
        c.drawText("AGRADECIMIENTOS", anchoPantalla/2, altoPantalla/16 * 2, tp);

        tp.setTextAlign(Paint.Align.LEFT);

        tp.setTextSize(altoPantalla/12);
        c.drawText("IMÁGENES", anchoPantalla/32 * 4, altoPantalla / 16 * 5, tp);
        tp.setTextSize(altoPantalla/15);
        c.drawText("D.Paige",anchoPantalla/32 * 4, altoPantalla / 16 * 6.5f, tp);
        c.drawText("La Red Games", anchoPantalla/32 * 4, altoPantalla / 16 * 8, tp);
        c.drawText("Kyrise", anchoPantalla/32 * 4, altoPantalla / 16 * 9.5f, tp);
        c.drawText("Robert Brooks", anchoPantalla/32 * 4, altoPantalla / 16 * 11, tp);
        c.drawText("CarterArt", anchoPantalla/32 * 4, altoPantalla / 16 * 12.5f, tp);

        tp.setTextSize(altoPantalla/12);
        c.drawText("ESTILO DE FUENTE", anchoPantalla/32 * 16, altoPantalla / 16 * 5, tp);
        tp.setTextSize(altoPantalla/15);
        c.drawText("Heaven castro", anchoPantalla/32 * 16, altoPantalla / 16 * 6.5f, tp);

        tp.setTextSize(altoPantalla/12);
        c.drawText("MÚSICA", anchoPantalla/32 * 16, altoPantalla / 16 * 10, tp);

    }
}
