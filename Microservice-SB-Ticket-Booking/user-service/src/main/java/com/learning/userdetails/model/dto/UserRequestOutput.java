package com.learning.userdetails.model.dto;

import com.learning.userdetails.model.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestOutput {
    private String userName;
    private String userEmail;
//    private String userPassword;
    private String userMobileNumber;
    private String userCity;
    private Integer userAge;
    private Gender gender;
    private String roles;

}
