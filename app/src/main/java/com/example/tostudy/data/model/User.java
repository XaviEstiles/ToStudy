package com.example.tostudy.data.model;

public class User {

    private String user;
    private String email;
    private String img;

    public User(String user, String email, String img) {
        this.user = user;
        this.email = email;
        this.img = img;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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