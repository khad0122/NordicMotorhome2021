package com.example.nordicmotorhome;

public class Price {

    //      Static Price
    private double basePrice;
    private int extraPrice;
    //fees
    private double fuelFee;
    private double kmFee;
    private double collectFee;


    //Getters & Setters
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public double getFuelFee() { return fuelFee; }
    public void setFuelFee(double fuelFee) { this.fuelFee = fuelFee; }

    public double getKmFee() { return kmFee; }
    public void setKmFee(double kmFee) {this.kmFee = kmFee; }

    public double getCollectFee() { return collectFee; }
    public void setCollectFee(double collectFee) { this.collectFee = collectFee; }

    public int getExtraPrice(){return extraPrice;}
    public void setExtraPrice(int ex){extraPrice = ex;}
}
