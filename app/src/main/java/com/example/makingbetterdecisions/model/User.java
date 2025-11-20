package com.example.makingbetterdecisions.model;

public class User {
    public static final String N_KEY = "EMAILV";
    public static final String DOB_KEY = "DOBV";
    private String email;
    private String dob;

    public void setEmail(String email) {
        this.email = email;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }
}
