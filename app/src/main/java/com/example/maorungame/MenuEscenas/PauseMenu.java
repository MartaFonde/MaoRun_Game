package com.example.maorungame.MenuEscenas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.maorungame.JuegoSV;
import com.example.maorungame.Pantalla;
import com.example.maorungame.R;

public class PauseMenu extends Pantalla {

    /**
     * Botón para reanudar la escena del juego
     */
    RectF btnVolver;

    /**
     * Botón de opciones
     */
    RectF btnOpciones;

    /**
     * Botón de ayuda
     */
    RectF btnAyuda;

    /**
     * Botón de menú principal
     */
    RectF btnMenuPpal;

    /**
     * Construye el menú de pausa de Escena a partir de unas dimensiones de ancho y alto de pantalla
     * y de un número identificativo. Llama a la función encargada de crear los rect de los botones de
     * las opciones del menú y redimensiona el tamaño de la letra.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     */
    public PauseMenu(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        setRectBotones();
        tpBeige.setTextSize(altoPantalla/12);
    }

    /**
     * Dibuja sobre lienzo los rect botones y el texto indicativo.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawRect(btnVolver, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.volver).toString(), anchoPantalla/2, altoPantalla/16*4, tpBeige);
        c.drawRect(btnOpciones, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.opciones).toString(), anchoPantalla/2, altoPantalla/16*7, tpBeige);
        c.drawRect(btnAyuda, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.ayuda).toString(), anchoPantalla/2, altoPantalla/16*10, tpBeige);
        c.drawRect(btnMenuPpal, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.menuPpal).toString(), anchoPantalla/2, altoPantalla/16*13, tpBeige);
    }

    /**
     * Crea los rect de botones de las opciones del menú.
     */
    public void setRectBotones(){
        btnVolver = new RectF(anchoPantalla/32 * 11, altoPantalla/16 * 2.5f,
                anchoPantalla/32*21, altoPantalla/16 * 4.5f);
        btnOpciones = new RectF(anchoPantalla/32 * 11, altoPantalla/16 * 5.5f,
                anchoPantalla/32*21, altoPantalla/16 * 7.5f);
        btnAyuda = new RectF(anchoPantalla/32 * 11, altoPantalla/16 * 8.5f,
                anchoPantalla/32*21, altoPantalla/16 * 10.5f);
        btnMenuPpal = new RectF(anchoPantalla/32 * 11, altoPantalla/16 * 11.5f,
                anchoPantalla/32*21, altoPantalla/16 * 13.5f);
    }

    /**
     * Obtiene las coordenadas de pulsación y si algún rect de los botones de opciones contiene las
     * coordenadas devuelve el número de pantalla correspondiente a la opción seleccionada.
     * El cambio de pantalla se gestiona en la clase PauseEscena.
     * @param event evento
     * @return 0 para retomar el juego, 11 para ir a las Opciones, 12 para ir a Ayuda o 1 para ir al menú principal.
     * Devuelve -1 si ningún rect contiene las coordenadas de pulsación.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(btnVolver.contains(x,y)){
                return 0;
            }else if(btnOpciones.contains(x,y)){
                return 11;
            }else if(btnAyuda.contains(x,y)){
                return 12;
            }else if(btnMenuPpal.contains(x,y)){
                JuegoSV.mediaPlayer.stop();
                JuegoSV.resetMusic = true;
                return 1;
            }
        }
        return -1;
    }
}
