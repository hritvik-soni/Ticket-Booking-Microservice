package com.learning.busservice.controller;

import com.learning.busservice.config.WebClientConfig;
import com.learning.busservice.model.Bus;
import com.learning.busservice.model.dto.BusDetailsForTicket;
import com.learning.busservice.model.dto.BusOppRequestInput;
import com.learning.busservice.model.dto.BusRequestInput;
import com.learning.busservice.model.dto.BusRequestOutput;
import com.learning.busservice.service.BusService;
import com.learning.busservice.service.util.JwtUtil;
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
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    JwtUtil jwtUtil;
    private final WebClient.Builder webClientBuilder;


    @PostMapping("/new")
    public String createBus(@Valid @RequestBody BusRequestInput busRequestInput, @RequestHeader("token") String token){

        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String email = jwtUtil.extractEmail(token);
        System.out.println(email);

        if(email.endsWith("@bus.com")){

           BusOppRequestInput oppDetails = webClientBuilder.build().get()
                    .uri("http://user-service/api/user/info/bus")
                    .headers(headers -> headers.setBearerAuth(token))
                    .header("token",token)
                    .retrieve()
                    .bodyToMono(BusOppRequestInput.class).block();

            return busService.createBus(busRequestInput, oppDetails);

        }

        return "Only bus operator can create bus!!!";
    }
    @GetMapping("/search")
    public List<BusRequestOutput> searchBus(@RequestHeader("token") String token ,@RequestParam String cityFrom, @RequestParam String cityTo){

        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return busService.searchBus(cityFrom,cityTo);
    }

    @GetMapping("/busIsValid")
    public boolean busIsValid(@RequestHeader("token") String token,@RequestParam String busNumber){
        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return busService.busIsValid(busNumber);
    }

    @GetMapping("/detail")
    public BusDetailsForTicket detailBus(@RequestParam("busNumber") String busNumber,@RequestHeader("token")String token){

        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
//        String email = jwtUtil.extractEmail(token);
        return busService.detailBus(busNumber);
    }
    @GetMapping("/all")
    public List<Bus> getAllBus(@RequestHeader("token") String token){
        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
//        String email = jwtUtil.extractEmail(token);
        return busService.getAllBus();
    }
    @DeleteMapping("/remove")
    public String deleteBus(@RequestParam("busNumber") String busNumber,@RequestHeader String token){

        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String email = jwtUtil.extractEmail(token);

        if(email.endsWith("@bus.com")){

            boolean isVerified = Boolean.TRUE.equals(webClientBuilder.build().get()
                    .uri("http://user-service/api/user/info/bus/isVerified" )
                    .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                    .header("token",token)
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
