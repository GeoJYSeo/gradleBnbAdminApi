package com.example.gradlebnbadminapi.controller;

import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;
import com.example.gradlebnbadminapi.model.network.response.AccommodationApiResponse;
import com.example.gradlebnbadminapi.service.AccommodationApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
public class AccommodationController {

    @Autowired
    private AccommodationApiLogicService accommodationApiLogicService;

    @GetMapping("")
    public Header<List<AccommodationApiResponse>> index(Pageable pageable) throws Exception {
        return accommodationApiLogicService.index(pageable);
    }

    @GetMapping("/{email}")
    public Header<List<AccommodationApiResponse>> get(@PathVariable String email) throws Exception {
        return accommodationApiLogicService.get(email);
    }

    @PostMapping("")
    public Header create(@RequestBody Header<AccommodationApiRequest> roomApiRequest) throws Exception {
        return accommodationApiLogicService.create(roomApiRequest);
    }

    @PutMapping("")
    public Header put(@RequestBody Header<AccommodationApiRequest> roomApiRequest) throws Exception {
        return accommodationApiLogicService.put(roomApiRequest);
    }
}
