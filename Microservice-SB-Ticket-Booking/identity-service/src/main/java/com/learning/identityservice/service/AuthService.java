package com.learning.identityservice.service;

import com.learning.identityservice.dto.UserCredentialInput;
import com.learning.identityservice.entity.UserCredential;
import com.learning.identityservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredentialInput credential) {

        UserCredential newUser = UserCredential.builder()
                .name(credential.getName())
                .email(credential.getEmail())
                .password(credential.getPassword())
                .build();
        repository.save(newUser);
        return "user added success for auth";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
       jwtService.validateToken(token);


    }

    public String removeUser(String token) {

        try {
            validateToken(token);
           String email= jwtService.extractEmail(token);
            System.out.println("inside remove user block before delete");
             Optional<UserCredential> user = repository.findByEmail(email);
             Integer userId =  user.get().getId();
             repository.deleteById(userId);
            System.out.println("inside remove user block after delete");
             return "user deleted from auth";
         }
           catch (Exception e) {
            return "Please retry";
           }
    }

}
