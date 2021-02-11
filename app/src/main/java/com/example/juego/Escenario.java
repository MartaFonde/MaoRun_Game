package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;

public class Escenario {

    Bitmap[] fondos;
    Bitmap fondo;
    int numFondo=0;

    int anchoPantalla;
    int altoPantalla;

    RectF[] arbolesRect;

    ArrayList<RectF> monedasRect;
    ArrayList<PointF> posicionMonedas;
    int totalMonedas = 10;       //pantalla pequeña no ejecuta con >8 monedas
    int anchoMoneda;
    int altoMoneda;

    Bitmap imgMoneda;
    Bitmap[] moneda;
    int col = 0;
    int cont;
    Bitmap monedaActual;

    Paint p;

    float propW;
    float propH;

    public Escenario(Bitmap[] fondos, Bitmap imagenMoneda, int numFondo, int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        this.propW = anchoPantalla / 32;
        this.propH = altoPantalla / 16;

        arbolesRect = new RectF[25];

        monedasRect = new ArrayList<>();
        posicionMonedas = new ArrayList<>();

        this.anchoMoneda = anchoPantalla/32;
        this.altoMoneda = altoPantalla/16;
        this.imgMoneda = imagenMoneda;
        moneda = new Bitmap[5];
        setBitmapMoneda();

        p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setAlpha(150);

        this.fondos = fondos;
        setFondos(numFondo);   //1er nivel
    }

    public void setFondos(int numFondo){
        this.numFondo = numFondo;
        fondo = fondos[numFondo];

        if(numFondo == 0){
            setRectArbolesMapa1();
            setPosicionMonedas();
        }else if(numFondo == 1){
            setRectArbolesMapa2();
            setPosicionMonedas();
        }else if(numFondo == 2){
            setRectArbolesMapa3();
            setPosicionMonedas();
        }
    }

    public void setBitmapMoneda(){
        for (int i = 0; i < moneda.length; i++) {
            moneda[i] = Bitmap.createBitmap(imgMoneda, imgMoneda.getWidth()/5*i, 0, anchoMoneda, altoMoneda);
        }
        this.monedaActual = moneda[0];
    }

    public Bitmap[] getBitmapMoneda(){
        return moneda;
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

    public void dibujaFondo(Canvas c){
        c.drawBitmap(fondo, 0, 0, null);
    }

    public void dibujaMonedas(Canvas c, int anchoPantalla, int altoPantalla){
//        RectF rect;
        int anchoMoneda = anchoPantalla / 32;
        int altoMoneda = altoPantalla / 16;

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

    public void dibujaArboles(Canvas c, int anchoPantalla, int altoPantalla){
        if(numFondo == 0){
            setRectArbolesMapa1();
        }else if(numFondo == 1){
            setRectArbolesMapa2();
        }else if(numFondo == 2){
            setRectArbolesMapa3();
        }

        for (RectF arbol : arbolesRect) {
            c.drawRect(arbol, p);
        }
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

    public void setRectArbolesMapa1(){
        //fila1
        arbolesRect[0] = new RectF(0, propH  * 12, propW  *4*1.005f, altoPantalla );
        arbolesRect[1] = new RectF(propW * 4, propH  * 13 * 1.02f, propW *7 *0.99f, altoPantalla);
        arbolesRect[2] = new RectF(propW  * 7, propH  * 14 * 1.015f, propW  *8 * 0.99f, altoPantalla);
        arbolesRect[3] = new RectF(propW  * 8, propH  * 15 * 1.007f, propW *11, altoPantalla);
        arbolesRect[4] = new RectF(propW  * 11 * 1.005f, propH  * 13 * 1.02f, propW  * 12 , propH  * 14);
        arbolesRect[5] = new RectF(propW  * 14 * 1.005f, propH  * 15 * 1.02f, propW *15 * 1.01f, altoPantalla);
        arbolesRect[6] = new RectF(propW  * 18 *1.005f, propH  * 15 * 1.02f, propW * 20, altoPantalla);
        arbolesRect[7] = new RectF(propW  * 23*1.005f, propH  * 14 * 1.02f, propW *25, altoPantalla);
        arbolesRect[8] = new RectF(propW  * 25 * 1.015f , propH  * 13 * 1.02f, propW *29, altoPantalla);
        arbolesRect[9] = new RectF(propW  * 29*1.02f , propH  * 12, anchoPantalla, altoPantalla);
        //fila2
        arbolesRect[10] = new RectF(propW * 1 * 1.05f, propH  * 9 * 1.03f, propW  *2 , propH  * 10 / 1.04f);
        arbolesRect[11] = new RectF(propW  * 10, propH  * 9*1.03f, propW  *12, propH  * 10 / 1.04f);
        arbolesRect[12] = new RectF(propW * 29*1.015f, propH * 9 *1.03f, propW *30, propH * 10/ 1.04f);
        //fila3
        arbolesRect[13] = new RectF(0, propH  * 6*1.015f, propW *1 * 1.03f, propH  * 7 / 1.04f);
        arbolesRect[14] = new RectF(propW * 3*1.015f, propH  * 5*1.05f, propW  * 4 * 0.99f, propH  * 6/ 1.03f);
        arbolesRect[15] = new RectF(propW  * 8*1.02f, propH * 6 * 1.015f, propW * 10 *1.015f, propH * 7/ 1.03f);
        arbolesRect[16] = new RectF(propW  * 18 *1.005f, propH  * 5*1.05f, propW*19*1.005f, propH  * 6/ 1.03f);
        arbolesRect[17] = new RectF(propW  * 21 *1.007f * 1.007f, propH * 6 * 1.05f, propW *22 , propH * 7/ 1.03f);
        arbolesRect[18] = new RectF(propW  * 29 * 1.01f, propH  * 5*1.05f, propW *30, propH  * 6/ 1.03f);
        arbolesRect[19] = new RectF(propW  * 31 * 1.015f, propH  * 6 *1.03f, anchoPantalla, propH  * 7/ 1.03f);
        //fila4
        arbolesRect[20] =  new RectF(0, 0, propW* 7 *1.015f, propH/ 1.03f);
        arbolesRect[21] = new RectF(propW * 11 * 1.01f, 0, propW* 12 *1.005f, propH   / 1.06f);
        arbolesRect[22] = new RectF(propW * 21 *1.02f, 0, propW  * 22, propH   / 1.06f);
        arbolesRect[23] = new RectF(propW  * 25 * 1.01f, 0, propW  * 26, propH  / 1.06f);
        arbolesRect[24] = new RectF(propW  * 29 * 1.01f, 0, anchoPantalla, propH   / 1.03f);

    }

    public void setRectArbolesMapa2(){
        //fila1
        arbolesRect[0] = new RectF(0, propH  * 13, propW  * 6 , altoPantalla);
        arbolesRect[1] = new RectF(propW * 6, propH  * 14 * 1.02f, propW * 7 *0.99f, altoPantalla);
        arbolesRect[2] = new RectF(propW  * 7, propH  * 15 * 1.015f, propW  * 14 * 0.99f, altoPantalla);
        arbolesRect[3] = new RectF(propW  * 9 * 1.015f, propH  * 13 * 1.007f, propW * 11 * 1.01f, propH * 14);
        arbolesRect[4] = new RectF(propW  * 19 * 1.015f, propH  * 13 * 1.02f, propW  * 21 , propH * 14);
        arbolesRect[5] = new RectF(propW  * 17 * 1.015f, propH  * 15 * 1.02f, propW  * 23 , altoPantalla);
        arbolesRect[6] = new RectF(propW  * 23 * 1.005f, propH  * 14 * 1.02f, propW * 27 * 1.01f, altoPantalla);
        arbolesRect[7] = new RectF(propW  * 27 *1.005f, propH  * 13 * 1.02f, anchoPantalla, altoPantalla);
        arbolesRect[8] = new RectF(propW  * 30 *1.005f, propH  * 12 * 1.02f, anchoPantalla, propH * 13);

        //fila2
        arbolesRect[9] = new RectF(0 , propH * 9 * 1.02f, propW * 2, propH * 10 );
        arbolesRect[10] = new RectF(propW  * 4 *1.05f , propH  * 9, propW * 5 / 1.04f, propH * 10 );
        arbolesRect[11] = new RectF(propW * 9 * 1.05f, propH  * 9 * 1.03f, propW  * 10 , propH  * 10 );
        arbolesRect[12] = new RectF(propW  * 14 * 1.015f, propH  * 9* 1.03f, propW *15 , propH  * 10 );
        arbolesRect[13] = new RectF(propW * 22 *1.015f, propH * 9 * 1.03f, propW * 23, propH * 10 );
        arbolesRect[14] = new RectF(propW * 28 *1.015f, propH  * 9 * 1.03f, propW * 29 , propH * 10 );
        arbolesRect[15] = new RectF(propW * 31 *1.015f, propH  * 9 *1.03f, anchoPantalla, propH * 10 );

        //fila3
        arbolesRect[16] = new RectF(0, propH * 4 * 1.015f, propW * 5 *1.015f, propH * 5 / 1.03f);
        arbolesRect[17] = new RectF(propW  * 8 *1.02f, propH * 4 * 1.015f, propW * 9 / 1.005f, propH * 5 / 1.03f);
        arbolesRect[18] = new RectF(propW  * 13 *1.015f * 1.007f, propH * 4 * 1.015f, propW * 14/1.005f , propH * 5 / 1.03f);
        arbolesRect[19] = new RectF(propW  * 20 * 1.015f, propH * 4 * 1.015f, propW * 21, propH * 5 / 1.03f);
        arbolesRect[20] = new RectF(propW  * 27 * 1.015f, propH * 4 * 1.015f, anchoPantalla, propH * 5 / 1.03f);
        //fila4
        arbolesRect[21] =  new RectF(0, 0, propW * 4 / 1.015f, propH *2 / 1.07f);
        arbolesRect[22] = new RectF(propW * 4 * 1.015f, 0, propW* 9 / 1.015f, propH / 1.07f);
        arbolesRect[23] = new RectF(propW  * 22 * 1.01f, 0, propW  * 28, propH / 1.06f);
        arbolesRect[24] = new RectF(propW  * 28 * 1.015f, 0, anchoPantalla, propH *2 / 1.03f);
    }

    public void setRectArbolesMapa3(){      //TODO retocar bordes
        //fila1
        arbolesRect[0] = new RectF(0, propH  * 12, propW  * 6, altoPantalla);
        arbolesRect[1] = new RectF(propW  * 6, propH  * 13 * 1.007f, propW * 10 * 1.01f, altoPantalla);
        arbolesRect[2] = new RectF(propW  * 12 * 1.015f, propH  * 12 * 1.02f, propW  * 13 , propH * 13);
        arbolesRect[3] = new RectF(propW  * 19 * 1.015f, propH  * 13 * 1.02f, propW  * 20 , propH * 14);

        arbolesRect[4] = new RectF(propW  * 22 * 1.005f, propH  * 14 * 1.02f, propW * 24 * 1.01f, altoPantalla);
        arbolesRect[5] = new RectF(propW  * 24 *1.005f, propH  * 13 * 1.02f, propW * 27, altoPantalla);

        arbolesRect[6] = new RectF(propW  * 27 *1.005f, propH  * 12 * 1.02f, anchoPantalla, altoPantalla);
        arbolesRect[7] = new RectF(0 , propH  * 6, propW * 4 / 1.04f, propH * 8 );
        arbolesRect[8] = new RectF(propW * 6 * 1.05f, propH  * 7 * 1.03f, propW  * 9 , propH  * 8);
        arbolesRect[9] = new RectF(propW * 27 , propH * 14 * 1.02f, anchoPantalla, altoPantalla );

        //fila2
        arbolesRect[10] = new RectF(propW  * 11 * 1.015f, propH  * 6 * 1.03f, propW * 14 , propH  * 7);
        arbolesRect[11] = new RectF(propW * 15 *1.015f, propH * 7 * 1.03f, propW * 20, propH * 8 );
        arbolesRect[12] = new RectF(propW * 21 *1.015f, propH  * 6 * 1.03f, propW * 24 , propH * 7);
        arbolesRect[13] = new RectF(propW * 26 *1.015f, propH  * 7 *1.03f, propW * 28, propH * 8);
        arbolesRect[14] = new RectF(propW * 28, propH * 6 * 1.015f, anchoPantalla, propH * 8 / 1.03f);

        //fila3
        arbolesRect[15] = new RectF(0, 0, propW * 6 , propH * 2 / 1.03f );
        arbolesRect[16] = new RectF(propW  * 6 * 1.015f, 0, propW * 9, propH / 1.03f);
        arbolesRect[17] = new RectF(propW  * 12 * 1.015f, propH, propW * 17 , propH * 2 / 1.03f);
        arbolesRect[18] = new RectF(propW * 23, 0, propW * 25 / 1.015f, propH / 1.07f);
        arbolesRect[19] = new RectF(propW * 25* 1.01f, 0, anchoPantalla, propH * 2);

        //rect de victoria /final
        arbolesRect[20] = new RectF(propW  * 18 , 0, propW * 4, propH * 2 / 1.03f);

        //No son necesarios
        arbolesRect[21] =  new RectF(0,0,0,0);
        arbolesRect[22] = new RectF(0,0,0,0);
        arbolesRect[23] = new RectF(0,0,0,0);
        arbolesRect[24] = new RectF(0,0,0,0);
    }

}
