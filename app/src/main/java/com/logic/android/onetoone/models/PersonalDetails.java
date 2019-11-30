package com.logic.android.onetoone.models;
public class PersonalDetails {

    String name,mobileNumber,referalCode,emailId, imageUrl;
    String createdOn;
    Boolean kycFlag=false,paymentFlag=false;
    long walletBalanace=0;
    String aadharNumber,panNumber,bankAcctNumber;
    String branch,ifsc,upiId;
    long coinBalance=0;
    String extra1="Firebase";
    String extra2;
    public PersonalDetails() {

    }


    public PersonalDetails(String name, String mobileNumber, String emailId) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;

    }

    public PersonalDetails(String name, String imageUrl, String emailId,String extra1) {
        this.name = name;
        this.extra1=extra1;
        this.imageUrl = imageUrl;
        this.emailId = emailId;

    }

    public PersonalDetails(String name, String mobileNumber, String createdOn,
                           String aadharNumber, String panNumber,
                           String bankAcctNumber, String branch, String ifsc, String upiId) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        //this.referalCode = referalCode;
        this.createdOn = createdOn;
        this.aadharNumber = aadharNumber;
        this.panNumber = panNumber;
        this.bankAcctNumber = bankAcctNumber;
        this.branch = branch;
        this.ifsc = ifsc;
        this.upiId = upiId;
    }

    public long getCoinBalance() {
        return coinBalance;
    }

    public void setCoinBalance(long coinBalance) {
        this.coinBalance = coinBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getReferalCode() {
        return referalCode;
    }

    public void setReferalCode(String referalCode) {
        this.referalCode = referalCode;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getKycFlag() {
        return kycFlag;
    }

    public void setKycFlag(Boolean kycFlag) {
        this.kycFlag = kycFlag;
    }

    public Boolean getPaymentFlag() {
        return paymentFlag;
    }

    public void setPaymentFlag(Boolean paymentFlag) {
        this.paymentFlag = paymentFlag;
    }

    public long getWalletBalanace() {
        return walletBalanace;
    }

    public void setWalletBalanace(long walletBalanace) {
        this.walletBalanace = walletBalanace;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getBankAcctNumber() {
        return bankAcctNumber;
    }

    public void setBankAcctNumber(String bankAcctNumber) {
        this.bankAcctNumber = bankAcctNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getUpiId() {
        return upiId;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }



}

