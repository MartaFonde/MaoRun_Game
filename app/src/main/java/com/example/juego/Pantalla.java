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
    Bitmap fondo[];     //Escenario
    Bitmap fondo1;
    Bitmap fondo2;
    Bitmap fondo3;

    Trafico trafico;
    Bitmap[] imgCochesLeft;
    Bitmap[] imgCochesRight;
    int anchoCoche;
    int altoCoche;
    int velocidadCoches = 5;      //pendiente

    Gato gato;
    Bitmap bitmapGato;

    static int mov = 3;

    int cocheColision = -1;
    int nuevoCoche;

    public Pantalla(Context context) {
        super(context);

        this.surfaceHolder = getHolder(); // Se obtiene el holder
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context; // Obtenemos el contexto

        hilo = new Hilo(); // Inicializamos el hilo
        setFocusable(true); // Aseguramos que reciba eventos de toque

        fondo1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mapa_nivel1);
        fondo2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mapa_nivel2);
        fondo3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mapa_nivel3);
        fondo = new Bitmap[3];
    }

    public void actualizarFisica() {        //movimiento automatico del juego
        if(trafico.coches.length > 0){
            for (int i = 0; i < trafico.coches.length; i++) {
                nuevoCoche = i;
                if (i % 2 == 0) {
                    trafico.coches[i].moverDerecha(anchoPantalla);
                } else {
                    trafico.coches[i].moverIzquierda(anchoPantalla);
                }
                if(trafico.coches[i].rectangulo.intersect(gato.rectangulo) && nuevoCoche != cocheColision){
                    cocheColision = i;
                    controles.vidas--;
                    //TODO vibración+sonido
                }
                //Cando coche colisión deixe de interset o xogador, xa podería volver colisionar
                if(cocheColision != -1 && !trafico.coches[cocheColision].rectangulo.intersect(gato.rectangulo)){
                    cocheColision = -1;
                }
            }

        }
    }


    public void dibujar(Canvas c) { // Rutina de dibujo en el lienzo. Se le llamará desde el hilo
        try {
            escenario.dibujaFondo(c);
            escenario.dibujaArboles(c, anchoPantalla, altoPantalla);
            escenario.dibujaMonedas(c, anchoPantalla, altoPantalla);
            trafico.dibujaCoches(c);
            gato.dibujaGato(c);
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

    public void colisionMonedas(){
        for (int i = escenario.monedasRect.size() -1 ; i >= 0; i--) {
            if(escenario.monedasRect.get(i).intersect(gato.rectangulo)){
                controles.puntos+=100;
                escenario.monedasRect.remove(i);
                escenario.posicionMonedas.remove(i);
                //break;
            }
        }
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
                        gato.moverAbajo(altoPantalla);
                        colisionMonedas();
                    }else if(controles.izq.contains(x,y)){
                        mov = 1;
                        gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverIzquierda();
                        colisionMonedas();
                    }else if(controles.der.contains(x, y))  {
                        mov = 2;
                        gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverDerecha(anchoPantalla);
                        colisionMonedas();
                    }else if(controles.arriba.contains(x, y)){
                        mov = 3;
                        gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverArriba();
                        colisionMonedas();

                        //TODO crear una función para esto
                        //if(escenario.numFondo < escenario.fondos.length){
                            if(gato.getPosicionFutura(mov).intersect(new RectF(anchoPantalla/32 *14, 0,
                                    anchoPantalla/32*17, 0))){
                                escenario.numFondo++;
                                escenario.setFondos(escenario.numFondo);
                                gato.setX(gato.getX());
                                gato.setY(altoPantalla/16*15);
                                Log.i("vel", trafico.velocidadCoche+"");
                                trafico.velocidadCoche += anchoPantalla / (32*30);
                                //FIXME en tamaños pequeños de pantalla no aumenta velocidad
                                trafico.setCoches(escenario.numFondo, trafico.velocidadCoche);
                                Log.i("vel", trafico.velocidadCoche+"");
                            }
                        //}
                    }
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
            long tiempoDormido = 0; //Tiempo que va a dormir el hilo
            final int FPS = 50; // Frames Por Segundo --- Nuestro objetivo
            final int TPS = 1000000000; //Ticks en un segundo para la función usada nanoTime()
            final int FRAGMENTO_TEMPORAL = TPS / FPS; // Espacio de tiempo en el que haremos todo de forma repetida
            // Tomamos un tiempo de referencia actual en nanosegundos más preciso que currenTimeMillis()
            long tiempoReferencia = System.nanoTime();

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
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) { // Se recomienda realizarlo de forma atómica
                if (fondo != null) { // Cambiamos el tamaño de la imagen de fondo al tamaño de la pantalla
                    fondo[0] = Bitmap.createScaledBitmap(fondo1, width, height, true);
                    fondo[1] = Bitmap.createScaledBitmap(fondo2, width, height, true);
                    fondo[2] = Bitmap.createScaledBitmap(fondo3, width, height, true);

                    //NO SE AJUSTA BIEN A PANTALLA:
//                    fondo[0] = escala(R.drawable.mapa_nivel1, width, height);
//                    fondo[1] = escala(R.drawable.mapa_nivel2, width, height);
//                    fondo[2] = escala(R.drawable.mapa_nivel3, width, height);
                }

                bitmapGato = escala( R.drawable.gato, (anchoPantalla/32)*4, (altoPantalla/16)*6);
                gato = new Gato(bitmapGato, anchoPantalla/2-1, altoPantalla/16*14, anchoPantalla / (32*2));

                escalaCoches();
                trafico = new Trafico(imgCochesRight, imgCochesLeft, anchoPantalla / (32 * 10), anchoPantalla, altoPantalla);

                moneda = escala( R.drawable.moneda, anchoPantalla/32*5, altoPantalla/16);
                escenario = new Escenario(fondo, moneda, 0, anchoPantalla, altoPantalla);

                escalaControles();
                controles = new Controles(bitmapControles, anchoPantalla, altoPantalla);
            }
        }
    }

    public void escalaCoches() {
        imgCochesRight = new Bitmap[5];
        imgCochesLeft = new Bitmap[5];

        this.anchoCoche = anchoPantalla/32;
        this.altoCoche = altoPantalla/16;
        //Log.i("dim coches ", anchoCoche+","+altoCoche);

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

