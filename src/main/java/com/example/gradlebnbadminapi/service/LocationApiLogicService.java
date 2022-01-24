package com.example.gradlebnbadminapi.service;

import org.springframework.http.ResponseEntity;

public interface LocationApiLogicService {

    String getLocation(String latitude, String longitude) throws Exception;

    String getPlaces(String searchKeyword) throws Exception;

    String getPlacesWithPlaceId(String placeId) throws Exception;
}
