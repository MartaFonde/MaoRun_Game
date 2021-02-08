package com.example.juego;

import android.graphics.Bitmap;
import android.graphics.RectF;

import java.util.ArrayList;

public class Escenario {

    Bitmap fondo;       //PENDIENTE

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

    public Escenario(Bitmap imagenMoneda, int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        arbolesRect = new RectF[25];
        monedasRect = new ArrayList<>();

        this.anchoMoneda = anchoPantalla/32;
        this.altoMoneda = altoPantalla/16;
        this.imgMoneda = imagenMoneda;
        moneda = new Bitmap[5];

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

    public void setRectArboles(int anchoPantalla, int altoPantalla){
        //fila1
        arbolesRect[0] = new RectF(0, (altoPantalla / 16) * 12, (anchoPantalla / 32) *4*1.005f, altoPantalla );
        arbolesRect[1] = new RectF(anchoPantalla / 32 * 4, (altoPantalla / 16) * 13, (anchoPantalla / 32) *7 *1.015f, altoPantalla);
        arbolesRect[2] = new RectF(anchoPantalla / 32 * 7, (altoPantalla / 16) * 14, (anchoPantalla / 32)*8*1.015f, altoPantalla);
        arbolesRect[3] = new RectF(anchoPantalla / 32 * 8, (altoPantalla / 16) * 15, (anchoPantalla / 32)*11*1.015f, altoPantalla);
        arbolesRect[4] = new RectF(anchoPantalla / 32 * 11 * 1.005f, (altoPantalla / 16) * 13, (anchoPantalla / 32) * 12 *1.01f, (altoPantalla / 16) * 14);
        arbolesRect[5] = new RectF(anchoPantalla / 32 * 14 * 1.005f, (altoPantalla / 16) * 15, (anchoPantalla / 32)*15*1.01f, altoPantalla);
        arbolesRect[6] = new RectF(anchoPantalla / 32 * 18 *1.005f, (altoPantalla / 16) * 15, (anchoPantalla / 32)*20*1.01f, altoPantalla);
        arbolesRect[7] = new RectF(anchoPantalla / 32 * 23*1.005f, (altoPantalla / 16) * 14, (anchoPantalla / 32)*25, altoPantalla);
        arbolesRect[8] = new RectF(anchoPantalla / 32 * 25*1.005f , (altoPantalla / 16) * 13, (anchoPantalla / 32)*29, altoPantalla);
        arbolesRect[9] = new RectF(anchoPantalla / 32 * 29*1.007f , (altoPantalla / 16) * 12, anchoPantalla, altoPantalla);
        //fila2
        arbolesRect[10] = new RectF(anchoPantalla / 32 * 1 * 1.01f, (altoPantalla / 16) * 9, (anchoPantalla / 32) *2*1.005f, altoPantalla / 16 * 10 / 1.04f );
        arbolesRect[11] = new RectF(anchoPantalla / 32 * 10, (altoPantalla / 16) * 9, (anchoPantalla / 32) *12 *1.015f, altoPantalla / 16 * 10 / 1.04f);
        arbolesRect[12] = new RectF(anchoPantalla / 32 * 29*1.015f, (altoPantalla / 16) * 9, (anchoPantalla / 32)*30, altoPantalla / 16 * 10/ 1.04f);
        //fila3
        arbolesRect[13] = new RectF(0, (altoPantalla / 16) * 6, (anchoPantalla / 32)*1*1.015f, altoPantalla / 16 * 7/ 1.03f);
        arbolesRect[14] = new RectF(anchoPantalla / 32 * 3*1.007f, (altoPantalla / 16) * 5, (anchoPantalla / 32) * 4 *1.015f, (altoPantalla / 16) * 6/ 1.03f);
        arbolesRect[15] = new RectF(anchoPantalla / 32 * 8*1.007f, (altoPantalla / 16) * 6, (anchoPantalla / 32)* 10 *1.015f, altoPantalla / 16 * 7/ 1.03f);
        arbolesRect[16] = new RectF(anchoPantalla / 32 * 18 *1.005f, (altoPantalla / 16) * 5, (anchoPantalla / 32)*19*1.005f, altoPantalla / 16 * 6/ 1.03f);
        arbolesRect[17] = new RectF(anchoPantalla / 32 * 21 *1.007f, (altoPantalla / 16) * 6, (anchoPantalla / 32)*22, altoPantalla / 16 * 7/ 1.03f);
        arbolesRect[18] = new RectF(anchoPantalla / 32 * 29 * 1.01f, (altoPantalla / 16) * 5, (anchoPantalla / 32)*30, altoPantalla / 16 * 6/ 1.03f);
        arbolesRect[19] = new RectF(anchoPantalla / 32 * 31 * 1.01f, (altoPantalla / 16) * 6, anchoPantalla, altoPantalla / 16 * 7/ 1.03f);
        //fila4
        arbolesRect[20] =  new RectF(0, 0, (anchoPantalla / 32)* 7 *1.015f, altoPantalla / 16 * 1/ 1.03f);
        arbolesRect[21] = new RectF(anchoPantalla / 32 * 11 * 1.01f, 0, (anchoPantalla / 32)* 12 *1.005f, altoPantalla / 16 * 1 / 1.06f);
        arbolesRect[22] = new RectF(anchoPantalla / 32 * 21 *1.007f, 0, (anchoPantalla / 32) * 22, altoPantalla / 16 * 1 / 1.06f);
        arbolesRect[23] = new RectF(anchoPantalla / 32 * 25 * 1.01f, 0, (anchoPantalla / 32) * 26, altoPantalla / 16 * 1 / 1.06f);
        arbolesRect[24] = new RectF(anchoPantalla / 32 * 29 * 1.01f, 0, anchoPantalla, altoPantalla / 16 * 1 / 1.03f);
    }

    public RectF[] getRectArboles(){
        return arbolesRect;
    }

    public void setRectMonedas(int anchoPantalla, int altoPantalla){
        float x;
        float y;
        RectF pos;

        while(monedasRect.size() <= totalMonedas) {
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
            if(posValida) monedasRect.add(pos);
        }
    }

    public ArrayList<RectF> getRectMonedas(){
        return monedasRect;
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
}
