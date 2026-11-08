package com.project.movie.controller;

import com.project.movie.entity.Booking;
import com.project.movie.service.BookingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path="/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

	@Autowired
    private BookingServices bookingService;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable int id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
    	Booking savedBooking = bookingService.saveBooking(booking);
        return new ResponseEntity<String>("Booking successfull with ID: " + savedBooking.getBookingId(),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable int id, @RequestBody Booking bookingDetails) {
        Booking updatedBooking = bookingService.updateBooking(id, bookingDetails);
        return new ResponseEntity<String>("Booking with ID: " + id + " Updated!",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        return new ResponseEntity<String>("Booking with ID: " + id + " Deleted!",HttpStatus.OK);
    }
    
}
