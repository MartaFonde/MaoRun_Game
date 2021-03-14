package com.example.maorungame.FinPartida;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.example.maorungame.Pantalla;
import com.example.maorungame.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class PantallaGuardaRecord extends Pantalla {

    /**
     * Proporción del ancho de pantalla
     */
    float propW;

    /**
     * Proporción del alto de pantalla
     */
    float propH;

    /**
     * Letras del nombre
     */
    ArrayList<String> nombre = new ArrayList<>();

    /**
     * Botones de las teclas del teclado y su letra correspondiente
     */
    Hashtable<RectF, String> teclas;

    /**
     * Botón de borrar letra
     */
    RectF btnborrar;

    /**
     * Botón de guardar récord
     */
    RectF btnGuardar;

    /**
     * Rectángulos donde se sitúan las letras del nombre
     */
    RectF[] letras;

    /**
     * Puntos logrados
     */
    int puntos;

    /**
     * Estilo del botón de guardar récord
     */
    Paint pGuardar;

    /**
     * Estilo del botón de borrar letra
     */
    Paint pBorrar;

    /**
     * Estilo de las letras del nombre
     */
    TextPaint tpLetras;

    /**
     * Botón de avance
     */
    RectF btnAvanza;

    /**
     * Imagen del botón de avance
     */
    Bitmap avanzaBitmap;

    /**
     * Construye la pantalla en la que se introduce el nombre que compondrá el récord junto a la puntuación
     * a partir de las dimensiones ancho y alto de la pantalla, de un número identificativo y de la puntuación.
     * Crea el estilo de los botones guardar récord y de borrar letra.
     * Asigna la imagen del botón de avanza redimensionada.
     * Llama a la función que crea los rect de los botones de las letras del teclado.
     * @param context contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     * @param numPantalla número identificativo de la pantalla
     * @param puntos puntuación lograda en la partida
     */
    public PantallaGuardaRecord(Context context, int anchoPantalla, int altoPantalla, int numPantalla, int puntos) {
        super(context, anchoPantalla, altoPantalla, numPantalla);

        pGuardar = new Paint();
        pGuardar.setStyle(Paint.Style.FILL);
        pGuardar.setColor(context.getResources().getColor(R.color.guardar));

        pBorrar = new Paint();
        pBorrar.setStyle(Paint.Style.FILL);
        pBorrar.setColor(context.getResources().getColor(R.color.borrar));

        tpLetras = new TextPaint();
        tpLetras.setColor(Color.BLACK);
        tpLetras.setTextSize(tpBeige.getTextSize());
        tpLetras.setTypeface(tpBeige.getTypeface());

        propW = anchoPantalla /32;
        propH = altoPantalla / 16;
        this.puntos = puntos;

        avanzaBitmap = Pantalla.escala(context, "menu/menu_avance.png",
                (int)propW * 2, (int)propH * 2);

        teclas = new Hashtable<>();
        letras = new RectF[3];
        setTeclas();
    }

    /**
     * Dibuja fondo negro, el botón de avanza, los botones de borrar letra y guardar récord,
     * las letras del nombre y las teclas para escribir el nombre.
     * @param c lienzo
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawBitmap(fondoMenu, 0, 0, null);

        c.drawBitmap(avanzaBitmap, btnAvanza.left, btnAvanza.top, null);
        c.drawText(context.getResources().getText(R.string.guardaRecordsTitulo).toString(), anchoPantalla/2, altoPantalla/16 * 2, tpBeige);

        tpBeige.setTextSize(altoPantalla/12);
        c.drawRect(btnborrar, pBorrar);
        c.drawText(context.getResources().getText(R.string.borrar).toString(), (btnborrar.left + btnborrar.right)/2, btnborrar.bottom - propH/2, tpBeige);
        c.drawRect(btnGuardar, pGuardar);
        c.drawText(context.getResources().getText(R.string.guardar).toString(), (btnGuardar.left + btnGuardar.right)/2, btnGuardar.bottom - propH/2, tpBeige);

        for (Map.Entry<RectF, String> e : teclas.entrySet()){
            c.drawRect(e.getKey(), pBotonNaranja);
            c.drawText(e.getValue(), (e.getKey().left + e.getKey().right)/2, e.getKey().bottom - propH/2, tpBeige);
        }

        int i = 0;
        for (String n : nombre) {
            c.drawText(n, (letras[i].left + letras[i].right)/2, letras[i].bottom - propH/2, tpLetras);
            i++;
        }
    }

    /**
     * Si las coordenadas de pulsación están contenidas en el rect del botón de borrar elimina la última letra
     * del nombre. Si están contenidas en el rect de alguna tecla añade la letra asociada al nombre con un límite
     * máximo de tres letras. Si lo están en en el botón guardar y el nombre tiene más de una letra llama
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
            if(btnGuardar.contains(x,y) && nombre.size() > 0){
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
     * Crea los rect de los botones de avanzar, borrar, guardar y los de las letras del nombre.
     * Añade a la hashtable letras el rect de los botones de las letras y la letra asociada.
     */
    public void setTeclas(){
        btnAvanza = new RectF(propW * 30, propH*14, anchoPantalla, altoPantalla);

        btnborrar = new RectF(propW * 2, propH * 3, propW * 8, propH * 5);
        btnGuardar = new RectF(propW * 23, propH * 3, propW * 31, propH * 5);

        letras[0] = new RectF(propW * 11, propH * 3, propW * 13, propH * 5);
        letras[1] = new RectF(propW * 14, propH * 3, propW * 16, propH * 5);
        letras[2] = new RectF(propW * 17, propH * 3, propW * 19, propH * 5);

        String[][] filaLetras = {{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ"},
                {"Z", "X", "C", "V", "B", "N", "M"}};

        int left = 2;
        int right = left+2;
        int top = 7;
        int bottom = top+2;
        for (int i = 0; i < filaLetras.length; i++) {
            if(i == 1){
                left = 2;
                right = left+2;
                top = 10;
                bottom = top+2;
            }else if(i == 2){
                left = 5;
                right = left + 2;
                top = 13;
                bottom = top + 2;
            }
            for (int j = 0; j < filaLetras[i].length; j++) {
                teclas.put(new RectF(propW * left, propH * top, propW * right, propH * bottom), filaLetras[i][j]);
                left+=3;
                right+=3;
            }
        }
    }
}
