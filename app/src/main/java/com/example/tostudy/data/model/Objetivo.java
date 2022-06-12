package com.example.tostudy.data.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.tostudy.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Objetivo implements Comparable, Serializable {

    Integer id;
    Integer userId;
    String name;
    String date;
    String description;
    int priority;
    float progress;

    public Objetivo(Integer id, Integer userId, String name, String date, String description, int priority, float progress) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.description = description;
        this.priority =priority;
        this.progress = progress;
    }

    public Objetivo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
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
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(this.getDate(),format);
        LocalDate date2 = LocalDate.parse(((Objetivo)o).getDate(),format);

        if(date1.isBefore(date2)){
            result = -1;
        }else if(date1.isAfter(date2)){
            result = 1;
        }
        return result;
    }
}
