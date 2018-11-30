package com.example.pedrohenrique.listacompras.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pedrohenrique.listacompras.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarListas();
            }
        },2000);



    }

    private void mostrarListas() {
        Intent intent = new Intent(SplashScreen.this,
                TelaListas.class);
        startActivity(intent);
        finish();
    }
}
