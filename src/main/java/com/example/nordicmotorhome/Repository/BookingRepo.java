package com.example.nordicmotorhome.Repository;

import com.example.nordicmotorhome.Model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepo {
    @Autowired
    JdbcTemplate template;

    public void addContract(Booking booking){
        String sql = "INSERT INTO `contract` (`motorhomeID`,`renterID`,`startKM`,`pickup_date`,`return_date`,`pickup_location`,`dropoff_location`,`extras`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, booking.getMotorhome_ID(),booking.getRenter_ID(),booking.getStart_km(),booking.getPickup_date(),booking.getReturn_date(),booking.getPickup_location(), booking.getDropoff_location(),booking.getExtras());
    }
    public void updateContract(Booking c){
        String sql = "UPDATE contract SET pickupDate = ?, returnDate = ?, startKM = ? WHERE contractID = ?";
        template.update(sql, c.getPickup_date(),c.getReturn_date(),c.getStart_km());
    }
    public void deleteContract(int id){
        String sql = "DELETE FROM contract WHERE contractID = ?";
        template.update(sql,id);
    }
    public List<Booking> fetchAll(){
        String sql = "SELECT  renter.first_name, renter.last_name, model,brand, booking.* from booking\n" +
                "left join renter on booking.renter_ID = renter.renter_ID\n" +
                "left join motorhome on booking.motorhome_ID = motorhome.motorhome_ID;\n" +
                ";";//May be set as joined select booking - invoice
        RowMapper<Booking> list = new BeanPropertyRowMapper<>(Booking.class);

        return template.query(sql,list);
    }


    public Booking fetchById(int id){
        String sql = "SELECT * FROM Booking WHERE booking_ID = ?";
        RowMapper<Booking> list = new BeanPropertyRowMapper<>(Booking.class);

        return template.queryForObject(sql,list,id);
    }
}