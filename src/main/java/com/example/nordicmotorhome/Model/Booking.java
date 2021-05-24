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

    //PickupLocation And Distance to the Location: 0 if Nordic Rental Office
    private String pickup_location;
    private int kmToPickup;
    private String dropoff_location;
    private int kmToDropoff;

    private int extras; //Amount of Extras ie. 1 item, 2 items, ...


    //constructors

    public Booking() {

    }

    public Booking( int renter_ID, int motorhome_ID, int start_km, String pickup_date, String return_date, String pickup_location, String dropoff_location, int extras) {

        this.renter_ID = renter_ID;
        this.motorhome_ID = motorhome_ID;
        this.start_km = start_km;
        this.pickup_date = pickup_date;
        this.return_date = return_date;
        this.pickup_location = pickup_location;
        this.dropoff_location = dropoff_location;
        this.extras = extras;
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
    public int getExtras() { return extras; }
    public int getKmToPickup() { return kmToPickup; }
    public int getKmToDropoff() { return kmToDropoff; }
    public int getTotalKm(){return kmToPickup+kmToDropoff;}

    public void setBooking_ID(int booking_ID) { this.booking_ID = booking_ID; }
    public void setRenter_ID(int renter_ID) { this.renter_ID = renter_ID; }
    public void setMotorhome_ID(int motorhome_ID) { this.motorhome_ID = motorhome_ID; }
    public void setStart_km(int start_km) { this.start_km = start_km; }
    public void setPickup_date(String pickup_date) { this.pickup_date = pickup_date; }
    public void setReturn_date(String return_date) { this.return_date = return_date; }
    public void setPickup_location(String pickup_location) { this.pickup_location = pickup_location; }
    public void setDropoff_location(String dropoff_location) { this.dropoff_location = dropoff_location; }
    public void setExtras(int extras) { this.extras = extras; }
    public void setKmToPickup(int kmToPickup) { this.kmToPickup = kmToPickup; }
    public void setKmToDropoff(int kmToDropoff) { this.kmToDropoff = kmToDropoff; }



    public String toString(){
        return "BookingID "+booking_ID+" - RenterID "+renter_ID+" - MotorhomeID "+motorhome_ID+" - startKm "+start_km+" - Pickup-date "+pickup_date+" - return-date "+return_date+" - pickup-location "+pickup_location+" - KmTOPickup "+kmToPickup+" - dropoff-location "+dropoff_location+"kmToDropoff "+kmToDropoff+" - extra "+extras;
    }
}
