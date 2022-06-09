package com.example.tostudy.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class User {

    private int id;
    private String name;
    private String email;
    private String img;

    public User(int id , String name, String email, String img) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.img = img;
    }

    public User(String name, String email, String img) {
        this.name = name;
        this.email = email;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return name;
    }

    public void setUser(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}