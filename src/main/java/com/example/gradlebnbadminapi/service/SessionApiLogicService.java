package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.SessionApiRequest;
import com.example.gradlebnbadminapi.model.network.response.SessionApiResponse;

public interface SessionApiLogicService {

    Header<SessionApiResponse> authenticate(SessionApiRequest request) throws Exception;
}
