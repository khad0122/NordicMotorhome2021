package com.example.nordicmotorhome.Service;

import com.example.nordicmotorhome.Repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepo invoiceRepo;

}
