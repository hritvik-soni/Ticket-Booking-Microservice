package com.learning.ticketservice.service.utility;

import java.util.Random;

public class AppUtils {

    public static int generateRandomSeatNo (Integer totalSeats){

        int seat = (int)(Math.random()*totalSeats);
        if(seat==0){
            generateRandomSeatNo(totalSeats);
        }
        return seat;
    }




}
