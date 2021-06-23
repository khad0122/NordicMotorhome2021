package com.example.nordicmotorhome.Model;

import com.example.nordicmotorhome.Price;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void updateInvoice() {
        //Initialize Invoice Object
        Invoice invoice = new Invoice();
        //Initialize and define Price Object
        Price price = new Price(100,10,1,1,1);

        //Calling updateInvoice method
        invoice.updateInvoice(0,price,new Booking(0,0,0,10));

        //Assert Total price after updateInvoice method call
        assertEquals(1000.0, invoice.getPrice());

        //Calling updateInvoice method with different parameter
        invoice.updateInvoice(50,price,new Booking(10,9,9,20));

        //assert Total Price after method call
        assertEquals(3118.0,invoice.getPrice());

        //assert fee, outsidekmFee and extra in Invoice Object
        assertEquals(118, invoice.getFee());
        assertEquals(18, invoice.getOutsideKmFee());
        assertEquals(100,invoice.getExtra());
    }
}

