package com.example.maorungame.FinPartida;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.maorungame.Pantalla;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class GuardarRecord extends Pantalla {

    float propW;
    float propH;
    ArrayList<String> nombre = new ArrayList<>();
    Hashtable<RectF, String> teclas;
    RectF btnborrar;
    RectF btnOK;

    RectF[] letras;
    int puntos;

    Paint pOk;
    Paint pBorrar;

    RectF btnAvanza;
    Bitmap avanzaBitmap;

    public GuardarRecord(Context context, int anchoPantalla, int altoPantalla, int numPantalla, int puntos) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        pOk = new Paint();
        pOk.setStyle(Paint.Style.FILL);
        pOk.setColor(Color.argb(220, 0, 128, 64));

        pBorrar = new Paint();
        pBorrar.setStyle(Paint.Style.FILL);
        pBorrar.setColor(Color.argb(220, 255, 40,40));

        propW = anchoPantalla /32 ;
        propH = altoPantalla / 16;
        this.puntos = puntos;

        avanzaBitmap = Pantalla.escala(context, "menu/menu_avance.png", (int)propW * 2, (int)propH * 2);

        teclas = new Hashtable<>();
        letras = new RectF[3];
        setTeclas();
    }

    /**
     * Dibuja fondo negro, el botón de avanza, los botones de borrar letra y OK, las letras del nombre
     * y las teclas para escribir el nombre.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawBitmap(avanzaBitmap, btnAvanza.left, btnAvanza.top, null);
        c.drawText("GUARDAR RÉCORD", anchoPantalla/2, altoPantalla/16 * 2, tpBeige);

        tpBeige.setTextSize(altoPantalla/12);
        c.drawRect(btnborrar, pBorrar);
        c.drawText("Borrar", (btnborrar.left + btnborrar.right)/2, btnborrar.bottom - propH/2, tpBeige);
        tpBeige.setTextSize(altoPantalla/10);
        c.drawRect(btnOK, pOk);
        c.drawText("OK", (btnOK.left + btnOK.right)/2, btnOK.bottom - propH/2, tpBeige);

        for (Map.Entry<RectF, String> e : teclas.entrySet()){
            c.drawRect(e.getKey(), pBotonVerde);
            c.drawText(e.getValue(), (e.getKey().left + e.getKey().right)/2, e.getKey().bottom - propH/2, tpBeige);
        }

        int i = 0;
        for (String n : nombre) {
            c.drawText(n, (letras[i].left + letras[i].right)/2, letras[i].bottom - propH/2, tpBeige);
            i++;
        }
    }

    /**
     * Si las coordenadas de pulsación están contenidas en el botón de borrar elimina la última letra
     * del nombre. Si están contenidas en alguna tecla añade la letra asociada al nombre con un límite
     * máximo de tres letras. Si lo están en en el botón OK y el nombre tiene más de una letra llama
     * a la función que guarda el récord, y si lo están en el botón de avanzar no guarda el récord.
     * En los dos últimos casos, avanza a la pantalla que muestra los récords.
     * @param event evento
     * @return 14 número de pantalla que muestra los récords, -1 en caso de que las coordenadas no estean
     * contenidas en ningún rect de los botones.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(btnborrar.contains(x,y)){
                if(nombre.size() > 0) nombre.remove(nombre.size()-1);
            }
            for (Map.Entry<RectF, String> e : teclas.entrySet()){
                if(e.getKey().contains(x,y)){
                    if(nombre.size() < 3 ) nombre.add(e.getValue());
                }
            }
            if(btnOK.contains(x,y) && nombre.size() > 0){
                escribirFichero();
                return 14;
            }
            if(btnAvanza.contains(x,y)){
                return 14;
            }
        }
        return -1;
    }

    /**
     * Lee el fichero que guarda los récords y los añade por líneas al arrayList records. Después
     * sobreescribe el fichero con el récord nuevo y los anteriores, ordenados de mayor a menor.
     * Como máximo almacena 5 récords (puntuación + nombre).
     */
    public void escribirFichero(){
        String nom = "";
        for (String n : nombre) {
            nom += n;
        }

        ArrayList<String> records = new ArrayList<>();

        try (FileInputStream fis = context.openFileInput("records.txt");
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader buffer = new BufferedReader(reader)) {
             String linea = "";
             while((linea = buffer.readLine()) != null){
                 records.add(linea+"\n");
             }
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean escrito = false;
        int ind = 0;
        int lineas = 0;
        String record = puntos + " "+nom +"\n";

        try(FileOutputStream fos = context.openFileOutput("records.txt", Context.MODE_PRIVATE)){
            if(records.size() == 0){
                fos.write(record.getBytes());
            }else {
                while (ind < records.size() && lineas < 5) {
                    if (Integer.parseInt(records.get(ind).split(" ")[0]) <= puntos && !escrito) {
                        fos.write(record.getBytes());
                        lineas++;
                        escrito = true;
                        if(lineas < 5) {
                            fos.write(records.get(ind).getBytes());
                            lineas ++;
                            ind++;
                        }
                    } else {
                        fos.write(records.get(ind).getBytes());
                        lineas++;
                        ind++;
                    }
                }
                if(!escrito && lineas < 5) fos.write(record.getBytes());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Inicializa los rect de los botones y añade a la hashtable letras el rect y la letra
     * asociada.
     */
    public void setTeclas(){
        btnAvanza = new RectF(propW * 29, propH*14, anchoPantalla, altoPantalla);

        btnborrar = new RectF(propW * 3, propH * 3, propW * 8, propH * 5);
        btnOK = new RectF(propW * 23, propH * 3, propW * 30, propH * 5);

        letras[0] = new RectF(propW * 11, propH * 3, propW * 13, propH * 5);
        letras[1] = new RectF(propW * 14, propH * 3, propW * 16, propH * 5);
        letras[2] = new RectF(propW * 17, propH * 3, propW * 19, propH * 5);

        teclas.put(new RectF(propW * 2, propH * 7, propW * 4, propH * 9), "Q");
        teclas.put(new RectF(propW * 5, propH * 7, propW * 7, propH * 9), "W");
        teclas.put(new RectF(propW * 8, propH * 7, propW * 10, propH * 9), "E");
        teclas.put(new RectF(propW * 11, propH * 7, propW * 13, propH * 9), "R");
        teclas.put(new RectF(propW * 14, propH * 7, propW * 16, propH * 9), "T");
        teclas.put(new RectF(propW * 17, propH * 7, propW * 19, propH * 9), "Y");
        teclas.put(new RectF(propW * 20, propH * 7, propW * 22, propH * 9), "U");
        teclas.put(new RectF(propW * 23, propH * 7, propW * 25, propH * 9), "I");
        teclas.put(new RectF(propW * 26, propH * 7, propW * 28, propH * 9), "O");
        teclas.put(new RectF(propW * 29, propH * 7, propW * 31, propH * 9), "P");

        teclas.put(new RectF(propW * 2, propH * 10, propW * 4, propH * 12), "A");
        teclas.put(new RectF(propW * 5, propH * 10, propW * 7, propH * 12), "S");
        teclas.put(new RectF(propW * 8, propH * 10, propW * 10, propH * 12), "D");
        teclas.put(new RectF(propW * 11, propH * 10, propW * 13, propH * 12), "F");
        teclas.put(new RectF(propW * 14, propH * 10, propW * 16, propH * 12), "G");
        teclas.put(new RectF(propW * 17, propH * 10, propW * 19, propH * 12), "H");
        teclas.put(new RectF(propW * 20, propH * 10, propW * 22, propH * 12), "J");
        teclas.put(new RectF(propW * 23, propH * 10, propW * 25, propH * 12), "K");
        teclas.put(new RectF(propW * 26, propH * 10, propW * 28, propH * 12), "L");
        teclas.put(new RectF(propW * 29, propH * 10, propW * 31, propH * 12), "Ñ");

        teclas.put(new RectF(propW * 5, propH * 13, propW * 7, propH * 15), "Z");
        teclas.put(new RectF(propW * 8, propH * 13, propW * 10, propH * 15), "X");
        teclas.put(new RectF(propW * 11, propH * 13, propW * 13, propH * 15), "C");
        teclas.put(new RectF(propW * 14, propH * 13, propW * 16, propH * 15), "V");
        teclas.put(new RectF(propW * 17, propH * 13, propW * 19, propH * 15), "B");
        teclas.put(new RectF(propW * 20, propH * 13, propW * 22, propH * 15), "N");
        teclas.put(new RectF(propW * 23, propH * 13, propW * 25, propH * 15), "M");
    }
}
