package com.project.movie.controller;

import com.project.movie.entity.Showtime;
import com.project.movie.service.ShowtimeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/showtimes")
@CrossOrigin(origins = "http://localhost:3000")
public class ShowtimeController {

	@Autowired
	private ShowtimeServices showtimeService;

	@GetMapping
	public ResponseEntity<List<Showtime>> getAllShowtimes() {
		return ResponseEntity.ok(showtimeService.getAllShowtimes());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Showtime> getShowtimeById(@PathVariable Integer id) {
		Showtime showtime = showtimeService.getShowtimeById(id);
		return ResponseEntity.ok(showtime);
	}

//	@PostMapping
//	public ResponseEntity<String> createShowtime(@RequestBody Showtime showtime) {
//		Showtime savedShowtime = showtimeService.saveShowtime(showtime);
//		return new ResponseEntity<String>("Showtime saved with ID: " + savedShowtime.getShowtimeId(),HttpStatus.CREATED);
//	}
	
	@PostMapping
	public ResponseEntity<Showtime> createShowtime(@RequestBody Showtime showtime) {
		System.out.println("new Showtime"+showtime);
		Showtime savedShowtime = showtimeService.saveShowtime(showtime);
		return new ResponseEntity<Showtime>(savedShowtime,HttpStatus.CREATED);
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<String> updateShowtime(@PathVariable Integer id, @RequestBody Showtime showtimeDetails) {
//		Showtime updatedShowtime = showtimeService.updateShowtime(id, showtimeDetails);
//		return new ResponseEntity<String>("Showtime with ID: " + updatedShowtime.getShowtimeId()+ " Updated!",HttpStatus.OK);
//	}

	@PutMapping("/{id}")
	public ResponseEntity<Showtime> updateShowtime(@PathVariable Integer id, @RequestBody Showtime showtimeDetails) {
		Showtime updatedShowtime = showtimeService.updateShowtime(id, showtimeDetails);
		return new ResponseEntity<Showtime>(updatedShowtime,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteShowtime(@PathVariable Integer id) {
		showtimeService.deleteShowtime(id);
		return new ResponseEntity<String>("Showtime with ID: " + id + " Deleted!",HttpStatus.OK);
	}
	
//	//to get all the showtimes of a movie by ID
//	@GetMapping("/movies/{movie_id}")
//	public ResponseEntity<List<Showtime>> getAllShowtimesByMovieId(@PathVariable Integer movie_id){
//		return ResponseEntity.ok(showtimeService.getAllShowtimesByMovieId(movie_id));
//	}
//	
//	//to get all the showtimes in a theater by ID
//	@GetMapping("/theaters/{theater_id}")
//	public ResponseEntity<List<Showtime>> getAllShowtimesByTheaterId(@PathVariable Integer theater_id){
//		return ResponseEntity.ok(showtimeService.getAllShowtimesByTheaterId(theater_id));
//	}

}
