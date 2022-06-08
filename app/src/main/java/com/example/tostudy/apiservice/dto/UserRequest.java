package com.example.tostudy.apiservice.dto;

public class UserRequest {
    private String user;
    private String email;
    private String img;

    public UserRequest(String user, String email, String img) {
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
