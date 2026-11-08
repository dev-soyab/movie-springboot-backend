package com.project.movie.service.impl;

import com.project.movie.entity.Theater;
import com.project.movie.exceptions.ResourceNotFoundException;
import com.project.movie.repository.MovieRepository;
import com.project.movie.repository.TheaterRepository;
import com.project.movie.service.MovieServices;
import com.project.movie.service.TheaterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class TheaterServicesImpl implements TheaterServices {
	
	@Autowired
    private TheaterRepository theaterRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieServices movieService;

	@Override
    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

	@Override
    public Theater getTheaterById(Integer id) {
        return theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + id));
    }

	@Override
    public Theater saveTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

	@Override
    public Theater updateTheater(Integer id, Theater theaterDetails) {
        Theater theater = getTheaterById(id);
        theater.setName(theaterDetails.getName());
        theater.setLocation(theaterDetails.getLocation());
        theater.setCapacity(theaterDetails.getCapacity());
        theater.setShowtimes(theater.getShowtimes());
        return theaterRepository.save(theater);
    }

	@Override
    public void deleteTheater(Integer id) {
        Theater theater = getTheaterById(id);
        theaterRepository.delete(theater);
    }

	@Override
	public List<Integer> getMoviesById(Integer id) {
		Theater theater = getTheaterById(id);
		HashSet<Integer> movieSet = new HashSet<>();
		theater.getShowtimes().forEach(showtime -> {
			if(showtime.getMovieId()!=null)
				movieSet.add(showtime.getMovieId());
		});
		
		ArrayList<Integer> movieIds = new ArrayList<>(movieSet);
		System.out.println(movieIds);
		return movieIds;
//		System.out.println(movieRepository.findById(1).get());
//		List<Movie> movies = new ArrayList();
//		movieIds.forEach(movieId -> {
//			movies.add(movieService.getMovieById(movieId));
//		});
//		System.out.println(movies);
//		return movies;
	}
}
