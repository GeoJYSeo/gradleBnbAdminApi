package com.example.gradlebnbadminapi.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmenitiesApiResponse {

    private boolean wifi;

    private boolean tv;

    private boolean heater;

    private boolean airConditioner;

    private boolean iron;

    private boolean shampoo;

    private boolean hairDryer;

    private boolean breakfast;

    private boolean businessSpace;

    private boolean fireplace;

    private boolean closet;

    private boolean guestEntrance;
}
