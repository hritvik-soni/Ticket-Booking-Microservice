package com.learning.userdetails.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusOppRequestOutput {
    private String busOppNumber;
    private String busOppEmail;
    private String busOppName;
}
