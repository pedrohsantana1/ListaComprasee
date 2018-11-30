package com.example.pedrohenrique.listacompras.models;

import com.example.pedrohenrique.listacompras.activity.TelaListas;

public interface OnItemClickListener {
    void somaValor(int position, String valor);
    void adicionaQuantidade(int position);
    void removeQuantidade(int position);
    void removeItem(int position, TelaListas telaListas);
    void updadteCheckBox(int position);

}
