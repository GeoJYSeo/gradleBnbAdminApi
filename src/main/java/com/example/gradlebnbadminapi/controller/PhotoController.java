package com.example.gradlebnbadminapi.controller;

import com.example.gradlebnbadminapi.model.entity.User;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.response.PhotoApiResponse;
import com.example.gradlebnbadminapi.repository.UserRepository;
import com.example.gradlebnbadminapi.service.PhotoLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/photo")
public class PhotoController {

    @Autowired
    private PhotoLogicService photoLogicService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public Header<List<PhotoApiResponse>> index(@RequestParam String email) throws Exception {
        return photoLogicService.index(email);
    }

    @PostMapping("")
    public Header<PhotoApiResponse> upload(@RequestPart("photo") MultipartFile mpPhoto, @RequestParam("email") String email) throws Exception {
        return photoLogicService.create(mpPhoto, email);
    }

    @PutMapping("")
    public Header<PhotoApiResponse> update(@RequestPart("photo") MultipartFile mpPhoto, @RequestParam("email") String email, @RequestParam("id") Long id) throws Exception {
        return photoLogicService.update(mpPhoto, email, id);
    }

    @DeleteMapping("")
    public Header delete(@RequestParam Long id, @RequestParam String email) throws Exception {
        return photoLogicService.delete(id, email);
    }
}
