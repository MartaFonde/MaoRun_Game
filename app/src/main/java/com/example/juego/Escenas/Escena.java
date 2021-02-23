package com.example.juego.Escenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.juego.ElemEscena.Controles;
import com.example.juego.ElemEscena.Gato;
import com.example.juego.JuegoSV;
import com.example.juego.MenuEscenas.PauseEscena;
import com.example.juego.Pantalla;
import com.example.juego.R;
import com.example.juego.ElemEscena.Trafico;

import java.util.ArrayList;

abstract public class Escena extends Pantalla {
    Context context;
    Bitmap fondo;
    int numEscena;
    int anchoPantalla;
    int altoPantalla;

    RectF[] arbolesRect;

    ArrayList<RectF> monedasRect;
    ArrayList<PointF> posicionMonedas;
    int totalMonedas = 10;
    int anchoMoneda;
    int altoMoneda;
    Bitmap imagenMoneda;
    Bitmap[] monedas;
    int col = 0;
    int cont;
    Bitmap monedaActual;

    Paint p;
    float propW;
    float propH;

    Controles controles;
    Trafico trafico;
    Gato gato;
    boolean colisionCoche = false;
    public static int mov = 3;
    int cocheColision = -1;
    int nuevoCoche;

    RectF puntuacionRect;
    Paint pPuntuacion;
    Bitmap vidaBitmap;
    Bitmap monedasPuntuacionBitmap;

    //AudioManager audioManager;
    SoundPool efectosSonido;
    int sonidoCoche, sonidoGato, sonidoMoneda;
    final int maxSonidosSimultaneos = 10;
    //static MediaPlayer mediaPlayer;
    int actualizaVolumen = 0;
    //int volumen;

    Vibrator vibrator;

    RectF rectPausa;
    Bitmap bitmapPausa;
    PauseEscena pantallaPause;
    boolean pause = false;
    Paint pPausa;

    public Escena(Context context, int anchoPantalla, int altoPantalla, int numPantalla, Gato gato) {
        super(context, anchoPantalla,altoPantalla, numPantalla);

        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        this.propW = anchoPantalla / 32;
        this.propH = altoPantalla / 16;

        monedasRect = new ArrayList<>();
        posicionMonedas = new ArrayList<>();
        this.anchoMoneda = anchoPantalla/32;
        this.altoMoneda = altoPantalla/16;
        this.imagenMoneda = Pantalla.escala(context, "moneda/moneda.png" , anchoMoneda*5, altoMoneda);
        monedas = new Bitmap[5];
        setBitmapMoneda(imagenMoneda);

        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setAlpha(150);

        trafico = new Trafico(context, anchoPantalla, altoPantalla);
        controles = new Controles(context, anchoPantalla, altoPantalla);
        this.gato = gato;

        setPaintPuntuacion();
        vidaBitmap = Pantalla.escala(context, "gato/heart.png",  anchoPantalla / 32, altoPantalla / 16);
        monedasPuntuacionBitmap = Pantalla.escala(context, "moneda/monedas_controles.png", anchoPantalla / 32, altoPantalla / 16);

        setSonidosMusica();

        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        
        bitmapPausa = Pantalla.escala(context, "juego_pause.png", (int)propW*2, (int)propH*2);
        rectPausa = new RectF(propW * 0.5f, propH * 0.5f, propW*2.5f, propH*2.5f);
        pPausa = new Paint();
        pPausa.setAlpha(150);
    }

    /**
     * Establece el fondo de la escena adaptándolo al ancho y alto de la pantalla
     * @param fondo imagen de fondo
     */
    public void setFondo(Bitmap fondo) {
        this.fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, true);;
    }

    /**
     * Dibuja fondo de escena
     * @param c lienzo
     */
    public void dibujaFondo(Canvas c){
        c.drawBitmap(fondo, 0, 0, null);
    }

    /**
     * Dibuja todos los elementos de Escena: fondo, monedas, coches, gato, puntuación, controles y
     * botón de pausa. Si el juego está en pausa, además de dibujar todos los elementos, dibuja
     * lo correspondiente en la función dibuja de pantallaPause.
     * Si se produce colisión con coche, se dibuja fondo rojo
     * @param c
     */
    @Override
    public void dibuja(Canvas c){
        try{
            dibujaFondo(c);
            if(colisionCoche){
                c.drawColor(Color.RED, PorterDuff.Mode.LIGHTEN);
                colisionCoche = false;
            }
            dibujaMonedas(c);
            trafico.dibujaCoches(c);
            gato.dibujaGato(c);
            dibujaPuntuacion(c);
            controles.dibujaControles(c);

            //rectPausa = new RectF(propW * 0.5f, propH * 0.5f, propW*2.5f, propH*2.5f);
            c.drawBitmap(bitmapPausa, propW * 0.5f, propH*0.5f, pPausa);

            if(pause){
                pantallaPause.dibuja(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Realiza el movimiento automático del juego, actualizando las posiciones de cada coche de tráfico.
     * Comprueba si el rect de cada coche colisiona con el rect de gato, y si es así, produce sonidos,
     * vibración y resta una vida. Comprueba que el gato sigue teniendo vidas después de la colisión.
     * Si vidas es cero, ejecuta cambiaPantalla a pantalla finPartida.
     * Si la pantalla está en pause no hace nada.
     */
    public void actualizaFisica() {        //movimiento automatico del juego
        if (trafico.coches.length > 0 && !pause) {
            for (int i = 0; i < trafico.coches.length; i++) {
                nuevoCoche = i;
                if (i % 2 == 0) {
                    trafico.coches[i].moverDerecha(anchoPantalla);
                } else {
                    trafico.coches[i].moverIzquierda(anchoPantalla);
                }
                if(trafico.coches[i].rectangulo.intersect(rectPausa)){
                    rectPausa = new RectF(propW * 0.5f, propH * 0.5f, propW*2.5f, propH*2.5f);
                }
                if (trafico.coches[i].rectangulo.intersect(gato.rectangulo) && nuevoCoche != cocheColision) {
                    colisionCoche = true;
                    if(JuegoSV.sonido){
                        if(actualizaVolumen % 10 == 0){
                            JuegoSV.volumen = JuegoSV.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        }
                        actualizaVolumen++;
                        efectosSonido.play(sonidoCoche, JuegoSV.volumen, JuegoSV.volumen, 1, 0, 1);
                        efectosSonido.play(sonidoGato, JuegoSV.volumen, JuegoSV.volumen, 1, 0, 1);
                    }
                    if(JuegoSV.vibracion){
                        efectoVibracion();
                    }
                    cocheColision = i;
                    gato.numVidas--;
                        //para que dibuxe pantalla roja
                    if(gato.numVidas == 0){
                       //JuegoSV.pantallaActual = new PantallaFinPartida(context, anchoPantalla, altoPantalla, 9, true, gato.puntos);
                        JuegoSV.mediaPlayer.stop();
                        JuegoSV.cambiaPantalla(9);
                    }
                }
                //Cando coche colisión deixe de interset o xogador, xa podería volver colisionar
                if (cocheColision != -1 && !trafico.coches[cocheColision].rectangulo.intersect(gato.rectangulo)) {
                    cocheColision = -1;
                }
            }
        }
    }

    /**
     * Obtiene las coordenadas de pulsación y, si pause es false, comprueba si los rect de controles
     * contienen las coordenadas. Si alguno las contiene, comprueba si el gato se puede mover (no
     * colisiona con árboles) y ejecuta la función encargada de modificar las posiciones del rect de
     * gato. Si puede el gato efectúa el cambio de posición, llama a colisionMonedas para comprobar
     * si en la nueva posición colisiona con algún rect de monedas.
     * Si el movimiento es en dirección arriba (decrementa coordenada y) se gestiona el movimiento
     * en cada Escena, porque varían sus acciones dependiendo de la Escena.
     * Si el rect pause contiene las coordenadas, se pausa la dinámica del juego y se crea PauseMenu.
     * Si el juego está pausado, y el TouchEvent de pantallaPause retorna 0, las dinámicas del juego
     * vuelven a funcionar.
     * Si ningún rect contiene las coordenadas, la imagen del gato es sprite gato parado.
     * @param event
     * @return número de Escena
     */
    public int onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        if(!pause){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                if(controles.abajo.contains(x, y)){
                    mov = 0;
                    gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                    gato.moverAbajo(altoPantalla);
                    if(gato.puedeMoverse) colisionMonedas();
                }else if(controles.izq.contains(x,y)){
                    mov = 1;
                    gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                    gato.moverIzquierda();
                    if(gato.puedeMoverse) colisionMonedas();
                }else if(controles.der.contains(x, y))  {
                    mov = 2;
                    gato.puedeMoverse = !colisionArboles(gato.getPosicionFutura(mov));
                    gato.moverDerecha(anchoPantalla);
                    if(gato.puedeMoverse) colisionMonedas();
                }else if(controles.arriba.contains(x, y)){
                    mov = 3;
                    return -3;
                }else if(rectPausa.contains(x,y)){
                    pause = true;
                    pantallaPause = new PauseEscena(context, anchoPantalla, altoPantalla, 10);
                } else{
                    gato.parado(); //TODO mirar onde meter isto
                }

            }
        }else{
            int aux = pantallaPause.onTouchEvent(event);
            if(aux == 1){
                return aux;
            }
            if(aux == 0){
                pantallaPause = null;
                pause = false;
            }
        }
        //return numEscena;
        return numPantalla;
    }

    /**
     * Compruebo si la posición futura de gato colisiona con algún rect Arbol para comprobar si el
     * gato puede efectuar el movimiento.
     * @param posFutura posición futura de gato
     * @return true si colisiona (el gato no puede efectuar movimiento), false si no colisiona
     * (el gato sí puede efectuar movimiento)
     */
    public boolean colisionArboles(RectF posFutura) {
        for (RectF arbol : arbolesRect) {
            if (arbol.intersect(posFutura)) {
                setArbolesRect();
                return true;
            }
        }
        return false;
    }

    /**
     * Compruebo si el rect de gato colisiona con algún rect de monedas. Si colisiona, se ejecuta
     * el sonido correspondiente si está activado sonido, incremento la variable puntos de gato y
     * elimino el rect de la colección monedas
     */
    public void colisionMonedas(){
        for (int i = monedasRect.size() -1 ; i >= 0; i--) {
            if(monedasRect.get(i).intersect(gato.rectangulo)){
                if(JuegoSV.sonido){
                    if(actualizaVolumen % 10 == 0){
                        JuegoSV.volumen = JuegoSV.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    }
                    efectosSonido.play(sonidoMoneda, JuegoSV.volumen, JuegoSV.volumen, 1, 0, 1);
                    actualizaVolumen++;
                }
                gato.puntos+=100;
                monedasRect.remove(i);
                //posicionMonedas.remove(i);
                //break;
            }
        }
    }

    /**
     * Creo el array de imágenes de moneda a partir de la imagen que las contiene todas para crear
     * la animación, y asigno a monedaActual la imagen en la que la moneda estaría estática
     * @param conjuntoMonedas imagen que contiene todas las imágenes de moneda
     */
    public void setBitmapMoneda(Bitmap conjuntoMonedas){
        for (int i = 0; i < monedas.length; i++) {
            monedas[i] = Bitmap.createBitmap(conjuntoMonedas, conjuntoMonedas.getWidth()/5*i, 0, anchoMoneda, altoMoneda);
        }
        this.monedaActual = monedas[0];
    }

    /**
     * Sitúa las monedas en pantalla. Comprueba que la posición no interseque ni estea contenida en
     * ningún rect de Arboles para que el gato pueda colisionar con ella.
     * Hace la misma comprobación con respecto a los rect de monedas, para que no se solapen.
     * Si la posición cumple las condiciones, se añade el rect con esas posiciones a la coleccion
     * monedasRect.
     * Para que las monedas no se situen muy en los extremos de la pantalla se limita la superficie
     * en la que pueden aparecer las monedas en con respecto a ancho y alto pantalla.
     * Se calcularán posiciones hasta que el tamaño de la colección monedasRect se corresponda con
     * el total monedas.
     */
    public void setPosicionMonedas(){
        float x;
        float y;

        while(monedasRect.size() < totalMonedas) {
            x = (float)Math.random()*(anchoPantalla-anchoMoneda*4)+anchoMoneda*2;
            y = (float)Math.random()*(altoPantalla-altoMoneda*4)+altoMoneda*2;
            boolean posValida = true;

            for (int i = 0; i < arbolesRect.length; i++) {
                if(new RectF(arbolesRect[i].left, arbolesRect[i].top, arbolesRect[i].right,
                        arbolesRect[i].bottom).contains(new RectF(x, y, x+anchoMoneda, y+altoMoneda))
                        || new RectF(arbolesRect[i].left, arbolesRect[i].top, arbolesRect[i].right,
                        arbolesRect[i].bottom).intersect(new RectF(x, y, x+anchoMoneda, y+altoMoneda))) {
                    posValida = false;
                    break;
                }
            }

            if(posValida){
                for (int i = 0; i < monedasRect.size(); i++) {
                    if(new RectF(monedasRect.get(i).left, monedasRect.get(i).top, monedasRect.get(i).right,
                            monedasRect.get(i).bottom).intersect(new RectF(x, y, x+anchoMoneda, y+altoMoneda))
                            || new RectF(monedasRect.get(i).left, monedasRect.get(i).top, monedasRect.get(i).right,
                            monedasRect.get(i).bottom).contains(new RectF(x, y, x+anchoMoneda, y+altoMoneda))){
                        posValida = false;
                        break;
                    }
                }
            }

            if(posValida){
                monedasRect.add(new RectF(x, y, x+anchoMoneda, y+altoMoneda));
                //posicionMonedas.add(new PointF(x,y));
            }
        }
    }

    /**
     * Si la escena no está en pause, actualiza el valor de monedaActual según la animación y recrea
     * y reasigna los rect de monedas según sus coordenadas left, top y sus medidas, porque en la
     * dinámica cambian las dimensiones de algunos rect y la colisión con el rect de gato se hace
     * imperfecta y variante.
     * Dibuja la imagen monedaActual en cada una de las posiciones de monedasRect
     * @param c
     */
    public void dibujaMonedas(Canvas c){
        if(!pause){
            if(++cont % 10 == 0){
                monedaActual = actualizaImagenMoneda();     //bitmap de la animacion de moneda que se va a mostrar
            }
        }
        //Repintando os rect de monedasRect algúns cambiaban de dimensions ancho + alto
        //Gardo pos left, top e repinto coas súas medidas. Volvo asignar a monedasRect
        for (int i = 0; i < monedasRect.size(); i++) {
            monedasRect.set(i, new RectF(monedasRect.get(i).left, monedasRect.get(i).top,
                    monedasRect.get(i).left+anchoMoneda, monedasRect.get(i).top + altoMoneda));
            c.drawBitmap(monedaActual, monedasRect.get(i).left, monedasRect.get(i).top, null);
            //c.drawRect(monedasRect.get(i), p);
        }
    }

    /**
     * Actualiza la imagen animada de moneda apartir del array de imágenes moneda
     * @return imagen que será la actual de moneda
     */
    public Bitmap actualizaImagenMoneda() {
        if(col < monedas.length){
            this.imagenMoneda = monedas[col];
            col++;
        }else{
            col = 0;
        }
        return imagenMoneda;
    }

    /**
     * Inicializa el paint que se utiliza de fondo en la parte de la pantalla donde se sitúa la puntuación
     * y las vidas y el textPaint que se usará para pintar el número de la puntuación
     */
    public void setPaintPuntuacion(){
        pPuntuacion = new Paint();
        pPuntuacion.setColor(Color.GRAY);
        pPuntuacion.setStyle(Paint.Style.FILL);
        pPuntuacion.setAlpha(150);

        //textPaint = new TextPaint();
        //textPaint.setTypeface(face);
        tpBeige.setTextSize(altoPantalla / 20); // tamaño del texto en pixels
        //textPaint.setTextAlign(Paint.Align.CENTER); // Alineación del texto
        tpBeige.setColor(Color.BLACK); // C
    }

    /**
     * Dibuja el rect de fondo de la puntuación, tantas imágenes de vidaBitmap como vidas tenga el
     * gato, el número correspondiente a la puntuación y monedasPuntuaciónBitmap a la derecha del número
     * @param c
     */
    public void dibujaPuntuacion(Canvas c){
        puntuacionRect = new RectF(anchoPantalla/32*24, 0, anchoPantalla, altoPantalla/16*2.5f);
        c.drawRect(puntuacionRect, pPuntuacion);
        for (int i = 0; i < gato.numVidas; i++) {
            c.drawBitmap(vidaBitmap, anchoPantalla/32 * (31 - i), altoPantalla / 16 * 0.5f, null);
        }
        c.drawText(""+gato.puntos, anchoPantalla/32*27.5f,  altoPantalla / 16 *2.25f, tpBeige);
        c.drawBitmap(monedasPuntuacionBitmap, anchoPantalla /32 *30, altoPantalla/16 * 1.5f, null);
    }

    /**
     * Inicializa los sonidos y la música
     */
    public void setSonidosMusica(){
        //JuegoSV.audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb=new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSonidosSimultaneos);
            this.efectosSonido=spb.build();
        } else {
            this.efectosSonido=new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }

        sonidoCoche = efectosSonido.load(context, R.raw.sonido_coche_atropello, 1);
        sonidoGato = efectosSonido.load(context, R.raw.sonido_gato_atropello, 1);
        sonidoMoneda = efectosSonido.load(context, R.raw.sonido_monedas, 1);

        if(numPantalla == 6){
            JuegoSV.mediaPlayer= MediaPlayer.create(context,R.raw.city_ambience);
            JuegoSV.volumen = JuegoSV.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            JuegoSV.mediaPlayer.setVolume(JuegoSV.volumen/3, JuegoSV.volumen/3);
            JuegoSV.mediaPlayer.setLooping(true);
        }

        if(JuegoSV.musica && numPantalla == 6){
            JuegoSV.mediaPlayer.start();
        }
    }

    /**
     * Inicializa el vibrador
     */
    public void efectoVibracion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        }else {
            vibrator.vibrate(300);
        }
    }

    /**
     * Inicializa el array de los rect de árboles. Las posiciones de los rect y el tamaño del array
     * dependen de cada escena
     */
    abstract  void setArbolesRect();

    /**
     * Inicializa el array de los rect de coches. Las posiciones de los coches dependen de
     * cada escena
     */
    abstract void setCoches();

}
