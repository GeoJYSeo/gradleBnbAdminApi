package com.example.gradlebnbadminapi.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationApiRequest {

    private Long id;

    private String email;

    // Accommodation
    private String title;

    private String largeBuildingType;

    private String buildingType;

    private int maximumGuestCount;

    private String description;

    // Room
    private String roomType;

    private int roomCount;

    private Boolean isSetUpForGuest;

    private BigDecimal bathroomCount;

    private String bathroomType;

    // Bed
    private List<BedListApiRequest> bedList;

    private List<BedApiRequest> publicBedList;

    // Location
    private int latitude;

    private int longitude;

    private String country;

    private String state;

    private String city;

    private String streetAddress;

    private String detailAddress;

    private String postcode;

    // Amenities
    private List<String> amenities;

    // Conveniences
    private List<String> conveniences;

    // Reservation
    private String price;

    private String startDate;

    private String endDate;


}
