package com.project.movie.service.impl;


import com.project.movie.dto.UserLoginDto;
import com.project.movie.dto.UserRegistrationDto;
import com.project.movie.dto.BookingResponseDto;
import com.project.movie.dto.UserBookingsResponseDto;
import com.project.movie.entity.Booking;
import com.project.movie.entity.User;
import com.project.movie.repository.BookingRepository;
import com.project.movie.exceptions.ResourceNotFoundException;
import com.project.movie.repository.UserRepository;
import com.project.movie.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        // Check if the user already exists
        if (userRepository.findByUsernameOrEmail(userRegistrationDto.getUsername(), userRegistrationDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email or username already exists");
        }

        // Create a new User entity and populate its fields
        User user = new User();

        // Set and encode the password
        if (userRegistrationDto.getPassword() != null && !userRegistrationDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        } else {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        user.setUsername(userRegistrationDto.getUsername());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setRole(userRegistrationDto.getRole());
        user.setLastName(userRegistrationDto.getLastName());

        return userRepository.save(user);
    }


    @Override
    public User loginUser(UserLoginDto userLoginDto) {
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(userLoginDto.getUsernameOrEmail(), userLoginDto.getUsernameOrEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
                return user; // Login successful
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } 
        else {
            throw new RuntimeException("User not found with username or email: " + userLoginDto.getUsernameOrEmail());
        }
    }

    @Override
    public List<User> getAllUsers(){
    	return userRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public UserBookingsResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        List<BookingResponseDto> bookings = bookingRepository.findAllByUser_UserId(user.getUserId())
                .stream()
                .map(this::toBookingResponse)
                .toList();

        return new UserBookingsResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                bookings);
    }

    private BookingResponseDto toBookingResponse(Booking booking) {
        return new BookingResponseDto(
                booking.getBookingId(),
                booking.getBookingDate(),
                booking.getNumberOfTickets(),
                booking.getTotalPrice(),
                booking.getShowtimeId());
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User updateUser(int id, User newUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existingUser.setUsername(newUser.getUsername());
        existingUser.setPassword(passwordEncoder.encode(newUser.getPassword())); // Encrypt the new password
        existingUser.setEmail(newUser.getEmail());
        existingUser.setRole(newUser.getRole());
        existingUser.setFirstName(newUser.getFirstName());
        existingUser.setLastName(newUser.getLastName());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.deleteById(id);
    }


	@Override
	public User updatePassword(String usernameOrEmail, String newPassword) {
		Optional<User> userOpt = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			user.setPassword(passwordEncoder.encode(newPassword));
			User updatedUser = userRepository.save(user);
			return updatedUser;
		}
		throw new ResourceNotFoundException("Username doesn't exist! Register yourself first");
	}
	
}
