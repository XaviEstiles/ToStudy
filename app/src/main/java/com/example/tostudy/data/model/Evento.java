package com.example.tostudy.data.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Evento implements Comparable, Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer id;
    @NonNull
    String nombre;
    @NonNull
    String horaIni;
    @NonNull
    String horaFin;
    String fecha;
    String descripcion;
    Prioridad prioridad;

    @Ignore
    public Evento(){}
    @Ignore
    public Evento(Integer id, @NonNull String nombre, String descripcion, Prioridad prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }


    public Evento(Integer id, @NonNull String nombre, String horaIni, String horaFin, String fecha, String descripcion,@NonNull Prioridad prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.horaIni = horaIni;
        this.horaFin = horaFin;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(String horaIni) {
        this.horaIni = horaIni;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

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
        if (((Evento)obj).getId() == this.id)
            return true;
        else
            return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Object o) {
        int result = 0;//new EventComparator().compare(this,o);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.parse(this.getFecha(),format);
        LocalDate date2 = LocalDate.parse(((Evento)o).getFecha(),format);

        if(date1.isBefore(date2)){
            result = -1;
        }else if(date1.isAfter(date2)){
            result = 1;
        }
        return result;
    }
}
