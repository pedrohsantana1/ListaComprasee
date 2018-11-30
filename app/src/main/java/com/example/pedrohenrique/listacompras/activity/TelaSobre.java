package com.example.pedrohenrique.listacompras.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pedrohenrique.listacompras.R;

public class TelaSobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_sobre);
        this.setTitle("Sobre");
    }
}
