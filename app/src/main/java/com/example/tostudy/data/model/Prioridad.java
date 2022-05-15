package com.example.tostudy.data.model;

public enum Prioridad{
    BAJA(0), MEDIA(1), ALTA(2);
    private int indice;
    Prioridad(int indice){ this.indice = indice;}
    public int getIndice(){return indice;}
}
