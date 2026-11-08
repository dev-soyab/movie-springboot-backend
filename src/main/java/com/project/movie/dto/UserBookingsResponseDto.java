package com.project.movie.dto;

import java.util.List;

public record UserBookingsResponseDto(
        Integer userId,
        String username,
        String email,
        String firstName,
        String lastName,
        String role,
        List<BookingResponseDto> bookings) {
}
