package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Pantalla;
import com.example.juego.R;

import java.io.FileOutputStream;

public class PauseOpciones extends PauseEscena {

    public RectF rectSonAct;
    public RectF rectSonDesact;
    public RectF rectMusicaAct;
    public RectF rectMusicaDesact;
    public RectF rectVibracionAct;
    public RectF rectVibracionDesact;

    Bitmap sonidoActBitmap;
    Bitmap sonidoDesactBitmap;
    Bitmap vibracionActBitmap;
    Bitmap vibracionDesactBitmap;

    Bitmap atrasbtmp;
    RectF rectAtras;
    Paint pCircle;
    boolean cambios;

    public PauseOpciones(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        cambios = false;
        pCircle = new Paint();
        pCircle.setColor(Color.argb(150, 255, 128, 128));

        atrasbtmp = Pantalla.escala(context, "menu/menu_atras.png",
                anchoPantalla/32*2, altoPantalla/16 *2);

        sonidoActBitmap = Pantalla.escala(context, "opciones/sonido_activado.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        sonidoDesactBitmap = Pantalla.escala(context, "opciones/sonido_desactivado.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        vibracionActBitmap = Pantalla.escala(context, "opciones/vibracion_activada.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        vibracionDesactBitmap = Pantalla.escala(context, "opciones/vibracion_desactivada.png",
                anchoPantalla/32*2, altoPantalla/16*2);

        setRect();
    }

    /**
     * Dibuja sobre lienzo los iconos de activación/desactivación de sonido. música y vibración y el
     * texto indicativo.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        tpBeige.setTextAlign(Paint.Align.CENTER);
        tpBeige.setTextSize(altoPantalla/15);
        c.drawText("OPCIONES", anchoPantalla/2, altoPantalla/16 * 3.5f, tpBeige);

        tpBeige.setTextAlign(Paint.Align.LEFT);
        tpBeige.setTextSize(altoPantalla/18);

        c.drawText("SONIDO", anchoPantalla/32 * 10, altoPantalla / 16 * 6, tpBeige);
        if(JuegoSV.sonido) c.drawCircle( rectSonAct.left + (rectSonAct.right - rectSonAct.left)/2,
                rectSonAct.top + (rectSonAct.bottom-rectSonAct.top)/2, altoPantalla/16, pCircle);
        else c.drawCircle(rectSonDesact.left + (rectSonDesact.right - rectSonDesact.left)/2,
                rectSonDesact.top + (rectSonDesact.bottom-rectSonDesact.top)/2, altoPantalla/16, pCircle);
        c.drawBitmap(sonidoActBitmap, rectSonAct.left, rectSonAct.top, null);
        c.drawBitmap(sonidoDesactBitmap, rectSonDesact.left, rectSonDesact.top, null);

        c.drawText("MÚSICA", anchoPantalla/32 * 10, altoPantalla / 16 * 9, tpBeige);
        if(JuegoSV.musica) c.drawCircle( rectMusicaAct.left + (rectMusicaAct.right - rectMusicaAct.left)/2,
                rectMusicaAct.top + (rectMusicaAct.bottom-rectMusicaAct.top)/2, altoPantalla/16, pCircle);
        else c.drawCircle(rectMusicaDesact.left + (rectMusicaDesact.right - rectMusicaDesact.left)/2,
                rectMusicaDesact.top + (rectMusicaDesact.bottom-rectMusicaDesact.top)/2, altoPantalla/16, pCircle);
        c.drawBitmap(sonidoActBitmap, rectMusicaAct.left, rectMusicaAct.top, null);
        c.drawBitmap(sonidoDesactBitmap, rectMusicaDesact.left, rectMusicaDesact.top, null);


        c.drawText("VIBRACIÓN", anchoPantalla/32 * 10, altoPantalla / 16 * 12, tpBeige);
        if(JuegoSV.vibracion) c.drawCircle( rectVibracionAct.left + (rectVibracionAct.right - rectVibracionAct.left)/2,
                rectVibracionAct.top + (rectVibracionAct.bottom-rectVibracionAct.top)/2, altoPantalla/16, pCircle);
        else c.drawCircle(rectVibracionDesact.left + (rectVibracionDesact.right - rectVibracionDesact.left)/2,
                rectVibracionDesact.top + (rectVibracionDesact.bottom-rectVibracionDesact.top)/2, altoPantalla/16, pCircle);
        c.drawBitmap(vibracionActBitmap, rectVibracionAct.left, rectVibracionAct.top, null);
        c.drawBitmap(vibracionDesactBitmap, rectVibracionDesact.left, rectVibracionDesact.top, null);
    }

    /**
     * Inicializa los rect de activación/desactivación de sonido. música y vibración y el de retroceso.
     */
    public void setRect() {
        rectAtras = new RectF(anchoPantalla / 32 *8, altoPantalla/16*2,
                anchoPantalla/32*10, altoPantalla/16*4);

        rectSonAct = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 4.5f,
                anchoPantalla/32 * 18, altoPantalla/16 * 6.5f);
        rectSonDesact = new RectF(anchoPantalla/32 * 20, altoPantalla / 16 * 4.5f,
                anchoPantalla/32 * 22, altoPantalla/16 * 6.5f);
        rectMusicaAct = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 7.5f,
                anchoPantalla/32 * 18, altoPantalla/16 * 9.5f);
        rectMusicaDesact = new RectF(anchoPantalla/32 * 20, altoPantalla / 16 * 7.5f,
                anchoPantalla/32 * 22, altoPantalla/16 * 9.5f);
        rectVibracionAct = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 10.5f,
                anchoPantalla/32 * 18, altoPantalla/16 * 12.5f);
        rectVibracionDesact = new RectF(anchoPantalla/32 * 20, altoPantalla / 16 * 10.5f,
                anchoPantalla/32 * 22, altoPantalla/16 * 12.5f);
    }

    /**
     * Obtiene la coordenada de las pulsaciones. Si se presiona sobre algún rect de activación/
     * desactivación se realizan los cambios. Si se presiona sobre el rect de retroceso y se han
     * hecho cambios llama a la función que guarda la configuración y se vuelve a pantalla PauseMenu.
     * @param event
     * @return 10 para volver a pantalla PauseMenu, -1 en caso contrario.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if (rectAtras.contains(x, y)) {
                if(cambios){
                    guardarConfig();
                }
                return 10;
            } else {                     //non touch btnAtras
                if (rectSonAct.contains(x, y)) {
                    if (!JuegoSV.sonido){
                        JuegoSV.sonido = true;
                        cambios = true;
                    }
                } else if (rectSonDesact.contains(x, y)) {
                    if (JuegoSV.sonido){
                        JuegoSV.sonido = false;
                        cambios = true;
                    }
                } else if (rectMusicaAct.contains(x, y)) {    //TODO
                    if (!JuegoSV.musica) {
                        JuegoSV.musica = true;
                        cambios = true;
                        JuegoSV.mediaPlayer= MediaPlayer.create(context, R.raw.city_ambience);
                        JuegoSV.volumen = JuegoSV.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        JuegoSV.mediaPlayer.setVolume(JuegoSV.volumen/3, JuegoSV.volumen/3);
                        JuegoSV.mediaPlayer.setLooping(true);
                        JuegoSV.mediaPlayer.start();
                    }
                } else if (rectMusicaDesact.contains(x, y)) {     //TODO
                    if (JuegoSV.musica) {
                        JuegoSV.musica = false;
                        cambios = true;
                        JuegoSV.mediaPlayer.stop();
                    }
                } else if (rectVibracionAct.contains(x, y)) {
                    if (!JuegoSV.vibracion){
                        JuegoSV.vibracion = true;
                        cambios = true;
                    }
                } else if (rectVibracionDesact.contains(x, y)) {
                    if (JuegoSV.vibracion) {
                        JuegoSV.vibracion = false;
                        cambios = true;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Guarda la configuración de sonido, música y vibración en el fichero config.
     */
    private void guardarConfig(){
        try(FileOutputStream fos = context.openFileOutput("config.txt", Context.MODE_PRIVATE)){
            fos.write((JuegoSV.sonido+"\n").getBytes());
            fos.write((JuegoSV.musica+"\n").getBytes());
            fos.write((JuegoSV.vibracion+"\n").getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
