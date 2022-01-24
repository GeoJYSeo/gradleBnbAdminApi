package com.example.gradlebnbadminapi.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationApiResponse {

    // Accommodation
    private Long id;

    private String title;

    private String largeBuildingType;

    private String buildingType;

    private int maximumGuestCount;

    private String description;

    // Room && Bed && amenities
    // private List<RoomApiResponse> roomList;
    private String roomType;

    private boolean isSetUpForGuest;

    private int bedroomCount;

    private List<BedListApiResponse> bedList;

    private List<BedApiResponse> publicBedList;

    private BigDecimal bathroomCount;

    private String bathroomType;

    // Location
    private String country;

    private String state;

    private String city;

    private String streetAddress;

    private String detailAddress;

    private String postcode;

    private int latitude;

    private int longitude;

    private List<String> amenities;

    // conveniences
    private List<String> conveniences;

    // vacancy
    //private List<VacancyApiResponse> vacancyList;
    private String price;

    private String startDate;

    private String endDate;

    // photo
    private List<PhotoApiResponse> photos;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
