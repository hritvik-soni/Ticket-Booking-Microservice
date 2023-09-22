package com.learning.userdetails.service;

import com.learning.userdetails.model.User;
import com.learning.userdetails.model.dto.BusOppRequestOutput;
import com.learning.userdetails.model.dto.UserDetailsForTicket;
import com.learning.userdetails.model.dto.UserRequestInput;
import com.learning.userdetails.model.dto.UserRequestOutput;
import com.learning.userdetails.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    public String createUser(UserRequestInput userRequestInput) {
        String email = userRequestInput.getUserEmail();
        email = email.substring(email.length()-8,email.length());
        String role = "ROLE_USER";
        if(email.equals("@bus.com")){
            role = "ROLE_BUS";
        }
        User newUser = User.builder()
                .userName(userRequestInput.getUserName())
                .userAge(userRequestInput.getUserAge())
                .userEmail(userRequestInput.getUserEmail())
                .userPassword(userRequestInput.getUserPassword())
                .userCity(userRequestInput.getUserCity())
                .userMobileNumber(userRequestInput.getUserMobileNumber())
                .gender(userRequestInput.getGender())
                .roles(role)
                .build();
        User savedUser = userRepo.save(newUser);
        return "user saved successfully!!!!";
    }

    public UserRequestOutput getUserInfo(String email) {
        User currentUser = userRepo.findByUserEmail(email);
        if(currentUser==null){
            return null;
        }
        return UserRequestOutput.builder()
                .userName(currentUser.getUserName())
                .userAge(currentUser.getUserAge())
                .userEmail(currentUser.getUserEmail())
                .userCity(currentUser.getUserCity())
                .userMobileNumber(currentUser.getUserMobileNumber())
                .gender(currentUser.getGender())
                .roles(currentUser.getRoles())
                .build();
    }

    public BusOppRequestOutput getBusUserInfo(String email) {
        User currentUser = userRepo.findByUserEmail(email);
        if(currentUser==null){
            return null;
        }
        return BusOppRequestOutput.builder()
                .busOppEmail(currentUser.getUserEmail())
                .busOppNumber(currentUser.getUserMobileNumber())
                .busOppName(currentUser.getUserName())
                .build();
    }

    public UserDetailsForTicket getUserInfoForTicket(String email) {
        User currentUser = userRepo.findByUserEmail(email);
        if(currentUser==null){
            return null;
        }
        return UserDetailsForTicket.builder()
                .userName(currentUser.getUserName())
                .userEmail(currentUser.getUserEmail())
                .userMobileNumber(currentUser.getUserMobileNumber())
                .userAge(currentUser.getUserAge())
                .gender(currentUser.getGender())

                .build();

    }
}
