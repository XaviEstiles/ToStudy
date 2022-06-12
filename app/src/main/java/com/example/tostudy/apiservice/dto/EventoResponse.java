package com.example.tostudy.apiservice.dto;

import com.example.tostudy.data.model.Evento;

import java.util.ArrayList;

public class EventoResponse {

    private ArrayList<Evento> result;

    public EventoResponse(ArrayList<Evento> result) {
        this.result = result;
    }

    public ArrayList<Evento> getResult() {
        return result;
    }

    public void setResult(ArrayList<Evento> result) {
        this.result = result;
    }
}
