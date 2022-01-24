package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Accommodation;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.RoomApiRequest;
import com.example.gradlebnbadminapi.model.network.response.AccommodationApiResponse;

import java.util.List;

public interface RoomApiLogicService {

    Header<List<AccommodationApiResponse>> get(String email) throws Exception;

    Header create(Header<RoomApiRequest> roomApiRequest) throws Exception;
}
