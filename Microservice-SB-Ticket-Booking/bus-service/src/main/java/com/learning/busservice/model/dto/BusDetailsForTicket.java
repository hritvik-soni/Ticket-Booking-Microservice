package com.learning.busservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BusDetailsForTicket {

    private String busCityFrom;
    private String busCityTo;
    private String busNumber;
    private String busName;
    private Integer busTotalSeats;
    private String busOppNumber;
    private Integer busTicketPrice;
    private String busDepartureTime;
    private String busArrivalTime;

}
