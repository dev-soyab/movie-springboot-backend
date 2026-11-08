package com.project.movie.service;

import com.project.movie.dto.UserLoginDto;
import com.project.movie.dto.UserRegistrationDto;
import com.project.movie.dto.UserBookingsResponseDto;
import com.project.movie.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices {

	UserBookingsResponseDto getUserByUsername(String username);

	User registerUser(UserRegistrationDto userRegistrationDto);

	User loginUser(UserLoginDto userLoginDto);

	User getUserById(Integer id);

	User updateUser(int id, User newUser);

	void deleteUser(int id);

	List<User> getAllUsers();

	User updatePassword(String usernameOrEmail, String newPassword);
	
}
