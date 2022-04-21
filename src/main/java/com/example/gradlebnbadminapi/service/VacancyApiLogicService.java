package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Accommodation;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;

public interface VacancyApiLogicService {

    void create(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception;

    void put(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception;
}
