package com.example.gradlebnbadminapi.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConveniencesApiResponse {

    private boolean kitchen;

    private boolean laundryRoom;

    private boolean parkingLot;

    private boolean gym;

    private boolean swimmingPool;

    private boolean jacuzzi;
}
