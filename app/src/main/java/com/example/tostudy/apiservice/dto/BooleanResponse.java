package com.example.tostudy.apiservice.dto;

public class BooleanResponse {
    private String result;

    public BooleanResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
