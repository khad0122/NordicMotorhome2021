package Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Renter {
    @Id
    private int renter_ID;
    private String first_name;
    private String last_name;
    private int mobile_number;
    private String email;
    private String address_street;
    private int address_zip;
    private String address_city;
    private int DLN;
    private String driversince_date;

    //constructors
    public Renter(){}
    public Renter(int renter_ID, String first_name, String last_name, int mobile_number, String email, String address_street, int address_zip, String address_city, int DLN, String driversince_date) {
        this.renter_ID = renter_ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile_number = mobile_number;
        this.email = email;
        this.address_street = address_street;
        this.address_zip = address_zip;
        this.address_city = address_city;
        this.DLN = DLN;
        this.driversince_date = driversince_date;
    }

    //Getters & Setters
    public int getRenter_ID() { return renter_ID; }
    public void setRenter_ID(int renter_ID) { this.renter_ID = renter_ID; }

    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }

    public int getMobile_number() { return mobile_number; }
    public void setMobile_number(int mobile_number) { this.mobile_number = mobile_number; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress_street() { return address_street; }
    public void setAddress_street(String address_street) { this.address_street = address_street; }

    public int getAddress_zip() { return address_zip; }
    public void setAddress_zip(int address_zip) { this.address_zip = address_zip; }

    public String getAddress_city() { return address_city; }
    public void setAddress_city(String address_city) { this.address_city = address_city; }

    public int getDLN() { return DLN; }
    public void setDLN(int DLN) { this.DLN = DLN; }

    public String getDriversince_date() { return driversince_date; }
    public void setDriversince_date(String driversince_date) { this.driversince_date = driversince_date; }
}
