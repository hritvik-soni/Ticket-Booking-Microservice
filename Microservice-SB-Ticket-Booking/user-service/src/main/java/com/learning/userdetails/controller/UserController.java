package com.learning.userdetails.controller;

import com.learning.userdetails.model.Users;
import com.learning.userdetails.model.dto.*;
import com.learning.userdetails.service.UserService;
import com.learning.userdetails.service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/new")
    public String createUser(@RequestBody UserRequestInput userRequestInput){

        return userService.createUser(userRequestInput);
    }
    @GetMapping("/all")
    public List<Users> getAllUser(){
        return userService.getAllUsers();
    }
    @GetMapping("/info")
    public UserRequestOutput getUserInfo(@RequestHeader("token") String token){
        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String email = jwtUtil.extractEmail(token);
        return userService.getUserInfo(email);
    }

    @DeleteMapping("/remove")
    public String removeUser(@RequestHeader("token") String token ){

        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String email = jwtUtil.extractEmail(token);

        return userService.removeUser(email,token);
    }

    @PutMapping("/update")
    public String updateUser(@RequestHeader("token") String token,
                             @RequestBody UserUpdateRequestInput updateRequestInput){
        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String email = jwtUtil.extractEmail(token);

        return userService.updateUser(email,updateRequestInput);
    }

    /**
     * Mapping for Bus Related to User
     * @param email
     * @return
     */

    @GetMapping("/info/bus")
    public BusOppRequestOutput getBusUserInfo(@RequestHeader("token") String token){
        webClientBuilder.build().get()
                .uri("http://identity-service/auth/validate?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String email = jwtUtil.extractEmail(token);

        return userService.getBusUserInfo(email);
    }
//    @GetMapping("/info/bus/isVerified")
//    public boolean getUserIsVerified(@RequestHeader("token") String token ){
//
//        webClientBuilder.build().get()
//                .uri("http://identity-service/auth/validate?token="+token)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//        String email = jwtUtil.extractEmail(token);
//
//        return userService.getUserIsVerified(email);
//    }

    /**
     *
     * Mapping for Ticket Related to User
     * @param email
     * @return
     *
     */
    @GetMapping("/info/ticket")
    public UserDetailsForTicket getUserInfoForTicket(@RequestHeader("token") String token){

            webClientBuilder.build().get()
                    .uri("http://identity-service/auth/validate?token="+token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
             String email = jwtUtil.extractEmail(token);

          return userService.getUserInfoForTicket(email);
    }


    /**
     * mapping for testing
     *
     */
      @GetMapping("/print")
      public String print (@RequestHeader("token") String token){
          System.out.println("inside print");
          String result = webClientBuilder.build().get()
                  .uri("http://user-service/api/user/demo")
                  .headers(headers -> headers.setBearerAuth(token))
                  .header("token",token)
                  .retrieve()
                  .bodyToMono(String.class)
                  .block();
          return  result +"\n"+token;
      }
    @GetMapping("/demo")
    public String demo (@RequestHeader("token") String token){
        System.out.println("inside demo");
        try{
            webClientBuilder.build().get()
                    .uri("http://identity-service/auth/validate?token="+token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return "inside valid block and verified";
        }
        catch(Exception e){
            return "verification failed";
        }


}}


