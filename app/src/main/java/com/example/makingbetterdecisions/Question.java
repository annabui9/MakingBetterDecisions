package com.example.makingbetterdecisions;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Question implements Parcelable {

    private String text;
    private List<String> options; // null if free response
    private boolean isMultipleChoice;

    public Question() {
        // REQUIRED empty constructor for Firebase
    }


    public Question(String text, List<String> options, boolean isMultipleChoice) {
        this.text = text;
        this.options = options;
        this.isMultipleChoice = isMultipleChoice;
    }

    protected Question(Parcel in) {
//        text = in.readString();
//        options = in.createStringArrayList();
//        isMultipleChoice = in.readByte() != 0;
        text = in.readString();

        // Read whether options exists
        boolean hasOptions = in.readByte() != 0;
        if (hasOptions) {
            options = in.createStringArrayList();
        } else {
            options = null;
        }

        isMultipleChoice = in.readByte() != 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(text);
//        dest.writeStringList(options);
//        dest.writeByte((byte) (isMultipleChoice ? 1 : 0));
        dest.writeString(text);

        // Write whether options exists
        if (options == null) {
            dest.writeByte((byte) 0);  // 0 = no options
        } else {
            dest.writeByte((byte) 1);  // 1 = has options
            dest.writeStringList(options);
        }

        dest.writeByte((byte) (isMultipleChoice ? 1 : 0));
    }

    public String getText() { return text; }
    public List<String> getOptions() { return options; }
    public boolean isMultipleChoice() { return isMultipleChoice; }
}
