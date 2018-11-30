package com.example.pedrohenrique.listacompras.models;

public class Listas {
    private String Id;
    private String nome;
    private String textQtd;
    private String textPreco;
    private String totalItem;
    private Boolean selecionado;

    public Listas() {
    }

    public Listas(String nome) {
        this.nome = nome;
        this.textQtd = 0+"";
        this.totalItem = 0+"";
        this.textPreco = 0+"";
        this.selecionado = false;


    }

    public Listas(String id, String nome, String textQtd, String textPreco, String totalItem, Boolean selecionado) {
        Id = id;
        this.nome = nome;
        this.textQtd = textQtd;
        this.textPreco = textPreco;
        this.totalItem = totalItem;
        this.selecionado = selecionado;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTextPreco() {
        return textPreco;
    }

    public void setTextPreco(String textPreco) {
        this.textPreco = textPreco;
    }

    public String getTextQtd() {
        return textQtd;
    }

    public void setTextQtd(String textQtd) {
        this.textQtd = textQtd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }

    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }
}
