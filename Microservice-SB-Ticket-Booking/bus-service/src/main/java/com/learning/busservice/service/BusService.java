package com.learning.busservice.service;

import com.learning.busservice.model.Bus;
import com.learning.busservice.model.dto.BusDetailsForTicket;
import com.learning.busservice.model.dto.BusOppRequestInput;
import com.learning.busservice.model.dto.BusRequestInput;
import com.learning.busservice.repository.IBusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class BusService {
    @Autowired
    IBusRepo busRepo;

    public String createBus(BusRequestInput busRequestInput, BusOppRequestInput oppDetails) {
        Bus newBus = Bus.builder()

                .busName(busRequestInput.getBusName())
                .busCityFrom(busRequestInput.getBusCityFrom())
                .busCityTo(busRequestInput.getBusCityTo())
                .busNumber(busRequestInput.getBusNumber())
                .busTotalSeats(busRequestInput.getBusTotalSeats())
                .busTicketPrice(busRequestInput.getBusTicketPrice())
                .busDepartureTime(busRequestInput.getBusDepartureTime())
                .busArrivalTime(busRequestInput.getBusArrivalTime())

                .busOppNumber(oppDetails.getBusOppNumber())
                .busOppName(oppDetails.getBusOppName())
                .busOppEmail(oppDetails.getBusOppEmail())

                .build();
        Bus savedBus = busRepo.save(newBus);
        return "A new Bus service is created : "+ savedBus.getBusNumber();
    }

    public String searchBus(String cityFrom, String cityTo) {

        return "empty";
    }

    public boolean busIsValid(String busNumber) {
        return busRepo.findByBusNumber(busNumber) != null;
    }


    public BusDetailsForTicket detailBus(String busNumber) {

      Bus currBus = busRepo.findByBusNumber(busNumber);

        if(currBus==null){
            return null;
        }
        return BusDetailsForTicket.builder()
                .busCityFrom(currBus.getBusCityFrom())
                .busCityTo(currBus.getBusCityTo())
                .busNumber(currBus.getBusNumber())
                .busName(currBus.getBusName())
                .busTotalSeats(currBus.getBusTotalSeats())
                .busOppNumber(currBus.getBusOppNumber())
                .busTicketPrice(currBus.getBusTicketPrice())
                .busDepartureTime(currBus.getBusDepartureTime())
                .busArrivalTime(currBus.getBusArrivalTime())

                .build();
    }
}
