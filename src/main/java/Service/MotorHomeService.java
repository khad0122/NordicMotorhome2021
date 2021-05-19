package Service;

import Model.MotorHome;
import Repository.MotorHomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorHomeService {
    @Autowired
    MotorHomeRepo motorHomeRepo;

    public void addMotorHome(MotorHome m) {
        motorhomeRepo.addMotorhome(m);
    }

    public void updateMotorHome(MotorHome m) {
        motorhomeRepo.updateMotorhome(m);
    }

    public void deleteMotorHome(int id) {
        motorhomeRepo.deleteMotorhome(id);
    }

    public List<MotorHome> fetchAll() {
        return motorhomeRepo.fetchAll();
    }

    public MotorHome fetchById(int id) {
        return motorhome.fetchById(id);
    }
}