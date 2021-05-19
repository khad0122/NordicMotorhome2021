package Repository;

import Model.MotorHome;
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
        String sql = "UPDATE cars SET model = ?, brand = ?, type = ?, size = ? WHERE motorhomeID = ?";
        template.update(sql, m.getModel(),m.getBrand(),m.getType(),m.getSize(), m.getMotorhome_ID());
    }
    public void deleteMotorHome(int id){
        String sql = "DELETE FROM cars WHERE motorhomeID = ?";
        template.update(sql,id);
    }
    public List<MotorHome> fetchAll(){
        String sql = "SELECT * FROM motorhomes";
        RowMapper<MotorHome> list = new BeanPropertyRowMapper<>(MotorHome.class);
        return template.query(sql,list);
    }
    public MotorHome fetchById(int id){
        String sql = "SELECT * FROM motorhomes WHERE motorhomesID = ?";
        RowMapper<MotorHome> list = new BeanPropertyRowMapper<>(MotorHome.class);
        MotorHome motorHome = template.queryForObject(sql,list,id);
        return motorHome;
    }
}