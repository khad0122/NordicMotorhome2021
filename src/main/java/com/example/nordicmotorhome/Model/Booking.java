package com.example.nordicmotorhome.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.thymeleaf.util.StringUtils.substring;

@Entity
public class Booking {
    @Id
    private int booking_ID;
    private int renter_ID;
    private int motorhome_ID;
    private int start_km;
    private String pickup_date;
    private String return_date;
    private String pickup_location;
    private String dropoff_location;
    private String extras;
    private int season_ID;
    private String first_name;
    private String last_name;
    private String model;
    private String brand;
    private String season_name;

    //constructors

    public Booking() { }

    public Booking( int renter_ID, int motorhome_ID, int start_km, String pickup_date, String return_date, String pickup_location, String dropoff_location, String extras,int season) {

        this.renter_ID = renter_ID;
        this.motorhome_ID = motorhome_ID;
        this.start_km = start_km;
        this.pickup_date = pickup_date;
        this.return_date = return_date;
        this.pickup_location = pickup_location;
        this.dropoff_location = dropoff_location;
        this.extras = extras;
        this.season_ID = season;
    }
    //Getters & Setters

    public int getBooking_ID() { return booking_ID; }
    public int getRenter_ID() { return renter_ID; }
    public int getMotorhome_ID() { return motorhome_ID; }
    public int getStart_km() { return start_km; }
    public String getPickup_date() { return pickup_date; }
    public String getReturn_date() { return return_date; }
    public String getPickup_location() { return pickup_location; }
    public String getDropoff_location() { return dropoff_location; }
    public String getExtras() { return extras; }
    public int getSeason(){return season_ID;}
    public String getName(){return first_name+" "+last_name;}


    public void setBooking_ID(int booking_ID) { this.booking_ID = booking_ID; }
    public void setRenter_ID(int renter_ID) { this.renter_ID = renter_ID; }
    public void setMotorhome_ID(int motorhome_ID) { this.motorhome_ID = motorhome_ID; }
    public void setStart_km(int start_km) { this.start_km = start_km; }
    public void setPickup_date(String pickup_date) { this.pickup_date = pickup_date; }
    public void setReturn_date(String return_date) { this.return_date = return_date; }
    public void setPickup_location(String pickup_location) { this.pickup_location = pickup_location; }
    public void setDropoff_location(String dropoff_location) { this.dropoff_location = dropoff_location; }
    public void setExtras(String extras) { this.extras = extras; }
    public void setSeason_ID(int num){ this.season_ID = num; }
    public void setFirst_name(String name){first_name=name;}
    public void setLast_name(String name){last_name=name;}
    public void setModel(String model){this.model = model;}
    public void setBrand(String brand){this.brand = brand;}
    public void setSeason_name(){


    }

    public String toString(){
        return "BookingID "+booking_ID+" - RenterID "+renter_ID+" - MotorhomeID "+motorhome_ID+" - Pickup-date "+pickup_date+" - return-date "+return_date+" - pickup-location "+pickup_location+" - dropoff-location "+dropoff_location+" - extra "+extras+" - Season "+season_ID;
    }
}
