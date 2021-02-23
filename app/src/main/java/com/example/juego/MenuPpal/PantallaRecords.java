package com.example.juego.MenuPpal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.juego.JuegoSV;
import com.example.juego.Pantalla;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class PantallaRecords extends Pantalla {
    boolean menu;
    protected Bitmap atrasBitmap;
    protected Bitmap avanzaBitmap;
    protected RectF btnAtras;
    protected RectF btnAvanzar;
    HashMap<Integer, String> rec = new HashMap<>();

    int x = 16;
    int y = 5;

    public PantallaRecords(Context context, int anchoPantalla, int altoPantalla, int numPantalla) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        tpVerde.setTextAlign(Paint.Align.CENTER);

        if(numPantalla == 2){
            btnAtras = new RectF(0, altoPantalla/16 * 13, anchoPantalla / 32 * 3,
                    altoPantalla);
            atrasBitmap = Pantalla.escala(context, "menu/menu_atras.png",
                    anchoPantalla / 32 * 3, altoPantalla/16*3);
        }else{
            btnAvanzar = new RectF(anchoPantalla/32 * 29, altoPantalla/16 * 13, anchoPantalla, altoPantalla);
            avanzaBitmap = Pantalla.escala(context, "menu/menu_avance.png",
                    anchoPantalla / 32 * 3, altoPantalla/16*3);
        }
        leerFichero();
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        if(numPantalla == 2){
            c.drawBitmap(atrasBitmap, 0, altoPantalla/16*13, null);
        }else{
            c.drawBitmap(avanzaBitmap,anchoPantalla/32 * 29, altoPantalla/16 * 13, null );
        }
        c.drawText("RÉCORDS", anchoPantalla / 2, altoPantalla / 16 * 2, tpVerde);
        for (int i = 0; i < records.size(); i++) {
            c.drawText(records.get(i), anchoPantalla / 32 * x, altoPantalla/16 * y, tpVerde);
            y += 2;
            if(i == records.size() - 1){
                x = 16;
                y = 5;
            }
        }
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(numPantalla == 2){
                if(btnAtras.contains(x,y)){
                    JuegoSV.restartMusica = false;
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

    ArrayList<String> records;
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
