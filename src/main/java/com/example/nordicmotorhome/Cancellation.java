package com.example.nordicmotorhome;

import com.example.nordicmotorhome.Repository.PriceRepo;

public class Cancellation {
    private int cancellation_percent;
    private int toDay;
    private int fromDay;
    private int minPrice;
    private int cancellation_ID;

    //Repo
    private PriceRepo priceRepo;

    public int getCancellation_percent() { return cancellation_percent; }
    public void setCancellation_percent(int cancellation_percent) { this.cancellation_percent = cancellation_percent; }

    public int getToDay() { return toDay; }
    public void setToDay(int toDay) { this.toDay = toDay; }

    public int getFromDay() { return fromDay; }
    public void setFromDay(int fromDay) { this.fromDay = fromDay; }

    public int getMinPrice() { return minPrice; }
    public void setMinPrice(int minPrice) { this.minPrice = minPrice; }

    public int getCancellation_ID(){return cancellation_ID;}
    public void setCancellation_ID(int id){cancellation_ID = id;}
}
