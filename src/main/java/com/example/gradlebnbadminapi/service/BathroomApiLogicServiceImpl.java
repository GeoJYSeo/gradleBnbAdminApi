package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Accommodation;
import com.example.gradlebnbadminapi.model.entity.Bathroom;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;
import com.example.gradlebnbadminapi.repository.BathroomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BathroomApiLogicServiceImpl implements BathroomApiLogicService {

    @Autowired
    private BathroomRepository bathroomRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void create(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("Bathroom builder");

        Bathroom bathroom = Bathroom.builder()
                .count(accommodationApiRequest.getBathroomCount())
                .type(accommodationApiRequest.getBathroomType())
                .accommodation(accommodation)
                .build();

        log.info("Bathroom save");
        bathroomRepository.save(bathroom);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void put(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("Modify Bathroom.");

        Bathroom modifyBathroom = accommodation.getBathroom()
                .setCount(accommodationApiRequest.getBathroomCount())
                .setType(accommodationApiRequest.getBathroomType());

        log.info("Bathroom modify: " + modifyBathroom);
        bathroomRepository.save(modifyBathroom);
    }
}
