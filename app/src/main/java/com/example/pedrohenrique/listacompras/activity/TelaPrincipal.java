package com.example.pedrohenrique.listacompras.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pedrohenrique.listacompras.R;

public class TelaPrincipal extends AppCompatActivity  implements DialogInterface.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
    }



    public void menuListas(View view)
    {
     Intent intencao = new Intent(this, TelaListas.class);
     startActivity(intencao);
    }

    public void menuSupermercado(View view)
    {
        Intent intencao = new Intent(this, TelaSupermercado.class);
        startActivity(intencao);
    }

    public void menuSobre(View view)
    {
        Intent intencao = new Intent(this, TelaSobre.class);
        startActivity(intencao);
    }

    public void sairAplicacao(View view){

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Aviso");
            alert.setIcon(R.mipmap.icone);
            alert.setMessage("Tem certeza que deseja sair?");
            alert.setPositiveButton("Sim", this);
            alert.setNegativeButton("Não", this);
            alert.show();


    }

    public void onClick(DialogInterface dialog, int which)
    {
        if(which == DialogInterface.BUTTON_POSITIVE)
        {
            finish();
        }
    }
}
