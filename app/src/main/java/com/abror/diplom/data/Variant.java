package com.abror.diplom.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Variant implements Parcelable {

    private String title;
    private ArrayList<Test> questions;
    private int[] answers;

    public Variant(String title, ArrayList<Test> questions, int[] answers) {
        this.title = title;
        this.questions = questions;
        this.answers = answers;
    }

    protected Variant(Parcel in) {
        title = in.readString();
        answers = in.createIntArray();
    }

    public static final Creator<Variant> CREATOR = new Creator<Variant>() {
        @Override
        public Variant createFromParcel(Parcel in) {
            return new Variant(in);
        }

        @Override
        public Variant[] newArray(int size) {
            return new Variant[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public ArrayList<Test> getQuestions() {
        return questions;
    }

    public int[] getAnswers() {
        return answers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeIntArray(answers);
    }
}
