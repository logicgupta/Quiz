package com.logic.android.onetoone.models;

import android.os.Parcel;
import android.os.Parcelable;
public class Questions implements Parcelable {

    public static final Creator<Questions> CREATOR=new Creator<Questions>(){

        @Override
        public Questions createFromParcel(Parcel source) {
            return new Questions(source);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String correctAnswer;
    Boolean answered=false;
    long answeringTime;

    public Questions() {
    }

    public Questions(String question, String option1, String option2, String option3,
                     String option4, String correctAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }

    public Questions(Parcel in){
        this.question=in.readString();
        this.option1=in.readString();
        this.option2=in.readString();
        this.option3=in.readString();
        this.option4=in.readString();
        this.correctAnswer=in.readString();
        this.answered=(in.readInt()==1);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public long getAnsweringTime() {
        return answeringTime;
    }

    public void setAnsweringTime(long answeringTime) {
        this.answeringTime = answeringTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeString(correctAnswer);
        dest.writeInt(answered ?1:0);
    }
}
