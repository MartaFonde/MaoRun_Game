package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

public class Escenario {

    Bitmap fondo;

    int anchoPantalla;
    int altoPantalla;

    RectF[] arbolesRect;

    ArrayList<RectF> monedasRect;
    int totalMonedas = 15;
    int anchoMoneda;
    int altoMoneda;

    Bitmap imgMoneda;
    Bitmap[] moneda;
    int col = 0;
    int cont;
    Bitmap monedaActual;

    Paint p2;

    Rect pista1;
    Rect pista2;
    Rect pista3;
    Rect pista4;

    Paint pPista;

    public Escenario(Bitmap fondo, Bitmap imagenMoneda, int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        this.fondo = fondo;

        arbolesRect = new RectF[25];
        monedasRect = new ArrayList<>();

        this.anchoMoneda = anchoPantalla/32;
        this.altoMoneda = altoPantalla/16;
        this.imgMoneda = imagenMoneda;
        moneda = new Bitmap[5];

        p2 = new Paint();
        p2.setColor(Color.BLUE);
        p2.setStyle(Paint.Style.STROKE);
        p2.setStrokeWidth(5);
        p2.setAlpha(150);

        setRectArboles(anchoPantalla, altoPantalla);
        setRectMonedas(anchoPantalla, altoPantalla);
        setBitmapMoneda();
    }

    public void setBitmapMoneda(){
        for (int i = 0; i < moneda.length; i++) {
            moneda[i] = Bitmap.createBitmap(imgMoneda, imgMoneda.getWidth()/5*i, 0, anchoMoneda, altoMoneda);
        }
    }

    public Bitmap[] getBitmapMoneda(){
        return moneda;
    }

    public void setRectArboles(int w, int h){
        //fila1
        arbolesRect[0] = new RectF(0, h / 16 * 12, w / 32 *4*1.005f, h );
        arbolesRect[1] = new RectF(w / 32 * 4, h / 16 * 13 * 1.02f, w / 32 *7 *0.99f, h);
        arbolesRect[2] = new RectF(w / 32 * 7, h / 16 * 14 * 1.015f, w / 32 *8 * 0.99f, h);
        arbolesRect[3] = new RectF(w / 32 * 8, h / 16 * 15 * 1.007f, w / 32*11 * 1.01f, h);
        arbolesRect[4] = new RectF(w / 32 * 11 * 1.005f, h / 16 * 13 * 1.02f, w / 32 * 12 , h / 16 * 14);
        arbolesRect[5] = new RectF(w / 32 * 14 * 1.005f, h / 16 * 15 * 1.02f, w / 32*15 * 1.01f, h);
        arbolesRect[6] = new RectF(w / 32 * 18 *1.005f, h / 16 * 15 * 1.02f, w / 32*20, h);
        arbolesRect[7] = new RectF(w / 32 * 23*1.005f, h / 16 * 14 * 1.02f, w / 32*25, h);
        arbolesRect[8] = new RectF(w / 32 * 25 * 1.015f , h / 16 * 13 * 1.02f, w / 32*29, h);
        arbolesRect[9] = new RectF(w / 32 * 29*1.02f , h / 16 * 12, w, h);
        //fila2
        arbolesRect[10] = new RectF(w / 32 * 1 * 1.05f, h / 16 * 9 * 1.03f, w / 32 *2 , h / 16 * 10 / 1.04f);
        arbolesRect[11] = new RectF(w / 32 * 10, (h / 16) * 9*1.03f, (w / 32) *12, h / 16 * 10 / 1.04f);
        arbolesRect[12] = new RectF(w / 32 * 29*1.015f, (h / 16) * 9 *1.03f, (w / 32)*30, h / 16 * 10/ 1.04f);
        //fila3
        arbolesRect[13] = new RectF(0, (h / 16) * 6*1.015f, (w / 32)*1 * 1.03f, h / 16 * 7 / 1.04f);

        arbolesRect[14] = new RectF(w / 32 * 3*1.015f, (h / 16) * 5*1.05f, (w / 32) * 4 * 0.99f, (h / 16) * 6/ 1.03f);

        arbolesRect[15] = new RectF(w / 32 * 8*1.02f, (h / 16) * 6 * 1.015f, (w / 32)* 10 *1.015f, h / 16 * 7/ 1.03f);

        arbolesRect[16] = new RectF(w / 32 * 18 *1.005f, (h / 16) * 5*1.05f, (w / 32)*19*1.005f, h / 16 * 6/ 1.03f);

        arbolesRect[17] = new RectF(w / 32 * 21 *1.007f * 1.007f, (h / 16) * 6 * 1.05f, (w / 32)*22 , h / 16 * 7/ 1.03f);
        arbolesRect[18] = new RectF(w / 32 * 29 * 1.01f, (h / 16) * 5*1.05f, (w / 32)*30, h / 16 * 6/ 1.03f);
        arbolesRect[19] = new RectF(w / 32 * 31 * 1.015f, (h / 16) * 6 *1.03f, w, h / 16 * 7/ 1.03f);
        //fila4
        arbolesRect[20] =  new RectF(0, 0, (w / 32)* 7 *1.015f, h / 16 * 1/ 1.03f);
        arbolesRect[21] = new RectF(w / 32 * 11 * 1.01f, 0, (w / 32)* 12 *1.005f, h / 16 * 1 / 1.06f);
        arbolesRect[22] = new RectF(w / 32 * 21 *1.02f, 0, (w / 32) * 22, h / 16 * 1 / 1.06f);
        arbolesRect[23] = new RectF(w / 32 * 25 * 1.01f, 0, (w / 32) * 26, h / 16 * 1 / 1.06f);
        arbolesRect[24] = new RectF(w / 32 * 29 * 1.01f, 0, w, h / 16 * 1 / 1.03f);
    }

    public RectF[] getRectArboles(){
        return arbolesRect;
    }

    public void setRectMonedas(int anchoPantalla, int altoPantalla){
        float x;
        float y;
        RectF pos;

        while(monedasRect.size() <= totalMonedas) {
            //para que las monedas no se situen muy en los extremos de la pantalla
            //limito la superficie en la que pueden aparecer las monedas
            x = (float)Math.random()*(anchoPantalla-anchoMoneda*5)+anchoMoneda*2;
            y = (float)Math.random()*(altoPantalla-altoMoneda*5)+altoMoneda*2;
            pos = new RectF(x, y, x+anchoMoneda, y+altoMoneda);
            boolean posValida = true;

            for (int i = 0; i < arbolesRect.length; i++) {
                if(arbolesRect[i].contains(pos) || arbolesRect[i].intersect(pos)){
                    posValida = false;
                    break;
                }
            }
            if(posValida){
                for (int i = 0; i < monedasRect.size(); i++) {
                    if(monedasRect.get(i).intersect(pos) || monedasRect.get(i).contains(pos)
                    || Math.abs(monedasRect.get(i).right - pos.left) < 40
                    || Math.abs(monedasRect.get(i).left - pos.right) < 40
                    || Math.abs(monedasRect.get(i).top - pos.bottom) < 20
                    || Math.abs(monedasRect.get(i).bottom - pos.top) < 20){
                        posValida = false;
                        break;
                    }
                }
            }
            if(posValida) monedasRect.add(pos);  //coleccion de los rect que contienen monedas -> posiciones
        }
    }

    public ArrayList<RectF> getRectMonedas(){
        return monedasRect;
    }

    public void dibujaFondo(Canvas c){
        c.drawBitmap(fondo, 0, 0, null);
    }

    public void dibujaMonedas(Canvas c){
        RectF rect;

        cont++;
        if(cont%10==0){
            monedaActual = actualizaImagenMoneda();     //bitmap de la animacion de moneda que se va a mostrar
        }else if(cont == 99){
            cont = 0;
        }
        //repinta Rect de monedasRect
        for (RectF m: monedasRect) {
            rect = new RectF(m.left, m.top, m.left+anchoMoneda,m.top+ altoMoneda);
            //c.drawRect(rect, p2);
            c.drawBitmap(monedaActual, rect.left, rect.top, p2);
        }
    }

    public void dibujaArboles(Canvas c, int anchoPantalla, int altoPantalla){
        setRectArboles(anchoPantalla, altoPantalla);
//        for (RectF arbol : arbolesRect) {
//            c.drawRect(arbol, p2);
//        }
    }

    public Bitmap actualizaImagenMoneda() {
        if(col < moneda.length){
            this.imgMoneda = moneda[col];
            col++;
        }else{
            col = 0;
        }
        return imgMoneda;
    }

//    public void pistas() {
//        pista1 = new Rect(0, (altoPantalla / 16) * 10, anchoPantalla, (altoPantalla / 16) * 12);
//        pista2 = new Rect(0, (altoPantalla / 16) * 7, anchoPantalla, (altoPantalla / 16) * 9);
//        pista3 = new Rect(0, (altoPantalla / 16) * 3, anchoPantalla, (altoPantalla / 16) * 5);
//        pista4 = new Rect(0, (altoPantalla / 16) * 1, anchoPantalla, (altoPantalla / 16) * 3);
//        pPista = new Paint();
//        pPista.setColor(Color.MAGENTA);
//        pPista.setAlpha(50);
//    }
//
//    public void dibujaPistas(Canvas c) {
//        pista1 = new Rect(0, (altoPantalla / 16) * 10, anchoPantalla, (altoPantalla / 16) * 12);
//        pista2 = new Rect(0, (altoPantalla / 16) * 7, anchoPantalla, (altoPantalla / 16) * 9);
//        pista3 = new Rect(0, (altoPantalla / 16) * 3, anchoPantalla, (altoPantalla / 16) * 5);
//        pista4 = new Rect(0, altoPantalla / 16, anchoPantalla, (altoPantalla / 16) * 3);
//        c.drawRect(pista1, pPista);
//        c.drawRect(pista2, pPista);
//        c.drawRect(pista3, pPista);
//        c.drawRect(pista4, pPista);
//    }


}
