package com.example.juego.MenuPpal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Pantalla;

abstract public class Menu extends Pantalla {
    protected Context context;
    protected RectF btnAtras;
    protected Bitmap atrasBitmap;

    public Menu(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        this.context = context;

        if(numPantalla != 1){
            btnAtras = new RectF(0, altoPantalla/16 * 13, anchoPantalla / 32 * 3,
                    altoPantalla);
            atrasBitmap = Pantalla.escala(context, "menu/menu_atras.png",
                    anchoPantalla / 32 * 3, altoPantalla/16*3);
        }
    }

    /**
     * Dibuja en el lienzo un fondo negro y un bitmap de retroceso, excepto en la pantalla MenuPrincipal
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c){
        c.drawColor(Color.BLACK);
        if(numPantalla != 1){       //1 menú ppal
            c.drawBitmap(atrasBitmap, 0, altoPantalla/16*13, null);
        }
    }

    /**
     * Obtiene coordenadas de pulsaciones al tocar pantalla. Si la pantalla actual no es el menú
     * principal (numPantalla 1), es una opción del menú que contiene un botón de retroceso y si
     * rect del botón contiene las coordenadas x,y retorna al menú principal. Para que el mediaPlayer
     * no vuelva a reproducir la música, pone la booleana restartMusica a false.
     * @param event evento de pulsación
     * @return devuelve 1 para volver al menú principal. En caso contrario, o si la pantalla actual es
     * el menú principal, devuelve el número de la pantalla actual.
     */
    public int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();

        if(numPantalla != 1 && btnAtras.contains(x,y)) {
            JuegoSV.restartMusica = false; //para que no se reproduzca de nuevo la música (se solapa con la que ya suena)
            return 1;       //vuelve a menu principal
        }
        return numPantalla;
    }
}
