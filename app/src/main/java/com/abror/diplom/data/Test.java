package com.abror.diplom.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Test implements Parcelable {

    private String title;
    private String[] options;

    public Test(String title, String[] options) {
        this.title = title;
        this.options = options;
    }

    protected Test(Parcel in) {
        title = in.readString();
        options = in.createStringArray();
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String[] getOptions() {
        return options;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeStringArray(options);
    }
}
