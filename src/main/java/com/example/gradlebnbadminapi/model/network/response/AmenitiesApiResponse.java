package com.example.gradlebnbadminapi.model.network.response;

import constant.ConstAmenities;
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
    
    public static String convertValue(String value) {
        switch (value) {
            case ConstAmenities.KEY_WIFI: return ConstAmenities.WIFI;
            case ConstAmenities.KEY_TV: return ConstAmenities.TV;
            case ConstAmenities.KEY_HEATER: return ConstAmenities.HEATER;
            case ConstAmenities.KEY_AIR_CONDITIONER: return ConstAmenities.AIR_CONDITIONER;
            case ConstAmenities.KEY_IRON: return ConstAmenities.IRON;
            case ConstAmenities.KEY_SHAMPOO: return ConstAmenities.SHAMPOO;
            case ConstAmenities.KEY_HAIR_DRYER: return ConstAmenities.HAIR_DRYER;
            case ConstAmenities.KEY_BREAKFAST: return ConstAmenities.BREAKFAST;
            case ConstAmenities.KEY_BUSINESS_SPACE: return ConstAmenities.BUSINESS_SPACE;
            case ConstAmenities.KEY_FIREPLACE: return ConstAmenities.FIREPLACE;
            case ConstAmenities.KEY_CLOSET: return ConstAmenities.CLOSET;
            case ConstAmenities.KEY_GUEST_ENTRANCE: return ConstAmenities.GUEST_ENTRANCE;
            default: return null;
        }
    }
}
