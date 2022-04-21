package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Accommodation;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;

import java.util.List;

public interface AmenitiesApiLogicService {

    void create(AccommodationApiRequest roomApiRequest, Accommodation accommodation) throws Exception;

    void put(List<String> conveniences, Accommodation accommodation) throws Exception;
}
