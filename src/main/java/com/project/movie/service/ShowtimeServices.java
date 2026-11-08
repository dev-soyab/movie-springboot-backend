package com.project.movie.service;

import com.project.movie.entity.Showtime;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ShowtimeServices {

	void deleteShowtime(Integer id);

	Showtime updateShowtime(Integer id, Showtime showtimeDetails);

	Showtime saveShowtime(Showtime showtime);

	Showtime getShowtimeById(Integer id);

	List<Showtime> getAllShowtimes();

}
