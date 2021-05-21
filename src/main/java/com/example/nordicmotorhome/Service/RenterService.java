package com.example.nordicmotorhome.Service;

import com.example.nordicmotorhome.Model.Renter;
import com.example.nordicmotorhome.Repository.RenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public int renterCount(){return renterRepo.renterCount();}
}