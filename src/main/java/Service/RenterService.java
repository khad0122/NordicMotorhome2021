package Service;

import Model.Renter;
import Repository.RenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RenterService {
    @Autowired
    RenterRepo renterRepo;

    public void addRenter(Renter r) {
        renterRepo.addRenter(r);
    }

    public void updateRenter(Renter r) {
        renterRepo.updateRenter(r);
    }

    public void deleteRenter(int id) {
        renterRepo.deleteRenter(id);
    }

    public List<Renter> fetchAll() {
        return renterRepo.fetchAll();
    }

    public Renter fetchById(int id) {
        return renterRepo.fetchById(id);
    }
}