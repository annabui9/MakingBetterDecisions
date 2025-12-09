package com.example.makingbetterdecisions.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public static final String N_KEY = "EMAILV";
    private String email;
    private ArrayList<String> responses;
    private HashMap<String, HashMap<String, String>> answers;

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
    public HashMap<String, HashMap<String, String>> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, HashMap<String, String>> answers) {
        this.answers = answers;
    }
}
