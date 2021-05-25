package com.example.nordicmotorhome.Controller;

import com.example.nordicmotorhome.Admin;
import com.example.nordicmotorhome.Model.Booking;
import com.example.nordicmotorhome.Model.Invoice;
import com.example.nordicmotorhome.Model.MotorHome;
import com.example.nordicmotorhome.Model.Renter;
import com.example.nordicmotorhome.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    InvoiceService invoiceService;

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


        Admin admin = adminService.fetchPrice();
        //Add Invoice Here? //
        //defines startKM an stores in booking object
        booking.setStart_km(motorHomeService.fetchById(motorID).getKm());

        //Sets total Of Days
        booking.setDaysTotal(bookingService.getTotalDays(booking.getPickup_date(),booking.getReturn_date()));
        System.out.println(booking.getDaysTotal());
        //Booking instance is added to DB
        bookingService.addBooking(booking);
        //booking Object is updated with a bookingID

        booking = null;
        booking = bookingService.fetchByRenterID(renterID);
       System.out.println(booking);

        //Invoice Section

        //General Fields that always needs to be done before invoice,to make sure all prices are updated
        //Extra Equipments, Price pr equimpent is stored in Admin Object * amount of extra items in booking
        int extra = admin.getExtraPrice()*booking.getExtras();

        //Set BasePrice from Admin Object
        double price = admin.getBasePrice();

        //For outside pickup & dropoff, a fee for each km to distination
        double outsideKmFee = booking.getTotalKm()*admin.getCollectFee();

        int season_percent = adminService.getPrice_percent(booking.getPickup_date());

        Invoice invoice = new Invoice(booking.getBooking_ID(),season_percent,price,extra,outsideKmFee);
        invoiceService.addInvoice(invoice);



        return "redirect:/bookings";
    }
    @GetMapping("/updateBooking/{booking_ID}")
    public String updateBookingPage(@PathVariable("booking_ID") int bookingID,Model model){

        model.addAttribute("booking",bookingService.fetchById(bookingID));
        return "home/Booking/updateBooking";
    }
    @PostMapping("/saveUpdate")
    public String saveUpdate(@ModelAttribute Booking booking){
        Admin admin = adminService.fetchPrice();
        booking.setDaysTotal(bookingService.getTotalDays(booking.getPickup_date(),booking.getReturn_date()));
        Invoice invoice = invoiceService.fetchByID(booking.getBooking_ID());
        invoice.updateInvoice(adminService.getPrice_percent(booking.getPickup_date()),admin,booking);
        invoiceService.updateInvoice(invoice);
        bookingService.updateBooking(booking);

        return "redirect:/bookings";
    }
    @GetMapping("/deleteBooking{booking_ID}")
    public String deleteBooking(@PathVariable("booking_ID") int bookingID){

        bookingService.deleteBooking(bookingID);

        return "redirect:/adminBooking";
    }

    //Not DOne
    @GetMapping("/cancelBooking{booking_ID}")
    public String cancelBooking(@PathVariable("booking_ID") int bookingID ){
        return "redirect:/";
    }

    /*******************************    Invoice     ********************************/
    @GetMapping("/invoice/{booking_ID}")
    public String invoice(@PathVariable("booking_ID") int bookingID, Model model){
        Booking booking = bookingService.fetchById(bookingID);;
        Invoice invoice = invoiceService.fetchByID(bookingID);
        model.addAttribute("booking",booking);

        if(invoice != null) {
            invoice.updateInvoice(adminService.getPrice_percent(booking.getPickup_date()),adminService.fetchPrice(), booking);
            invoiceService.updateInvoice(invoice);
            model.addAttribute("renter",renterService.fetchById(booking.getRenter_ID()));
            model.addAttribute("invoice",invoice);

            return "home/Invoice/invoice";
        }

       return "home/Invoice/emptyInvoice";
    }
    @GetMapping("/allInvoices")
    public String allInvoices(Model model){

        ArrayList<Invoice> list = (ArrayList<Invoice>) invoiceService.fetchAll();
        model.addAttribute("invoice",list);

        return "home/Invoice/invoices";
    }
    @GetMapping("/generateInvoice{booking_ID}")
    public String generateInvoice(@PathVariable("booking_ID") int booking_ID, Model model){

        Booking booking = bookingService.fetchById(booking_ID);
        //price
        Admin admin = adminService.fetchPrice();
        int ex = admin.getExtraPrice()*booking.getExtras();
        int ol = booking.getKmToPickup()+booking.getKmToDropoff();



        //define season percent for booking


        return "redirect:/invoice/{booking_ID}";
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
    @PostMapping("/add")
    public String add(@ModelAttribute Renter r){
        renterService.addRenter(r);
        return "redirect:/renter";
    }
    @GetMapping("/updateRenter/{renter_ID}")
    public String updateRenterPage(@PathVariable("renter_ID") int renterID, Model model){
        model.addAttribute("renter",renterService.fetchById(renterID));
        return "home/Renter/updateRenter";
    }
    @PostMapping("/updateRenter/")
    public String updateRenter(@ModelAttribute Renter r){
        renterService.updateRenter(r);
        return "redirect:/renter";
    }
    @GetMapping("/deleteRenter/{renter_ID}")
    public String deleteRenterPage(@PathVariable("renter_ID") int id){
        renterService.deleteRenter(id);
        return "redirect:/renter";
    }

    /*******************************    Motorhome     *******************************/
    @GetMapping("/motorhomes")
    public String MotorHomePage(Model model){
        ArrayList<MotorHome> list =(ArrayList<MotorHome>) motorHomeService.fetchAll();
        model.addAttribute("MotorHomes",list);
        return "home/MotorHome/MotorHomePage";
    }
    @GetMapping("/addMotorHome")
    public String addMotorHomePage(){ return "home/MotorHome/addMotorHome"; }

    @PostMapping("/addm")
    public String addm(@ModelAttribute MotorHome m) {
        MotorHomeService.addMotorHome(m);
        return "redirect:/motorhomes";
    }

    @GetMapping("/updateMotorHome")
    public String updateMotorHomePage(){ return "home/MotorHome/updateMotorHome"; }


    /*******************************    Owner   ************************************/

    @GetMapping("/admin")
    public String adminPage(Model model){
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