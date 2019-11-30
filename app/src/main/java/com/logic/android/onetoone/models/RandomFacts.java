package com.logic.android.onetoone.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RandomFacts implements Parcelable {

    String fact;

    public RandomFacts() {
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fact);
    }

    protected RandomFacts(Parcel in) {
        this.fact = in.readString();
    }

    public static final Creator<RandomFacts> CREATOR = new Creator<RandomFacts>() {
        @Override
        public RandomFacts createFromParcel(Parcel source) {
            return new RandomFacts(source);
        }

        @Override
        public RandomFacts[] newArray(int size) {
            return new RandomFacts[size];
        }
    };
}
