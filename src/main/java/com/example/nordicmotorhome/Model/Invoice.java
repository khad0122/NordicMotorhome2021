package com.example.nordicmotorhome.Model;


import com.example.nordicmotorhome.Price;

import java.text.DecimalFormat;


public class Invoice{

    private int invoice_ID;

    //Referencing to a specific booking
    private int booking_ID;

    //the seasonal percent for the booking
    private int season_percent;

    //Extra eq. Amount * extraPrice.
    private int extra;
    //sum of Distance to pickup, and drop off, if not 0
    private double outsideKmFee;

    //Fee is combined of multiple fees, outsideKMFee, extraKmFee, FuelFee
    private double fee;

    //Final Price, starts as the basePrice, then accumulated with all fees, and extra charges.
    //if cancellation then, certain percentage of that accumulated price is final price
    private double price;


    //A Check after rent, extraKm and fuelCheck By mechanic
    //should be able to register it, then invoice is either updated or generated
    private int extra_km;
    private boolean fuelCheck;
    private double  fuelFee;


    //constructors
    public Invoice() {fuelCheck=false; }
    public Invoice(int booking_ID) {
        this.booking_ID = booking_ID;
    }

    //Getters & Setters


    public int getInvoice_ID() { return invoice_ID; }
    public int getBooking_ID() { return booking_ID; }
    public int getSeason_percent() { return season_percent; }
    public int getExtra() { return extra; }
    public double getOutsideKmFee() { return outsideKmFee; }
    public double getFee() { return fee; }
    public double getPrice() { return price; }
    public int getExtra_km() { return extra_km; }
    public double getFuelFee() { return fuelFee; }
    public boolean getFuelCheck(){return fuelCheck;}




    public void setInvoice_ID(int invoice_ID) { this.invoice_ID = invoice_ID; }
    public void setBooking_ID(int booking_ID) { this.booking_ID = booking_ID; }
    public void setSeason_percent(int season_percent) { this.season_percent = season_percent; }
    public void setExtra(int extra) { this.extra = extra; }
    public void setOutsideKmFee(double outsideKmFee) { this.outsideKmFee = outsideKmFee; }
    public void setFee(double fee) { this.fee = fee; }
    public void setPrice(double price) { this.price = price; }
    public void setExtra_km(int extra_km) { this.extra_km = extra_km; }
    public void setFuelFee(double fuelFee) { this.fuelFee = fuelFee; }


    public boolean isFuelCheck() { return fuelCheck; }
    public void setFuelCheck(boolean fuelCheck) { this.fuelCheck = fuelCheck; }



    //methods Calculator
    public void updateInvoice(int seasonP, Price price, Booking booking){
        DecimalFormat deci = new DecimalFormat("##.##");
        fee = 0;

        this.price = price.getBasePrice();

        season_percent = seasonP;

        //Setting the seasonal percentage
        if(season_percent != 0) {
            this.price = this.price + (this.price * ((double) season_percent / 100));
        }

        /*          Fee         */
        //multiplying number of days with price per day
        this.price = (this.price * booking.getDaysTotal());

        if(booking.getExtras() != 0) {
            extra = booking.getExtras() * price.getExtraPrice();
            fee = extra;
        }

        //OutsideKMFee
        if(booking.getTotalKm() != 0) {
            outsideKmFee = booking.getTotalKm() * price.getCollectFee();
            outsideKmFee = Double.parseDouble(deci.format(outsideKmFee).replace(",","."));
            fee += outsideKmFee;
        }

/*          Check-out       */
        //FuelCheck And Extra KM
                if(fuelCheck) { fuelFee = price.getFuelFee(); fee += fuelFee; }
                if(extra_km != 0){ fee += extra_km * price.getKmFee(); }
            this.price += fee;

        this.price = Double.parseDouble(deci.format(this.price).replace(",","."));
    }

    public double bookingCancel(){
        price = price - fee;
        fee = 0;
        outsideKmFee = 0;
        extra = 0;
        return price;
    }

}

