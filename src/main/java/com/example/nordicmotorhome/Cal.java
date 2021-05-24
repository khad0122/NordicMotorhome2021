package com.example.nordicmotorhome;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cal {
    public static void main(String[] args){

        double a = 15.84521;
        DecimalFormat deci = new DecimalFormat("##0.00");
        double b = Double.parseDouble(deci.format(a).replace(",","."));
        String d = deci.format(a).replace(",",".");
        double c = Double.parseDouble(d);
        System.out.println(c);

    }
}
