package com.logic.android.onetoone.models;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {

    String categoryName;
    String name;
    String imageUrl;
    String username;
    String duration;
    Boolean isOpen=false;
    long amountWin=-1;
    String coins;
    String desc;
    String date;
    int enrollFee;
    List<Questions> questionsList=new ArrayList<>();
    int totalCorrectAnswers;
    long totalTime;
    Boolean showResult=false;

    public UserResponse() {
    }

    public UserResponse(String name, String imageUrl, String duration, String coins, String desc,
                        int enrollFee, List<Questions> questionsList,
                        int totalCorrectAnswers, long totalTime, Boolean showResult) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.duration = duration;
        this.coins = coins;
        this.desc = desc;
        this.enrollFee = enrollFee;
        this.questionsList = questionsList;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.totalTime = totalTime;
        this.showResult = showResult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
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

    public int getEnrollFee() {
        return enrollFee;
    }

    public void setEnrollFee(int enrollFee) {
        this.enrollFee = enrollFee;
    }

    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    public int getTotalCorrectAnswers() {
        return totalCorrectAnswers;
    }

    public void setTotalCorrectAnswers(int totalCorrectAnswers) {
        this.totalCorrectAnswers = totalCorrectAnswers;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public Boolean getShowResult() {
        return showResult;
    }

    public void setShowResult(Boolean showResult) {
        this.showResult = showResult;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public long getAmountWin() {
        return amountWin;
    }

    public void setAmountWin(long amountWin) {
        this.amountWin = amountWin;
    }
}
