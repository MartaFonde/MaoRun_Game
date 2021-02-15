package com.example.juego;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final static int requestCode_Vibracion  = 1;
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


        if ( checkSelfPermission(Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            String[] permisos = new String[]{
                    Manifest.permission.VIBRATE,
            };
            ActivityCompat.requestPermissions(MainActivity.this, permisos, requestCode_Vibracion);
        }else{
            Toast.makeText(this, "Ya tengo los permisos de vibración", Toast.LENGTH_SHORT).show();
        }

        getSupportActionBar().hide(); // se oculta la barra de ActionBar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        JuegoSV juego = new JuegoSV(this);
        juego.setKeepScreenOn(true);
        setContentView(juego);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestCode_Vibracion) { // Procede de la solicitud de permisos de vibración
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // permiso concedido
                Toast.makeText(getApplicationContext(), "Permiso de vibración concedido", Toast.LENGTH_LONG).show();
            } else { // Se ha rechazado el permiso
                Toast.makeText(getApplicationContext(), "Permiso vibración denegado", Toast.LENGTH_LONG).show();
            }
        }
    }
}