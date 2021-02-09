package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Pantalla extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder; // Interfaz abstracta para manejar la superficie de dibujado
    private Context context;

    private Hilo hilo; // Hilo encargado de dibujar y actualizar la física
    private boolean funcionando = false;

    int anchoPantalla;
    int altoPantalla;

    Controles controles;
    Bitmap[] bitmapControles;

    Escenario escenario;
    Bitmap moneda;
    Bitmap fondo;     //Escenario

    Trafico trafico;
    Bitmap[] imgCochesLeft;
    Bitmap[] imgCochesRight;
    int anchoCoche;
    int altoCoche;
    int velCoches;

    Gato gato;
    Bitmap bitmapGato;

    Paint p;
    Paint p2;

    static int mov = 3;

    int cocheColision;
    int nuevoCoche;

    public Pantalla(Context context) {
        super(context);

        this.surfaceHolder = getHolder(); // Se obtiene el holder
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context; // Obtenemos el contexto

        hilo = new Hilo(); // Inicializamos el hilo
        setFocusable(true); // Aseguramos que reciba eventos de toque

        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.street);

        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setAlpha(150);

        p2 = new Paint();
        p2.setColor(Color.BLUE);
        p2.setStyle(Paint.Style.STROKE);
        p2.setStrokeWidth(5);
        p2.setAlpha(150);

    }

    public void actualizarFisica() {
        if(trafico.coches.length > 0){
            for (int i = 0; i < trafico.coches.length; i++) {
                nuevoCoche = i;
                if (i % 2 == 0) {
                    trafico.coches[i].moverDerecha(anchoPantalla);
                } else {
                    trafico.coches[i].moverIzquierda(anchoPantalla);// Actualizamos la física de los elementos en pantalla
                }
                if(trafico.coches[i].rectangulo.intersect(gato.rectangulo) && nuevoCoche != cocheColision){
                    cocheColision = i;
                    controles.vidas--;

                    //velCoche = 0;
                    //velGato=0;
                    //funcionando = false;
                    // break;
                }
            }
        }

        //rectsMoneda = escenario.getRectMonedas();
        for (int i = escenario.monedasRect.size() -1 ; i >= 0; i--) {
            if(escenario.monedasRect.get(i).intersect(gato.rectangulo)){
                controles.puntos+=100;
                escenario.monedasRect.remove(i);
                //break;
            }
        }
    }


    public void dibujar(Canvas c) { // Rutina de dibujo en el lienzo. Se le llamará desde el hilo
        try {
            escenario.dibujaFondo(c);
            gato.dibujaGato(c);
            trafico.dibujaCoches(c);
            escenario.dibujaMonedas(c);
            escenario.dibujaArboles(c, anchoPantalla, altoPantalla);
            controles.dibujaControles(c, anchoPantalla, altoPantalla);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean colisionArboles(RectF posFutura) {
        for (RectF arbol : escenario.arbolesRect) {
            if (arbol.intersect(posFutura)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized(surfaceHolder) {
            int accion = event.getAction(); // Solo gestiona la pulsación de un dedo.
            //int accion = event.getActionMasked (); // Gestiona el toque con múltiples dedos
            float x = event.getX();
            float y = event.getY();

            switch (accion) {
                case MotionEvent.ACTION_DOWN:       //non hai mov cte
                    if(controles.abajo.contains(x, y)){
                        mov = 0;
                        gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverAbajo(anchoPantalla, altoPantalla);

                    }else if(controles.izq.contains(x,y)){
                        mov = 1;
                        gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverIzquierda(anchoPantalla, altoPantalla);

                    }else if(controles.der.contains(x, y))  {
                        mov = 2;
                        gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverDerecha(anchoPantalla, altoPantalla);

                    }else if(controles.arriba.contains(x, y)){
                        mov = 3;
                        gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverArriba(anchoPantalla, altoPantalla);
                    }
                    //gato.actualizaImagen();
                    break;
                case MotionEvent.ACTION_UP:
                    gato.parado();
                    break;
            }
            return true;
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.anchoPantalla = w;
        this.altoPantalla = h;
        hilo.setSurfaceSize(w, h);
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
        hilo.setSurfaceSize(width, height);
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

    public Bitmap escala(int res, int nuevoAncho, int nuevoAlto) {
        Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        if (nuevoAncho == bitmapAux.getWidth() && nuevoAlto == bitmapAux.getHeight())
            return bitmapAux;
        bitmapAux = bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                bitmapAux.getWidth(), true);
        bitmapAux = bitmapAux.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
                bitmapAux.getHeight(), nuevoAlto, true);
        return bitmapAux;
    }

    // Clase Hilo en la cual se ejecuta el método de dibujo y de física para que se haga en paralelo con la
// gestión de la interfaz de usuario
    class Hilo extends Thread {
        public Hilo() {
        }

        @Override
        public void run() {
            while (funcionando) {
                Canvas c = null; //Siempre es necesario repintar todo el lienzo
                try {
                    if (!surfaceHolder.getSurface().isValid())
                        continue; // si la superficie no está preparada repetimos
                    c = surfaceHolder.lockCanvas(); // Obtenemos el lienzo con aceleración software
                    //c = surfaceHolder.lockHardwareCanvas(); // Obtenemos el lienzo con Aceleración Hw. Desde la API 26
                    synchronized (surfaceHolder) { // La sincronización es necesaria por ser recurso común
                        actualizarFisica(); // Movimiento de los elementos
                        dibujar(c); // Dibujamos los elementos
                    }
                } finally { // Haya o no excepción, hay que liberar el lienzo
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        // Activa o desactiva el funcionamiento del hilo
        void setFuncionando(boolean flag) {
            funcionando = flag;
        }

        // Función llamada si cambia el tamaño del view
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) { // Se recomienda realizarlo de forma atómica
                if (fondo != null) { // Cambiamos el tamaño de la imagen de fondo al tamaño de la pantalla
                    fondo = Bitmap.createScaledBitmap(fondo, width, height, true);
                }

                bitmapGato = escala( R.drawable.gato, (anchoPantalla/32)*4, (altoPantalla/16)*6);
                gato = new Gato(bitmapGato, anchoPantalla/2, altoPantalla/16*14, 30);

                escalaCoches();
                trafico = new Trafico(imgCochesRight, imgCochesLeft, 5, anchoPantalla, altoPantalla);

                moneda = escala( R.drawable.moneda, anchoPantalla/32*5, altoPantalla/16);
                escenario = new Escenario(fondo, moneda, anchoPantalla, altoPantalla);

                escalaControles();
                controles = new Controles(bitmapControles, anchoPantalla, altoPantalla);
                //moneda = escenario.actualizaImagenMoneda();
            }
        }
    }

    public void escalaCoches() {
        imgCochesRight = new Bitmap[5];
        imgCochesLeft = new Bitmap[5];

        this.anchoCoche = anchoPantalla/32;
        this.altoCoche = altoPantalla/16;
        Log.i("dim coches ", anchoCoche+","+altoCoche);

        imgCochesLeft[0] = escala(R.drawable.blue_car_left, anchoCoche, altoCoche);
        imgCochesLeft[1] = escala(R.drawable.green_car_left, anchoCoche, altoCoche);
        imgCochesLeft[2] = escala(R.drawable.red_car_left, anchoCoche, altoCoche);
        imgCochesLeft[3] = escala(R.drawable.white_car_left, anchoCoche, altoCoche);
        imgCochesLeft[4] = escala(R.drawable.orange_car_left, anchoCoche, altoCoche);

        imgCochesRight[0] = escala(R.drawable.blue_car_right, anchoCoche, altoCoche);
        imgCochesRight[1] = escala(R.drawable.green_car_right, anchoCoche, altoCoche);
        imgCochesRight[2] = escala(R.drawable.red_car_right, anchoCoche, altoCoche);
        imgCochesRight[3] = escala(R.drawable.white_car_right, anchoCoche, altoCoche);
        imgCochesRight[4] = escala(R.drawable.orange_car_right, anchoCoche, altoCoche);
    }

    public void escalaControles(){
        bitmapControles = new Bitmap[6];

        int anchoControles = anchoPantalla/32 * 3;
        int altoControles = altoPantalla /16 * 3;

        int anchoPuntuacion = anchoPantalla / 32;
        int altoPuntuacion = altoPantalla / 16 ;

        bitmapControles[0] = escala(R.drawable.arrow_down, anchoControles, altoControles);
        bitmapControles[1] = escala(R.drawable.arrow_left, anchoControles, altoControles);
        bitmapControles[2] = escala(R.drawable.arrow_right, anchoControles, altoControles);
        bitmapControles[3] = escala(R.drawable.arrow_up, anchoControles, altoControles);

        bitmapControles[4] = escala(R.drawable.heart, anchoPuntuacion, altoPuntuacion);
        bitmapControles[5] = escala(R.drawable.monedas_controles, anchoPuntuacion, altoPuntuacion);
    }

}

