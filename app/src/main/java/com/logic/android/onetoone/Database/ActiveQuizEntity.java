package com.logic.android.onetoone.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.logic.android.onetoone.models.Questions;

import java.util.List;
@Entity(tableName = "quiz1")
public class ActiveQuizEntity {

    @PrimaryKey
    @NonNull
    private String id;
    private  String name;
    private  String coins;
    private  String desc;
    private String duration;
    private  String imageUrl;
    private String date;
    private  String startTime;
    private  int enrollFee;
    private int maxUsers;
    private int noOfRegistered;
    private Boolean open;
    private int percentWinners;

    @ColumnInfo(name = "questionList")
    private List<Questions> questionsList;

    public ActiveQuizEntity(String id,String name, String coins, String desc,
                            String duration, String imageUrl, String startTime
                            ,String date,
                            int enrollFee, int maxUsers, int noOfRegistered,
                            Boolean open, int percentWinners, List<Questions> questionsList) {

        this.id=id;
        this.date=date;
        this.name = name;
        this.coins = coins;
        this.desc = desc;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.startTime = startTime;
        this.enrollFee = enrollFee;
        this.maxUsers = maxUsers;
        this.noOfRegistered = noOfRegistered;
        this.open = open;
        this.percentWinners = percentWinners;
        this.questionsList = questionsList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getEnrollFee() {
        return enrollFee;
    }

    public void setEnrollFee(int enrollFee) {
        this.enrollFee = enrollFee;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public int getNoOfRegistered() {
        return noOfRegistered;
    }

    public void setNoOfRegistered(int noOfRegistered) {
        this.noOfRegistered = noOfRegistered;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public int getPercentWinners() {
        return percentWinners;
    }

    public void setPercentWinners(int percentWinners) {
        this.percentWinners = percentWinners;
    }

    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
