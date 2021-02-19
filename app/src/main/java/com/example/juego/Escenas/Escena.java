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
    Bitmap conjuntoMonedas;
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

    AudioManager audioManager;
    SoundPool efectosSonido;
    int sonidoCoche, sonidoGato, sonidoMoneda;
    final int maxSonidosSimultaneos = 10;
    static MediaPlayer mediaPlayer;
    int actualizaVolumen = 0;
    int volumen;

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
        this.conjuntoMonedas = Pantalla.escala(context, "moneda/moneda.png" , anchoMoneda*5, altoMoneda);
        monedas = new Bitmap[5];
        setBitmapMoneda();

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
        volumen = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        
        bitmapPausa = Pantalla.escala(context, "juego_pause.png", (int)propW*2, (int)propH*2);
        pPausa = new Paint();
        pPausa.setAlpha(150);
    }

    public void setFondo(Bitmap fondo) {
        this.fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, true);;
    }

    public void dibujaFondo(Canvas c){
        c.drawBitmap(fondo, 0, 0, null);
    }
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

            rectPausa = new RectF(propW * 0.5f, propH * 0.5f, propW*2.5f, propH*2.5f);
            c.drawBitmap(bitmapPausa, propW * 0.5f, propH*0.5f, pPausa);

            if(pause){
                pantallaPause.dibuja(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizaFisica() {        //movimiento automatico del juego
        if (trafico.coches.length > 0 && !pause) {
            for (int i = 0; i < trafico.coches.length; i++) {
                nuevoCoche = i;
                if (i % 2 == 0) {
                    trafico.coches[i].moverDerecha(anchoPantalla);
                } else {
                    trafico.coches[i].moverIzquierda(anchoPantalla);
                }
                if (trafico.coches[i].rectangulo.intersect(gato.rectangulo) && nuevoCoche != cocheColision) {
                    colisionCoche = true;
                    if(JuegoSV.sonidoAct){
                        if(actualizaVolumen % 3 == 0){
                            volumen = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        }
                        efectosSonido.play(sonidoCoche, volumen, volumen, 1, 0, 1);
                        efectosSonido.play(sonidoGato, volumen, volumen, 1, 0, 1);
                        actualizaVolumen++;
                    }
                    if(JuegoSV.vibracionAct){
                        efectoVibracion();
                    }
                    cocheColision = i;
                    gato.numVidas--;
                        //para que dibuxe pantalla roja
                    if(gato.numVidas == 0){
                       //JuegoSV.pantallaActual = new PantallaFinPartida(context, anchoPantalla, altoPantalla, 9, true, gato.puntos);
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

    public int onTouchEvent(MotionEvent event){
        int accion = event.getAction();
        float x = event.getX();
        float y = event.getY();

        if(!pause){
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
                        return -3;
                    }else if(rectPausa.contains(x,y)){
                        pause = true;
                        pantallaPause = new PauseEscena(context, anchoPantalla, altoPantalla, 13);
                        //pantallaPause = new PantallaPause(context, anchoPantalla, altoPantalla, 10);
                    } else{
                        gato.parado(); //TODO mirar onde meter isto
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    //gato.parado();
                    Toast.makeText(context, "gato parado", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else{
            int aux = pantallaPause.onTouchEvent(event);
            if(aux == 0){
                pantallaPause = null;
                pause = false;
            }
        }
        return numEscena;
    }

    public boolean colisionArboles(RectF posFutura) {
        for (RectF arbol : arbolesRect) {
            if (arbol.intersect(posFutura)) {
                setArbolesRect();
                return true;
            }
        }
        return false;
    }

    public void colisionMonedas(){
        for (int i = monedasRect.size() -1 ; i >= 0; i--) {
            if(monedasRect.get(i).intersect(gato.rectangulo)){
                if(JuegoSV.sonidoAct){
                    int v = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    efectosSonido.play(sonidoMoneda, v, v, 1, 0, 1);
                }
                gato.puntos+=100;
                monedasRect.remove(i);
                posicionMonedas.remove(i);
                //break;
            }
        }
    }

    public void setBitmapMoneda(){
        for (int i = 0; i < monedas.length; i++) {
            monedas[i] = Bitmap.createBitmap(conjuntoMonedas, conjuntoMonedas.getWidth()/5*i, 0, anchoMoneda, altoMoneda);
        }
        this.monedaActual = monedas[0];
    }

    public void setPosicionMonedas(){
        float x;
        float y;

        while(monedasRect.size() < totalMonedas) {
            //para que las monedas no se situen muy en los extremos de la pantalla
            //limito la superficie en la que pueden aparecer las monedas
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
                posicionMonedas.add(new PointF(x,y));
            }
        }
    }

    public void dibujaMonedas(Canvas c){
        if(!pause){
            if(++cont % 10 == 0){
                monedaActual = actualizaImagenMoneda();     //bitmap de la animacion de moneda que se va a mostrar
            }
        }
        //Repintando os rect de monedasRect algúns cambiaban de dimensions ancho + alto
        //Gardo pos left, top e repinto coas súas medidas. Volvo asignar a monedasRect
        for (int i = 0; i < monedasRect.size(); i++) {
            monedasRect.set(i, new RectF(posicionMonedas.get(i).x, posicionMonedas.get(i).y,
                    posicionMonedas.get(i).x+anchoMoneda, posicionMonedas.get(i).y + altoMoneda));
            c.drawBitmap(monedaActual, monedasRect.get(i).left, monedasRect.get(i).top, null);
            //c.drawRect(monedasRect.get(i), p);
        }
    }

    public Bitmap actualizaImagenMoneda() {
        if(col < monedas.length){
            this.conjuntoMonedas = monedas[col];
            col++;
        }else{
            col = 0;
        }
        return conjuntoMonedas;
    }

    public void setPaintPuntuacion(){
        pPuntuacion = new Paint();
        pPuntuacion.setColor(Color.GRAY);
        pPuntuacion.setStyle(Paint.Style.FILL);
        pPuntuacion.setAlpha(150);

        //textPaint = new TextPaint();
        //textPaint.setTypeface(face);
        tp.setTextSize(altoPantalla / 20); // tamaño del texto en pixels
        //textPaint.setTextAlign(Paint.Align.CENTER); // Alineación del texto
        tp.setColor(Color.BLACK); // C
    }

    public void dibujaPuntuacion(Canvas c){
        puntuacionRect = new RectF(anchoPantalla/32*24, 0, anchoPantalla, altoPantalla/16*2.5f);
        c.drawRect(puntuacionRect, pPuntuacion);
        for (int i = 0; i < gato.numVidas; i++) {
            c.drawBitmap(vidaBitmap, anchoPantalla/32 * (31 - i), altoPantalla / 16 * 0.5f, null);
        }
        c.drawText(""+gato.puntos, anchoPantalla/32*27.5f,  altoPantalla / 16 *2.25f, tp);
        c.drawBitmap(monedasPuntuacionBitmap, anchoPantalla /32 *30, altoPantalla/16 * 1.5f, null);
    }

    public void setSonidosMusica(){
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
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

        mediaPlayer=MediaPlayer.create(context, R.raw.city_ambience);
        int v = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(v/3, v/3);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void efectoVibracion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        }else {
            vibrator.vibrate(300);
        }
    }

    abstract  void setArbolesRect();

    abstract void setCoches();

}
