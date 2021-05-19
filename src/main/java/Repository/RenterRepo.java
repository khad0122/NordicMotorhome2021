package Repository;

import Model.Renter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RenterRepo {
    @Autowired
    JdbcTemplate template;

    public void addRenter(Renter r){
        String sql = "INSERT INTO renter(first_name,last_name,mobil_number,email,address_street,address_zip,address_city,DLN,driver_since) values(?,?,?,?,?,?,?,?,?)";
        template.update(sql,r.getFirst_name(),r.getLast_name(),r.getMobile_number(),r.getEmail(),r.getAddress_street(),r.getAddress_zip(),r.getAddress_zip(),r.getDLN(),r.getDriversince_date());
    }
    public void updateRenter(Renter r){
        String sql = "UPDATE renter set first_name = ?, last_name = ?, mobil_number = ?, email = ?, address_street = ?, adresse_zip = ?, adresse_city = ?, DLN = ?, driver_since = ? WHERE renterID = ?";
        template.update(sql,r.getFirst_name(),r.getLast_name(),r.getMobile_number(),r.getEmail(),r.getAddress_street(),r.getAddress_zip(),r.getAddress_city(),r.getDLN(),r.getDriversince_date(),r.getRenter_ID());
    }
    public void deleteRenter(int id){
        String sql ="DELETE FROM renter WHERE renterID = ?";
        template.update(sql, id);
    }
    public List<Renter> fetchAll(){
        String sql = "SELECT * FROM renter";
        RowMapper<Renter> list = new BeanPropertyRowMapper<>(Renter.class);
            return template.query(sql,list);
    }
    public Renter fetchById(int id){
        String sql = "SELECT * FROM renter WHERE renterID = ?";
        RowMapper<Renter> list = new BeanPropertyRowMapper<>(Renter.class);
        Renter r = template.queryForObject(sql,list,id);
            return r;
    }
}
