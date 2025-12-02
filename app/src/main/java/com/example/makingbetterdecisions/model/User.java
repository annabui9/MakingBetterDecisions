package com.example.makingbetterdecisions.model;

import java.util.ArrayList;

public class User {
    public static final String N_KEY = "EMAILV";
    private String email;
    private ArrayList<String> responses;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setResponses(ArrayList<String> responses) {
        this.responses = responses;
    }

    public ArrayList<String> getResponses() {
        return responses;
    }
}
