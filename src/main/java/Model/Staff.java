package Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Staff {
    @Id
    private String first_name;
    private String last_name;
    private int mobile_number;
    private String email;

    //constructors
    public Staff() { }
    public Staff(String first_name, String last_name, int mobile_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile_number = mobile_number;
        this.email = email;
    }

    //Getters & Setters
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }

    public int getMobile_number() { return mobile_number; }
    public void setMobile_number(int mobile_number) { this.mobile_number = mobile_number; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

