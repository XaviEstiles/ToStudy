package com.example.tostudy.apiservice.dto;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.data.model.Prioridad;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Entity
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
