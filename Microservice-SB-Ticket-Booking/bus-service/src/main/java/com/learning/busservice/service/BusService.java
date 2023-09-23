package com.learning.busservice.service;

import com.learning.busservice.model.Bus;
import com.learning.busservice.model.dto.BusDetailsForTicket;
import com.learning.busservice.model.dto.BusOppRequestInput;
import com.learning.busservice.model.dto.BusRequestInput;
import com.learning.busservice.model.dto.BusRequestOutput;
import com.learning.busservice.repository.IBusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    /**
     *
     * @param cityFrom
     * @param cityTo
     * @return
     */

    public List<BusRequestOutput> searchBus(String cityFrom, String cityTo) {



        List<Bus> busList = busRepo.findAll()
                .stream()
                .filter(busInfo -> busInfo.getBusCityFrom().equals(cityFrom))
                .filter(busInfo -> busInfo.getBusCityTo().equals(cityTo))
                .toList();
        List<BusRequestOutput> outputList = new ArrayList<>();

        for (Bus currBus : busList) {

            BusRequestOutput ans = BusRequestOutput.builder()
                    .busName(currBus.getBusName())
                    .busCityFrom(currBus.getBusCityFrom())
                    .busCityTo(currBus.getBusCityTo())
                    .busTicketPrice(currBus.getBusTicketPrice())
                    .busDepartureTime(currBus.getBusDepartureTime())
                    .busArrivalTime(currBus.getBusArrivalTime())
                    .build();

            outputList.add(ans);

        }
        if(outputList.isEmpty()){
            throw new RuntimeException("No bus for this route");
        }
        return outputList;

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
    public List<Bus> getAllBus() {
        return busRepo.findAll();
    }

    public String removeBus(String busNumber, String email) {
        Bus currBus = busRepo.findByBusNumber(busNumber);
        String busOppEmail = currBus.getBusOppEmail();
        if(busOppEmail.equals(email)){
            busRepo.delete(currBus);
            return "Bus Removed Successfully !!! ";
        }
        return "Only Valid bus Operator can remove Bus.!!!!";
    }



}
