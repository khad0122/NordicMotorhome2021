package com.example.nordicmotorhome.Controller;

import com.example.nordicmotorhome.Model.Booking;
import com.example.nordicmotorhome.Service.BookingService;
import com.example.nordicmotorhome.Service.MotorHomeService;
import com.example.nordicmotorhome.Service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

import static org.thymeleaf.util.StringUtils.substring;

@Controller
public class HomeController {
    @Autowired
    RenterService renterService;
    @Autowired
    MotorHomeService motorHomeService;
    @Autowired
    BookingService bookingService;



    @GetMapping("/")
    public String index(){return "home/index";}


    @GetMapping("/renters")
    public String rentersPage(){

        return "home/Renter/rentersPage";
    }


    /*******************    Booking     *******************************/
    @GetMapping("/bookings")
    public String bookingPage(Model model){
        ArrayList<Booking> list =(ArrayList<Booking>) bookingService.fetchAll();

        model.addAttribute("bookings",list);

        return "home/Booking/BookingsPage";
    }

    @GetMapping("/addBooking")
    public String addBookingPage(){
        return "home/Booking/addBookingFunction";
    }
    @GetMapping("/updateBooking")
    public String updateBookingPage(){
        return "home/Booking/updateBookingFunction";
    }








}
