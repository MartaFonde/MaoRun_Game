package com.example.maorungame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.maorungame.ElemEscena.Gato;
import com.example.maorungame.Escenas.Escena1;
import com.example.maorungame.Escenas.Escena2;
import com.example.maorungame.Escenas.Escena3;
import com.example.maorungame.FinPartida.PantallaGuardaRecord;
import com.example.maorungame.FinPartida.MenuFinPartida;
import com.example.maorungame.FinPartida.PantallaFinPartida;
import com.example.maorungame.MenuPpal.MenuAyuda;
import com.example.maorungame.MenuPpal.MenuCreditos;
import com.example.maorungame.MenuPpal.MenuOpciones;
import com.example.maorungame.MenuPpal.MenuPrincipal;
import com.example.maorungame.MenuPpal.PantallaRecords;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class JuegoSV extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder; // Interfaz abstracta para manejar la superficie de dibujado
    static Context context;

    private Hilo hilo; // Hilo encargado de dibujar y actualizar la física
    private boolean funcionando = false;

    static int anchoPantalla;
    static int altoPantalla;

    static Pantalla pantallaActual;
    int numPantallaNueva;

    static Bitmap bitmapGato;
    static Gato gato;

    public static boolean sonido;
    public static boolean musica;
    public static boolean vibracion;

    public static MediaPlayer mediaPlayer;
    public static AudioManager audioManager;
    public static boolean restartMusica = true;
    public static int volumen;

    /**
     * Obtiene el holder e indica donde van las funciones callback. Instancia el hilo encargado de
     * las dinámicas del juego y establece la variable que lo pone en funcionamiento.
     * Asegura que reciba eventos de toque. También llama a la función encargada de establecer la
     * configuración del estado de sonido, música y vibración e inicializa el audioManager como
     * servicio de audio.
     * @param context contexto
     */
    public JuegoSV(Context context) {
        super(context);
        this.surfaceHolder = getHolder(); // Se obtiene el holder
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context;
        hilo = new Hilo(); // Inicializamos el hilo
        setFocusable(true); // Aseguramos que reciba eventos de toque
        hilo.setFuncionando(true);
        leerConfig();
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * Recibe el valor devuelto por pantallaActual al pulsar la pantalla y en función de este valor
     * sale de la aplicación o llama al método que cambia la pantalla si el valor es distinto a -1
     * (-1 indica que no procede ningún cambio importante).
     * @param event evento de pulsación
     * @return true si el evento es controlado, false en caso contrario.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized(surfaceHolder) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                numPantallaNueva = pantallaActual.onTouchEvent(event);
                if(numPantallaNueva == 0){
                    mediaPlayer.stop();
                    salir();
                    return true;
                }
                if(numPantallaNueva != -1) cambiaPantalla(numPantallaNueva);   //por defecto
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * Llama al recolector de basura para liberar memoria y finaliza la activity que contiene
     * esta clase.
     */
    private void salir(){
        funcionando = false;
        pantallaActual = null;
        System.gc();
        ((Activity)context).finish();
    }

    /**
     * Lee el archivo de configuración de sonido, música y vibración. Si el archivo no existe
     * o está corrupto, asigna por defecto estas variables a true.
     */
    private void leerConfig(){
        try (FileInputStream fis = context.openFileInput("config.txt");
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader buffer = new BufferedReader(reader)) {
             sonido = Boolean.parseBoolean(buffer.readLine());
             musica = Boolean.parseBoolean(buffer.readLine());
             vibracion = Boolean.parseBoolean(buffer.readLine());
        } catch (Exception e) {
            sonido = true;
            musica = true;
            vibracion = true;
            e.printStackTrace();
        }
    }

    /**
     * Gestiona el control de pantallas a partir del número de Pantalla. Si el parámetro es distinto
     * al número de pantalla de pantallaActual procede un cambio de pantalla, y asigna a pantallaActual
     * la correspondiente clase.
     * @param nuevaPantalla número de pantalla que representa el número de la nueva pantalla o el
     *  número de pantallaActual
     */
    public static void cambiaPantalla(int nuevaPantalla){
        if (pantallaActual.numPantalla != nuevaPantalla){
            switch (nuevaPantalla){
                case 1: pantallaActual = new MenuPrincipal(context, anchoPantalla, altoPantalla, 1);
                    break;
                case 2: pantallaActual = new PantallaRecords(context, anchoPantalla, altoPantalla, 2);
                    break;
                case 3: pantallaActual = new MenuCreditos(context, anchoPantalla, altoPantalla, 3);
                    break;
                case 4: pantallaActual = new MenuAyuda(context, anchoPantalla, altoPantalla, 4);
                    break;
                case 5: pantallaActual = new MenuOpciones(context, anchoPantalla, altoPantalla, 5);
                    break;
                case 6:
                    bitmapGato = Pantalla.escala(context, "gato/gato.png", (anchoPantalla/32)*4, (altoPantalla/16)*6);
                    gato = new Gato(bitmapGato, anchoPantalla/2-1, altoPantalla/16*14, anchoPantalla / (32*3));
                    pantallaActual = new Escena1(context, anchoPantalla, altoPantalla, 6, gato);
                    break;
                case 7: pantallaActual = new Escena2(context, anchoPantalla, altoPantalla, 7, gato);
                    break;
                case 8: pantallaActual = new Escena3(context, anchoPantalla, altoPantalla, 8, gato);
                    break;
                case 9:
                    mediaPlayer.stop();
                    boolean sinVidas = gato.numVidas==0? true : false;
                    pantallaActual = new PantallaFinPartida(context, anchoPantalla, altoPantalla, 9,
                           sinVidas , gato.puntos);
                    break;
                case 13: pantallaActual = new PantallaGuardaRecord(context, anchoPantalla, altoPantalla, 13, gato.puntos);
                break;
                case 14: pantallaActual = new PantallaRecords(context, anchoPantalla, altoPantalla, 14);
                    break;
                case 15: pantallaActual = new MenuFinPartida(context, anchoPantalla, altoPantalla, 15);
                    break;
            }
        }
    }

    /**
     * Se ejecuta cuendo cambia el tamaño de la pantalla.
     * @param w nuevo ancho de pantalla
     * @param h nuevo alto de pantalla
     * @param oldw  antiguo ancho de pantalla
     * @param oldh antiguo alto de pantalla
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.anchoPantalla = w;
        this.altoPantalla = h;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    /**
     * Se ejecuta si hay algún cambio en la superficie de dibujo. Obtenemos su nuevo tamaño.
     * Si hay cambios, la pantalla MenuPrincipal es asignada a pantallaActual y se lanza el hilo.
     * @param holder
     * @param format
     * @param width ancho de pantalla
     * @param height alto de pantalla
     */
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        anchoPantalla = width;
        altoPantalla = height;

        pantallaActual = new MenuPrincipal(context, anchoPantalla, altoPantalla, 1);

        if (hilo.getState() == Thread.State.NEW) hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo.start();
        }
    }

    /**
     * Al finalizar el surface, se para el hilo.
     * @param holder
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        hilo.setFuncionando(false);
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    class Hilo extends Thread {
        public Hilo() {
        }

        /**
         * Ejecuta las acciones del hilo: llama al método dibujo y actualiza física para que se haga
         * en paralelo con la gestión de la interfaz de usuario. Se repite en función del fragmento
         * temporal estipulado.
         */
        @Override
        public void run() {
            long tiempoDormido = 0; //Tiempo que va a dormir el hilo
            final int FPS = 50; // Frames Por Segundo --- Nuestro objetivo
            final int TPS = 1000000000; //Ticks en un segundo para la función usada nanoTime()
            final int FRAGMENTO_TEMPORAL = TPS / FPS; // Espacio de tiempo en el que haremos todo de forma repetida
            // Tomamos un tiempo de referencia actual en nanosegundos más preciso que currenTimeMillis()
            long tiempoReferencia = System.nanoTime();

            while (funcionando) {
                Canvas c = null; //Siempre es necesario repintar todo el lienzo
                try {
                    if (!surfaceHolder.getSurface().isValid()) continue;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        c = surfaceHolder.lockHardwareCanvas();
                    }
                    if (c == null) c = surfaceHolder.lockCanvas();

                    synchronized (surfaceHolder) {
                        if(pantallaActual != null)
                        pantallaActual.dibuja(c);

                        if(pantallaActual != null
                                && pantallaActual instanceof Escena1
                                || pantallaActual instanceof Escena2
                                || pantallaActual instanceof Escena3)
                        {
                            pantallaActual.actualizaFisica();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();

                } finally { // Haya o no excepción, hay que liberar el lienzo
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                // Calculamos el siguiente instante temporal donde volveremos a actualizar y pintar
                tiempoReferencia += FRAGMENTO_TEMPORAL;
                // El tiempo que duerme será el siguiente menos el actual (Ya ha terminado de pintar y actualizar)
                tiempoDormido = tiempoReferencia - System.nanoTime();
                //Si tarda mucho, dormimos.
                if (tiempoDormido > 0) {
                    try {
                        Thread.sleep(tiempoDormido / 1000000); //Convertimos a ms
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * Activa o desactiva el funcionamiento del hilo
         * @param flag asigna valor a funcionando, variable que activa o desactiva el hilo
         */
        void setFuncionando(boolean flag) {
            funcionando = flag;
        }

    }
}



