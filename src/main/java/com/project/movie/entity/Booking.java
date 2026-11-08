package com.project.movie.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.movie.entity.Showtime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

//
@Entity
@Table(name="BOOKINGS_DTLS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "booking_id",columnDefinition = "INTEGER")
	    private Integer bookingId;

	    @Column(name = "booking_date")
	    private LocalDate bookingDate;

	    @Column(name = "number_of_tickets")
	    private int numberOfTickets;

	    @Column(name = "total_price")
	    private double totalPrice;

	    @ToString.Exclude
	    @JsonBackReference("user-booking") // Child side
	    @ManyToOne(fetch = FetchType.LAZY) // No cascading here
	    @JoinColumn(name = "user_id", referencedColumnName = "user_id",columnDefinition = "INTEGER")
	    private User user;

	    @ToString.Exclude
	    @JsonBackReference("showtime-booking") // Child side
	    @ManyToOne(fetch = FetchType.LAZY) // No cascading here
	    @JoinColumn(name = "showtime_id", referencedColumnName = "showtime_id")
	    private Showtime showtime;

	    // Custom Getters
	    @JsonProperty("userId")
	    public Integer getUserId() {
	        return user != null ? user.getUserId() : null;
	    }

	    @JsonProperty("showtimeId")
	    public Integer getShowtimeId() {
	        return showtime != null ? showtime.getShowtimeId() : null;
	    }
}



