package com.project.movie.service.impl;

import com.project.movie.entity.Movie;
import com.project.movie.exceptions.ResourceNotFoundException;
import com.project.movie.repository.MovieRepository;
import com.project.movie.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServicesImpl implements MovieServices{
	
	@Autowired
    private MovieRepository movieRepository;

	@Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
	
	@Override
	public Movie updateMovie(int id,Movie movie) {
		Optional<Movie> movieOpt = movieRepository.findById(id);
		if(movieOpt.isPresent()) {
			Movie curMovie = movieOpt.get();
			curMovie.setDescription(movie.getDescription());
			curMovie.setDuration(movie.getDuration());
			curMovie.setGenre(movie.getGenre());
			curMovie.setRating(movie.getRating());
			curMovie.setTitle(movie.getTitle());
			curMovie.setShowtimes(movie.getShowtimes());
			
			return movieRepository.save(curMovie);
		}
			
		else
			throw new ResourceNotFoundException("No matching movie found with ID: "+id);
	}
	
	@Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

	@Override
    public Movie getMovieById(Integer id) {
		Optional<Movie> opt = movieRepository.findById(id);
		if(opt.isPresent())
			return opt.get();
		else
			throw new ResourceNotFoundException("No matching movie found with ID: "+id);
    }



	@Override
    public void deleteMovie(Integer id) {
		Optional<Movie> opt = movieRepository.findById(id);
		if(opt.isPresent()) {
			movieRepository.deleteById(id);
		}
		else
			throw new ResourceNotFoundException("No matching movie found with ID: "+id);     
    }
	
}
