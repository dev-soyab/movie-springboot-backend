package com.project.movie.controller;

import com.project.movie.entity.Theater;
import com.project.movie.service.TheaterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/theaters")
@CrossOrigin(origins = "http://localhost:3000")
public class TheaterController {

	@Autowired
    private TheaterServices theaterService;


    @GetMapping
    public List<Theater> getAllTheaters() {
        return theaterService.getAllTheaters();
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheaterById(@PathVariable Integer id) {
    	System.out.println("Request with ID in get:" + id);
        Theater theater = theaterService.getTheaterById(id);
        return ResponseEntity.ok(theater);
    }

   
    @PostMapping
    public ResponseEntity<String> createTheater(@RequestBody Theater theater) {
    	Theater savedTheater = theaterService.saveTheater(theater);
        return new ResponseEntity<String>("Theater saved with ID: " + savedTheater.getTheaterId(),HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTheater(@PathVariable Integer id, @RequestBody Theater theaterDetails) {
    	
        Theater updatedTheater = theaterService.updateTheater(id, theaterDetails);
        return new ResponseEntity<String>("Theater with ID: " + id + " Updated!",HttpStatus.OK);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTheater(@PathVariable Integer id) {
        theaterService.deleteTheater(id);
        return new ResponseEntity<String>("Theater with ID: " + id + " Deleted!",HttpStatus.OK);
    }
    
    @GetMapping("/{id}/movies")
    public ResponseEntity<List<Integer>> getMovies(@PathVariable Integer id){
    	List<Integer> movieIds = theaterService.getMoviesById(id);
    	return ResponseEntity.status(HttpStatus.OK).body(movieIds);
    }
}
