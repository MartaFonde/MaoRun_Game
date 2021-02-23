package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.Pantalla;

public class PauseEscena extends Pantalla {

    Pantalla pantallaPauseActual;
    int numPantallaNueva;
    protected Paint pFondo;
    RectF rectFondo;
    Bitmap atrasbtmp;
    RectF rectAtras;

    public PauseEscena(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        pFondo = new Paint();
        pFondo.setColor(Color.argb(210, 50,50,50));

        tpBeige.setARGB(250,233,217,168);
        tpBeige.setTextSize(altoPantalla/12);
        tpBeige.setTextAlign(Paint.Align.CENTER);

        rectFondo = new RectF(anchoPantalla/32 * 8, altoPantalla/16 * 2, anchoPantalla/32*24, altoPantalla/16 * 14);
        rectAtras = new RectF(anchoPantalla / 32 *8, altoPantalla/16*2, anchoPantalla/32*10, altoPantalla/16*4);
        atrasbtmp = Pantalla.escala(context, "menu/menu_atras.png", anchoPantalla/32*2, altoPantalla/16 *2);

        pantallaPauseActual = new PauseMenu(context, anchoPantalla, altoPantalla, 10);
    }

    /**
     * Dibuja rect oscuro que ocupa parte central de la pantalla, sobre fondo transparente y
     * si la pantalla no es PauseMenu dibuja botón de retroceso. Sobre esto dibuja lo descrito
     * en la función dibuja de pantallaActual
     * @param c
     */
    @Override
    public void dibuja(Canvas c) {
        c.drawColor(Color.TRANSPARENT); //fondo transparente
        c.drawRect(rectFondo, pFondo);
        if(pantallaPauseActual.numPantalla != 10){
            c.drawBitmap(atrasbtmp, anchoPantalla/32*8, altoPantalla/16*2, null);
        }
        pantallaPauseActual.dibuja(c);
    }

    /**
     * Obtiene las coordenadas de pulsación. Si el valor devuelto de pantallaActual es 1,
     * devuelve 1 y se produce un cambio de pantalla a Pantalla MenuPrincipal,
     * si el valor es 0, devuelve 0 que en Escena finalizaría la pausa del juego.
     * Si el valor es distinto a -1, se cambia de pantalla al valor devuelto. Se retorna
     *  -1 cuando la pulsación no implica ninguna acción concreta de cambio de pantalla.
     * @param event
     * @return 1 para cambiar pantalla a MenuPrincipal, 0 para retomar Escena y -1 en el resto de
     * los casos
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        int accion = event.getAction(); // Solo gestiona la pulsación de un dedo.
        int x=(int)event.getX();
        int y=(int)event.getY();

        switch (accion){
            case MotionEvent.ACTION_DOWN:
                numPantallaNueva = pantallaPauseActual.onTouchEvent(event);
                if(numPantallaNueva == 1) return 1; //mnu ppal
                if(numPantallaNueva == 0) return 0;         //volve a escena
                if(numPantallaNueva != -1 ) cambiaPantalla(numPantallaNueva);
        }
        return -1;
    }

    /**
     * Gestiona el control de pantalla del menú de pause de la Escena
     * @param num número de pantalla nueva
     */
    private void cambiaPantalla(int num){
        switch (num){
            case 10 : pantallaPauseActual = new PauseMenu(context, anchoPantalla, altoPantalla, 10);
                break;
            case 11 : pantallaPauseActual = new PauseOpciones(context, anchoPantalla, altoPantalla, 11);
                break;
            case 12: pantallaPauseActual = new PauseAyuda(context, anchoPantalla, altoPantalla, 12);
                break;
        }
    }
}
