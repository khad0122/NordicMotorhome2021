package com.example.nordicmotorhome.Controller;

import com.example.nordicmotorhome.Admin;
import com.example.nordicmotorhome.Model.Booking;
import com.example.nordicmotorhome.Model.Invoice;
import com.example.nordicmotorhome.Model.MotorHome;
import com.example.nordicmotorhome.Model.Renter;
import com.example.nordicmotorhome.Repository.InvoiceRepo;
import com.example.nordicmotorhome.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;


@Controller
public class HomeController {
    @Autowired
    BookingService bookingService;
    @Autowired
    RenterService renterService;
    @Autowired
    MotorHomeService motorHomeService;
    @Autowired
    AdminService adminService;

    @Autowired
    InvoiceRepo invoiceRepo;

    /*******************************    Homepage     *******************************/
    @GetMapping("/")
    public String index(){ return "home/index"; }

    /*******************************    Booking     *******************************/
    @GetMapping("/bookings")
    public String bookingPage(Model model){
        ArrayList<Booking> list =(ArrayList<Booking>) bookingService.fetchAll();
        model.addAttribute("bookings",list);

        return "home/Booking/bookingsPage";
    }
    @GetMapping("/addBooking")
    public String addBookingPage(Model model){
        ArrayList<Renter> renterList = (ArrayList<Renter>) renterService.fetchAll();
        ArrayList<Booking> bookingList = (ArrayList<Booking>) bookingService.fetchAll();

        for(Booking b : bookingList){
            renterList.removeIf(r -> r.getRenter_ID() == b.getRenter_ID());
        }

        if(renterList.isEmpty()){
            return "home/Booking/allBooked";
        }
        model.addAttribute("renter",renterList);



        return "home/Booking/addBooking";

    }
    @GetMapping("/pickRenter/{renter_ID}")
    public String pickRenter(@PathVariable("renter_ID") int renterID, Model model){
        ArrayList<MotorHome> motorList = (ArrayList<MotorHome>) motorHomeService.fetchAll();
        ArrayList<Booking> bookingList = (ArrayList<Booking>) bookingService.fetchAll();

        for(Booking b : bookingList){
            motorList.removeIf(m -> m.getMotorhome_ID() == b.getMotorhome_ID());
        }

        model.addAttribute("assignedRenter",renterService.fetchById(renterID));
        model.addAttribute("motors",motorList);
        return "home/Booking/addBookingAssignMotorhome";
    }
    @GetMapping("/addBookingConfirm/{renter_ID}/{motorhome_ID}")
    public String confirmBooking(@PathVariable("renter_ID") int renterID, @PathVariable("motorhome_ID") int motorID, Model model){
        model.addAttribute("assignedRenter", renterService.fetchById(renterID));
        model.addAttribute("assignedMotor",motorHomeService.fetchById(motorID));

        return "home/Booking/bookingDetails";
    }
    @PostMapping("/confirmBookingDetails/{renter_ID}/{motorhome_ID}")
    public String confirmBookingDetails(@PathVariable("renter_ID") int renterID,
                                        @PathVariable("motorhome_ID") int motorID,
                                        @ModelAttribute Booking booking, Model model){


        booking.setStart_km(motorHomeService.fetchById(motorID).getKm());
        bookingService.addBooking(booking);

        return "redirect:/bookings";
    }


    @GetMapping("/updateBooking/{booking_ID}")
    public String updateBookingPage(@PathVariable("booking_ID") int bookingID,Model model){

        model.addAttribute("booking",bookingService.fetchById(bookingID));
        return "home/Booking/updateBooking";
    }
    @PostMapping("/saveUpdate")
    public String saveUpdate(@ModelAttribute Booking booking){
        System.out.println(booking);
        bookingService.updateBooking(booking);

        return "redirect:/bookings";
    }
    @GetMapping("/deleteBooking{booking_ID}")
    public String deleteBooking(@PathVariable("booking_ID") int bookingID){

        bookingService.deleteBooking(bookingID);

        return "redirect:/adminBooking";
    }


    /*******************************    Invoice     ********************************/
    @GetMapping("/invoice/{booking_ID}")
    public String invoice(@PathVariable("booking_ID") int bookingID, Model model){

        Booking booking = bookingService.fetchById(bookingID);;
        Invoice invoice = invoiceRepo.fetchByID(bookingID);
        model.addAttribute("booking",booking);

        if(invoice != null) {
            Admin admin = adminService.fetchPrice();
            invoice.setExtra(booking.getExtras());
            invoice.setSeason_percent(adminService.getPrice_percent(booking.getPickup_date()));
            invoice.setOutsideLocationKm(booking.getTotalKm());
            invoice.setExtra(booking.getExtras()*admin.getExtraPrice());
            invoice.updatePrice();

            model.addAttribute("invoice",invoice);
            model.addAttribute("admin",adminService.fetchPrice());

            return "home/Booking/invoice";
        }

       return "home/Booking/emptyInvoice";
    }

    /*******************************    Renter     *******************************/
    @GetMapping("/renter")
    public String renterPage(Model model){
        ArrayList<Renter> list =(ArrayList<Renter>) renterService.fetchAll();

        model.addAttribute("renters",list);

        return "home/Renter/rentersPage";
    }
    @GetMapping("/addRenter")
    public String addRenterPage(){ return "home/Renter/addRenter"; }
    @GetMapping("/updateRenter")
    public String updateRenterPage(){ return "home/Renter/updateRenter"; }

    /*******************************    Motorhome     *******************************/
    @GetMapping("/motorhomes")
    public String MotorHomePage(Model model){
        ArrayList<MotorHome> list =(ArrayList<MotorHome>) motorHomeService.fetchAll();

        model.addAttribute("MotorHomes",list);

        return "home/MotorHome/MotorHomePage";
    }
    @GetMapping("/addMotorHome")
    public String addMotorHomePage(){ return "home/MotorHome/addMotorHome"; }
    @GetMapping("/updateMotorHome")
    public String updateMotorHomePage(){ return "home/MotorHome/updateMotorHome"; }



    /*******************************    Owner   ************************************/

    @GetMapping("/owner")
    public String ownerPage(Model model){
        model.addAttribute("renterCount",renterService.renterCount());
        model.addAttribute("motorCount",motorHomeService.motorhomeCount());
        model.addAttribute("bookingCount",bookingService.bookingCount());
        model.addAttribute("staffCount",adminService.staffCount());
        model.addAttribute("seasonName", adminService.getSeasonName());
        model.addAttribute("price",adminService.fetchPrice());
        String percent = adminService.getCurrentPricePercent()+" %";
        model.addAttribute("pricePercent",percent);

        return "home/Admin/ownerPage";
    }
    @GetMapping("/adminRenter")
    public String adminRenter(Model model){
        ArrayList<Renter> list = (ArrayList<Renter>) renterService.fetchAll();
        model.addAttribute("renters",list);

        return "home/Admin/rentersView";
    }
    @GetMapping("/adminBooking")
    public String adminBooking(Model model){
        ArrayList<Booking> list = (ArrayList<Booking>) bookingService.fetchAll();
        model.addAttribute("bookings",list);
        return "home/Admin/Booking/bookingsView";
    }
    @GetMapping("/adminAddBooking")
    public String adminAddBooking(Model model){
        ArrayList<Renter> renterList = (ArrayList<Renter>) renterService.fetchAll();
        ArrayList<Booking> bookList = (ArrayList<Booking>) bookingService.fetchAll();

        for(Booking b : bookList){
            renterList.removeIf(r -> r.getRenter_ID() == b.getRenter_ID());
        }


        model.addAttribute("renters",renterList);
        return "home/Admin/Booking/adminAddBooking";
    }
    @GetMapping("/adminAssignMotor/{renter_ID}")
    public String adminAssignMotor(@PathVariable("renter_ID") int renterID,Model model){
        ArrayList<MotorHome> list = (ArrayList<MotorHome>) motorHomeService.fetchAll();
        ArrayList<Booking> bookingList = (ArrayList<Booking>) bookingService.fetchAll();

        for(Booking b: bookingList){
            list.removeIf(c -> c.getMotorhome_ID() == b.getMotorhome_ID());
        }
        if(!list.isEmpty()){
            model.addAttribute("motors",list);
            model.addAttribute("assignedRenter",renterService.fetchById(renterID));
            return "home/Admin/Booking/adminAssignBooking";
        }
        else return "home/Admin/emptyCarList";

    }
    @GetMapping("/adminPricing")
    public String adminPricing(Model model){
        ArrayList<Admin> seasonList = (ArrayList<Admin>) adminService.fetchSeasons();
        model.addAttribute("price",adminService.fetchPrice());
        model.addAttribute("season",seasonList);

        return "home/Admin/pricingView";
    }
    @GetMapping("/savePricing")
    public String savePricing(@ModelAttribute Admin admin){
        System.out.println(admin.getBasePrice());
        System.out.println(admin.getExtraPrice());
        System.out.println(admin.getCollectFee());
        System.out.println(admin.getFuelFee());
        System.out.println(admin.getKmFee());

        //update DB
        return "redirect: /";
    }

}