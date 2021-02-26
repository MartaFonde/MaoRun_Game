package com.example.maorungame.MenuPpal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.maorungame.JuegoSV;
import com.example.maorungame.Pantalla;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PantallaRecords extends Pantalla {

    protected Bitmap atrasBitmap;
    protected Bitmap avanzaBitmap;
    protected RectF btnAtras;
    protected RectF btnAvanzar;

    final int POS_X = 16;
    final int POS_Y = 5;
    int y = POS_Y;

    ArrayList<String> records;

        //Esta clase se usará para mostrar los récords de la opción del menú principal y para
        //mostrarlos al final de la partida
    public PantallaRecords(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        if(numPantalla == 2){       //opción menú principal
            btnAtras = new RectF(0, altoPantalla/16 * 13, anchoPantalla / 32 * 3,
                    altoPantalla);
            atrasBitmap = Pantalla.escala(context, "menu/menu_atras.png",
                    anchoPantalla / 32 * 3, altoPantalla/16*3);
        }else{      //fin de partida
            btnAvanzar = new RectF(anchoPantalla/32 * 29, altoPantalla/16 * 13, anchoPantalla, altoPantalla);
            avanzaBitmap = Pantalla.escala(context, "menu/menu_avance.png",
                    anchoPantalla / 32 * 3, altoPantalla/16*3);
        }
        leerFichero();
    }

    /**
     * Dibuja el fondo negro, el botón de retroceso si es opción de menú principal o botón de avance
     * si se muestra al final de la partida, y los récords.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);

        if(numPantalla == 2){
            c.drawBitmap(atrasBitmap, 0, altoPantalla/16*13, null);
        }else{
            c.drawBitmap(avanzaBitmap,anchoPantalla/32 * 29, altoPantalla/16 * 13, null );
        }
        tpVerde.setTextSize(altoPantalla/10);
        c.drawText("RÉCORDS", anchoPantalla / 2, altoPantalla / 16 * 2, tpVerde);
        tpVerde.setTextSize(altoPantalla/12);
        for (int i = 0; i < records.size(); i++) {
            c.drawText(records.get(i), anchoPantalla / 32 * POS_X, altoPantalla/16 * y, tpVerde);
            y += 2.5f;
            if(i == records.size() - 1){
                y = POS_Y;
            }
        }
    }

    /**
     * Gestiona el avance o el retroceso de la pantalla dependiendo de si es opción de menú o es llamada
     * al final de la partida. Si es opción de menú asigna a restartMusica false para que no se
     * reproduzca de nuevo la música.
     * @param event evento
     * @return 1 para volver a menú principal, 15 para ir a menú fin de partida, -1 si las coordenadas
     * de pulsación no están contenidas en ningún rect de botón.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(numPantalla == 2){
                if(btnAtras.contains(x,y)){
                    JuegoSV.restartMusica = false;  //Esta clase no hereda de Menu. Para que no se vuelva a rep.mús
                    return 1;
                }
            }else{
                if(btnAvanzar.contains(x,y)){
                    return 15;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * Lee el fichero que guarda los récords y los añade línea a línea al arrayList records.
     */
    public void leerFichero(){
        records = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput("records.txt");
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader buffer = new BufferedReader(reader)) {
            String linea;
            while((linea = buffer.readLine()) != null){
                records.add(linea + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
