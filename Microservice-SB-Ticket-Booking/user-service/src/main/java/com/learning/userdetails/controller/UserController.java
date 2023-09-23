package com.learning.userdetails.controller;

import com.learning.userdetails.model.Users;
import com.learning.userdetails.model.dto.*;
import com.learning.userdetails.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/new")
    public String createUser(@RequestBody UserRequestInput userRequestInput){
        return userService.createUser(userRequestInput);
    }
    @GetMapping("/all")
    public List<Users> getAllUser(){
        return userService.getAllUsers();
    }
    @GetMapping("/info")
    public UserRequestOutput getUserInfo(@RequestParam("email") String email){
        return userService.getUserInfo(email);
    }

    @DeleteMapping("/remove")
    public String removeUser(@RequestParam("email") String email,@RequestParam("password") String password){

        return userService.removeUser(email,password);
    }
    @PutMapping("/update")
    public String updateUser(@RequestParam("email") String email, @RequestParam("password") String password,
                             @RequestBody UserUpdateRequestInput updateRequestInput){

        return userService.updateUser(email,password,updateRequestInput);
    }

    /**
     * Mapping for Bus Related to User
     * @param email
     * @return
     */

    @GetMapping("/info/bus")
    public BusOppRequestOutput getBusUserInfo(@RequestParam("email") String email,
                                              @RequestParam("password") String userPassword){
        return userService.getBusUserInfo(email,userPassword);
    }
    @GetMapping("/info/bus/isVerified")
    public boolean getUserIsVerified(@RequestParam("email") String email ,@RequestParam("password")String password){
        return userService.getUserIsVerified(email,password);
    }

    /**
     * Mapping for Ticket Related to User
     * @param email
     * @return
     */
    @GetMapping("/info/ticket")
    public UserDetailsForTicket getUserInfoForTicket(@RequestParam("email") String email){
        return userService.getUserInfoForTicket(email);
    }

    /**
     * Mapping for Auth Related to User
     *
     *
     */
    @GetMapping("/info/auth")
    public UserRequestAuthOutput getUserInfoForAuth (@RequestParam("email") String email){
        return userService.getUserInfoForAuth(email);
    }


}
