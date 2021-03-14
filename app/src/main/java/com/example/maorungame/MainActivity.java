package com.example.maorungame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /**
     * Gestiona el juego
     */
    JuegoSV juego;

    /**
     * Gestiona las acciones que dependen de si la aplicación se está ejecutando en segundo plano
     * o si se reanuda
     */
    private boolean pausa=false;

    /**
     * Accede a los servicios del sensor
     */
    SensorManager sensorManager;

    /**
     * Sensor de luminosidad
     */
    Sensor sensorLuz;

    /**
     * Nivel de luz ambiental
     */
    static float luz;

    /**
     * Establece la pantalla completa en horizontal, oculta la barra de navegación y la ActionBar
     * e instancia el sensor de luz. Crea el View que será el juego. Establece que en el
     * juego la pantalla siempre estará encendida.
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

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        juego = new JuegoSV(this);
        juego.setKeepScreenOn(true);
        setContentView(juego);
    }

    /**
     * Pausa la música cuando la activity está en segundo plano y desregistra el sensor.
     */
    @Override
    protected void onPause() {
        super.onPause();
        pausa=true;
        if (sensorLuz!=null) sensorManager.unregisterListener(this);
        if(juego.mediaPlayer != null)juego.mediaPlayer.pause();
    }

    /**
     * Reanuda la música cuando lo hace la activity y registra el sensor.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(sensorLuz!=null) {
            sensorManager.registerListener(this, sensorLuz,SensorManager.SENSOR_DELAY_GAME);
        }
        if (pausa && juego.mediaPlayer != null) {
            JuegoSV.resetMusic = false;
            juego.mediaPlayer.start();
        }
    }

    /**
     * Se ejecuta cuando el sensor de nivel de luminosidad varía, y se asigna a la variable
     * luz el nuevo nivel.
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                luz = event.values[0];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}