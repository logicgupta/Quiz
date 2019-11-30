package com.logic.android.onetoone.models;

import java.util.ArrayList;
import java.util.List;

public class CountryCoin {

    String country;
    List<Coins> Coins =new ArrayList<>();

    public CountryCoin() {
    }

    public CountryCoin(String country, List<Coins> Coins) {
        this.country = country;
        this.Coins = Coins;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Coins> getCoins() {
        return Coins;
    }

    public void setCoins(List<Coins> coins) {
        this.Coins = coins;
    }
}
