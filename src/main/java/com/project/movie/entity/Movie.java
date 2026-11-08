package com.project.movie.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="MOVIES_DTLS")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(includeFieldNames=false,exclude="showtimes")
public class Movie {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="movie_id")
    private Integer movieId;
	
    private String title;
    
    private String description;
    
    private String genre;
    
    @Column(name="duration_in_mins")
    private Integer duration;
    
    private Double rating;

    @JsonManagedReference("movie-showtime")     //parent side
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Showtime> showtimes;
    
}
