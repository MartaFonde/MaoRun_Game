package com.example.juego.MenuEscenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.MenuPpal.MenuOpciones;
import com.example.juego.Pantalla;

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

    public Paint pAct;
    public Paint pDesact;

    Bitmap atrasbtmp;
    RectF rectAtras;

    public PauseOpciones(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        pAct = new Paint();
        pAct.setColor(Color.GREEN);
        pDesact = new Paint();
        pDesact.setColor(Color.RED);
        setRect();
        tp.setARGB(225,129,157,80);

        atrasbtmp = Pantalla.escala(context, "menu/menu_atras.png", anchoPantalla/32*2, altoPantalla/16 *2);
        tp.setARGB(250,233,217,168);
        setRect();

        sonidoActBitmap = Pantalla.escala(context, "opciones/sonido_activado.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        sonidoDesactBitmap = Pantalla.escala(context, "opciones/sonido_desactivado.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        vibracionActBitmap = Pantalla.escala(context, "opciones/vibracion_activada.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        vibracionDesactBitmap = Pantalla.escala(context, "opciones/vibracion_desactivada.png",
                anchoPantalla/32*2, altoPantalla/16*2);
    }

    /**
     * Dibuja sobre lienzo los iconos de activación/desactivación de sonido. música y vibración y el
     * texto indicativo.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        //super.dibuja(c);
        //c.drawRect(rectAtras, null);
        //c.drawBitmap(atrasbtmp, anchoPantalla/32*8, altoPantalla/16*2, null);

        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTextSize(altoPantalla/15);
        c.drawText("OPCIONES", anchoPantalla/2, altoPantalla/16 * 3.5f, tp);

        tp.setTextAlign(Paint.Align.LEFT);
        tp.setTextSize(altoPantalla/18);
        c.drawText("SONIDO", anchoPantalla/32 * 10, altoPantalla / 16 * 6, tp);
        c.drawBitmap(sonidoActBitmap, rectSonAct.left, rectSonAct.top, null);
        c.drawBitmap(sonidoDesactBitmap, rectSonDesact.left, rectSonDesact.top, null);

        c.drawText("MÚSICA", anchoPantalla/32 * 10, altoPantalla / 16 * 9, tp);
        c.drawBitmap(sonidoActBitmap, rectMusicaAct.left, rectMusicaAct.top, null);
        c.drawBitmap(sonidoDesactBitmap, rectMusicaDesact.left, rectMusicaDesact.top, null);


        c.drawText("VIBRACIÓN", anchoPantalla/32 * 10, altoPantalla / 16 * 12, tp);
        c.drawBitmap(vibracionActBitmap, rectVibracionAct.left, rectVibracionAct.top, null);
        c.drawBitmap(vibracionDesactBitmap, rectVibracionDesact.left, rectVibracionDesact.top, null);
    }

    /**
     * Inicializa los rect de activación/desactivación de sonido. música y vibración y el de retroceso
     */
    public void setRect() {
        rectAtras = new RectF(anchoPantalla / 32 *8, altoPantalla/16*2, anchoPantalla/32*10, altoPantalla/16*4);

        rectSonAct = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 18, altoPantalla/16 * 6.5f);
        rectSonDesact = new RectF(anchoPantalla/32 * 20, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 22, altoPantalla/16 * 6.5f);
        rectMusicaAct = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 7.5f, anchoPantalla/32 * 18, altoPantalla/16 * 9.5f);
        rectMusicaDesact = new RectF(anchoPantalla/32 * 20, altoPantalla / 16 * 7.5f, anchoPantalla/32 * 22, altoPantalla/16 * 9.5f);
        rectVibracionAct = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 10.5f, anchoPantalla/32 * 18, altoPantalla/16 * 12.5f);
        rectVibracionDesact = new RectF(anchoPantalla/32 * 20, altoPantalla / 16 * 10.5f, anchoPantalla/32 * 22, altoPantalla/16 * 12.5f);
    }

    /**
     * Obtiene la coordenada de las pulsaciones. Si se presiona sobre algún rect de activación/
     * desactivación se gestiona la acción en la clase padre MenuOpciones. Comprueba si se
     * presiona sobre el rect de retroceso y si es así se vuelve a pantalla PauseMenu mediante el
     * retorno de su número de pantalla.
     * @param event
     * @return 10 para volver a pantalla PauseMenu, -1 en caso contrario
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int accion = event.getAction();
        if(accion == MotionEvent.ACTION_DOWN){
            if (rectAtras.contains(x, y)) {
                return 9;
            } else {                     //non touch btnAtras
                if (rectSonAct.contains(x, y)) {
                    if (!JuegoSV.sonido) JuegoSV.sonido = true;
                    //Toast.makeText(context, "sonidoAct"+JuegoSV.sonidoAct, Toast.LENGTH_SHORT).show();
                    //return 0;
                } else if (rectSonDesact.contains(x, y)) {
                    if (JuegoSV.sonido) JuegoSV.sonido = false;
                    //Toast.makeText(context, "sonidoAct"+JuegoSV.sonidoAct, Toast.LENGTH_SHORT).show();
                    // return 0;
                } else if (rectMusicaAct.contains(x, y)) {    //TODO
                    if (!JuegoSV.musica) {
                        JuegoSV.musica = true;
                        JuegoSV.mediaPlayer.start();
                    }
                    ///Toast.makeText(context, "musicaAct"+JuegoSV.musicaAct, Toast.LENGTH_SHORT).show();
                    //return 0;
                } else if (rectMusicaDesact.contains(x, y)) {     //TODO
                    if (JuegoSV.musica) {
                        JuegoSV.musica = false;
                        JuegoSV.mediaPlayer.pause();
                    }
                    //Toast.makeText(context, "musicaAct"+JuegoSV.musicaAct, Toast.LENGTH_SHORT).show();
                    //return 0;
                } else if (rectVibracionAct.contains(x, y)) {
                    if (!JuegoSV.vibracion) JuegoSV.vibracion = true;
                    //Toast.makeText(context, "vibracionAct"+JuegoSV.vibracionAct, Toast.LENGTH_SHORT).show();
                    //return 0;
                } else if (rectVibracionDesact.contains(x, y)) {
                    if (JuegoSV.vibracion) JuegoSV.vibracion = false;
                    //Toast.makeText(context, "vibracionAct"+JuegoSV.vibracionAct, Toast.LENGTH_SHORT).show();
                    // return 0;
                }

            }
        }
        return -1;
    }
}
