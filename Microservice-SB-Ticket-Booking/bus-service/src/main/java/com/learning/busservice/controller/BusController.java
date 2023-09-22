package com.learning.busservice.controller;

import com.learning.busservice.config.WebClientConfig;
import com.learning.busservice.model.dto.BusDetailsForTicket;
import com.learning.busservice.model.dto.BusOppRequestInput;
import com.learning.busservice.model.dto.BusRequestInput;
import com.learning.busservice.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus")
public class BusController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    BusService busService;

    private final WebClient.Builder webClientBuilder;


    @PostMapping("/new")
    public String createBus(@RequestBody BusRequestInput busRequestInput, @RequestParam("email") String email){
        System.out.println("user-service/api/user/info/bus?email="+email);
//        String uri = "user-service/api/user/info/bus" ;
        if(email.endsWith("@bus.com")){

//           BusOppRequestInput oppDetails =
//                    restTemplate.getForObject("http://user-service/api/user/info/bus?email=bus1@bus.com",
//                    BusOppRequestInput.class);

            BusOppRequestInput oppDetails = webClientBuilder.build().get()
                    .uri("http://user-service/api/user/info/bus?email="+email)
                    .retrieve()
                    .bodyToMono(BusOppRequestInput.class)
                    .block();

            if(oppDetails==null){
                return "Bus operator not found cannot create bus service";
            }
            return busService.createBus(busRequestInput,oppDetails);

        }

        return "Only bus operator can create bus!!!";
    }
    @GetMapping("/search/bus")
    public String searchBus(@RequestParam String cityFrom,@RequestParam String cityTo){
        return busService.searchBus(cityFrom,cityTo);
    }

//    @GetMapping("/busIsValid")
//    public boolean busIsValid"(@RequestParam String busNumber){
//        return busService.busIsValid(busNumber);
//    }

    @GetMapping("/detail")
    public BusDetailsForTicket detailBus(@RequestParam("busNumber") String busNumber){
        return busService.detailBus(busNumber);
    }
}
