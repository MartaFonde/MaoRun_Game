package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.maorungame.JuegoSV;
import com.example.maorungame.R;

public class MenuPrincipal extends Menu {

    /**
     * Botón de Jugar
     */
    RectF btnJugar;

    /**
     * Botón de Récords
     */
    RectF btnRecords;

    /**
     * Botón de Créditos
     */
    RectF btnCreditos;

    /**
     * Botón de Ayuda
     */
    RectF btnAyuda;

    /**
     * Botón de Opciones
     */
    RectF btnOpciones;

    /**
     * Botón de Salir
     */
    RectF btnSalir;

    /**
     * Construye la pantalla del menú principal a partir de unas dimensiones ancho y alto de pantalla
     * y de un número identificativo. Llama a la función encargada de crear los rect de los botones de
     * las opciones de menú. Si la música está activada y si no se crea esta pantalla a partir de alguna
     * opción del menú, mediante el retroceso, se reproduce la música del menú, en bucle y con el volumen
     * preestablecido del sistema para la música.
     * @param context contexto
     * @param anchoPantalla ancho de pantalla
     * @param altoPantalla alto de pantalla
     * @param numPantalla número identificativo de la pantalla
     */
    public MenuPrincipal(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        setBotonesRect();
        if(JuegoSV.musica  && JuegoSV.resetMusic) {
            JuegoSV.mediaPlayer = MediaPlayer.create(context, R.raw.bensound_highoctane);
            JuegoSV.mediaPlayer.setLooping(true);
            JuegoSV.volumen = JuegoSV.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            JuegoSV.mediaPlayer.setVolume(JuegoSV.volumen, JuegoSV.volumen );
            JuegoSV.mediaPlayer.start();
        }
    }

    /**
     * Dibuja los rect de los botones de las opciones de menú y el texto asociado, y el fondo.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawRect(btnJugar, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.jugar).toString(), anchoPantalla / 2, altoPantalla / 16 * 2.5f, tpBeige);
        c.drawRect(btnRecords, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.records).toString(), anchoPantalla/2, altoPantalla / 16 * 5, tpBeige);
        c.drawRect(btnCreditos, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.creditos).toString(), anchoPantalla/2, altoPantalla /16 * 7.5f , tpBeige);
        c.drawRect(btnAyuda, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.ayuda).toString(), anchoPantalla/2, altoPantalla /16 * 10, tpBeige);
        c.drawRect(btnOpciones, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.opciones).toString(), anchoPantalla/2, altoPantalla /16 * 12.5f, tpBeige);
        c.drawRect(btnSalir, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.salir).toString(), anchoPantalla/2, altoPantalla /16 * 15 , tpBeige);
    }

    /**
     * Obtiene las coordenadas de la pulsación y comprueba si algún rect de las opciones las contiene.
     * Si hay alguno que las contenga, en JuegoSV se llevará a cabo el cambio de Pantalla a la pantalla
     * correspondiente al rect.
     * @param event evento
     * @return número de la nueva pantalla o -1 si ningún rect contiene las coordenadas y no se cambia
     * pantalla.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x= event.getX();
        float y= event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(btnRecords.contains(x,y)){
                return 2;
            } else if(btnCreditos.contains(x,y)){
                return 3;
            } else if(btnAyuda.contains(x,y)){
                return 4;
            } else if(btnOpciones.contains(x,y)){
                return 5;
            } else if(btnJugar.contains(x,y)){
                if(JuegoSV.mediaPlayer != null)  JuegoSV.mediaPlayer.stop();
                return 6;
            } else if(btnSalir.contains(x,y)){
                if(JuegoSV.mediaPlayer != null) JuegoSV.mediaPlayer.stop();
                return 0;
            }
        }
        return super.onTouchEvent(event);       //-1
    }

    /**
     * Crea los rect de los botones que representan los botones de las opciones de menú.
     */
    public void setBotonesRect(){
        btnJugar = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 3);
        btnRecords = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 3.5f,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 5.5f);
        btnCreditos = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 6,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 8);
        btnAyuda = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 8.5f,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 10.5f);
        btnOpciones = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 *11,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 13);
        btnSalir = new RectF(anchoPantalla / 32 * 8, altoPantalla / 16 * 13.5f,
                anchoPantalla / 32 * 24, altoPantalla / 16 * 15.5f);
    }
}
