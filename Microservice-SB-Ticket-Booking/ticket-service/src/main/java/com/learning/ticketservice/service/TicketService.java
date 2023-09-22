package com.learning.ticketservice.service;

import com.learning.ticketservice.model.Ticket;
import com.learning.ticketservice.model.dto.BusDetailsInput;
import com.learning.ticketservice.model.dto.UserDetailsForTicketInput;
import com.learning.ticketservice.repository.ITicketRepo;
import com.learning.ticketservice.service.utility.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service

public class TicketService {

    @Autowired
    ITicketRepo ticketRepo;


    public String createTicket(UserDetailsForTicketInput userDetailsForTicket, BusDetailsInput busDetailsInput) {

        Ticket newTicket = Ticket.builder()
                .ticketNumber(UUID.randomUUID().toString())
                .busCityFrom(busDetailsInput.getBusCityFrom())
                .busCityTo(busDetailsInput.getBusCityTo())
                .busNumber(busDetailsInput.getBusNumber())
                .busName(busDetailsInput.getBusName())
                .busSeatNumber(AppUtils.generateRandomSeatNo(busDetailsInput.getBusTotalSeats()))
                .busOppNumber(busDetailsInput.getBusOppNumber())
                .busTicketPrice(busDetailsInput.getBusTicketPrice())
                .busDepartureTime(busDetailsInput.getBusDepartureTime())
                .busArrivalTime(busDetailsInput.getBusArrivalTime())

                .userName(userDetailsForTicket.getUserName())
                .userEmail(userDetailsForTicket.getUserEmail())
                .userMobileNumber(userDetailsForTicket.getUserMobileNumber())
                .userAge(userDetailsForTicket.getUserAge())
                .userGender(userDetailsForTicket.getGender())

                .build();

        Ticket savedTicket = ticketRepo.save(newTicket);

        return " Your Ticket is Booked Successfully : " + savedTicket.getTicketNumber() +" at : " + savedTicket .getCreatedAt() ;
    }

    public List<Ticket> getAllTicket() {

        return ticketRepo.findAll();
    }
}
