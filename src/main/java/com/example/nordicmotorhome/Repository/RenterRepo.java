package com.example.nordicmotorhome.Repository;

import com.example.nordicmotorhome.Model.Renter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RenterRepo {
    @Autowired
    JdbcTemplate template;

    public void addRenter(Renter r){
        String sql = "INSERT INTO renter(first_name,last_name,mobile_number,email,address_street,address_zip,address_city,DLN,driversince_date) values(?,?,?,?,?,?,?,?,?)";
        template.update(sql,r.getFirst_name(),r.getLast_name(),r.getMobile_number(),r.getEmail(),r.getAddress_street(),r.getAddress_zip(),r.getAddress_zip(),r.getDLN(),r.getDriversince_date());
    }
    public void updateRenter(Renter r){
        String sql = "UPDATE renter set first_name = ?, last_name = ?, mobile_number = ?, email = ?, address_street = ?, address_zip = ?, address_city = ?, DLN = ?, driversince_date = ? WHERE renter_ID = ?";
        template.update(sql,r.getFirst_name(),r.getLast_name(),r.getMobile_number(),r.getEmail(),r.getAddress_street(),r.getAddress_zip(),r.getAddress_city(),r.getDLN(),r.getDriversince_date(),r.getRenter_ID());
    }
    public void deleteRenter(int id){
        String sql ="DELETE FROM renter WHERE renter_ID = ?";
        template.update(sql, id);
    }
    public List<Renter> fetchAll(){
        String sql = "SELECT * FROM renter";
        RowMapper<Renter> list = new BeanPropertyRowMapper<>(Renter.class);
            return template.query(sql,list);
    }
    public Renter fetchById(int id){
        String sql = "SELECT * FROM renter WHERE renter_ID = ?";
        RowMapper<Renter> list = new BeanPropertyRowMapper<>(Renter.class);
            return template.queryForObject(sql,list,id);
    }
    public int renterCount(){
        String sql = "SELECT count(*) FROM renter";
        return template.queryForObject(sql, Integer.class);
    }
}
