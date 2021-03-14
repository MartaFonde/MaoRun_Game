package com.example.maorungame.Escenas;

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
import android.util.Log;
import android.view.MotionEvent;

import com.example.maorungame.ElemEscena.Controles;
import com.example.maorungame.ElemEscena.Gato;
import com.example.maorungame.ElemEscena.Moneda;
import com.example.maorungame.ElemEscena.Puntuacion;
import com.example.maorungame.JuegoSV;
import com.example.maorungame.MenuEscenas.PauseEscena;
import com.example.maorungame.Pantalla;
import com.example.maorungame.R;
import com.example.maorungame.ElemEscena.Trafico;

import java.util.ArrayList;

abstract public class Escena extends Pantalla {

    /**
     * Imagen de fondo
     */
    Bitmap fondo;

    /**
     * Array de rect de las zonas que ocupan los árboles
     */
    RectF[] arbolesRect;

    /**
     * Proporción del ancho de pantalla
     */
    float propW;

    /**
     * Proporción del alto de pantalla
     */
    float propH;

    /**
     * Controles para mover el gato
     */
    Controles controles;

    /**
     * Coches que circulan por la escena
     */
    Trafico trafico;

    /**
     * Personaje del jugador
     */
    Gato gato;

    /**
     * Items de monedas
     */
    Moneda moneda;

    /**
     * Puntuación lograda y vidas
     */
    Puntuacion puntuacion;

    /**
     * Gestiona las acciones que se producen si hay colisión entre el gato y un coche
     */
    boolean colisionCoche = false;

    /**
     * Movimiento inicial del gato, dirección arriba
     */
    public static int mov = 3;

    /**
     * Indice del coche con el que colisiona el gato
     */
    int cocheColision = -1;

    /**
     * Indice del último coche que se mueve
     */
    int nuevoCoche;

    /**
     * Contador que mide cuando se puede volver a producir otra colisión con efectos
     */
    int cont = 0;

    /**
     * Gestiona los efectos de sonido
     */
    SoundPool efectosSonido;

    /**
     * Efecto de sonido del coche en una colisión
     */
    int sonidoCoche;

    /**
     * Efecto de sonido del gato en una colisión
     */
    int sonidoGato;

    /**
     * Efecto de sonido producido cuando el gato coge una moneda
     */
    int sonidoMoneda;

    /**
     * Número máximo de efectos de sonido que pueden sonar simultáneamente
     */
    final int maxSonidosSimultaneos = 10;

    /**
     * Vibrador
     */
    Vibrator vibrator;

    /**
     * Botón de pausa
     */
    RectF rectPausa;

    /**
     * Imagen del botón de pausa
     */
    Bitmap bitmapPausa;

    /**
     * Estilo del botón de pausa
     */
    Paint pPausa;

    /**
     * Pantalla del menú de pausa
     */
    PauseEscena pantallaPause;

    /**
     * Indica si la escena está en pausa o no
     */
    boolean pause = false;


    /**
     * Clase base de cada Escena. Éstas se crearán a partir de unas dimensiones ancho y alto de pantalla,
     * de un número identificativo y de un objeto tipo Gato, que será el jugador.
     * Instancia elementos básicos que componen todas las escenas y con los que interactúa el jugador:
     * monedas, tráfico y controles. Instancia la puntuación que contiene los puntos y vidas.
     * Llama a la función encargada de establecer la música y los sonidos.
     * Crea el rect del botón de pausa y su imagen redimensionada asociada.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     * @param gato jugador
     */
    public Escena(Context context, int anchoPantalla, int altoPantalla, int numPantalla, Gato gato) {
        super(context, anchoPantalla,altoPantalla, numPantalla);
        this.propW = anchoPantalla / 32;
        this.propH = altoPantalla / 16;

        trafico = new Trafico(context, anchoPantalla, altoPantalla);
        controles = new Controles(context, anchoPantalla, altoPantalla);
        moneda = new Moneda(context, anchoPantalla, altoPantalla);
        puntuacion = new Puntuacion(context, anchoPantalla, altoPantalla);
        this.gato = gato;

        setSonidosMusica();

        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        
        bitmapPausa = Pantalla.escala(context, "juego_pause.png", (int)propW*2, (int)propH*2);
        rectPausa = new RectF(propW * 0.5f, propH * 0.5f, propW*2.5f, propH*2.5f);
        pPausa = new Paint();
        pPausa.setAlpha(150);
    }

    /**
     * Establece el fondo de la escena adaptándolo al ancho y alto de la pantalla.
     * @param fondo imagen de fondo
     */
    public void setFondo(Bitmap fondo) {
        this.fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, true);
    }

    /**
     * Dibuja fondo de escena.
     * @param c lienzo
     */
    public void dibujaFondo(Canvas c){
        c.drawBitmap(fondo, 0, 0, null);
    }

    /**
     * Dibuja todos los elementos de Escena: fondo, monedas, coches, gato, puntuación, controles y
     * botón de pausa. Si se produce colisión del gato con algún coche, se dibuja fondo rojo momentáneo.
     * Si el juego está en pausa, además de dibujar todos los elementos, dibuja lo correspondiente
     * en la función dibuja de la pantalla de pausa.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c){
        try{
            dibujaFondo(c);
            if(colisionCoche){
                cont++;
                if(cont % 2 == 0 && cont <= 10){
                    c.drawColor(Color.RED, PorterDuff.Mode.LIGHTEN);
                }
                if(cont == 50){
                    colisionCoche = false;
                    cont = 0;
                }
            }
            moneda.dibujaMonedas(c, pause);
            trafico.dibujaCoches(c);
            gato.dibujaGato(c);
            puntuacion.dibujaPuntuacion(c, gato.numVidas, gato.puntos);
            controles.dibujaControles(c);
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
     * Si vidas es cero, cambia pantalla a fin de partida.
     * Si la pantalla está en pause no hace nada.
     */
    public void actualizaFisica() {
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
                if (trafico.coches[i].rectangulo.intersect(gato.rectangulo) && nuevoCoche != cocheColision && !colisionCoche) {
                    colisionCoche = true;
                    if(JuegoSV.sonido){
                        efectosSonido.play(sonidoCoche, JuegoSV.volumen, JuegoSV.volumen,1, 0, 1);
                        efectosSonido.play(sonidoGato, JuegoSV.volumen, JuegoSV.volumen, 1, 0, 1);
                    }
                    if(JuegoSV.vibracion){
                        efectoVibracion();
                    }
                    cocheColision = i;
                    gato.numVidas--;
                    if(gato.numVidas == 0){
                        JuegoSV.mediaPlayer.stop();
                        JuegoSV.cambiaPantalla(9);
                    }
                }
                //Cuando el coche colisión deje de interset el jugador, ya podría volver a colisionar
                if (cocheColision != -1 && !trafico.coches[cocheColision].rectangulo.intersect(gato.rectangulo)) {
                    cocheColision = -1;
                }
            }
        }
    }

    /**
     * Obtiene las coordenadas de pulsación y, si la escena no está en pausa, comprueba si los rect de
     * controles contienen las coordenadas. Si alguno las contiene, comprueba si el gato se puede mover
     * (no colisiona con árboles) y ejecuta la función encargada de modificar las posiciones del rect de
     * gato. Cuando el gato efectúa el cambio de posición, llama a la función que comprueba
     * si en la nueva posición colisiona con algún rect de monedas.
     * Si el movimiento es en dirección arriba (decrementa coordenada y) se gestiona el movimiento
     * en cada Escena, porque varían los resultados de sus acciones dependiendo de la Escena.
     * Si el rect de pausa contiene las coordenadas, se pausa la dinámica del juego y se crea el menú.
     * Si el juego está pausado, y el TouchEvent de pantallaPause retorna 0, las dinámicas del juego
     * vuelven a funcionar.
     * Si ningún rect contiene las coordenadas, la imagen del gato es el gato parado.
     * @param event evento
     * @return número de pantalla
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
                    gato.parado();
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
        return numPantalla;
    }

    /**
     * Comprueba si la posición futura de gato colisiona con algún rect Arbol para comprobar si el
     * gato puede efectuar el movimiento.
     * @param posFutura posición futura de gato
     * @return true si colisiona (el gato no puede efectuar movimiento), false en caso contrario.
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
     * Comprueba si el rect de gato colisiona con algún rect de monedas. Si colisiona, se ejecuta
     * el sonido correspondiente si está activado sonido, incrementa la variable puntos de gato y
     * elimina el rect de la colección monedas.
     */
    public void colisionMonedas(){
        for (int i = moneda.monedasRect.size() -1 ; i >= 0; i--) {
            if(moneda.monedasRect.get(i).intersect(gato.rectangulo)){
                if(JuegoSV.sonido){
                    efectosSonido.play(sonidoMoneda, JuegoSV.volumen, JuegoSV.volumen, 1, 0, 1);
                }
                gato.puntos+=100;
                moneda.monedasRect.remove(i);
                break;
            }
        }
    }

    /**
     * Establece los sonidos y la música.
     */
    public void setSonidosMusica(){
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
            //JuegoSV.mediaPlayer.stop();
            JuegoSV.mediaPlayer= MediaPlayer.create(context,R.raw.city_ambience);
            JuegoSV.volumen = 0.7f;
            JuegoSV.mediaPlayer.setVolume(JuegoSV.volumen, JuegoSV.volumen);
            JuegoSV.mediaPlayer.setLooping(true);
        }

        if(JuegoSV.musica && numPantalla == 6){
            JuegoSV.mediaPlayer.start();
        }
    }

    /**
     * Define el vibrador.
     */
    public void efectoVibracion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        }else {
            vibrator.vibrate(300);
        }
    }

    /**
     * Crea el array de los rect de árboles. Las posiciones de los rect y el tamaño del array
     * dependen de cada escena.
     */
    abstract  void setArbolesRect();

}
