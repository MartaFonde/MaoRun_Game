package com.example.maorungame.FinPartida;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.maorungame.JuegoSV;
import com.example.maorungame.Pantalla;

public class MenuFinPartida extends Pantalla {

    RectF btnRepetir;
    RectF btnMenuPpal;

    public MenuFinPartida(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
    }

    /**
     * Dibuja el fondo negro y los botones de jugar de nuevo y volver a menú principal con su texto
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        tpBeige.setTextSize(altoPantalla/10);
        btnRepetir = new RectF(anchoPantalla / 32 *  9.5f, altoPantalla/16*5,
                anchoPantalla / 32 *22.5f, altoPantalla/16*7);
        c.drawRect(btnRepetir, pBotonVerde);
        c.drawText("Volver a jugar", anchoPantalla / 2, altoPantalla/16 * 6.5f, tpBeige);
        btnMenuPpal = new RectF(anchoPantalla / 32 *  9.5f, altoPantalla/16*9,
                anchoPantalla / 32 *22.5f, altoPantalla/16*11);
        c.drawRect(btnMenuPpal, pBotonVerde);
        c.drawText("Menú principal", anchoPantalla / 2, altoPantalla/16 * 10.5f, tpBeige);
    }

    /**
     * Si las coordenadas de la pulsación están contenidas en el botón repetir, vuelve a la primera
     * escena del juego, si lo están en el botón de menú principal vuelve a este.
     * @param event evento
     * @return 6 para volver a Escena1 y 1 para ir al menú principal. -1 si las coordenadas no están
     * contenidas en ningún rect de botón.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(btnRepetir.contains(x, y)){
            JuegoSV.mediaPlayer.stop();
            return 6;
        }else if(btnMenuPpal.contains(x,y)){
            JuegoSV.restartMusica = false;
            return 1;
        }
        return super.onTouchEvent(event);
    }
}
