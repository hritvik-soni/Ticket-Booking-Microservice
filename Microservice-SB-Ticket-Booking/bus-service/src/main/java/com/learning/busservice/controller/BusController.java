package com.learning.busservice.controller;

import com.learning.busservice.config.WebClientConfig;
import com.learning.busservice.model.Bus;
import com.learning.busservice.model.dto.BusDetailsForTicket;
import com.learning.busservice.model.dto.BusOppRequestInput;
import com.learning.busservice.model.dto.BusRequestInput;
import com.learning.busservice.model.dto.BusRequestOutput;
import com.learning.busservice.service.BusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus")
@Validated
public class BusController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    BusService busService;



    @Autowired
    private PasswordEncoder passwordEncoder;

    private final WebClient.Builder webClientBuilder;


    @PostMapping("/new")
    public String createBus(@Valid @RequestBody BusRequestInput busRequestInput, @RequestParam("email") String email
                                    ,@RequestParam("password") String userPassword){
        userPassword = passwordEncoder.encode(userPassword);
        if(email.endsWith("@bus.com")){

            BusOppRequestInput oppDetails = webClientBuilder.build().get()
                    .uri("http://user-service/api/user/info/bus?email="+email+"&password="+userPassword)
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
    @GetMapping("/search")
    public List<BusRequestOutput> searchBus(@RequestParam String cityFrom, @RequestParam String cityTo){
        return busService.searchBus(cityFrom,cityTo);
    }

    @GetMapping("/busIsValid")
    public boolean busIsValid(@RequestParam String busNumber){
        return busService.busIsValid(busNumber);
    }

    @GetMapping("/detail")
    public BusDetailsForTicket detailBus(@RequestParam("busNumber") String busNumber){
        return busService.detailBus(busNumber);
    }
    @GetMapping("/all")
    public List<Bus> getAllBus(){
        return busService.getAllBus();
    }
    @DeleteMapping("/remove")
    public String deleteBus(@RequestParam("busNumber") String busNumber,@RequestParam("email") String email,
                            @RequestParam("password") String userPassword){
        userPassword = passwordEncoder.encode(userPassword);
        if(email.endsWith("@bus.com")){

//           BusOppRequestInput oppDetails =
//                    restTemplate.getForObject("http://user-service/api/user/info/bus?email=bus1@bus.com",
//                    BusOppRequestInput.class);

            boolean isVerified = Boolean.TRUE.equals(webClientBuilder.build().get()
                    .uri("http://user-service/api/user/info/bus/isVerified?email=" + email+"&password="+userPassword)
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block());


            if(!isVerified){
                return "Bus operator not found or Invalid credentials!!! ";
            }
            return busService.removeBus(busNumber,email);

        }

        return "Only bus operator can remove bus!!!";

    }

}
