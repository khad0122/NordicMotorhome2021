package com.example.nordicmotorhome.Repository;

import com.example.nordicmotorhome.Admin;
import com.example.nordicmotorhome.Model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class AdminRepo {
    @Autowired
    JdbcTemplate template;
    /*  Staff   */
    public List<Staff> fetchAll(){
        String sql = "SELECT * FROM staff";
        RowMapper<Staff> list = new BeanPropertyRowMapper<>(Staff.class);
        return template.query(sql,list);
    }
    public Staff fetchByName(String fName, String lName){
        String sql = "SELECT * FROM staff WHERE first_name = ? AND last_name = ?";
        RowMapper<Staff> list = new BeanPropertyRowMapper<>(Staff.class);
        return template.queryForObject(sql,list,fName,lName);
    }
    public void addStaff(Staff staff){
        String sql = "INSERT INTO staff(first_name,last_name,mobile_number,email) VALUES(?,?,?,?)";
        template.update(sql,staff.getFirst_name(),staff.getLast_name(),staff.getMobile_number(),staff.getEmail());
    }
    public void updateStaff(Staff staff){
        String sql = "UPDATE staff set first_name = ?, last_name = ?, mobile_number = ?, email = ? WHERE staff_ID = ?";
        template.update(sql,staff.getFirst_name(),staff.getLast_name(),staff.getMobile_number(),staff.getEmail(),staff.getStaff_ID());
    }
    public void deleteStaff(int id){
        String sql = "DELETE from staff WHERE staff_ID = ?";
        template.update(sql,id);
    }
    public int staffCount(){
        String sql = "SELECT count(*) FROM staff";
        return template.queryForObject(sql,Integer.class);
    }

    /*  Season  */
    public String getSeasonName(){
        String sql = "SELECT season_name FROM season WHERE season_from < MONTH(CURDATE()) AND season_to > MONTH(CURDATE())";
        return template.queryForObject(sql,String.class);
    }
    public int getSeasonID(String from){
        String sql = "SELECT season_ID FROM season WHERE season_from <= MONTH(?) AND season_to >= MONTH(?)";
        return template.queryForObject(sql,Integer.class,from,from);
    }//season id might be removed

    //get season percent from specific season
    public int getSeason_percent(int id){
        String sql = "SELECT price_percent FROM season WHERE season_ID = ?";
        return template.queryForObject(sql,Integer.class,id);
    }

    //get season percent from specific date
    public int getPrice_percent(String from){
        String sql = "SELECT price_percent FROM season WHERE season_from < MONTH(?) AND season_to > MONTH(?)";
        return template.queryForObject(sql,Integer.class,from,from);
    }

    //get Current season percent
    public int getCurrentPricePercent(){
        String sql = "SELECT price_percent FROM season WHERE season_from < MONTH(CURDATE()) AND season_to > MONTH(CURDATE())";
        return template.queryForObject(sql,Integer.class);
    }

    //get All season, for update and delete
    public List<Admin> fetchSeasons(){
        String sql = "SELECT * FROM season";
        RowMapper<Admin> list = new BeanPropertyRowMapper<>(Admin.class);
        return template.query(sql,list);
    }

    //Pricing
    public Admin fetchPrice(){
        String sql = "SELECT * FROM price";
        RowMapper<Admin> list = new BeanPropertyRowMapper<>(Admin.class);
        return template.queryForObject(sql,list);
    }


}
