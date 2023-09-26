package com.learning.ticketservice.controller;

import com.learning.ticketservice.model.Ticket;
import com.learning.ticketservice.model.dto.BusDetailsInput;

import com.learning.ticketservice.model.dto.UserDetailsForTicketInput;
import com.learning.ticketservice.service.TicketService;
import com.learning.ticketservice.service.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {

   private RestTemplate restTemplate;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    JwtUtil jwtUtil;

   @Autowired
    TicketService ticketService;

    @PostMapping("/new")
    public String createTicket (@RequestHeader("token") String token ,@RequestParam("busNumber") String busNumber ){


        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String email = jwtUtil.extractEmail(token);

        BusDetailsInput busDetailsInput = webClientBuilder.build().get()
                .uri("http://bus-service/api/bus/detail?busNumber="+ busNumber)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .header("token",token)
                .retrieve()
                .bodyToMono(BusDetailsInput.class)
                .block();

        if(busDetailsInput==null){
            return "Invalid Bus number please try again !!!";
        }


        UserDetailsForTicketInput userDetailsForTicket = webClientBuilder.build().get()
                .uri("http://user-service/api/user/info/ticket")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .header("token",token)
                .retrieve()
                .bodyToMono(UserDetailsForTicketInput.class)
                .block();

        if(userDetailsForTicket==null){
            return "User Details not Found please try again with correct details !!!";
        }

        return ticketService.createTicket(userDetailsForTicket,busDetailsInput);


    }


  @GetMapping("all")
    public List<Ticket> getAllTickets (@RequestHeader("token")String token){
      webClientBuilder.build().get()
              .uri("http://identity-service/auth/validate?token="+token)
              .retrieve()
              .bodyToMono(String.class)
              .block();
        return ticketService.getAllTicket();
  }





}
