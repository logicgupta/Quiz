package com.logic.android.onetoone.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Quiz1Details implements Parcelable {

    String docId;
    String categoryName;
    String id;
    String name;
    String imageUrl;
    String date;
    String duration;
    String startTime;
    Boolean isOpen;
    String coins;
    String desc;
    int noOfRegistered;
    int percentWinners;
    int maxUsers;
    Boolean result;
    int enrollFee;
    int correctAnswer;
    int winAmount;
    Top10Prize prizeList;
    List<RandomFacts> randomFacts=new ArrayList<>();
    List<Questions> questionsList=new ArrayList<>();

    public Quiz1Details(){
    }


    public Quiz1Details(String id, String name, String imageUrl, String startTime,String duration,
                        String date,
                        String desc, int percentWinners, int maxUsers,
                        int enrollFee, Top10Prize prizeList, List<Questions> questionsList) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.startTime = startTime;
        this.duration=duration;
        this.desc = desc;
        this.date=date;
        this.percentWinners = percentWinners;
        this.maxUsers = maxUsers;
        this.enrollFee = enrollFee;
        this.prizeList = prizeList;
        this.questionsList = questionsList;
    }
    public Quiz1Details(String id, String name, String imageUrl, String startTime,String duration,
                        String desc, int percentWinners, int maxUsers,
                        int enrollFee, Top10Prize prizeList,List<RandomFacts> randomFacts, List<Questions> questionsList) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.startTime = startTime;
        this.duration=duration;
        this.desc = desc;
        this.percentWinners = percentWinners;
        this.maxUsers = maxUsers;
        this.enrollFee = enrollFee;
        this.prizeList = prizeList;
        this.randomFacts=randomFacts;
        this.questionsList = questionsList;
    }


    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RandomFacts> getRandomFacts() {
        return randomFacts;
    }

    public void setRandomFacts(List<RandomFacts> randomFacts) {
        this.randomFacts = randomFacts;
    }

    public static Creator<Quiz1Details> getCREATOR() {
        return CREATOR;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public int getNoOfRegistered() {
        return noOfRegistered;
    }

    public void setNoOfRegistered(int noOfRegistered) {
        this.noOfRegistered = noOfRegistered;
    }

    public int getPercentWinners() {
        return percentWinners;
    }

    public void setPercentWinners(int percentWinners) {
        this.percentWinners = percentWinners;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public int getEnrollFee() {
        return enrollFee;
    }

    public void setEnrollFee(int enrollFee) {
        this.enrollFee = enrollFee;
    }


    public Top10Prize getPrizeList() {
        return prizeList;
    }

    public void setPrizeList(Top10Prize prizeList) {
        this.prizeList = prizeList;
    }

    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(int winAmount) {
        this.winAmount = winAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.coins);
        dest.writeString(this.duration);
        dest.writeString(this.categoryName);
        dest.writeString(this.docId);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeString(this.startTime);
        dest.writeValue(this.isOpen);
        dest.writeString(this.desc);
        dest.writeInt(this.noOfRegistered);
        dest.writeInt(this.percentWinners);
        dest.writeInt(this.maxUsers);
        dest.writeInt(this.enrollFee);
        dest.writeParcelable(this.prizeList, flags);
        dest.writeValue(questionsList);
    }

    protected Quiz1Details(Parcel in) {
        this.id = in.readString();
        this.categoryName = in.readString();
        this.coins = in.readString();
        this.duration = in.readString();
        this.docId=in.readString();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.startTime = in.readString();
        this.isOpen = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.desc = in.readString();
        this.noOfRegistered = in.readInt();
        this.percentWinners = in.readInt();
        this.maxUsers = in.readInt();
        this.enrollFee = in.readInt();
        this.prizeList = in.readParcelable(Top10Prize.class.getClassLoader());
        this.questionsList = in.createTypedArrayList(Questions.CREATOR);
    }

    public static final Creator<Quiz1Details> CREATOR = new Creator<Quiz1Details>() {
        @Override
        public Quiz1Details createFromParcel(Parcel source) {
            return new Quiz1Details(source);
        }

        @Override
        public Quiz1Details[] newArray(int size) {
            return new Quiz1Details[size];
        }
    };
}
