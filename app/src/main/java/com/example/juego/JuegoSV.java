package com.example.juego;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.juego.ElemEscena.Gato;
import com.example.juego.Escenas.Escena;
import com.example.juego.Escenas.Escena1;
import com.example.juego.Escenas.Escena2;
import com.example.juego.Escenas.Escena3;
import com.example.juego.MenuEscenas.PantallaFinPartida;
import com.example.juego.MenuPpal.MenuAyuda;
import com.example.juego.MenuPpal.MenuCreditos;
import com.example.juego.MenuPpal.MenuOpciones;
import com.example.juego.MenuPpal.MenuPrincipal;
import com.example.juego.MenuPpal.MenuRecords;

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

    public static boolean sonidoAct;
    public static boolean musicaAct;   //TODO controlar esto
    public static boolean vibracionAct;

    public JuegoSV(Context context) {
        super(context);
        this.surfaceHolder = getHolder(); // Se obtiene el holder
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context;
        hilo = new Hilo(); // Inicializamos el hilo
        setFocusable(true); // Aseguramos que reciba eventos de toque

        hilo = new Hilo();
        hilo.setFuncionando(true);

        sonidoAct = true;
        musicaAct = true;
        vibracionAct = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized(surfaceHolder) {
            int accion = event.getAction(); // Solo gestiona la pulsación de un dedo.
            int x=(int)event.getX();
            int y=(int)event.getY();

            switch (accion){
                case MotionEvent.ACTION_DOWN:
                    numPantallaNueva = pantallaActual.onTouchEvent(event);
                    if(numPantallaNueva == -10)  salir();
                    if(numPantallaNueva != -1 ) cambiaPantalla(numPantallaNueva);
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void salir(){
        pantallaActual = null;
        System.gc();
        ((Activity)context).finish();
    }

    public static void cambiaPantalla(int nuevaPantalla){
        if (pantallaActual.numPantalla != nuevaPantalla){
            switch (nuevaPantalla){
                case 1: pantallaActual = new MenuPrincipal(context, anchoPantalla, altoPantalla, 1);
                    break;
                case 2: pantallaActual = new MenuRecords(context, anchoPantalla, altoPantalla, 2);
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
                    boolean sinVidas = gato.numVidas==0? true : false;
                    pantallaActual = new PantallaFinPartida(context, anchoPantalla, altoPantalla, 9,
                           sinVidas , gato.puntos);
                    break;
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.anchoPantalla = w;
        this.altoPantalla = h;
        //hilo.setSurfaceSize(w, h);
    }

    @Override           // En cuanto se crea el SurfaceView se lanze el hilo
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    // Si hay algún cambio en la superficie de dibujo (normalmente su tamaño) obtenemos el nuevo tamaño
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        anchoPantalla = width;
        altoPantalla = height;
        pantallaActual = new MenuPrincipal(context, anchoPantalla, altoPantalla, 1);

        if (hilo.getState() == Thread.State.NEW) hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo.start();
        }
        //hilo.setSurfaceSize(width, height);
    }

    // Al finalizar el surface, se para el hilo
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        hilo.setFuncionando(false);
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Clase Hilo en la cual se ejecuta el método de dibujo y de física para que se haga en paralelo con la
// gestión de la interfaz de usuario
    class Hilo extends Thread {
        public Hilo() {
        }

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

        // Activa o desactiva el funcionamiento del hilo
        void setFuncionando(boolean flag) {
            funcionando = flag;
        }

        // Función llamada si cambia el tamaño del view
//        public void setSurfaceSize(int width, int height) {
//            synchronized (surfaceHolder) { // Se recomienda realizarlo de forma atómica
//
//                //escenaActual = new Escena1(context, 1, anchoPantalla, altoPantalla);
//
//            }
//        }
    }



}



