package com.example.nordicmotorhome.Repository;

import com.example.nordicmotorhome.Model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public class InvoiceRepo {
    @Autowired
    JdbcTemplate template;


    public List<Invoice> fetchAll(){
        String sql = "SELECT * FROM invoice";
        RowMapper<Invoice> list = new BeanPropertyRowMapper<>(Invoice.class);
        return template.query(sql, list);
    }
    public Invoice fetchByID(int id) {
        String sql = "SELECT * FROM invoice WHERE booking_ID = ?";
        RowMapper<Invoice> list = new BeanPropertyRowMapper<>(Invoice.class);
        if(getCount(id) != 0) {
            return template.queryForObject(sql, list, id);
        }return null;
    }

    public int getCount(int id){
        String sql = "SELECT count(*) FROM invoice WHERE booking_id = ?";
        return template.queryForObject(sql,Integer.class,id);
    }


    public void addInvoice(Invoice invoice){
        String sql = "INSERT INTO invoice(booking_ID,season_percent,extra_km,fee,price) VALUES(?,?,?,?,?)";
        template.update(sql,invoice.getBooking_ID(),invoice.getSeason_percent(),invoice.getExtra_km(),invoice.getFee(),invoice.getPrice());
    }
    public void updateInvoice(Invoice i) {
        String sql = "UPDATE invoice SET season_percent = ?, extra_km = ?, fee = ?, price = ? WHERE invoice_ID = ?";
        template.update(sql,i.getSeason_percent(),i.getExtra_km(),i.getFee(),i.getPrice(),i.getInvoice_ID());
    }
    public void deleteInvoice(int id){
        String sql = "DELETE FROM invoice WHERE invoice_ID = ?";
        template.update(sql,id);
    }
}







