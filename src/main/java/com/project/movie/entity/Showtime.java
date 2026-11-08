package com.project.movie.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.movie.entity.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="SHOWTIMES_DTLS")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(includeFieldNames = false, exclude = "movie")
public class Showtime {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Integer showtimeId;

    @Column(name = "show_date")
    private LocalDate showDate;

    @Column(name = "show_time")
    private LocalTime showTime;
    
    @Column(name = "seats_left")
    private Integer seatsLeft;

    @JsonBackReference("movie-showtime") // Child side
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @JsonBackReference("theater-showtime") // Child side
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @JsonIgnore
    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    // Custom Getters
    @JsonProperty("movieId")
    public Integer getMovieId() {
        return movie != null ? movie.getMovieId() : null;
    }

    @JsonProperty("theaterId")
    public Integer getTheaterId() {
        return theater != null ? theater.getTheaterId() : null;
    }
}
