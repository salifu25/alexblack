package com.maamefashion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String appointmentType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate preferredDate;
    private String message;
}