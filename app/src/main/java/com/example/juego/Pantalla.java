package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Pantalla extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder; // Interfaz abstracta para manejar la superficie de dibujado
    Context context;

    private Hilo hilo; // Hilo encargado de dibujar y actualizar la física
    private boolean funcionando = false;

    int anchoPantalla;
    int altoPantalla;

    Escena escenaActual;
    int nuevaEscena;

    Canvas c;


    public Pantalla(Context context) {
        super(context);
        this.context = context;

        this.surfaceHolder = getHolder(); // Se obtiene el holder
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context; // Obtenemos el contexto

        hilo = new Hilo(); // Inicializamos el hilo
        setFocusable(true); // Aseguramos que reciba eventos de toque

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized(surfaceHolder) {
            int accion = event.getAction(); // Solo gestiona la pulsación de un dedo.
            int x=(int)event.getX();
            int y=(int)event.getY();

            switch (accion){
                case MotionEvent.ACTION_DOWN:
                    cambiaEscena(escenaActual.onTouchEvent(event));
                    //Log.i("escena", "onTouchEvent: "+nuevaEscena+" "+escenaActual.numEscena);
                    //cambiaEscena(nuevaEscena);
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void cambiaEscena(int nuevaEscena){
        if (nuevaEscena != -1 && escenaActual.numEscena!=nuevaEscena){
            switch (nuevaEscena){
                case 1: escenaActual=new Escena1(context, 1, anchoPantalla, altoPantalla); break;
                case 2: escenaActual=new Escena2(context, 2, anchoPantalla, altoPantalla); break;
                case 3: escenaActual=new Escena3(context, 3, anchoPantalla, altoPantalla); break;
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
        hilo.setFuncionando(true);
        if (hilo.getState() == Thread.State.NEW) hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo = new Hilo();
            hilo.start();
        }
    }

    // Si hay algún cambio en la superficie de dibujo (normalmente su tamaño) obtenemos el nuevo tamaño
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        anchoPantalla = width;
        altoPantalla = height;

        escenaActual = new Escena1(context, 1, anchoPantalla, altoPantalla);

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
                        escenaActual.actualizarFisica();
                        escenaActual.dibuja(c);
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

    public static Bitmap escala(Context context, int res, int nuevoAncho, int nuevoAlto) {
        Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (nuevoAncho == bitmapAux.getWidth() && nuevoAlto == bitmapAux.getHeight())
            return bitmapAux;
        bitmapAux = bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                bitmapAux.getWidth(), true);
        bitmapAux = bitmapAux.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
                bitmapAux.getHeight(), nuevoAlto, true);
        return bitmapAux;
    }

}

