package Service;

import Model.Booking;
import Repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepo bookingRepo;

    public void addContract(Booking booking) {
        bookingRepo.addContract(booking);
    }

    public void updateContract(Booking c) {
        BookingRepo bookingRepoo;
        bookingRepo.updateContract(c);
    }

    public void deleteContract(int id) {
        bookingRepo.deleteContract(id);
    }

    public List<Booking> fetchAll() {
        return bookingRepo.fetchAll();
    }

    public Booking fetchById() {
        return bookingRepo.fetchById(id);
    }
}
