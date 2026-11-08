package com.project.movie.service;

import com.project.movie.entity.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingServices {

	List<Booking> getAllBookings();

	Booking getBookingById(int id);

	Booking saveBooking(Booking booking);

	Booking updateBooking(int id, Booking bookingDetails);

	void deleteBooking(int id);

}
