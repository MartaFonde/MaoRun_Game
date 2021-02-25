package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Pantalla;

public class PauseMenu extends Pantalla {

    RectF btnVolver;
    RectF btnOpciones;
    RectF btnAyuda;
    RectF btnMenuPpal;

    public PauseMenu(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        setRectBotones();
        tpBeige.setTextSize(altoPantalla/12);
    }

    /**
     * Dibuja sobre lienzo los rect botones y el texto indicativo.
     * @param c
     */
    @Override
    public void dibuja(Canvas c) {
        c.drawRect(btnVolver, pBotonVerde);
        c.drawText("Volver", anchoPantalla/2, altoPantalla/16*4, tpBeige);
        c.drawRect(btnOpciones, pBotonVerde);
        c.drawText("Opciones", anchoPantalla/2, altoPantalla/16*7, tpBeige);
        c.drawRect(btnAyuda, pBotonVerde);
        c.drawText("Ayuda", anchoPantalla/2, altoPantalla/16*10, tpBeige);
        c.drawRect(btnMenuPpal, pBotonVerde);
        c.drawText("Menú principal", anchoPantalla/2, altoPantalla/16*13, tpBeige);
    }

    /**
     * Inicializa los rect de botones de las opciones del menú.
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
     * Obtiene las coordenadas de pulsación y si algún rect contiene la coordenadas devuelve el
     * número de pantalla correspondiente a la opción seleccionada. Se gestiona en la clase
     * PauseEscena el cambio de pantalla.
     * @param event evento
     * @return 0 para retomar el juego, 11 para cambiar pantalla a PauseOpciones,
     * 12 para cambiar pantalla a PauseAyuda o 1 para cambiar pantalla a MenuPrincipal.
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
                JuegoSV.restartMusica = true;
                return 1;
            }
        }
        return -1;
    }
}
