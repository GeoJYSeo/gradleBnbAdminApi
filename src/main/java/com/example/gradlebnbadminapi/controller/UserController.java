package com.example.gradlebnbadminapi.controller;

import com.example.gradlebnbadminapi.annotation.NoAuth;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.UserApiRequest;
import com.example.gradlebnbadminapi.model.network.response.UserApiResponse;
import com.example.gradlebnbadminapi.service.UserApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @NoAuth
    @PostMapping("")
    public Header<UserApiResponse> create(@RequestBody @Valid Header<UserApiRequest> request) throws Exception {
        return userApiLogicService.create(request);
    }

    @GetMapping("")
    public Header<UserApiResponse> read(@RequestParam String email) throws Exception {
        return userApiLogicService.read(email);
    }
}
