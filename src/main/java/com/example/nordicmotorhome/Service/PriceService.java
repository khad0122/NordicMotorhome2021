package com.example.nordicmotorhome.Service;

import com.example.nordicmotorhome.Price;
import com.example.nordicmotorhome.Cancellation;
import com.example.nordicmotorhome.Model.Staff;
import com.example.nordicmotorhome.Repository.PriceRepo;
import com.example.nordicmotorhome.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PriceService {
    @Autowired
    PriceRepo priceRepo;
    //Price
    public Price fetchPrice(){return priceRepo.fetchPrice();}
    public void updatePrice(Price price){ priceRepo.updatePrice(price);}

    //Season
    public List<Season> fetchSeasons(){return priceRepo.fetchSeasons();}

    public Season getSeasonsByName(String name){ return priceRepo.getSeasonsByName(name); }
    public void updateSeason(Season season){ priceRepo.updateSeason(season);}

    public String getSeasonName(){return priceRepo.getSeasonName();}
    public int getPrice_percent(String from){return priceRepo.getPrice_percent(from);}
    public int getCurrentPricePercent(){
        return priceRepo.getCurrentPricePercent();
    }


    //Cancellation
    public List<Cancellation> fetchCancellation(){return priceRepo.fetchCancellation();}
    public void updateCancellation(Cancellation c){ priceRepo.updateCancellation(c);}
    public int getCancellationPercent(int id){return priceRepo.getCancellationPercent(id);}

}
