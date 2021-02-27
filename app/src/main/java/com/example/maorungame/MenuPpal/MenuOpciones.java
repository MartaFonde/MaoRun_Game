package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.maorungame.JuegoSV;
import com.example.maorungame.Pantalla;
import com.example.maorungame.R;

import java.io.FileOutputStream;

public class MenuOpciones extends Menu {

    RectF rectSonAct;
    RectF rectSonDesact;
    RectF rectMusicaAct;
    RectF rectMusicaDesact;
    RectF rectVibracionAct;
    RectF rectVibracionDesact;
    RectF rectReiniciarRecords;

    Bitmap sonidoActBitmap;
    Bitmap sonidoDesactBitmap;
    Bitmap vibracionActBitmap;
    Bitmap vibracionDesactBitmap;

    Paint p;
    Paint pCircle;

    boolean cambios;

    /**
     * Construye la pantalla de la opción de menú Opciones a partir de unas dimensiones ancho y alto de
     * pantalla y de un número identificativo. Crea el paint que se usará para los iconos de
     * activación y desativación de sonido, música y vibración, y el círculo que marcará el estado de
     * dicha configuración. Redimensiona las imágenes de los iconos.
     * Establece la alineación del texto y define su dimensión.
     * Llama a la función encargada de crear los rect de los botones de los iconos de sonido, música y
     * vibración y del botón de reiniciar los récords.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     */
    public MenuOpciones(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        //paint iconos de act y desact
        p = new Paint();
        p.setAlpha(180);
        p.setColor(Color.argb(200,233,217,168));    //beige
        //base de act o desact
        pCircle = new Paint();
        pCircle.setColor(Color.argb(150, 255, 128, 128));

        sonidoActBitmap = Pantalla.escala(context, "opciones/sonido_activado.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        sonidoDesactBitmap = Pantalla.escala(context, "opciones/sonido_desactivado.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        vibracionActBitmap = Pantalla.escala(context, "opciones/vibracion_activada.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        vibracionDesactBitmap = Pantalla.escala(context, "opciones/vibracion_desactivada.png",
                anchoPantalla/32*2, altoPantalla/16*2);

        cambios = false;    //si no hay cambios no se reescribe archivo config

        tpVerde.setTextAlign(Paint.Align.CENTER);
        tpVerde.setTextSize(altoPantalla/10);
        tpBeige.setTextSize(altoPantalla/12);
        setRect();
    }

    /**
     * Dibuja sobre lienzo texto que representa las opciones de sonido, música y vibración,
     * y los botones para su activación y desactivación, el fondo y el botón de retroceso.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);    //fondo+btnAtras

        c.drawText("OPCIONES", anchoPantalla/2, altoPantalla/16 * 2, tpVerde);

//        tpVerde.setTextAlign(Paint.Align.LEFT);
//        tpVerde.setTextSize(altoPantalla/12);
        tpBeige.setTextAlign(Paint.Align.LEFT);
        c.drawText("SONIDO", anchoPantalla/32 * 8, altoPantalla / 16 * 5, tpBeige);
        if(JuegoSV.sonido) c.drawCircle( rectSonAct.left + (rectSonAct.right - rectSonAct.left)/2,
                rectSonAct.top + (rectSonAct.bottom-rectSonAct.top)/2, altoPantalla/16, pCircle);
        else c.drawCircle(rectSonDesact.left + (rectSonDesact.right - rectSonDesact.left)/2,
                rectSonDesact.top + (rectSonDesact.bottom-rectSonDesact.top)/2, altoPantalla/16, pCircle);
        c.drawBitmap(sonidoActBitmap, rectSonAct.left, rectSonAct.top, p);
        c.drawBitmap(sonidoDesactBitmap, rectSonDesact.left, rectSonDesact.top, p);

        c.drawText("MÚSICA", anchoPantalla/32 * 8, altoPantalla / 16 * 8, tpBeige);
        if(JuegoSV.musica) c.drawCircle( rectMusicaAct.left + (rectMusicaAct.right - rectMusicaAct.left)/2,
                rectMusicaAct.top + (rectMusicaAct.bottom-rectMusicaAct.top)/2, altoPantalla/16, pCircle);
        else c.drawCircle(rectMusicaDesact.left + (rectMusicaDesact.right - rectMusicaDesact.left)/2,
                rectMusicaDesact.top + (rectMusicaDesact.bottom-rectMusicaDesact.top)/2, altoPantalla/16, pCircle);
        c.drawBitmap(sonidoActBitmap, rectMusicaAct.left, rectMusicaAct.top, p);
        c.drawBitmap(sonidoDesactBitmap, rectMusicaDesact.left, rectMusicaDesact.top, p);

        c.drawText("VIBRACIÓN", anchoPantalla/32 * 8, altoPantalla / 16 * 11, tpBeige);
        if(JuegoSV.vibracion) c.drawCircle( rectVibracionAct.left + (rectVibracionAct.right - rectVibracionAct.left)/2,
                rectVibracionAct.top + (rectVibracionAct.bottom-rectVibracionAct.top)/2, altoPantalla/16, pCircle);
        else c.drawCircle(rectVibracionDesact.left + (rectVibracionDesact.right - rectVibracionDesact.left)/2,
                rectVibracionDesact.top + (rectVibracionDesact.bottom-rectVibracionDesact.top)/2, altoPantalla/16, pCircle);
        c.drawBitmap(vibracionActBitmap, rectVibracionAct.left, rectVibracionAct.top, p);
        c.drawBitmap(vibracionDesactBitmap, rectVibracionDesact.left, rectVibracionDesact.top, p);

        tpBeige.setTextAlign(Paint.Align.CENTER);
        c.drawRect(rectReiniciarRecords, pBotonVerde);
        c.drawText("REINICIAR RÉCORDS", (rectReiniciarRecords.left+rectReiniciarRecords.right)/2,
                rectReiniciarRecords.bottom - (anchoPantalla/16)/3, tpBeige);
    }

    /**
     * Gestiona la pulsación sobre pantalla. Si se pulsa sobre botón de retroceso vuelve a la pantalla
     * de menú principal. Previamente, si hay cambios, llama a la función que guarda la configuración.
     * Si las coordenadas de la pulsación están contenidas en algún rect de botón creado para activar
     * o desactivar las diferentes opciones, cambia el valor de la opción correspondiente, si tiene
     * lugar, contenida en su respectiva variable en la clase JuegoSV.
     * Si hay cambios asigna la variable cambios a true.
     * @param event evento
     * @return devuelve 1 si se pulsa el botón de retroceso, que hace retornar al menú principal.
     * Si se pulsa en cualquier otro punto, devuelve -1.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int aux=super.onTouchEvent(event);  //si se pulsa btnAtras vuelve a mnu ppal -- menu return 1
            if (aux == 1){
                if(cambios){
                    guardarConfig();
                }
                return aux;
            }else{
                if(rectSonAct.contains(x,y) && !JuegoSV.sonido ){
                    JuegoSV.sonido = true;
                    cambios = true;
                } else if(rectSonDesact.contains(x,y) && JuegoSV.sonido) {
                    JuegoSV.sonido = false;
                    cambios = true;
                } else if(rectMusicaAct.contains(x,y) && !JuegoSV.musica) {
                    JuegoSV.musica = true;
                    cambios = true;
                    JuegoSV.mediaPlayer = MediaPlayer.create(context, R.raw.bensound_highoctane);
                    JuegoSV.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    JuegoSV.mediaPlayer.setLooping(true);
                    JuegoSV.volumen = JuegoSV.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    JuegoSV.mediaPlayer.setVolume(JuegoSV.volumen, JuegoSV.volumen);
                    JuegoSV.mediaPlayer.start();
                } else if(rectMusicaDesact.contains(x,y) && JuegoSV.musica) {
                    JuegoSV.musica = false;
                    cambios = true;
                    JuegoSV.mediaPlayer.stop();
                } else if(rectVibracionAct.contains(x,y) && !JuegoSV.vibracion) {
                    JuegoSV.vibracion = true;
                    cambios = true;
                } else if(rectVibracionDesact.contains(x,y) && JuegoSV.vibracion) {
                    JuegoSV.vibracion = false;
                    cambios = true;
                }else if(rectReiniciarRecords.contains(x,y)){
                    reiniciarRecords();
                }
            }
        }
        return -1;
    }

    /**
     * Sobreescribe el archivo que guarda los récords con una cadena vacía para que eliminarlos.
     */
    private void reiniciarRecords(){
        try(FileOutputStream fos = context.openFileOutput("records.txt", Context.MODE_PRIVATE)){
           fos.write("".getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sobreescribe el archivo que guarda la configuración de sonido, música y vibración a partir
     * del estado actual.
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

    /**
     * Crea los rect que se corresponden con la activación y desactivación de las diferentes opciones
     * y el de reinicio de récords.
     */
    public void setRect(){
        rectSonAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 3.5f,
                anchoPantalla/32 * 20, altoPantalla/16 * 5.5f);
        rectSonDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 3.5f,
                anchoPantalla/32 * 24, altoPantalla/16 * 5.5f);
        rectMusicaAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 6.5f,
                anchoPantalla/32 * 20, altoPantalla/16 * 8.5f);
        rectMusicaDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 6.5f,
                anchoPantalla/32 * 24, altoPantalla/16 * 8.5f);
        rectVibracionAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 9.5f,
                anchoPantalla/32 * 20, altoPantalla/16 * 11.5f);
        rectVibracionDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 9.5f,
                anchoPantalla/32 * 24, altoPantalla/16 * 11.5f);

        rectReiniciarRecords = new RectF(anchoPantalla/32 * 8, altoPantalla / 16 * 13,
                anchoPantalla/32 * 24.5f, altoPantalla/16 *15);
    }
}
