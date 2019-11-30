package com.logic.android.onetoone.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Top10Prize implements Parcelable {

    float first;
    float second;
    float third;
    float forth;
    float fifth;
    float sixth;
    float seventh;
    float eight;
    float nine;
    float tenth;
    float extra1;
    float extra2;
    float extra3;
    float extra4;
    float extra5;


    public Top10Prize() {
    }

    public Top10Prize(float first, float second, float third, float forth, float fifth,
                      float sixth, float seventh, float eight, float nine, float tenth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
        this.eight = eight;
        this.nine = nine;
        this.tenth = tenth;
    }

    public float getFirst() {
        return first;
    }

    public void setFirst(float first) {
        this.first = first;
    }

    public float getSecond() {
        return second;
    }

    public void setSecond(float second) {
        this.second = second;
    }

    public float getThird() {
        return third;
    }

    public void setThird(float third) {
        this.third = third;
    }

    public float getForth() {
        return forth;
    }

    public void setForth(float forth) {
        this.forth = forth;
    }

    public float getFifth() {
        return fifth;
    }

    public void setFifth(float fifth) {
        this.fifth = fifth;
    }

    public float getSixth() {
        return sixth;
    }

    public void setSixth(float sixth) {
        this.sixth = sixth;
    }

    public float getSeventh() {
        return seventh;
    }

    public void setSeventh(float seventh) {
        this.seventh = seventh;
    }

    public float getEight() {
        return eight;
    }

    public void setEight(float eight) {
        this.eight = eight;
    }

    public float getNine() {
        return nine;
    }

    public void setNine(float nine) {
        this.nine = nine;
    }

    public float getTenth() {
        return tenth;
    }

    public void setTenth(float tenth) {
        this.tenth = tenth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.first);
        dest.writeFloat(this.second);
        dest.writeFloat(this.third);
        dest.writeFloat(this.forth);
        dest.writeFloat(this.fifth);
        dest.writeFloat(this.sixth);
        dest.writeFloat(this.seventh);
        dest.writeFloat(this.eight);
        dest.writeFloat(this.nine);
        dest.writeFloat(this.tenth);
        dest.writeFloat(this.extra1);
        dest.writeFloat(this.extra2);
        dest.writeFloat(this.extra3);
        dest.writeFloat(this.extra4);
        dest.writeFloat(this.extra5);
    }

    protected Top10Prize(Parcel in) {
        this.first = in.readFloat();
        this.second = in.readFloat();
        this.third = in.readFloat();
        this.forth = in.readFloat();
        this.fifth = in.readFloat();
        this.sixth = in.readFloat();
        this.seventh = in.readFloat();
        this.eight = in.readFloat();
        this.nine = in.readFloat();
        this.tenth = in.readFloat();
        this.extra1 = in.readFloat();
        this.extra2 = in.readFloat();
        this.extra3 = in.readFloat();
        this.extra4 = in.readFloat();
        this.extra5 = in.readFloat();
    }

    public static final Creator<Top10Prize> CREATOR = new Creator<Top10Prize>() {
        @Override
        public Top10Prize createFromParcel(Parcel source) {
            return new Top10Prize(source);
        }

        @Override
        public Top10Prize[] newArray(int size) {
            return new Top10Prize[size];
        }
    };
}
