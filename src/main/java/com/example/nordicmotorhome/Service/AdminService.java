package com.example.nordicmotorhome.Service;

import com.example.nordicmotorhome.Admin;
import com.example.nordicmotorhome.Model.Staff;
import com.example.nordicmotorhome.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService {
    @Autowired
    AdminRepo adminRepo;

    public List<Staff> fetchAll(){
        return adminRepo.fetchAll();
    }
    public Staff fetchByName(String fName, String lName){
        return adminRepo.fetchByName(fName,lName);
    }

    public void addStaff(Staff staff){
        adminRepo.addStaff(staff);
    }
    public void updateStaff(Staff staff){
        adminRepo.updateStaff(staff);
    }
    public void deleteStaff(int id){
        adminRepo.deleteStaff(id);
    }

    public int staffCount(){
        return adminRepo.staffCount();
    }
    public String getSeasonName(){return adminRepo.getSeasonName();}
    public int getPrice_percent(String from){return adminRepo.getPrice_percent(from);}
    public int getCurrentPricePercent(){
        return adminRepo.getCurrentPricePercent();
    }
    public int getSeasonID(String from){return adminRepo.getSeasonID(from);}

    public List<Admin> fetchSeasons(){return adminRepo.fetchSeasons();}
    public Admin fetchPrice(){return adminRepo.fetchPrice();}

}
