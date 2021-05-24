package com.example.nordicmotorhome;

import com.example.nordicmotorhome.Repository.AdminRepo;

public class Admin {

    //      Static Price
    private double basePrice;
    private int extraPrice;

    //********************************************** May Be Deleted
    //  Seasonal Price
    private int season_percent;
    private String season_name;
    private String season_from;
    private String season_to;

    //  Cancellation Price
    private int cancellation_percent;
    private int toDay;
    private int fromDay;
    private int minimumPrice;
    //***********************************************

    //fees
    private double fuelFee;
    private double kmFee;
    private double collectFee;

    //Repo
    private AdminRepo adminRepo;

    public Admin(){
        basePrice = 400;
        extraPrice = 50;
        fuelFee = 70;
        kmFee = 1;
        collectFee = 0.70;
    }
    public Admin(int basePrice, int extraPrice){
        this.basePrice = basePrice;
        this.extraPrice = extraPrice;
    }


    //Getters & Setters
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public int getSeason_percent() { return season_percent; }
    public void setSeason_percent(int season_percent) { this.season_percent = season_percent; }

    public int getCancellation_percent() { return cancellation_percent; }
    public void setCancellation_percent(int cancellation_percent) { this.cancellation_percent = cancellation_percent; }

    public int getToDay() { return toDay; }
    public void setToDay(int toDay) { this.toDay = toDay; }

    public int getFromDay() { return fromDay; }
    public void setFromDay(int fromDay) { this.fromDay = fromDay; }

    public int getExtraPrice() { return extraPrice; }
    public void setExtraPrice(int extras) { this.extraPrice = extras; }

    public String getSeason_name() { return season_name; }
    public void setSeason_name(String season_name) { this.season_name = season_name; }

    public String getSeason_from() { return season_from; }
    public void setSeason_from(String season_from) { this.season_from = season_from; }

    public String getSeason_to() { return season_to; }
    public void setSeason_to(String season_to) { this.season_to = season_to; }

    public int getMinimumPrice() { return minimumPrice; }
    public void setMinimumPrice(int minimumPrice) { this.minimumPrice = minimumPrice; }

    public double getFuelFee() { return fuelFee; }
    public void setFuelFee(double fuelFee) { this.fuelFee = fuelFee; }

    public double getKmFee() { return kmFee; }
    public void setKmFee(double kmFee) {this.kmFee = kmFee; }

    public double getCollectFee() { return collectFee; }
    public void setCollectFee(double collectFee) { this.collectFee = collectFee; }
}
