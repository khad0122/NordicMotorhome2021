package com.example.nordicmotorhome.Repository;

import com.example.nordicmotorhome.Price;
import com.example.nordicmotorhome.Cancellation;
import com.example.nordicmotorhome.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PriceRepo {
    @Autowired
    JdbcTemplate template;

    //Pricing
    public Price fetchPrice(){
        String sql = "SELECT * FROM price";
        RowMapper<Price> list = new BeanPropertyRowMapper<>(Price.class);
        return template.queryForObject(sql,list);
    }
    public void updatePrice(Price price){
        String sql = "UPDATE price SET basePrice = ?, extraPrice = ?, fuelFee = ?, kmFee = ?, collectFee = ?";
        template.update(sql, price.getBasePrice(), price.getExtraPrice(), price.getFuelFee(), price.getKmFee(), price.getCollectFee());
    }


    // Season
    //get All season, for update
    public List<Season> fetchSeasons(){
        String sql = "SELECT * FROM season";
        RowMapper<Season> list = new BeanPropertyRowMapper<>(Season.class);
        return template.query(sql,list);
    }
    public Season getSeasonsByID(int id){
        String sql = "SELECT * FROM season WHERE season_ID = ?";
        RowMapper<Season> list = new BeanPropertyRowMapper<>(Season.class);
        return template.queryForObject(sql,list,id);
    }

    public String getSeasonName(){
        String sql = "SELECT season_name FROM season WHERE season_from < MONTH(CURDATE()) AND season_to > MONTH(CURDATE())";
        return template.queryForObject(sql,String.class);
    }

    //get season percent from specific date
    public int getPrice_percent(String from){
        String sql = "SELECT price_percent FROM season WHERE season_from <= MONTH(?) AND season_to >= MONTH(?)";
        return template.queryForObject(sql,Integer.class,from,from);
    }
    //get Current season percent
    public int getCurrentPricePercent(){
        String sql = "SELECT price_percent FROM season WHERE season_from <= MONTH(CURDATE()) AND season_to >= MONTH(CURDATE())";
        return template.queryForObject(sql,Integer.class);
    }

    public void updateSeason(Season s){
        String sql = "UPDATE season SET season_name = ?, season_from = ?, season_to = ?,price_percent = ? WHERE season_ID = ?";
        template.update(sql,s.getSeason_name(),s.getSeason_from(),s.getSeason_to(),s.getPrice_percent(),s.getSeason_ID());
    }


    //Cancellation
    public List<Cancellation> fetchCancellation(){
        String sql = "SELECT * FROM cancellation";
        RowMapper<Cancellation> list = new BeanPropertyRowMapper<>(Cancellation.class);
        return template.query(sql,list);
    }
    public void updateCancellation(Cancellation a){
        String sql = "UPDATE cancellation SET cancellation_percent = ?, toDay = ?, fromDay = ?, minPrice = ? WHERE cancellation_ID = ?";
        template.update(sql,a.getCancellation_percent(),a.getToDay(),a.getFromDay(),a.getMinPrice(),a.getCancellation_ID());
    }

    public Cancellation getCancellation(int id){
        String sql = "SELECT cancellation.* FROM nordicmotorhomedb.cancellation  " +
                "join nordicmotorhomedb.booking WHERE DATEDIFF(pickup_date,CURDATE()) >= cancellation.fromDay " +
                "AND DATEDIFF(pickup_date, CURDATE()) <= cancellation.toDay AND booking_ID = ?";
            RowMapper<Cancellation> list = new BeanPropertyRowMapper<>(Cancellation.class);
        return template.queryForObject(sql,list,id);
    }

}
