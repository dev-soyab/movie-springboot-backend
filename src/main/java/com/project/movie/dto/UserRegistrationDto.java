package com.project.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    private String username;


    private String password;

  
    private String email;

    
    private String role;

    
    private String firstName;

   
    private String lastName;
}
