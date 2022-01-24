package com.example.gradlebnbadminapi.controller;

import com.example.gradlebnbadminapi.annotation.NoAuth;
import com.example.gradlebnbadminapi.jwt.TokenProvider;
import com.example.gradlebnbadminapi.model.entity.User;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.SessionApiRequest;
import com.example.gradlebnbadminapi.model.network.response.SessionApiResponse;
import com.example.gradlebnbadminapi.service.SessionApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping(value = "/api/session")
public class SessionController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SessionApiLogicService sessionApiLogicService;

    @NoAuth
    @PostMapping("")
    public Header<SessionApiResponse> create(@RequestBody SessionApiRequest request) throws Exception {
        return sessionApiLogicService.authenticate(request);
    }
}
