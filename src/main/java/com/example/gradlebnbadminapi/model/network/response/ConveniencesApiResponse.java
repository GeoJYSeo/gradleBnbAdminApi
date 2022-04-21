package com.example.gradlebnbadminapi.model.network.response;

import constant.ConstConveniences;
import constant.ConstConveniences;
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

    public static String convertValue(String value) {
        switch (value) {
            case ConstConveniences.KEY_KITCHEN: return ConstConveniences.KITCHEN;
            case ConstConveniences.KEY_LAUNDRY_ROOM: return ConstConveniences.LAUNDRY_ROOM;
            case ConstConveniences.KEY_PARKING_LOT: return ConstConveniences.PARKING_LOT;
            case ConstConveniences.KEY_GYM: return ConstConveniences.GYM;
            case ConstConveniences.KEY_SWIMMING_POOL: return ConstConveniences.SWIMMING_POOL;
            case ConstConveniences.KEY_JACUZZI: return ConstConveniences.JACUZZI;
            default: return null;
        }
    }
}
