package Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MotorHome {

@Id
    private int motorhome_ID;
    private String model;
    private String brand;
    private String type;
    private String size;

    //constructors
    public MotorHome() { }
    public MotorHome(int motorhome_ID, String model, String brand, String type, String size) {
        this.motorhome_ID = motorhome_ID;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.size = size;
    }

    //Getters & Setters
    public int getMotorhome_ID() {return motorhome_ID; }
    public void setMotorhome_ID(int motorhome_ID) {this.motorhome_ID = motorhome_ID; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSize() {return size; }
    public void setSize(String size) { this.size = size; }
}
