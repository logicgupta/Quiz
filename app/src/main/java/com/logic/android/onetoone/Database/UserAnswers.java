package com.logic.android.onetoone.Database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;

import javax.annotation.Nullable;

@Entity(tableName = "UserResponse")
public class UserAnswers  {
    @PrimaryKey
    @Nullable
    String name;
    String id;
    String questions;
    String answer;
    Boolean result;
    @ColumnInfo(name = "time_taken")
    Date timetaken;

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Date getTimetaken() {
        return timetaken;
    }

    public void setTimetaken(Date timetaken) {
        this.timetaken = timetaken;
    }
}
