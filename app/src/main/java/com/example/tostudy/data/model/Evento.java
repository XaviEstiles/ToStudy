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
import java.time.format.DateTimeFormatter;

public class Evento implements Comparable, Serializable {

    Integer id;
    String startTime;
    String finishTime;
    Integer userId;
    String name;
    String date;
    String description;
    int priority;

    @Ignore
    public Evento(){}

    public Evento(Integer id, String startTime, String finishTime, Integer userId, String name, String date, String description, int priority) {
        this.id = id;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.description = description;
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(this.getDate(),format);
        LocalDate date2 = LocalDate.parse(((Evento)o).getDate(),format);

        if(date1.isBefore(date2)){
            result = -1;
        }else if(date1.isAfter(date2)){
            result = 1;
        }
        return result;
    }
}
