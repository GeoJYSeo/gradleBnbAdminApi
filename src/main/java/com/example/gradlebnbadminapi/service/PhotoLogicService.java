package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.response.PhotoApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoLogicService {

    Header<List<PhotoApiResponse>> index(String email) throws Exception;

    Header<PhotoApiResponse> create(MultipartFile mpPhoto, String email) throws Exception;

    Header<PhotoApiResponse> update(MultipartFile mpPhoto, String email, Long id) throws Exception;

    Header delete(Long id, String email) throws Exception;
}
