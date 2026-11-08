package com.project.movie.controller;

import com.project.movie.entity.Movie;
import com.project.movie.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    @Autowired
    private MovieServices movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
    	return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<String> createMovie(@RequestBody Movie movie) {
    	Movie savedMovie = movieService.saveMovie(movie);
        return new ResponseEntity<String>("Movie saved with ID: " + savedMovie.getMovieId(),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
    	Movie updatedMovie = movieService.updateMovie(id, movie);
    	return new ResponseEntity<String>("Movie with ID: " + id + " Updated!",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable int id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<String>("Movie with ID: "+id+" Deleted!", HttpStatus.OK);
    }
    
}
