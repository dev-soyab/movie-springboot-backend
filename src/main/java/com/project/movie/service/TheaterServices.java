package com.project.movie.service;

import com.project.movie.entity.Theater;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TheaterServices {

	void deleteTheater(Integer id);

	Theater updateTheater(Integer id, Theater theaterDetails);

	Theater saveTheater(Theater theater);

	Theater getTheaterById(Integer id);

	List<Theater> getAllTheaters();

	List<Integer> getMoviesById(Integer id);

}
