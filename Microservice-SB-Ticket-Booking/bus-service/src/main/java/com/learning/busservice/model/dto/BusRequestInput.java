package com.learning.busservice.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusRequestInput {
    private String busName;
    private String busCityFrom;
    private String busCityTo;
    @Column(unique = true)
    private String busNumber;
    private Integer busTotalSeats;
    private Integer busTicketPrice;
    private String busDepartureTime;
    private String busArrivalTime;
}
