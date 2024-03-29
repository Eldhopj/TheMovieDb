package com.example.themoviedb.models;

public class ApiResponse {

    private Object response;
    private String error;

    public ApiResponse(Object response) {
        this.response = response;
        this.error = null;
    }

    public ApiResponse(String error) {
        this.error = error;
        this.response = null;
    }

    public Object getResponse() {
        return response;
    }


    public String getError() {
        return error;
    }
}
