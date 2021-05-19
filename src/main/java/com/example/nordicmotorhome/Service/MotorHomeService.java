package com.example.nordicmotorhome.Service;

import com.example.nordicmotorhome.Model.MotorHome;
import com.example.nordicmotorhome.Repository.MotorHomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorHomeService {
    @Autowired
    MotorHomeRepo motorHomeRepo;

    public void addMotorHome(MotorHome m) {
        motorHomeRepo.addMotorHome(m);
    }

    public void updateMotorHome(MotorHome m) {
        motorHomeRepo.updateMotorHome(m);
    }

    public void deleteMotorHome(int id) {
        motorHomeRepo.deleteMotorHome(id);
    }

    public List<MotorHome> fetchAll() {
        return motorHomeRepo.fetchAll();
    }

    public MotorHome fetchById(int id) {
        return motorHomeRepo.fetchById(id);
    }
}