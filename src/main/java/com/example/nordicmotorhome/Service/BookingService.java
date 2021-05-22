package com.example.nordicmotorhome.Service;

import com.example.nordicmotorhome.Model.Booking;
import com.example.nordicmotorhome.Repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepo bookingRepo;

    public void addBooking(Booking booking) {
        bookingRepo.addBooking(booking);
    }
    public void updateBooking(Booking c) { bookingRepo.updateBooking(c); }
    public void deleteBooking(int id) {
        bookingRepo.deleteBooking(id);
    }
    public List<Booking> fetchAll() {
        return bookingRepo.fetchAll();
    }
    public Booking fetchById(int id) {
        return bookingRepo.fetchById(id);
    }
    public int bookingCount(){return bookingRepo.bookingCount();}
}
