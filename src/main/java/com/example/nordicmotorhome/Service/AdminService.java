package com.example.nordicmotorhome.Service;

import com.example.nordicmotorhome.Admin;
import com.example.nordicmotorhome.Model.Staff;
import com.example.nordicmotorhome.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService {
    @Autowired
    AdminRepo adminRepo;

    public List<Staff> fetchAll(){
        return adminRepo.fetchAll();
    }

    public Admin getSeasonsByName(String name){ return adminRepo.getSeasonsByName(name); }
    public void updateSeason(Admin admin){adminRepo.updateSeason(admin);}


    public int staffCount(){
        return adminRepo.staffCount();
    }
    public String getSeasonName(){return adminRepo.getSeasonName();}
    public int getPrice_percent(String from){return adminRepo.getPrice_percent(from);}
    public int getCurrentPricePercent(){
        return adminRepo.getCurrentPricePercent();
    }

    public List<Admin> fetchSeasons(){return adminRepo.fetchSeasons();}
    public List<Admin> fetchCancellation(){return adminRepo.fetchCancellation();}
    public void updateCancellation(Admin a){adminRepo.updateCancellation(a);}

    public Admin fetchPrice(){return adminRepo.fetchPrice();}
    public void updatePrice(Admin admin){ adminRepo.updatePrice(admin);}
}
