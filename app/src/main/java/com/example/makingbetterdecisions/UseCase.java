package com.example.makingbetterdecisions;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UseCase implements Parcelable {
    private String title;
    private String description; // short description for card
    private String industry;
    private String scenario;
    private String details;
    private List<Question> questions;

    public UseCase(String title, String description, String industry, String scenario, String details, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.industry = industry;
        this.scenario = scenario;
        this.details = details;
        this.questions = questions;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getIndustry() { return industry; }
    public String getScenario() { return scenario; }
    public String getDetails() { return details; }
    public List<Question> getQuestions() { return questions; }

    // Parcelable implementation
    protected UseCase(Parcel in) {
        title = in.readString();
        description = in.readString();
        industry = in.readString();
        scenario = in.readString();
        details = in.readString();
        questions = in.createTypedArrayList(Question.CREATOR);
    }

    public static final Creator<UseCase> CREATOR = new Creator<UseCase>() {
        @Override
        public UseCase createFromParcel(Parcel in) { return new UseCase(in); }
        @Override
        public UseCase[] newArray(int size) { return new UseCase[size]; }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(industry);
        parcel.writeString(scenario);
        parcel.writeString(details);
        parcel.writeTypedList(questions);
    }
}

