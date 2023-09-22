package com.learning.ticketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsForTicketInput {

    private String userName;
    private String userEmail;
    private String userMobileNumber;
    private Integer userAge;
    private Gender gender;
}
