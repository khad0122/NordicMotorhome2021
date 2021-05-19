package Model;

import javax.persistence.Entity;
import javax.persistence.Id;

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

    //constructors

    public Booking() { }
    public Booking(int booking_ID, int renter_ID, int motorhome_ID, int start_km, String pickup_date, String dropoff_location, String extras) {
        this.booking_ID = booking_ID;
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
    public void setBooking_ID(int booking_ID) { this.booking_ID = booking_ID; }

    public int getRenter_ID() { return renter_ID; }
    public void setRenter_ID(int renter_ID) {this.renter_ID = renter_ID; }

    public int getMotorhome_ID() { return motorhome_ID; }
    public void setMotorhome_ID(int motorhome_ID) { this.motorhome_ID = motorhome_ID; }

    public int getStart_km() { return start_km; }
    public void setStart_km(int start_km) { this.start_km = start_km; }

    public String getPickup_date() { return pickup_date; }
    public void setPickup_date(String pickup_date) { this.pickup_date = pickup_date; }

    public String getReturn_date() { return return_date; }
    public void setReturn_date(String return_date) { this.return_date = return_date; }

    public String getPickup_location() { return pickup_location; }
    public void setPickup_location(String pickup_location) { this.pickup_location = pickup_location; }

    public String getDropoff_location() { return dropoff_location; }
    public void setDropoff_location(String dropoff_location) { this.dropoff_location = dropoff_location; }

    public String getExtras() { return extras; }
    public void setExtras(String extras) { this.extras = extras; }
}
