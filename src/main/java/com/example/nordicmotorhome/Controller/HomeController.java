package com.example.nordicmotorhome.Controller;

import com.example.nordicmotorhome.Model.Booking;
import com.example.nordicmotorhome.Model.MotorHome;
import com.example.nordicmotorhome.Model.Renter;
import com.example.nordicmotorhome.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

import static org.thymeleaf.util.StringUtils.substring;

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

    /*******************************    Homepage     *******************************/
    @GetMapping("/")
    public String index(){return "home/index";}

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

        return "home/Booking/confirmBooking";
    }


    @GetMapping("/updateBooking")
    public String updateBookingPage(){
        return "home/Booking/updateBooking";
    }

    /*******************************    Renter     *******************************/
    @GetMapping("/renter")
    public String renterPage(Model model){
        ArrayList<Renter> list =(ArrayList<Renter>) renterService.fetchAll();

        model.addAttribute("renters",list);

        return "home/Renter/rentersPage";
    }

    @GetMapping("/addRenter")
    public String addRenterPage(){
        return "home/Renter/addRenter";
    }

    @GetMapping("/updateRenter")
    public String updateRenterPage(){
        return "home/Renter/updateRenter";
    }

    /*******************************    Motorhome     *******************************/
    @GetMapping("/motorhomes")
    public String MotorHomePage(Model model){
        ArrayList<MotorHome> list =(ArrayList<MotorHome>) motorHomeService.fetchAll();

        model.addAttribute("MotorHomes",list);

        return "home/MotorHome/MotorHomePage";
    }

    @GetMapping("/addMotorHome")
    public String addMotorHomePage(){
        return "home/MotorHome/addMotorHome";
    }

    @GetMapping("/updateMotorHome")
    public String updateMotorHomePage(){
        return "home/MotorHome/updateMotorHome";
    }



    /*******************************    Owner   ************************************/

    @GetMapping("/owner")
    public String ownerPage(Model model){
        model.addAttribute("renterCount",renterService.renterCount());
        model.addAttribute("motorCount",motorHomeService.motorhomeCount());
        model.addAttribute("bookingCount",bookingService.bookingCount());
        model.addAttribute("staffCount",adminService.staffCount());
        model.addAttribute("seasonName", adminService.getSeasonName());
        String percent = adminService.getPrice_percent()+" %";
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



        return "home/Admin/pricingView";
    }

}