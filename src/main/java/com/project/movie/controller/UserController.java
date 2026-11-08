package com.project.movie.controller;

import com.project.movie.dto.UserLoginDto;
import com.project.movie.dto.UserRegistrationDto;
import com.project.movie.dto.UserBookingsResponseDto;
import com.project.movie.entity.User;
import com.project.movie.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
    private UserServices userService;

	

	@PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect validation error messages and return a bad request response
            String errors = bindingResult.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            userService.registerUser(userRegistrationDTO);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        String errors = bindingResult.getAllErrors()
	                .stream()
	                .map(e -> e.getDefaultMessage())
	                .collect(Collectors.joining(", "));
	        Map<String, String> response = new HashMap<>();
	        response.put("error", errors);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    try {
	        User user = userService.loginUser(userLoginDto);
	        Map<String, String> response = new HashMap<>();
	        // Add redirect URL based on the user's role
	        if (user.getRole().equalsIgnoreCase("Admin")) {
	            response.put("redirectUrl", "/admin-dashboard");
	        } else {
	            response.put("redirectUrl", "/user-dashboard");
	        }
	        // Example: Add a token if needed
//	        response.put("token", "true");  // Assume you have JWT-based auth
	        response.put("userId", Integer.toString(user.getUserId()));
	        response.put("username", user.getUsername());
	        response.put("firstName", user.getFirstName());
	        response.put("lastName", user.getLastName());
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (RuntimeException e) {
	        Map<String, String> response = new HashMap<>();
	        response.put("error", e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	    }
	}

	@PostMapping("/{usernameOrEmail}")
	public ResponseEntity<String> forgotPassword(@PathVariable String usernameOrEmail,@RequestParam String newPassword) {
		System.out.println("username: " +usernameOrEmail + " newPassword: "+newPassword);
		User updatedUser = userService.updatePassword(usernameOrEmail,newPassword);
		
		return ResponseEntity.ok("Password Updated successfully");
	}
	
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser(){
    	return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<UserBookingsResponseDto> getUserByUsername(@PathVariable String username) {
        UserBookingsResponseDto user = userService.getUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok("User Updated with ID: " + id);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User Deleted with ID: " + id);
    }
}
