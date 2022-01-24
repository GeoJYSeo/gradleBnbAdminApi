package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.UserApiRequest;
import com.example.gradlebnbadminapi.model.network.response.UserApiResponse;
import org.springframework.security.core.Authentication;

public interface UserApiLogicService {

    Header<UserApiResponse> create(Header<UserApiRequest> request) throws Exception;

    Header<UserApiResponse> read(String email) throws Exception;
}
