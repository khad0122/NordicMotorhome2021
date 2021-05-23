package com.example.nordicmotorhome.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


public class Invoice{

    private int invoice_ID;
    private int booking_ID;
    private int season_percent;
    private int extra;
    private int outsideLocationKm;
    private double fee;
    private double price;

    private int extra_km;
    private boolean fuelCheck;

    //constructors
    public Invoice() {fuelCheck=false; }
    public Invoice(int booking_ID, int season, int extra_km, double fee, double price) {
        this.invoice_ID = invoice_ID;
        this.booking_ID = booking_ID;
        this.season_percent = season;
        this.extra_km = extra_km;
        this.fee = fee;
        this.price = price;
        fuelCheck = false;
    }

    //Getters & Setters
    public int getInvoice_ID() { return invoice_ID; }
    public void setInvoice_ID(int invoice_ID) { this.invoice_ID = invoice_ID; }

    public int getBooking_ID() { return booking_ID; }
    public void setBooking_ID(int booking_ID) { this.booking_ID = booking_ID; }

    public int getSeason() { return season_percent; }
    public void setSeason(int season) { this.season_percent = season; }

    public int getExtra_km() { return extra_km; }
    public void setExtra_km(int extra_km) { this.extra_km = extra_km; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getSeason_percent() { return season_percent; }
    public void setSeason_percent(int season_percent) { this.season_percent = season_percent; }

    public int getOutsideLocationKm() { return outsideLocationKm; }
    public void setOutsideLocationKm(int remoteLocationKm) { this.outsideLocationKm = remoteLocationKm; }

    public int getExtra() { return extra; }
    public void setExtra(int extra) { this.extra = extra; }

    public boolean isFuelCheck() { return fuelCheck; }
    public void setFuelCheck(boolean fuelCheck) { this.fuelCheck = fuelCheck; }

    //methods Calculator
    public double updatePrice(){


        if(season_percent != 0){
            price =  price+(price *(((double)season_percent/100)));
            System.out.println("Percent * Price: "+((double)(season_percent/100))*price);
            System.out.println(price);
        }
        if(outsideLocationKm != 0) {
            fee = outsideLocationKm * 0.70;
            price += fee;
        }
        if(extra != 0) { price += extra; }

        //If fuel level below half
        if(fuelCheck){
            price += 70;
        }


        return price;
    }

}

