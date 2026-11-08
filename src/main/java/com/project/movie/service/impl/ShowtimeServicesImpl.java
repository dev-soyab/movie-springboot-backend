package com.project.movie.service.impl;

import com.project.movie.entity.Showtime;
import com.project.movie.entity.Theater;
import com.project.movie.exceptions.ResourceNotFoundException;
import com.project.movie.repository.ShowtimeRepository;
import com.project.movie.service.ShowtimeServices;
import com.project.movie.service.TheaterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShowtimeServicesImpl implements ShowtimeServices {
	
	@Autowired
    private ShowtimeRepository showtimeRepository;
	
	@Autowired
    private TheaterServices theaterServices;

	@Override
    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

	@Override
    public Showtime getShowtimeById(Integer id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with id: " + id));
    }

	@Override
    public Showtime saveShowtime(Showtime showtime) {
		Theater theater = theaterServices.getTheaterById(showtime.getTheaterId());
		showtime.setSeatsLeft(theater.getCapacity());
        return showtimeRepository.save(showtime);
    }

	@Override
    public Showtime updateShowtime(Integer id, Showtime showtimeDetails) {
        Showtime showtime = getShowtimeById(id);
        showtime.setMovie(showtimeDetails.getMovie());
        showtime.setTheater(showtimeDetails.getTheater());
        showtime.setShowDate(showtimeDetails.getShowDate());
        showtime.setShowTime(showtimeDetails.getShowTime());
        showtime.setBookings(showtimeDetails.getBookings());
        return showtimeRepository.save(showtime);
    }

	@Override
    public void deleteShowtime(Integer id) {
        Showtime showtime = getShowtimeById(id);
        showtimeRepository.delete(showtime);
    }


}
