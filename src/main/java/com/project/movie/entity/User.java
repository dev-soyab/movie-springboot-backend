package com.project.movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.movie.entity.Booking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "USERS_DTLS")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(includeFieldNames=false,exclude="bookings")
public class User {


	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",columnDefinition = "INTEGER")
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false)
    private String role; // ROLE_USER, ROLE_ADMIN, etc.

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Booking> bookings;

}
