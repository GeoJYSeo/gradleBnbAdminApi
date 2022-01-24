package com.example.gradlebnbadminapi.service;

import com.amazonaws.AmazonServiceException;
import com.example.gradlebnbadminapi.model.entity.Photo;
import com.example.gradlebnbadminapi.model.entity.User;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.response.PhotoApiResponse;
import com.example.gradlebnbadminapi.repository.PhotoRepository;
import com.example.gradlebnbadminapi.repository.UserRepository;
import com.example.gradlebnbadminapi.util.HandlingS3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PhotoLogicServiceImpl implements PhotoLogicService {

    @Autowired
    private HandlingS3 handlingS3;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    private PhotoCallback photoCallback;

    public interface PhotoCallback {
        Header<PhotoApiResponse> create(MultipartFile mpPhoto, String email);
        Header<PhotoApiResponse> update(MultipartFile mpPhoto, String reqEmail, Long id);
    }

    public void setCallback(PhotoCallback photoCallback) {
        this.photoCallback = photoCallback;
    }

    @Override
    public Header<List<PhotoApiResponse>> index(String email) throws Exception {
        log.info("Get S3 urls");
        log.info("Email: " + email);

        User user = userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);

        List<PhotoApiResponse> photoApiResponseList = user.getPhotoList().stream()
                .map(this::response)
                .collect(Collectors.toList());
        log.info("Photo info: " + photoApiResponseList);

        return Header.OK(photoApiResponseList);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Header<PhotoApiResponse> create(MultipartFile mpPhoto, String email) throws Exception {
        log.info("Create Original Photo name: " + mpPhoto.getOriginalFilename());
        log.info("email: " + email);

        handlingS3.execute(mpPhoto, email);

        return this.photoCallback.create(mpPhoto, email);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Header<PhotoApiResponse> update(MultipartFile mpPhoto, String reqEmail, Long id) throws Exception {
        log.info("Update Original Photo name: " + mpPhoto.getOriginalFilename());
        log.info("Target photo id to update: " + id);
        log.info("email: " + reqEmail);

        handlingS3.execute(mpPhoto, reqEmail);

        return this.photoCallback.update(mpPhoto, reqEmail, id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Header delete(Long id, String reqEmail) throws Exception {
        log.info("Target photo id to delete: " + id);
        log.info("email: " + reqEmail);

        User user = userRepository.findByEmail(reqEmail).orElseThrow(NoSuchElementException::new);
        log.info("Uploaded user: " + user.getEmail());

        Photo delPhoto = user.getPhotoList().stream().filter(photo -> photo.getId().equals(id)).findFirst().orElseThrow(NoSuchElementException::new);
        log.info("Delete: " + delPhoto);

        try {
            handlingS3.delS3Photo(delPhoto.getName());
            photoRepository.delete(delPhoto);
            log.info("Deleted Photo name: " + delPhoto.getName());
        } catch (AmazonServiceException e) {
            log.error(e.getMessage());
            log.error("AWS S3 Delete error");
            throw new Exception();
        }

        return Header.OK();
    }

    public PhotoApiResponse response(Photo photo) {

        return PhotoApiResponse.builder()
                .url(photo.getUrl())
                .build();
    }
}
