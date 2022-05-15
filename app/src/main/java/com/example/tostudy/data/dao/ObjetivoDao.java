package com.example.tostudy.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tostudy.data.model.Objetivo;

import java.util.List;
@Dao
public interface ObjetivoDao {

    @Insert
    long insert(Objetivo objetivo);

    @Update
    void update(Objetivo objetivo);

    @Delete
    void delete(Objetivo objetivo);

    @Query("DELETE FROM objetivo")
    void deleteAll();

    @Query("SELECT * FROM objetivo ORDER BY nombre ASC")
    List<Objetivo> select();

    @Query("SELECT * FROM objetivo WHERE nombre=:nombre")
    Objetivo findByNombre(String nombre);
    
}
