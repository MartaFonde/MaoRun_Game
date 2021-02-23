package com.example.juego.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Menu;

public class MenuPrincipal extends Menu {

    RectF btnJugar;
    RectF btnRecords;
    RectF btnCreditos;
    RectF btnAyuda;
    RectF btnOpciones;
    RectF btnSalir;

    public MenuPrincipal(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        setBotonesRect();

        if(JuegoSV.musica  && JuegoSV.restartMusica) {
            JuegoSV.mediaPlayer.start();
        }
    }

    /**
     * Dibuja botones (rect) de las opciones de menú
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        dibujaBotones(c);
    }

    /**
     * Obtiene las coordenadas de la pulsación y comprueba si algún rect de las opciones las contiene.
     * Si hay alguno que las contenga, en JuegoSV se llevará a cabo el cambioPantalla a la pantalla
     * correspondiente al rect. El valor devuelto es el número de la nueva pantalla.
     * @param event
     * @return 6 si se presiona en el rect de btnJugar, 3 si se presiona en el rect de btnCreditos,
     * 5 si se presiona en el rect de btnOpciones, 4 si se hace en rect de btnAyuda y -10 si se hace
     * en btnSalir.
     * Representan el número de pantalla.
     * Si ningún rect contiene las coordenadas, devuelve -1.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x= event.getX();
        float y= event.getY();
        int accion = event.getAction();
        switch (accion){
            case MotionEvent.ACTION_DOWN:
                if(btnJugar.contains(x,y)){
                    JuegoSV.mediaPlayer.stop();
                    JuegoSV.mediaPlayer.release();
                    return 6;
                }else if(btnCreditos.contains(x,y)){
                    return 3;
                }else if(btnOpciones.contains(x,y)){
                    return 5;
                }else if(btnAyuda.contains(x,y)){
                    return 4;
                }else if(btnSalir.contains(x,y)){
                    JuegoSV.mediaPlayer.stop();
                    return 0;
                }else if(btnRecords.contains(x,y)){
                    return 2;
                }
        }
        return super.onTouchEvent(event);       //-1
    }

    /**
     * Instancia los rect que representan los botones de las opciones de menú
     */
    public void setBotonesRect(){
        btnJugar = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16, anchoPantalla / 32 * 24, altoPantalla / 16 * 3);
        btnRecords = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 3.5f, anchoPantalla / 32 * 24, altoPantalla / 16 * 5.5f);
        btnCreditos = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 6, anchoPantalla / 32 * 24, altoPantalla / 16 * 8);
        btnAyuda = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 8.5f, anchoPantalla / 32 * 24, altoPantalla / 16 * 10.5f);
        btnOpciones = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 *11, anchoPantalla / 32 * 24, altoPantalla / 16 * 13);
        btnSalir = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 13.5f, anchoPantalla / 32 * 24, altoPantalla / 16 * 15.5f);
    }

    /**
     * Dibuja los rect de las opciones del menú y su texto identificativo
     * @param c lienzo
     */
    public void dibujaBotones(Canvas c){
        c.drawRect(btnJugar, pBotonMenu);
        c.drawText("JUGAR", anchoPantalla / 2, altoPantalla / 16 * 2.5f, tpBeige);
        c.drawRect(btnRecords, pBotonMenu);
        c.drawText("RECORDS", anchoPantalla/2, altoPantalla / 16 * 5, tpBeige);
        c.drawRect(btnCreditos, pBotonMenu);
        c.drawText("CRÉDITOS", anchoPantalla/2, altoPantalla /16 * 7.5f , tpBeige);
        c.drawRect(btnAyuda, pBotonMenu);
        c.drawText("AYUDA", anchoPantalla/2, altoPantalla /16 * 10, tpBeige);
        c.drawRect(btnOpciones, pBotonMenu);
        c.drawText("OPCIONES", anchoPantalla/2, altoPantalla /16 * 12.5f, tpBeige);
        c.drawRect(btnSalir, pBotonMenu);
        c.drawText("SALIR", anchoPantalla/2, altoPantalla /16 * 15 , tpBeige);
    }
}
