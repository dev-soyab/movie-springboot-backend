package com.project.movie.service.impl;

import com.project.movie.entity.Booking;
import com.project.movie.entity.Showtime;
import com.project.movie.exceptions.ResourceNotFoundException;
import com.project.movie.repository.BookingRepository;
import com.project.movie.service.BookingServices;
import com.project.movie.service.ShowtimeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BookingServicesImpl implements BookingServices {
	
	@Autowired
    private BookingRepository bookingRepository;
	
	@Autowired
	private ShowtimeServices showtimeServices;

	@Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

	@Override
    public Booking getBookingById(int id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
    @Transactional
    public Booking saveBooking(Booking booking) {
        if (booking.getNumberOfTickets() <= 0) {
            throw new IllegalArgumentException("Number of tickets must be greater than zero");
        }

		Showtime showtime = showtimeServices.getShowtimeById(booking.getShowtimeId());
        if (showtime.getSeatsLeft() < booking.getNumberOfTickets()) {
            throw new IllegalStateException("Not enough seats available");
        }

        showtime.setSeatsLeft(showtime.getSeatsLeft() - booking.getNumberOfTickets());
		showtimeServices.updateShowtime(showtime.getShowtimeId(), showtime);
        return bookingRepository.save(booking);
    }

	@Override
    public Booking updateBooking(int id, Booking bookingDetails) {
        Booking booking = getBookingById(id);
        booking.setBookingDate(bookingDetails.getBookingDate());
        booking.setNumberOfTickets(bookingDetails.getNumberOfTickets());
        booking.setTotalPrice(bookingDetails.getTotalPrice());
        booking.setUser(bookingDetails.getUser());
        booking.setShowtime(bookingDetails.getShowtime());
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void deleteBooking(int id) {
        Booking booking = getBookingById(id);
        Showtime showtime = showtimeServices.getShowtimeById(booking.getShowtimeId());
        showtime.setSeatsLeft(showtime.getSeatsLeft() + booking.getNumberOfTickets());
        showtimeServices.updateShowtime(showtime.getShowtimeId(), showtime);
        bookingRepository.delete(booking);
    }
    
}
