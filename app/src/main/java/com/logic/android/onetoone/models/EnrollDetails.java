package com.logic.android.onetoone.models;

public class EnrollDetails {

    String authId;
    String quizName;
    String category;
    String coins;
    String docId;
    String date;


    public EnrollDetails(String authId, String quizName,
                         String category, String coins, String docId, String date) {
        this.authId = authId;
        this.quizName = quizName;
        this.category = category;
        this.coins = coins;
        this.docId = docId;
        this.date = date;
    }


    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
