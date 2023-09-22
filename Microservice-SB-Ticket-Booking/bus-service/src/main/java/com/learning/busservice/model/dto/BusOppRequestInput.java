package com.learning.busservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusOppRequestInput {
    private String busOppNumber;
    private String busOppEmail;
    private String busOppName;
}
