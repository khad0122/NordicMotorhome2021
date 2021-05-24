package com.example.nordicmotorhome.Service;

import com.example.nordicmotorhome.Model.Invoice;
import com.example.nordicmotorhome.Repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepo invoiceRepo;

    public List<Invoice> fetchAll(){ return invoiceRepo.fetchAll(); }
    public int getCount(int id){ return invoiceRepo.getCount(id); }

    public Invoice fetchByID(int id) {return invoiceRepo.fetchByID(id); }
    public void addInvoice(Invoice invoice){ invoiceRepo.addInvoice(invoice); }

}
