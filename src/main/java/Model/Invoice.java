package Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Invoice{
    @Id
    private int invoice_ID;
    private int booking_ID;
    private String season;
    private int extra_km;
    private double fee;
    private double price;

    //constructors
    public Invoice() { }
    public Invoice(int invoice_ID, int booking_ID, String season, int extra_km, double fee, double price) {
        this.invoice_ID = invoice_ID;
        this.booking_ID = booking_ID;
        this.season = season;
        this.extra_km = extra_km;
        this.fee = fee;
        this.price = price;
    }

    //Getters & Setters
    public int getInvoice_ID() { return invoice_ID; }
    public void setInvoice_ID(int invoice_ID) { this.invoice_ID = invoice_ID; }

    public int getBooking_ID() { return booking_ID; }
    public void setBooking_ID(int booking_ID) { this.booking_ID = booking_ID; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }

    public int getExtra_km() { return extra_km; }
    public void setExtra_km(int extra_km) { this.extra_km = extra_km; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}

