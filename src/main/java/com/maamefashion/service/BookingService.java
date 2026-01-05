package com.maamefashion.service;

import com.maamefashion.dto.BookingRequest;
import com.maamefashion.dto.BookingResponse;
import com.maamefashion.model.Booking;
import com.maamefashion.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        Booking booking = Booking.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .appointmentType(request.getAppointmentType())
                .preferredDate(request.getPreferredDate())
                .message(request.getMessage())
                .status(Booking.Status.PENDING)
                .build();

        booking = bookingRepository.save(booking);
        return toResponse(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .status(booking.getStatus().name().toLowerCase())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}