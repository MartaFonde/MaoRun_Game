package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Pantalla extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder; // Interfaz abstracta para manejar la superficie de dibujado
    private Context context;

    RectF der;
    RectF izq;
    RectF arriba;
    RectF abajo;

    RectF puntuacionRect;

    Rect pista1;
    Rect pista2;
    Rect pista3;
    Rect pista4;

    Paint pDer;
    Paint pIzq;
    Paint pArriba;
    Paint pAbajo;
    Paint pPista;

    int anchoPantalla;
    int altoPantalla;

    private Bitmap bitmapFondo;     //Escenario

    private Hilo hilo; // Hilo encargado de dibujar y actualizar la física
    private boolean funcionando = false;

    ArrayList<Coche> coches;
    Bitmap bitmapCoche;
    int velCoche = 5;

    Gato gato;
    Bitmap bitmapGato;
    int velGato = 30;
    private static final int V_gato = 30;

    Escenario escenario;
    RectF[] arboles;
    ArrayList<RectF> rectsMoneda;
    Bitmap moneda;

    Paint p;
    Paint p2;
    Paint p3;
    TextPaint tpaint;

    RectF gatoPosFutura;
    int mov;
    int cont = 0;
    int puntos;

    public Pantalla(Context context) {
        super(context);

        this.surfaceHolder = getHolder(); // Se obtiene el holder
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context; // Obtenemos el contexto

        hilo = new Hilo(); // Inicializamos el hilo
        setFocusable(true); // Aseguramos que reciba eventos de toque

        bitmapFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.street);

        controles();
        pistas();

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

        p3 = new Paint();
        p3.setColor(Color.MAGENTA);
        p3.setStyle(Paint.Style.FILL);
        p3.setAlpha(50);

        tpaint = new TextPaint();
        tpaint.setTextSize(50); // tamaño del texto en pixels
        //tpaint.setTextAlign(Paint.Align.CENTER); // Alineación del texto
        tpaint.setColor(Color.WHITE); // Color del texto

        coches = new ArrayList<Coche>();
    }

    public void actualizarFisica() {
        for (int i = 0; i < coches.size(); i++) {
            if (i % 2 == 0) {
                coches.get(i).moverDerecha(anchoPantalla,altoPantalla,  velCoche);
            } else {
                coches.get(i).moverIzquierda(anchoPantalla,altoPantalla,  velCoche);// Actualizamos la física de los elementos en pantalla
            }
            if(coches.get(i).rectangulo.intersect(gato.rectangulo)){
                //velCoche = 0;
                //velGato=0;
                //funcionando = false;
               // break;
            }
        }

        //rectsMoneda = escenario.getRectMonedas();
        for (int i = escenario.monedasRect.size() -1 ; i >= 0; i--) {
            if(escenario.monedasRect.get(i).intersect(gato.rectangulo)){
                puntos+=100;
                escenario.monedasRect.remove(i);
                //break;
            }
        }
    }


    public void dibujar(Canvas c) { // Rutina de dibujo en el lienzo. Se le llamará desde el hilo
        try {
            c.drawBitmap(bitmapFondo, 0, 0, null);
            c.drawBitmap(gato.imagen, gato.posicion.x, gato.posicion.y, null);
            //c.drawRect(gato.rectangulo, p);

            escenario.setRectArboles(anchoPantalla, altoPantalla);
//            arboles = escenario.getRectArboles();
//            for (RectF arbol : arboles) {
//                c.drawRect(arbol, p);
//            }

            cont++;
            if(cont%10==0){
                moneda = escenario.actualizaImagenMoneda();
            }else if(cont == 99){
                cont = 0;
            }
            //escenario.setRectMonedas(anchoPantalla, altoPantalla);
            //rectsMoneda = escenario.getRectMonedas();
            Log.i("monedas", escenario.monedasRect.size()+"");
            for (RectF m: escenario.monedasRect) {
                //c.drawRect(m, p2);
                c.drawBitmap(moneda, m.left, m.top, p2);
            }

            for (Coche coche : coches) {
                c.drawBitmap(coche.imagen, coche.posicion.x, coche.posicion.y, null);
                //c.drawRect(coche.rectangulo,p);
            }
            //c.drawRect(gato.posicionFutura, p);

            dibujaControles(c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean colisionArboles(RectF posFutura) {
        for (RectF arbol : arboles) {
            if (arbol.intersect(posFutura)) {
                //Log.i("colision b-t", arbol.bottom + " - "+ posFutura.top);
                //Log.i("colision r-l", arbol.right + " - "+ posFutura.left);
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
                    if(abajo.contains(x, y)){
                        mov = 0;
                        if(!gato.puedeMoverse && mov != gato.fila) gato.puedeMoverse= true;
                        else gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverAbajo(anchoPantalla, altoPantalla, velGato);

                    }else if(izq.contains(x,y)){
                        mov = 1;
                        if(!gato.puedeMoverse && mov != gato.fila) gato.puedeMoverse= true;
                        else gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverIzquierda(anchoPantalla, altoPantalla, velGato);

                    }else if(der.contains(x, y))  {
                        mov = 2;
                        if(!gato.puedeMoverse && mov != gato.fila) gato.puedeMoverse= true;
                        else gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverDerecha(anchoPantalla, altoPantalla, velGato);

                    }else if(arriba.contains(x, y)){
                        mov = 3;
                        if(!gato.puedeMoverse && mov != gato.fila) gato.puedeMoverse= true;
                        else gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                        gato.moverArriba(anchoPantalla, altoPantalla, velGato);
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { // nuevo (x,y ) y viejo (oldw,oldh) tamaño del lienzo
        super.onSizeChanged(w, h, oldw, oldh);
        this.anchoPantalla = w;
        this.altoPantalla = h;
        hilo.setSurfaceSize(w, h);
    }

    public void controles() {
        pDer = new Paint();
        pDer.setColor(Color.BLUE);
        pDer.setAlpha(125);
        //this.der = new Rect((anchoPantalla / 32) * 4, (altoPantalla / 16) * 13, (anchoPantalla / 32) * 8, altoPantalla);

        pIzq = new Paint();
        pIzq.setColor(Color.RED);
        pIzq.setAlpha(125);
        //this.izq = new Rect(0, (altoPantalla / 16) * 13, (anchoPantalla / 32) * 4, altoPantalla);

        pArriba = new Paint();
        pArriba.setColor(Color.GREEN);
        pArriba.setAlpha(125);
        //this.arriba = new Rect((anchoPantalla / 32) * 28, (altoPantalla / 16) * 13, anchoPantalla, altoPantalla);

        pAbajo = new Paint();
        pAbajo.setColor(Color.YELLOW);
        pAbajo.setAlpha(125);
        //this.abajo = new Rect((anchoPantalla / 32) * 24, (altoPantalla / 16) * 13, (anchoPantalla / 32) * 28, altoPantalla);
    }

    public void dibujaControles(Canvas c) {
        izq = new RectF(0, (altoPantalla / 16) * 13, (anchoPantalla / 32) * 4, altoPantalla);
        c.drawRect(izq, pIzq);
        der = new RectF((anchoPantalla / 32) * 4, (altoPantalla / 16) * 13, (anchoPantalla / 32) * 8, altoPantalla);
        c.drawRect(der, pDer);
        abajo = new RectF((anchoPantalla / 32) * 24, (altoPantalla / 16) * 13, (anchoPantalla / 32) * 28, altoPantalla);
        c.drawRect(abajo, pAbajo);
        arriba = new RectF((anchoPantalla / 32) * 28, (altoPantalla / 16) * 13, anchoPantalla, altoPantalla);
        c.drawRect(arriba, pArriba);

        puntuacionRect = new RectF(anchoPantalla/32*25, 0, anchoPantalla, altoPantalla/16*2);
        c.drawRect(puntuacionRect, p3);
        c.drawText("Puntuación "+puntos, anchoPantalla/32*27,  altoPantalla / 16, tpaint);
    }

    public void pistas() {
        pista1 = new Rect(0, (altoPantalla / 16) * 10, anchoPantalla, (altoPantalla / 16) * 12);
        pista2 = new Rect(0, (altoPantalla / 16) * 7, anchoPantalla, (altoPantalla / 16) * 9);
        pista3 = new Rect(0, (altoPantalla / 16) * 3, anchoPantalla, (altoPantalla / 16) * 5);
        pista4 = new Rect(0, (altoPantalla / 16) * 1, anchoPantalla, (altoPantalla / 16) * 3);
        pPista = new Paint();
        pPista.setColor(Color.MAGENTA);
        pPista.setAlpha(50);
    }

    public void dibujaPistas(Canvas c) {
        pista1 = new Rect(0, (altoPantalla / 16) * 10, anchoPantalla, (altoPantalla / 16) * 12);
        pista2 = new Rect(0, (altoPantalla / 16) * 7, anchoPantalla, (altoPantalla / 16) * 9);
        pista3 = new Rect(0, (altoPantalla / 16) * 3, anchoPantalla, (altoPantalla / 16) * 5);
        pista4 = new Rect(0, (altoPantalla / 16) * 1, anchoPantalla, (altoPantalla / 16) * 3);
        c.drawRect(pista1, pPista);
        c.drawRect(pista2, pPista);
        c.drawRect(pista3, pPista);
        c.drawRect(pista4, pPista);
    }

    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is = context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }


    @Override           // En cuanto se crea el SurfaceView se lanze el hilo
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        crearCoches();
        arboles = escenario.getRectArboles();
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
                        //actualizarFisicaGato();
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
                if (bitmapFondo != null) { // Cambiamos el tamaño de la imagen de fondo al tamaño de la pantalla
                    bitmapFondo = Bitmap.createScaledBitmap(bitmapFondo, width, height, true);
                }
                bitmapGato = escala(R.drawable.gato, (anchoPantalla/32)*4, (altoPantalla/16)*6);
                gato = new Gato(bitmapGato, anchoPantalla/2, altoPantalla/16*14);

                moneda = escala(R.drawable.moneda, anchoPantalla/32*5, altoPantalla/16);
                escenario = new Escenario(moneda, anchoPantalla, altoPantalla);
                //escenario.setRectArboles(anchoPantalla, altoPantalla);

                moneda = escenario.actualizaImagenMoneda();
//                if(rectsMoneda==null) {
//                    rectsMoneda = new ArrayList<>();
//                    rectsMoneda = escenario.getRectMonedas();
//                }
            }
        }
    }

    public void crearCoches(){
        bitmapCoche = escala(R.drawable.blue_car_right, anchoPantalla / 32, altoPantalla / 16);
        coches.add(new Coche(bitmapCoche, anchoPantalla / 2, (altoPantalla / 16) * 11));

        bitmapCoche = escala(R.drawable.white_car_left, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla / 5, (altoPantalla / 16) * 10));

        bitmapCoche = escala(R.drawable.green_car_right, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla / 7, (altoPantalla / 16) * 8));

        bitmapCoche = escala(R.drawable.blue_car_left, anchoPantalla / 32 , altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla, (altoPantalla / 16) * 7));

        bitmapCoche = escala(R.drawable.red_car_right, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, -bitmapCoche.getWidth(), (altoPantalla / 16) * 4));

        bitmapCoche = escala(R.drawable.orange_car_left, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla, (altoPantalla / 16) * 3));

        bitmapCoche = escala(R.drawable.white_car_right, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla / 16, (altoPantalla / 16) * 2));

        bitmapCoche = escala(R.drawable.red_car_left, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, -bitmapCoche.getWidth(), (altoPantalla / 16) * 1));

        bitmapCoche = escala(R.drawable.red_car_right, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, -bitmapCoche.getWidth(), (altoPantalla / 16) * 11));

        bitmapCoche = escala(R.drawable.green_car_left, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla, (altoPantalla / 16) * 10));

        bitmapCoche = escala(R.drawable.orange_car_right, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla, (altoPantalla / 16) * 8));

        bitmapCoche = escala(R.drawable.white_car_left, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla/2, (altoPantalla / 16) * 7));

        bitmapCoche = escala(R.drawable.white_car_right, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla/2, (altoPantalla / 16) * 4));

        bitmapCoche = escala(R.drawable.white_car_left, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla/3, (altoPantalla / 16) * 3));

        bitmapCoche = escala(R.drawable.blue_car_right, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla/3, (altoPantalla / 16) * 2));

        bitmapCoche = escala(R.drawable.white_car_left, anchoPantalla / 32, altoPantalla / 16 );
        coches.add(new Coche(bitmapCoche, anchoPantalla/2, (altoPantalla / 16) * 1));
    }

}

