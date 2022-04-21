package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;
import com.example.gradlebnbadminapi.model.network.response.AccommodationApiResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccommodationApiLogicService {

    Header<List<AccommodationApiResponse>> index(Pageable pageable) throws Exception;

    Header<List<AccommodationApiResponse>> get(String email) throws Exception;

    Header create(Header<AccommodationApiRequest> roomApiRequest) throws Exception;

    Header put(Header<AccommodationApiRequest> roomApiRequest) throws Exception;
}
