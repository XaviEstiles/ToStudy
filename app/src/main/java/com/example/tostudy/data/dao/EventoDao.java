package com.example.tostudy.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tostudy.data.model.Evento;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EventoDao {

    @Insert
    long insert(Evento evento);

    @Update
    void update(Evento evento);

    @Delete
    void delete(Evento evento);

    @Query("DELETE FROM evento")
    void deleteAll();

    @Query("SELECT * FROM evento ORDER BY nombre ASC")
    List<Evento> select();

    @Query("SELECT * FROM evento WHERE fecha=:fecha  ORDER BY nombre ASC")
    List<Evento> selectFromDate(String fecha);

    @Query("SELECT * FROM evento WHERE nombre=:nombre")
    Evento findByNombre(String nombre);
}
