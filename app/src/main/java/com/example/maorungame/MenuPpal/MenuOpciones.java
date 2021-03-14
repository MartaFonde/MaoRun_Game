package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.maorungame.JuegoSV;
import com.example.maorungame.Pantalla;
import com.example.maorungame.R;

import java.io.FileOutputStream;

import static com.example.maorungame.JuegoSV.cambiaIdioma;

public class MenuOpciones extends Menu {

    /**
     * Botón para activar efectos de sonido
     */
    RectF rectSonAct;

    /**
     * Botón para desactivar efectos de sonido
     */
    RectF rectSonDesact;

    /**
     * Botón para activar la música
     */
    RectF rectMusicaAct;

    /**
     * Botón para desactivar la música
     */
    RectF rectMusicaDesact;

    /**
     * Botón para activar la vibración
     */
    RectF rectVibracionAct;

    /**
     * Botón para desactivar la vibración
     */
    RectF rectVibracionDesact;

    /**
     * Botón para reiniciar récords
     */
    RectF rectReiniciarRecords;

    /**
     * Botón para establecer el idioma a gallego
     */
    RectF rectGl;

    /**
     * Botón para establecer el idioma a español
     */
    RectF rectEs;

    /**
     * Botón para establecer el idioma a inglés
     */
    RectF rectEn;

    /**
     * Imagen del botón para activar sonido y música
     */
    Bitmap sonidoActBitmap;

    /**
     * Imagen del botón para desactivar sonido y música
     */
    Bitmap sonidoDesactBitmap;

    /**
     * Imagen del botón para activar vibración
     */
    Bitmap vibracionActBitmap;

    /**
     * Imagen del botón para desactivar vibración
     */
    Bitmap vibracionDesactBitmap;

    /**
     * Imagen del botón de idioma gallego
     */
    Bitmap imgGl;

    /**
     * Imagen del botón de idioma español
     */
    Bitmap imgEs;

    /**
     * Imagen del botón de idioma inglés
     */
    Bitmap imgEn;

    /***
     * Estilo de las imágenes de activar/desactivar
     */
    Paint p;

    /**
     * Estilo del círculo que indica activo
     */
    Paint pCircle;

    /**
     * Se asigna a true si la configuración cambia
     */
    boolean cambios;

    /**
     * Construye la pantalla de la opción de menú Opciones a partir de unas dimensiones ancho y alto de
     * pantalla y de un número identificativo. Crea el paint que se usará para los iconos de
     * activación y desativación de sonido, música y vibración, y el círculo que marcará el estado
     * activo de dicha configuración. Redimensiona las imágenes de los iconos.
     * Asigna las imágenes de las banderas redimensionadas.
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
        p.setColor(context.getResources().getColor(R.color.beigeClaro));
        p.setAlpha(200);
        //base de act o desact
        pCircle = new Paint();
        pCircle.setColor(context.getResources().getColor(R.color.opcionActivada));
        pCircle.setAlpha(150);

        sonidoActBitmap = Pantalla.escala(context, "opciones/sonido_activado.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        sonidoDesactBitmap = Pantalla.escala(context, "opciones/sonido_desactivado.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        vibracionActBitmap = Pantalla.escala(context, "opciones/vibracion_activada.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        vibracionDesactBitmap = Pantalla.escala(context, "opciones/vibracion_desactivada.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        imgGl = Pantalla.escala(context, "banderas/galego.png",
                anchoPantalla/32*2, altoPantalla/16*2);
        imgEs = Pantalla.escala(context, "banderas/español.jpg",
                anchoPantalla/32*2, altoPantalla/16*2);
        imgEn = Pantalla.escala(context, "banderas/ingles.png",
                anchoPantalla/32*2, altoPantalla/16*2);

        cambios = false;    //si no hay cambios no se reescribe archivo config

        tpNaranja.setTextAlign(Paint.Align.CENTER);
        tpNaranja.setTextSize(altoPantalla/10);
        tpBeige.setTextSize(altoPantalla/12);
        setRect();
    }

    /**
     * Dibuja sobre lienzo texto que representa las opciones de sonido, música, vibración e idioma,
     * y los botones para su activación y desactivación y selección de idioma, el fondo y el botón de retroceso.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);    //fondo+btnAtras
        c.drawRect(rectFondo, pFondo);

        c.drawText(context.getResources().getText(R.string.opciones).toString(), anchoPantalla/2, altoPantalla/16 * 2, tpNaranja);

        tpBeige.setTextAlign(Paint.Align.LEFT);

        c.drawText(context.getResources().getText(R.string.sonidos).toString(), anchoPantalla/32 * 8, altoPantalla / 16 * 4.25f, tpBeige);
        if(JuegoSV.sonido) c.drawCircle( rectSonAct.left + (rectSonAct.right - rectSonAct.left)/2,
                rectSonAct.top + (rectSonAct.bottom-rectSonAct.top)/2, altoPantalla/16, pCircle);
        else c.drawCircle(rectSonDesact.left + (rectSonDesact.right - rectSonDesact.left)/2,
                rectSonDesact.top + (rectSonDesact.bottom-rectSonDesact.top)/2, altoPantalla/16, pCircle);
        c.drawBitmap(sonidoActBitmap, rectSonAct.left, rectSonAct.top, p);
        c.drawBitmap(sonidoDesactBitmap, rectSonDesact.left, rectSonDesact.top, p);

        c.drawText(context.getResources().getText(R.string.musica).toString(), anchoPantalla/32 * 8, altoPantalla / 16 * 6.75f, tpBeige);
        if(JuegoSV.musica) c.drawCircle( rectMusicaAct.left + (rectMusicaAct.right - rectMusicaAct.left)/2,
                rectMusicaAct.top + (rectMusicaAct.bottom-rectMusicaAct.top)/2, altoPantalla/16, pCircle);
        else c.drawCircle(rectMusicaDesact.left + (rectMusicaDesact.right - rectMusicaDesact.left)/2,
                rectMusicaDesact.top + (rectMusicaDesact.bottom-rectMusicaDesact.top)/2, altoPantalla/16, pCircle);
        c.drawBitmap(sonidoActBitmap, rectMusicaAct.left, rectMusicaAct.top, p);
        c.drawBitmap(sonidoDesactBitmap, rectMusicaDesact.left, rectMusicaDesact.top, p);

        c.drawText(context.getResources().getText(R.string.vibracion).toString(), anchoPantalla/32 * 8, altoPantalla / 16 * 9.25f, tpBeige);
        if(JuegoSV.vibracion) c.drawCircle( rectVibracionAct.left + (rectVibracionAct.right - rectVibracionAct.left)/2,
                rectVibracionAct.top + (rectVibracionAct.bottom-rectVibracionAct.top)/2, altoPantalla/16, pCircle);
        else c.drawCircle(rectVibracionDesact.left + (rectVibracionDesact.right - rectVibracionDesact.left)/2,
                rectVibracionDesact.top + (rectVibracionDesact.bottom-rectVibracionDesact.top)/2, altoPantalla/16, pCircle);
        c.drawBitmap(vibracionActBitmap, rectVibracionAct.left, rectVibracionAct.top, p);
        c.drawBitmap(vibracionDesactBitmap, rectVibracionDesact.left, rectVibracionDesact.top, p);

        c.drawText(context.getResources().getText(R.string.idioma).toString(), anchoPantalla/32 * 8, altoPantalla / 16 * 11.75f, tpBeige);
        c.drawBitmap(imgGl, rectGl.left, rectGl.top, p);
        c.drawBitmap(imgEs, rectEs.left, rectEs.top, p);
        c.drawBitmap(imgEn, rectEn.left, rectEn.top, p);

        tpBeige.setTextAlign(Paint.Align.CENTER);

        c.drawRect(rectReiniciarRecords, pBotonNaranja);
        c.drawText(context.getResources().getText(R.string.reiniciarRecords).toString(), (rectReiniciarRecords.left+rectReiniciarRecords.right)/2,
                rectReiniciarRecords.bottom - (anchoPantalla/16)/3.5f, tpBeige);
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
                } else if (rectGl.contains(x,y)){
                    cambiaIdioma("gl");
                    cambios = true;
                }else if (rectEs.contains(x,y)){
                    cambiaIdioma("es");
                    cambios = true;
                } else if (rectEn.contains(x,y)){
                    cambiaIdioma("en");
                    cambios = true;
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
     * Sobreescribe el archivo que guarda la configuración de sonido, música, vibración e idioma a partir
     * del estado actual.
     */
    private void guardarConfig(){
        try(FileOutputStream fos = context.openFileOutput("config.txt", Context.MODE_PRIVATE)){
            fos.write((JuegoSV.sonido+"\n").getBytes());
            fos.write((JuegoSV.musica+"\n").getBytes());
            fos.write((JuegoSV.vibracion+"\n").getBytes());
            fos.write(JuegoSV.codLang.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Crea los rect que se corresponden con la activación y desactivación de las diferentes opciones
     * y el de reinicio de récords.
     */
    public void setRect(){
        rectSonAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 2.75f,
                anchoPantalla/32 * 20, altoPantalla/16 * 4.75f);
        rectSonDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 2.75f,
                anchoPantalla/32 * 24, altoPantalla/16 * 4.75f);
        rectMusicaAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 5.25f,
                anchoPantalla/32 * 20, altoPantalla/16 * 7.25f);
        rectMusicaDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 5.25f,
                anchoPantalla/32 * 24, altoPantalla/16 * 7.25f);
        rectVibracionAct = new RectF(anchoPantalla/32 * 18, altoPantalla / 16 * 7.75f,
                anchoPantalla/32 * 20, altoPantalla/16 * 9.75f);
        rectVibracionDesact = new RectF(anchoPantalla/32 * 22, altoPantalla / 16 * 7.75f,
                anchoPantalla/32 * 24, altoPantalla/16 * 9.75f);

        rectGl = new RectF(anchoPantalla/32 * 16, altoPantalla / 16 * 10.25f,
                anchoPantalla/32 * 18, altoPantalla/16 * 12.25f);
        rectEs = new RectF(anchoPantalla/32 * 19.5f, altoPantalla / 16 * 10.25f,
                anchoPantalla/32 * 21.5f, altoPantalla/16 * 12.25f);
        rectEn = new RectF(anchoPantalla/32 * 23, altoPantalla / 16 * 10.25f,
                anchoPantalla/32 * 25, altoPantalla/16 * 12.25f);


        rectReiniciarRecords = new RectF(anchoPantalla/32 * 8, altoPantalla / 16 * 13,
                anchoPantalla/32 * 24.5f, altoPantalla/16 *15);
    }
}
