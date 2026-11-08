package com.project.movie.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.movie.entity.Showtime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="THEATERS_DTLS")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(includeFieldNames=false,exclude="showtimes")
public class Theater {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="theater_id")
    private Integer theaterId;
	
    private String name;
    
    private String location;
    
    private Integer capacity;

    @JsonManagedReference("theater-showtime")   //parent side
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Showtime> showtimes;
    
}
