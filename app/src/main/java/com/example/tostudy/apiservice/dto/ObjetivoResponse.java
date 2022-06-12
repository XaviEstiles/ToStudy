package com.example.tostudy.apiservice.dto;

import com.example.tostudy.data.model.Objetivo;
import java.util.ArrayList;

public class ObjetivoResponse {

    private ArrayList<Objetivo> result;

    public ObjetivoResponse(ArrayList<Objetivo> result) {
        this.result = result;
    }

    public ArrayList<Objetivo> getResult() {
        return result;
    }

    public void setResult(ArrayList<Objetivo> result) {
        this.result = result;
    }
}
