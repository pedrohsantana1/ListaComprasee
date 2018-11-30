package com.example.pedrohenrique.listacompras.dao;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.pedrohenrique.listacompras.models.Listas;
import java.util.List;

public class BancoDados extends AppCompatActivity {

    private SQLiteDatabase banco;
    public BancoDados(SQLiteDatabase banco) {
        this.banco = banco;
    }

    //Executa qualquer comando no banco
    public void ExecutarComando(String sql)
    {
        try {
            if (banco != null) {
                banco.execSQL(sql);
            }
        }catch (Exception e){
                e.printStackTrace();
        }

    }

    //Pega dados do banco e coloca na lista
    public void PreencherLista(List<Listas> lista)
    {
        try {
            if(banco != null) {
                String consulta = "SELECT id, nome, quantidade, preco, total, selecao FROM Listas";
                Cursor cursor = banco.rawQuery(consulta, null);

                //Indice Tabelas
                int indiceNome = cursor.getColumnIndex("nome");
                int indiceQuantidade = cursor.getColumnIndex("quantidade");
                int indicePreco = cursor.getColumnIndex("preco");
                int indiceId = cursor.getColumnIndex("id");
                int indiceTotal = cursor.getColumnIndex("total");
                int indiceSelecao = cursor.getColumnIndex("selecao");

                cursor.moveToFirst();
                while (cursor != null) {
                    String nome = cursor.getString(indiceNome);
                    String quantidade = cursor.getString(indiceQuantidade);
                    String preco = cursor.getString(indicePreco);
                    String id = cursor.getString(indiceId);
                    String total = cursor.getString(indiceTotal);
                    String selecao = cursor.getString(indiceSelecao);

                    Boolean valor = false;
                    if(selecao.equals("1")){
                        valor = true;
                    }
                    lista.add(new Listas(id, nome, quantidade, preco,total,valor));
                    Log.e("Resultado --> ID:",id+" Nome: "+nome + " Quant. "+quantidade+" Preço "+ preco);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
