package com.example.nordicmotorhome.Repository;

import com.example.nordicmotorhome.Model.MotorHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorHomeRepo {
    @Autowired
    JdbcTemplate template;

    public void addMotorHome(MotorHome m){
        String sql = "INSERT INTO motorhome(model,brand,type,size) values(?,?,?,?)";
        template.update(sql,m.getModel(),m.getBrand(),m.getType(),m.getSize());
    }
    public void updateMotorHome(MotorHome m){
        String sql = "UPDATE motorhome SET model = ?, brand = ?, type = ?, size = ? WHERE motorhome_ID = ?";
        template.update(sql, m.getModel(),m.getBrand(),m.getType(),m.getSize(), m.getMotorhome_ID());
    }
    public void deleteMotorHome(int id){
        String sql = "DELETE FROM motorhome WHERE motorhome_ID = ?";
        template.update(sql,id);
    }
    public List<MotorHome> fetchAll(){
        String sql = "SELECT * FROM motorhome";
        RowMapper<MotorHome> list = new BeanPropertyRowMapper<>(MotorHome.class);
        return template.query(sql,list);
    }
    public MotorHome fetchById(int id){
        String sql = "SELECT * FROM motorhome WHERE motorhome_ID = ?";
        RowMapper<MotorHome> list = new BeanPropertyRowMapper<>(MotorHome.class);
        return template.queryForObject(sql,list,id);
    }
    public int motorhomeCount(){
        String sql = "SELECT count(*) FROM motorhome";
        return template.queryForObject(sql, Integer.class);
    }
}