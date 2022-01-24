package com.example.gradlebnbadminapi.controller;

import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.RoomApiRequest;
import com.example.gradlebnbadminapi.model.network.response.AccommodationApiResponse;
import com.example.gradlebnbadminapi.service.RoomApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomApiLogicService roomApiLogicService;

    @GetMapping("/{email}")
    public Header<List<AccommodationApiResponse>> get(@PathVariable String email) throws Exception {
        return roomApiLogicService.get(email);
    }

    @PostMapping("")
    public void create(@RequestBody Header<RoomApiRequest> roomApiRequest) throws Exception {
        roomApiLogicService.create(roomApiRequest);
    }
}
