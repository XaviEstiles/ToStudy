package com.example.tostudy.data.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Objetivo implements Comparable, Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer id;
    @NonNull
    String nombre;
    @NonNull
    String fecha;
    @NonNull
    String descripcion;
    @NonNull
    Prioridad prioridad;
    float progreso;


    public Objetivo(Integer id, String nombre, String fecha, String descripcion, Prioridad prioridad, float progreso) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.progreso = progreso;
    }

    public float getProgreso() {
        return progreso;
    }

    public void setProgreso(float progreso) {
        this.progreso = progreso;
    }

    public Objetivo() { }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (((Objetivo)obj).getId() == this.id)
            return true;
        else
            return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Object o) {
        int result = 0;// new OjetivoComparator().compare(this,o);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.parse(this.getFecha(),format);
        LocalDate date2 = LocalDate.parse(((Objetivo)o).getFecha(),format);

        if(date1.isBefore(date2)){
            result = -1;
        }else if(date1.isAfter(date2)){
            result = 1;
        }
        return result;
    }
}
