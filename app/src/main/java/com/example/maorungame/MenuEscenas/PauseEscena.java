package com.example.maorungame.MenuEscenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.maorungame.Pantalla;
import com.example.maorungame.R;

public class PauseEscena extends Pantalla {

    /**
     * Pantalla actual del menú de pausa
     */
    Pantalla pantallaPauseActual;

    /**
     * Número identificativo de la nueva pantalla
     */
    int numPantallaNueva;

    /**
     * Botón de retroceso
     */
    protected RectF rectAtras;

    /**
     * Imagen del botón de retroceso
     */
    protected Bitmap atrasbtmp;


    /**
     * Contruye la pantalla que gestiona el cambio de pantallas del menú de pausa de Escena a partir de
     * unas dimensiones ancho y alto de pantalla y de un número identificativo. Crea y establece
     * el color de fondo que se usa en las pantallas del menú y dimensiona la letra.
     * Define el rect del fondo, el de botón de retroceso y reescala la imagen que usará dicho botón.
     * Instancia la pantalla actual como el menú de pausa. Será la que primero se verá al crear esta clase.
     * @param context contexto
     * @param anchoPantalla ancho de pantalla
     * @param altoPantalla alto de pantalla
     * @param numPantalla número identificativo de la pantalla
     */
    public PauseEscena(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        pFondo.setAlpha(210);

        tpBeige.setTextSize(altoPantalla/12);

        rectFondo = new RectF(anchoPantalla/32 * 8, altoPantalla/16 * 2,
                anchoPantalla/32*24, altoPantalla/16 * 14);
        rectAtras = new RectF(anchoPantalla / 32 *8, altoPantalla/16*2,
                anchoPantalla/32*10, altoPantalla/16*4);
        atrasbtmp = Pantalla.escala(context, "menu/menu_atras.png",
                anchoPantalla/32*2, altoPantalla/16 *2);

        pantallaPauseActual = new PauseMenu(context, anchoPantalla, altoPantalla, 10);
    }

    /**
     * Dibuja rect de fondo oscuro que ocupa parte central de la pantalla, sobre fondo transparente y
     * si la pantalla no es el menú de pausa dibuja botón de retroceso. Sobre esto dibuja lo descrito
     * en la función dibuja de pantallaActual.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawColor(Color.TRANSPARENT);
        c.drawRect(rectFondo, pFondo);

        if(pantallaPauseActual.numPantalla != 10){
            c.drawBitmap(atrasbtmp, anchoPantalla/32*8, altoPantalla/16*2, null);
        }
        pantallaPauseActual.dibuja(c);
    }

    /**
     * Toma el valor devuelto por la pantallaActual cuando se toca la pantalla, y dependiendo de éste
     * se produce un cambio de pantalla a menú principal, se retoma la Escena o se cambia de pantalla
     * del menú de pausa.
     * @param event evento
     * @return 1 para cambiar pantalla a menú principal, 0 para reanudar Escena y -1 en el resto de
     * los casos
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
       if(event.getAction() == MotionEvent.ACTION_DOWN){
            numPantallaNueva = pantallaPauseActual.onTouchEvent(event);
            if(numPantallaNueva == 1) return 1; //mnu ppal
            if(numPantallaNueva == 0) return 0; //vuelve a escena
            if(numPantallaNueva != -1 ) cambiaPantalla(numPantallaNueva);
       }
       return -1;
    }

    /**
     * Gestiona el control de pantalla del menú de pausa de Escena, instanciando la pantalla correspondiente
     * al número identificativo pasado como parámetro y asignándola a la pantalla actual.
     * @param num número identificativo de la pantalla nueva
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
