package com.project.movie.service;

import com.project.movie.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MovieServices {

	List<Movie> getAllMovies();

	Movie getMovieById(Integer id);

	Movie saveMovie(Movie movie);

	void deleteMovie(Integer id);

	Movie updateMovie(int id, Movie movie);

}
