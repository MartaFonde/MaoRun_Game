package com.example.juego.MenuPpal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Menu;
import com.example.juego.Pantalla;

public class MenuOpciones extends Menu {

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

    public Paint p;


    public MenuOpciones(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        p = new Paint();
        p.setAlpha(180);
        p.setColor(Color.argb(250,233,217,168));    //beige
        setRect();
        tp.setARGB(225,129,157,80); //verde

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
     * Dibuja sobre lienzo texto que representa las opciones de sonido, música y vibración,
     * y los botones para su activación y desactivación.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);    //fondo+btnAtras
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setTextSize(altoPantalla/10);
        c.drawText("OPCIONES", anchoPantalla/2, altoPantalla/16 * 2, tp);

        tp.setTextAlign(Paint.Align.LEFT);
        tp.setTextSize(altoPantalla/12);
        c.drawText("SONIDO", anchoPantalla/32 * 10, altoPantalla / 16 * 6, tp);
        c.drawBitmap(sonidoActBitmap, rectSonAct.left, rectSonAct.top, p);
        c.drawBitmap(sonidoDesactBitmap, rectSonDesact.left, rectSonDesact.top, p);

        c.drawText("MÚSICA", anchoPantalla/32 * 10, altoPantalla / 16 * 10, tp);
        c.drawBitmap(sonidoActBitmap, rectMusicaAct.left, rectMusicaAct.top, p);
        c.drawBitmap(sonidoDesactBitmap, rectMusicaDesact.left, rectMusicaDesact.top, p);

        c.drawText("VIBRACIÓN", anchoPantalla/32 * 10, altoPantalla / 16 * 14, tp);
        c.drawBitmap(vibracionActBitmap, rectVibracionAct.left, rectVibracionAct.top, p);
        c.drawBitmap(vibracionDesactBitmap, rectVibracionDesact.left, rectVibracionDesact.top, p);
    }

    /**
     * Gestiona la pulsación de un dedo sobre pantalla. Si se pulsa sobre botón de retroceso vuelve
     * a la pantalla Menú principal. Si las coordenadas de la pulsación están contenidas en algún
     * botón (rect) creado para activar o desactivar las diferentes opciones, cambia el valor de la
     * opción correspondiente, si tiene lugar, contenida en su respectiva variable en la clase JuegoSV
     * @param event
     * @return devuelve 1 si se pulsa el botón de retroceso, que hace retornar a Menú Principal.
     * Si se pulsa en cualquier otro punto, devuelve -1
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();
        int accion = event.getAction();
        switch (accion){
            case MotionEvent.ACTION_DOWN:
                int aux=super.onTouchEvent(event);  //se se pulsa btnAtras volve a mnu ppal -- menu return 1
                if (aux == 1){
                    return aux;
                }else{
                //if(aux!= 1){        //non touch btnAtras
                    if(rectSonAct.contains(x,y)){
                        if(!JuegoSV.sonido) JuegoSV.sonido = true;
                        //Toast.makeText(context, "sonidoAct"+JuegoSV.sonidoAct, Toast.LENGTH_SHORT).show();
                    } else if(rectSonDesact.contains(x,y)) {
                        if(JuegoSV.sonido) JuegoSV.sonido = false;
                        //Toast.makeText(context, "sonidoAct"+JuegoSV.sonidoAct, Toast.LENGTH_SHORT).show();
                    } else if(rectMusicaAct.contains(x,y)) {    //TODO
                        if(!JuegoSV.musica){
                            JuegoSV.musica = true;
                            JuegoSV.mediaPlayer.start();
                        }
                        ///Toast.makeText(context, "musicaAct"+JuegoSV.musicaAct, Toast.LENGTH_SHORT).show();
                    } else if(rectMusicaDesact.contains(x,y)) {     //TODO
                        if(JuegoSV.musica){
                            JuegoSV.musica = false;
                            JuegoSV.mediaPlayer.pause();
                        }
                        //Toast.makeText(context, "musicaAct"+JuegoSV.musicaAct, Toast.LENGTH_SHORT).show();
                    } else if(rectVibracionAct.contains(x,y)) {
                        if(!JuegoSV.vibracion) JuegoSV.vibracion = true;
                        //Toast.makeText(context, "vibracionAct"+JuegoSV.vibracionAct, Toast.LENGTH_SHORT).show();
                    } else if(rectVibracionDesact.contains(x,y)) {
                        if(JuegoSV.vibracion) JuegoSV.vibracion = false;
                        //Toast.makeText(context, "vibracionAct"+JuegoSV.vibracionAct, Toast.LENGTH_SHORT).show();
                    }
                }
            break;
        }
        return -1;
    }

    /**
     * Asigna los rect que se corresponden con la activación y desactivación de las diferentes opciones
     */
    public void setRect(){
        rectSonAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 20, altoPantalla/16 * 6.5f);
        rectSonDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 4.5f, anchoPantalla/32 * 24, altoPantalla/16 * 6.5f);
        rectMusicaAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 8.5f, anchoPantalla/32 * 20, altoPantalla/16 * 10.5f);
        rectMusicaDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 8.5f, anchoPantalla/32 * 24, altoPantalla/16 * 10.5f);
        rectVibracionAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 12.5f, anchoPantalla/32 * 20, altoPantalla/16 * 14.5f);
        rectVibracionDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 12.5f, anchoPantalla/32 * 24, altoPantalla/16 * 14.5f);
    }
}
