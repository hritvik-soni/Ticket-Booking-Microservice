package com.learning.userdetails.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestAuthOutput {

    private String userEmail;
    private String userPassword;
    private String roles;
}
