package com.example.juego;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
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
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

abstract public class Escena extends Pantalla{
    Context context;
    Bitmap fondo;
    int numEscena=0;
    int anchoPantalla;
    int altoPantalla;

    RectF[] arbolesRect;

    ArrayList<RectF> monedasRect;
    ArrayList<PointF> posicionMonedas;
    int totalMonedas = 10;       //pantalla pequeña no ejecuta con >8 monedas
    int anchoMoneda;
    int altoMoneda;
    Bitmap conjuntoMonedas;
    Bitmap[] monedas;
    int col = 0;
    int cont;
    Bitmap monedaActual;

    Paint p;
    //TextPaint tp;
    float propW;
    float propH;

    Controles controles;
    Trafico trafico;
    Gato gato;
    Bitmap bitmapGato;
    boolean colision = false;
    static int mov = 3;
    int cocheColision = -1;
    int nuevoCoche;

    AudioManager audioManager;
    SoundPool efectosSonido;
    int sonidoCoche, sonidoGato, sonidoMoneda;
    final int maxSonidosSimultaneos = 10;
    MediaPlayer mediaPlayer;

    Vibrator vibrator;
    long[] patronVibrator = {0, 200,50, 200};

    public Escena(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla,altoPantalla, numPantalla);
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        //this.numEscena = numEscena;
        this.propW = anchoPantalla / 32;
        this.propH = altoPantalla / 16;
        //arbolesRect = new RectF[numArboles];
        monedasRect = new ArrayList<>();
        posicionMonedas = new ArrayList<>();
        this.anchoMoneda = anchoPantalla/32;
        this.altoMoneda = altoPantalla/16;
        this.conjuntoMonedas = Pantalla.escala(context, R.drawable.moneda, anchoMoneda, altoMoneda);
        monedas = new Bitmap[5];
        setBitmapMoneda();

        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setAlpha(150);

        //tp = super.tp;
        trafico = new Trafico(context, anchoPantalla, altoPantalla);
        controles = new Controles(context, anchoPantalla, altoPantalla);
        bitmapGato = Pantalla.escala(context, R.drawable.gato, (anchoPantalla/32)*4, (altoPantalla/16)*6);
        gato = new Gato(bitmapGato, anchoPantalla/2-1, altoPantalla/16*14, anchoPantalla / (32*3));

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

        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public Bitmap getFondo() {
        return fondo;
    }

    public void setFondo(Bitmap fondo) {
        if(fondo != null)
        this.fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, true);;
    }

    public void dibujaFondo(Canvas c){
        c.drawBitmap(fondo, 0, 0, null);
    }
    @Override
    public void dibuja(Canvas c){
        try{
            if(colision){
                c.drawColor(Color.RED, PorterDuff.Mode.LIGHTEN);
                colision = false;
            }
            dibujaMonedas(c);
            trafico.dibujaCoches(c);
            gato.dibujaGato(c);
            controles.dibujaControles(c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizaFisica() {        //movimiento automatico del juego
        if (trafico.coches.length > 0) {
            for (int i = 0; i < trafico.coches.length; i++) {
                nuevoCoche = i;
                if (i % 2 == 0) {
                    trafico.coches[i].moverDerecha(anchoPantalla);
                } else {
                    trafico.coches[i].moverIzquierda(anchoPantalla);
                }
                if (trafico.coches[i].rectangulo.intersect(gato.rectangulo) && nuevoCoche != cocheColision) {
                    int v = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    efectosSonido.play(sonidoCoche, v, v, 1, 0, 1);
                    efectosSonido.play(sonidoGato, v, v, 1, 0, 1);
                    efectoVibracion();
                    cocheColision = i;
                    controles.vidas--;      //FIXME cada cambio de escena restaurase
                    colision = true;    //para que dibuje pantalla roja
                }
                //Cando coche colisión deixe de interset o xogador, xa podería volver colisionar
                if (cocheColision != -1 && !trafico.coches[cocheColision].rectangulo.intersect(gato.rectangulo)) {
                    cocheColision = -1;
                }
            }
        }
    }

    int onTouchEvent(MotionEvent event){
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
                }
                break;
            case MotionEvent.ACTION_UP:
                gato.parado();
                //Log.i("parado", "");
                break;
        }
        return numEscena;
    }

    public boolean colisionArboles(RectF posFutura) {
        for (RectF arbol : arbolesRect) {
            if (arbol.intersect(posFutura)) {
                return true;
            }
        }
        return false;
    }

    public void colisionMonedas(){
        for (int i = monedasRect.size() -1 ; i >= 0; i--) {
            if(monedasRect.get(i).intersect(gato.rectangulo)){
                int v = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                efectosSonido.play(sonidoMoneda, v, v, 1, 0, 1);
                controles.puntos+=100;      // FIXME cada cambio de escena restaurase
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
        //RectF pos;

        while(monedasRect.size() < totalMonedas) {
            //para que las monedas no se situen muy en los extremos de la pantalla
            //limito la superficie en la que pueden aparecer las monedas
            x = (float)Math.random()*(anchoPantalla-anchoMoneda*4)+anchoMoneda*2;
            y = (float)Math.random()*(altoPantalla-altoMoneda*4)+altoMoneda*2;
            //pos = new RectF(x, y, x+anchoMoneda, y+altoMoneda);
            boolean posValida = true;

            for (int i = 0; i < arbolesRect.length; i++) {
                if(arbolesRect[i].contains(new RectF(x, y, x+anchoMoneda, y+altoMoneda)) ||
                        arbolesRect[i].intersect(new RectF(x, y, x+anchoMoneda, y+altoMoneda))){
                    posValida = false;
                    break;
                }
            }

            if(posValida){
                for (int i = 0; i < monedasRect.size(); i++) {
                    if(monedasRect.get(i).intersect(new RectF(x, y, x+anchoMoneda, y+altoMoneda))
                            || monedasRect.get(i).contains(new RectF(x, y, x+anchoMoneda, y+altoMoneda))
                            || Math.abs(monedasRect.get(i).right - x) < anchoMoneda * 2
                            || Math.abs(monedasRect.get(i).left - x+anchoMoneda) < anchoMoneda *2
                            || Math.abs(monedasRect.get(i).top - y+altoMoneda) < altoMoneda
                            || Math.abs(monedasRect.get(i).bottom - y) < altoMoneda){
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
        if(++cont % 10 == 0){
            monedaActual = actualizaImagenMoneda();     //bitmap de la animacion de moneda que se va a mostrar
        }
        //Repintando os rect de monedasRect algúns cambiaban de dimensions ancho + alto
        //Gardo pos left, top e repinto coas súas medidas. Volvo asignar a monedasRect
        for (int i = 0; i < monedasRect.size(); i++) {
            monedasRect.set(i, new RectF(posicionMonedas.get(i).x, posicionMonedas.get(i).y,
                    posicionMonedas.get(i).x+anchoMoneda, posicionMonedas.get(i).y + altoMoneda));
            c.drawBitmap(monedaActual, monedasRect.get(i).left, monedasRect.get(i).top, null);
            c.drawRect(monedasRect.get(i), p);
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

    public void dibujaArboles(Canvas c){
        for (RectF arbol : arbolesRect) {
            c.drawRect(arbol, p);
        }
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
