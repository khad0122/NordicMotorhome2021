package com.example.nordicmotorhome.Controller;

import com.example.nordicmotorhome.Price;
import com.example.nordicmotorhome.Cancellation;
import com.example.nordicmotorhome.Model.Booking;
import com.example.nordicmotorhome.Model.Invoice;
import com.example.nordicmotorhome.Model.MotorHome;
import com.example.nordicmotorhome.Model.Renter;
import com.example.nordicmotorhome.Season;
import com.example.nordicmotorhome.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    PriceService priceService;
    @Autowired
    InvoiceService invoiceService;

    /*******************************    Homepage     *******************************/
    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    /*******************************    Booking     *******************************/
    @GetMapping("/bookings")
    public String bookingPage(Model model){
        bookingService.setBookingStatus();
        ArrayList<Booking> list =(ArrayList<Booking>) bookingService.fetchAll();
        model.addAttribute("bookings",list);

        return "home/Booking/bookingsPage";
    }
    @GetMapping("/addBooking")
    public String addBookingPage(Model model){
        ArrayList<Renter> renterList = (ArrayList<Renter>) renterService.fetchAll();
        ArrayList<Booking> bookingList = (ArrayList<Booking>) bookingService.fetchAll();

        //removing every booked renter from list
        for(Booking b : bookingList){
            renterList.removeIf(r -> r.getRenter_ID() == b.getRenter_ID());
        }
        //if list is empty after removal
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

        //removing booked motorhomes
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


        Price price = priceService.fetchPrice();
        //defines startKM an stores in booking object
        booking.setStart_km(motorHomeService.fetchById(motorID).getKm());

        //Sets total Of Days
        booking.setDaysTotal(bookingService.getTotalDays(booking.getPickup_date(),booking.getReturn_date()));

        //Booking instance is added to DB
        bookingService.addBooking(booking);

        //sets status, pending : active
        bookingService.setBookingStatus();

        //booking Object is updated with a bookingID
        booking = bookingService.fetchByRenterID(renterID);

        int season_percent = priceService.getPrice_percent(booking.getPickup_date());


        Invoice invoice = new Invoice(booking.getBooking_ID());
        invoice.updateInvoice(season_percent,price,booking);
        invoiceService.addInvoice(invoice);
        return "redirect:/bookings";
    }
    @GetMapping("/updateBooking/{booking_ID}")
    public String updateBookingPage(@PathVariable("booking_ID") int bookingID,Model model){
        model.addAttribute("booking",bookingService.fetchById(bookingID));
        Invoice invoice = invoiceService.fetchByID(bookingID);
        model.addAttribute("invoice",invoiceService.fetchByID(bookingID));
        return "home/Booking/updateBooking";
    }
    @PostMapping("/saveUpdate")
    public String saveUpdate(@ModelAttribute Booking booking, @RequestParam("extra_km") int extraKm, @RequestParam("fuelCheck") boolean check){
        Price price = priceService.fetchPrice();

        //updating daysTotal incase pickup and drop are changed
        booking.setDaysTotal(bookingService.getTotalDays(booking.getPickup_date(),booking.getReturn_date()));

        //fetching the associated invoice via booking ID
        Invoice invoice = invoiceService.fetchByID(booking.getBooking_ID());
        invoice.setFuelCheck(check);
        invoice.setExtra_km(extraKm);


        //Invoice fields are updated with method updateInvoice()
        invoice.updateInvoice(priceService.getPrice_percent(booking.getPickup_date()), price, booking);
        invoiceService.updateInvoice(invoice);
        bookingService.updateBooking(booking);

        return "redirect:/bookings";
    }

    @GetMapping("/deleteBooking{booking_ID}")
    public String deleteBooking(@PathVariable("booking_ID") int bookingID){
        bookingService.deleteBooking(bookingID);
        return "redirect:/adminBooking";
    }
    @GetMapping("/cancelBooking{booking_ID}")
    public String cancelBooking(@PathVariable("booking_ID") int bookingID ){
        Cancellation cancel = priceService.getCancellation(bookingID);
        Invoice invoice = invoiceService.fetchByID(bookingID);
        double price = invoice.bookingCancel();


        bookingService.cancelBooking(bookingID);
        price = (price*((double)cancel.getCancellation_percent()/100));

        if(price < cancel.getMinPrice()){
            price = cancel.getMinPrice();
        }
        invoice.setPrice(price);
        invoiceService.updateInvoice(invoice);

        return "redirect:/bookings";
    }

    /*******************************    Invoice     ********************************/
    @GetMapping("/invoice/{booking_ID}")
    public String invoicePage(@PathVariable("booking_ID") int bookingID, Model model){
        Booking booking = bookingService.fetchById(bookingID);;
        Invoice invoice = invoiceService.fetchByID(bookingID);
        model.addAttribute("booking",booking);

        if(!booking.getStatus().equals("canceled")) {
            invoice.updateInvoice(priceService.getPrice_percent(booking.getPickup_date()), priceService.fetchPrice(), booking);
            invoiceService.updateInvoice(invoice);
            System.out.println(invoice.getFuelFee());
        }

        model.addAttribute("renter",renterService.fetchById(booking.getRenter_ID()));
        model.addAttribute("invoice",invoice);


        return "home/Invoice/invoice";
    }
    @GetMapping("/allInvoices")
    public String allInvoices(Model model){

        ArrayList<Invoice> list = (ArrayList<Invoice>) invoiceService.fetchAll();
        model.addAttribute("invoice",list);

        return "home/Invoice/invoices";
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
    @PostMapping("/add/")
    public String addRenter(@ModelAttribute Renter r, @RequestParam(value="enableBooking") String choice, RedirectAttributes rd){
        int id = renterService.addRenter(r);

        if(choice.equals("yes")){
            rd.addAttribute("renter_ID",id);
            return "redirect:/pickRenter/{renter_ID}";
        }

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
    public String motorHomePage(Model model){
        ArrayList<MotorHome> list =(ArrayList<MotorHome>) motorHomeService.fetchAll();
        model.addAttribute("MotorHomes",list);
        return "home/MotorHome/MotorHomePage";
    }
    @GetMapping("/addMotorHome")
    public String addMotorHomePage(){ return "home/MotorHome/addMotorHome"; }
    @PostMapping("/addMotorHome/")
    public String addMotorHome(@ModelAttribute MotorHome m) {
        motorHomeService.addMotorHome(m);
        return "redirect:/motorhomes";
    }

    @GetMapping("/updateMotorHome/{motorhome_ID}")
    public String updateMotorHomePage(@PathVariable("motorhome_ID") int motorhomeID, Model model){
        model.addAttribute("motorhome",motorHomeService.fetchById(motorhomeID));
        return "home/MotorHome/updateMotorHome";
    }
    @PostMapping("/updateMotorHome/")
    public String updateMotorHome(@ModelAttribute MotorHome m){
        motorHomeService.updateMotorHome(m);
        return "redirect:/motorhomes";
    }

    @GetMapping("/deleteMotorHome/{motorhome_ID}")
    public String deleteMotorHomePage(@PathVariable("motorhome_ID") int id){
        motorHomeService.deleteMotorHome(id);
        return "redirect:/motorhomes";
    }

    /*******************************    Admin   ************************************/

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("renterCount",renterService.renterCount());
        model.addAttribute("motorCount",motorHomeService.motorhomeCount());
        model.addAttribute("bookingCount",bookingService.bookingCount());
        model.addAttribute("seasonName", priceService.getSeasonName());
        model.addAttribute("price", priceService.fetchPrice());
        String percent = priceService.getCurrentPricePercent()+" %";
        model.addAttribute("pricePercent",percent);

        return "home/Admin/ownerPage";
    }

    @GetMapping("/adminBooking")
    public String adminBooking(Model model){
        ArrayList<Booking> list = (ArrayList<Booking>) bookingService.fetchAll();
        model.addAttribute("bookings",list);
        return "home/Admin/Booking/bookingsView";
    }

    //Invoice
    @GetMapping("/adminInvoice")
    public String adminInvoice(Model model){
        ArrayList<Invoice> list = (ArrayList<Invoice>) invoiceService.fetchAll();
        model.addAttribute("invoice",list);

        return "home/Admin/Invoice/invoiceView";
    }
    @GetMapping("/showInvoice/{booking_ID}")
    public String showInvoice(@PathVariable("booking_ID") int bookingID, Model model){

        Booking booking = bookingService.fetchById(bookingID);;
        Invoice invoice = invoiceService.fetchByID(bookingID);
        Price price = priceService.fetchPrice();

        if(!booking.getStatus().equals("canceled")) {
            int seasonP = priceService.getPrice_percent(booking.getPickup_date());
            invoice.updateInvoice(seasonP, price, booking);
        }
        model.addAttribute("invoice",invoice);
        model.addAttribute("renter",renterService.fetchById(booking.getRenter_ID()));
        model.addAttribute("booking",booking);

        return "home/Admin/Invoice/showInvoice";
    }
    @GetMapping("/deleteInvoice/{invoice_ID}")
    public String deleteInvoice(@PathVariable("invoice_ID") int invoiceID){
        invoiceService.deleteInvoice(invoiceID);

        return "redirect:/adminInvoice";
    }

    //Pricing
    @GetMapping("/adminPricing")
    public String adminPricing(Model model){
        ArrayList<Season> seasonList = (ArrayList<Season>) priceService.fetchSeasons();
        ArrayList<Cancellation> cancelList = (ArrayList<Cancellation>) priceService.fetchCancellation();

        model.addAttribute("cancellation",cancelList);
        model.addAttribute("price", priceService.fetchPrice());
        model.addAttribute("season",seasonList);

        return "home/Admin/Pricing/pricingView";
    }
    @GetMapping("/savePricing")
    public String savePricing(@ModelAttribute Price price){
            priceService.updatePrice(price);

        //update DB
        return "redirect:/adminPricing";
    }

    //Seasons
    @GetMapping("/updateSeason{season_name}")
    public String updateSeason(@PathVariable("season_ID") int seasonID, Model model){
            model.addAttribute("season", priceService.getSeasonsByID(seasonID));
        return "home/Admin/Pricing/updateSeason";
    }
    @GetMapping("/saveSeason")
    public String saveSeason(@ModelAttribute Season season){
        priceService.updateSeason(season);
        return "redirect:/adminPricing";
    }

    //cancellation
    @GetMapping("/saveCancellation")
    public String saveCancellation(@ModelAttribute Cancellation cancel){
        priceService.updateCancellation(cancel);
        return  "redirect:/adminPricing";
    }
}