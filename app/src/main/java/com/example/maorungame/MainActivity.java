package com.example.maorungame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    JuegoSV juego;
    private boolean pausa=false;

    /**
     * Establece la pantalla completa en horizontal, oculta la barra de navegación y la ActionBar
     * y crea el View que será el juego. También establece la pantalla siempre encendida.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < 16) { // versiones anteriores a Jelly Bean
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else { // versiones iguales o superiores a Jelly Bean
            final int flags= View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            final View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            });
        }

        getSupportActionBar().hide(); // se oculta la barra de ActionBar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        juego = new JuegoSV(this);
        juego.setKeepScreenOn(true);
        setContentView(juego);
    }

    /**
     * Pausa la música cuando la activity está en segundo plano.
     */
    @Override
    protected void onPause() {
        super.onPause();
        pausa=true;
        if(juego.mediaPlayer != null)juego.mediaPlayer.pause();
    }

    /**
     * Reanuda la música cuando lo hace la activity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (pausa && juego.mediaPlayer != null)  juego.mediaPlayer.start();
    }
}