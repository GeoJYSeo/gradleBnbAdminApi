package com.example.gradlebnbadminapi.controller;

import com.example.gradlebnbadminapi.service.LocationApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private static final String LOCATION = "location";

    @Autowired
    private LocationApiLogicService locationApiLogicService;

    @GetMapping("")
    public ResponseEntity<String> getLocation(@RequestParam String latitude, @RequestParam String longitude) throws Exception {

        return ResponseEntity.created(new URI(LOCATION)).body(locationApiLogicService.getLocation(latitude, longitude));
    }

    @GetMapping("/places")
    public ResponseEntity<String> getPlaces(@RequestParam String searchKeyword) throws Exception {
        return ResponseEntity.created(new URI(LOCATION + "/places")).body(locationApiLogicService.getPlaces(searchKeyword));
    }

    @GetMapping("/places/{placeId}")
    public ResponseEntity<String> getPlacesWithPlaceId(@PathVariable String placeId) throws Exception {
        return ResponseEntity.created(new URI(LOCATION + "/places")).body(locationApiLogicService.getPlacesWithPlaceId(placeId));
    }
}
