package com.project.movie.dto;

import java.time.LocalDate;

public record BookingResponseDto(
        Integer bookingId,
        LocalDate bookingDate,
        int numberOfTickets,
        double totalPrice,
        Integer showtimeId) {
}
