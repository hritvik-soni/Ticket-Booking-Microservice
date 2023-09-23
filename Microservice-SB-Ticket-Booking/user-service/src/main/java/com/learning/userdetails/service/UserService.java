package com.learning.userdetails.service;

import com.learning.userdetails.model.Users;
import com.learning.userdetails.model.dto.*;
import com.learning.userdetails.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createUser(UserRequestInput userRequestInput) {
        String email = userRequestInput.getUserEmail();
        email = email.substring(email.length()-8,email.length());
        String role = "ROLE_USER";
        if(email.equals("@bus.com")){
            role = "ROLE_BUS";
        }
        Users newUsers = Users.builder()
                .userName(userRequestInput.getUserName())
                .userAge(userRequestInput.getUserAge())
                .userEmail(userRequestInput.getUserEmail())
                .userPassword(passwordEncoder.encode(userRequestInput.getUserPassword()))
                .userCity(userRequestInput.getUserCity())
                .userMobileNumber(userRequestInput.getUserMobileNumber())
                .gender(userRequestInput.getGender())
                .roles(role)
                .build();
        Users savedUsers = userRepo.save(newUsers);
        return "user saved successfully!!!!";
    }

    public UserRequestOutput getUserInfo(String email) {
        Users currentUsers = userRepo.findByUserEmail(email);
        if(currentUsers ==null){
            return null;
        }
        return UserRequestOutput.builder()
                .userName(currentUsers.getUserName())
                .userAge(currentUsers.getUserAge())
                .userEmail(currentUsers.getUserEmail())
                .userCity(currentUsers.getUserCity())
                .userMobileNumber(currentUsers.getUserMobileNumber())
                .gender(currentUsers.getGender())
                .roles(currentUsers.getRoles())
                .build();
    }

    public BusOppRequestOutput getBusUserInfo(String email, String userPassword) {
        boolean isVerified = getUserIsVerified(email, userPassword);
        if (isVerified) {

        Users currentUsers = userRepo.findByUserEmail(email);
        if (currentUsers == null) {
            return null;
        }
        return BusOppRequestOutput.builder()
                .busOppEmail(currentUsers.getUserEmail())
                .busOppNumber(currentUsers.getUserMobileNumber())
                .busOppName(currentUsers.getUserName())
                .build();
    }
    return null;
    }

    public UserDetailsForTicket getUserInfoForTicket(String email) {
        Users currentUsers = userRepo.findByUserEmail(email);
        if(currentUsers ==null){
            return null;
        }
        return UserDetailsForTicket.builder()
                .userName(currentUsers.getUserName())
                .userEmail(currentUsers.getUserEmail())
                .userMobileNumber(currentUsers.getUserMobileNumber())
                .userAge(currentUsers.getUserAge())
                .gender(currentUsers.getGender())

                .build();

    }

    public boolean getUserIsVerified(String email, String password) {
        Users currUsers = userRepo.findByUserEmail(email);
        return currUsers.getUserPassword() == password;
    }

    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    public String removeUser(String email, String password) {
        password = passwordEncoder.encode(password);
        boolean isVerified = getUserIsVerified(email,password);
        if(isVerified){
            Users currUsers = userRepo.findByUserEmail(email);
            userRepo.delete(currUsers);
            return "User deleted Successfully!!!";
        }
        return "Invalid Credentials!!!";
    }

    public String updateUser(String email, String password, UserUpdateRequestInput updateRequestInput) {
        password = passwordEncoder.encode(password);
        boolean isVerified = getUserIsVerified(email,password);
        if(isVerified){
            Users currUsers = userRepo.findByUserEmail(email);
            if(updateRequestInput.getUserCity()!=null){
                currUsers.setUserCity(updateRequestInput.getUserCity());
            }
            if(updateRequestInput.getUserPassword()!=null){
                currUsers.setUserPassword(passwordEncoder.encode(updateRequestInput.getUserPassword()));
            }
            if(updateRequestInput.getUserMobileNumber()!=null){
                currUsers.setUserMobileNumber(updateRequestInput.getUserMobileNumber());
            }
            userRepo.save(currUsers);

            return "User details updated Successfully!!!";
        }
        return "Invalid Credentials!!!";

    }

    public UserRequestAuthOutput getUserInfoForAuth(String email) {
        Users currUsers = userRepo.findByUserEmail(email);
        if (currUsers == null) {
            return null;
        }
        return  UserRequestAuthOutput.builder()
                .userEmail(currUsers.getUserEmail())
                .userPassword(currUsers.getUserPassword())
                .roles(currUsers.getRoles())
                .build();
    }
}
