package com.example.tostudy.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tostudy.data.dao.EventoDao;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.data.model.Objetivo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Evento.class, Objetivo.class}, version = 1)
public abstract class Database extends RoomDatabase {

    //Crear los metodos de obtencion de los dao
    public abstract EventoDao eventoDao();

    private static volatile Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "rotineCalendar")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

  
    public static void create(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "rotineCalendar")
                            .build();
                }
            }
        }
    }

    public static Database getDatabase() {
        return INSTANCE;
    }
}
