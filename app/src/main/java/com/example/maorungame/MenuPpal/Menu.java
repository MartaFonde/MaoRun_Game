package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.maorungame.JuegoSV;
import com.example.maorungame.Pantalla;

abstract public class Menu extends Pantalla {

    /**
     * Contexto
     */
    protected Context context;

    /**
     * Botón de retroceso
     */
    protected RectF btnAtras;

    /**
     * Imagen del botón de retroceso
     */
    protected Bitmap atrasBitmap;

    /**
     * Construye la pantalla de menú a partir de unas dimensiones alto y ancho de pantalla y de
     * un número identificativo. Si la pantalla de menú no es la pantalla de menú principal se
     * crea un rect de botón de retroceso y la imagen correspondiente a dicho botón.
     * @param context contexto
     * @param anchoPantalla ancho de pantalla
     * @param altoPantalla alto de pantalla
     * @param numPantalla número identificativo de la pantalla
     */
    public Menu(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);
        this.context = context;

        if(numPantalla != 1){
            btnAtras = new RectF(0, altoPantalla/16 * 13, anchoPantalla / 32 * 3,
                    altoPantalla);
            atrasBitmap = Pantalla.escala(context, "menu/menu_atras.png",
                    anchoPantalla / 32 * 3, altoPantalla/16*3);
        }
    }

    /**
     * Dibuja en el lienzo el fondo del menú y el botón de retroceso, excepto en la pantalla de menú principal.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c){
        super.dibuja(c);
        c.drawBitmap(fondoMenu, 0, 0, null);
        if(numPantalla != 1){       //1 menú ppal
            c.drawBitmap(atrasBitmap, 0, altoPantalla/16*13, null);
        }
    }

    /**
     * Obtiene coordenadas de pulsaciones al tocar pantalla. Si la pantalla actual no es el menú
     * principal, es una opción del menú que contiene un botón de retroceso y si rect del botón contiene
     * las coordenadas x,y retorna al menú principal. Para que el mediaPlayer no vuelva a reproducir la
     * música, pone la booleana resettMusic a false.
     * @param event evento de pulsación
     * @return devuelve 1 para volver al menú principal. En caso contrario, o si la pantalla actual es
     * el menú principal, devuelve el número de la pantalla actual.
     */
    public int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();

        if(numPantalla != 1 && btnAtras.contains(x,y)) {
            JuegoSV.resetMusic = false; //para que no se reproduzca de nuevo la música (se solapa con la que ya suena)
            return 1;       //vuelve a menu principal
        }
        return numPantalla;
    }
}
