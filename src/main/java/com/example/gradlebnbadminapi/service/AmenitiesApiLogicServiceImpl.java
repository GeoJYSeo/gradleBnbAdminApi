package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Accommodation;
import com.example.gradlebnbadminapi.model.entity.Amenities;
import com.example.gradlebnbadminapi.model.enumClass.HasOrNot;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;
import com.example.gradlebnbadminapi.repository.AmenitiesRepository;
import constant.ConstAmenities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AmenitiesApiLogicServiceImpl implements AmenitiesApiLogicService {

    @Autowired
    private AmenitiesRepository amenitiesRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void create(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("Amenities builder.");

        Amenities amenities = Amenities.builder()
                .wifi(accommodationApiRequest.getAmenities().contains(ConstAmenities.WIFI) ? HasOrNot.HAS : HasOrNot.NOT)
                .tv(accommodationApiRequest.getAmenities().contains(ConstAmenities.TV) ? HasOrNot.HAS : HasOrNot.NOT)
                .heater(accommodationApiRequest.getAmenities().contains(ConstAmenities.HEATER) ? HasOrNot.HAS : HasOrNot.NOT)
                .airConditioner(accommodationApiRequest.getAmenities().contains(ConstAmenities.AIR_CONDITIONER) ? HasOrNot.HAS : HasOrNot.NOT)
                .iron(accommodationApiRequest.getAmenities().contains(ConstAmenities.IRON) ? HasOrNot.HAS : HasOrNot.NOT)
                .shampoo(accommodationApiRequest.getAmenities().contains(ConstAmenities.SHAMPOO) ? HasOrNot.HAS : HasOrNot.NOT)
                .hairDryer(accommodationApiRequest.getAmenities().contains(ConstAmenities.HAIR_DRYER) ? HasOrNot.HAS : HasOrNot.NOT)
                .breakfast(accommodationApiRequest.getAmenities().contains(ConstAmenities.BREAKFAST) ? HasOrNot.HAS : HasOrNot.NOT)
                .businessSpace(accommodationApiRequest.getAmenities().contains(ConstAmenities.BUSINESS_SPACE) ? HasOrNot.HAS : HasOrNot.NOT)
                .fireplace(accommodationApiRequest.getAmenities().contains(ConstAmenities.FIREPLACE) ? HasOrNot.HAS : HasOrNot.NOT)
                .closet(accommodationApiRequest.getAmenities().contains(ConstAmenities.CLOSET) ? HasOrNot.HAS : HasOrNot.NOT)
                .guestEntrance(accommodationApiRequest.getAmenities().contains(ConstAmenities.GUEST_ENTRANCE) ? HasOrNot.HAS : HasOrNot.NOT)
                .accommodation(accommodation)
                .build();

        log.info("Amenities save: " + amenities);
        amenitiesRepository.save(amenities);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void put(List<String> amenities, Accommodation accommodation) throws Exception {
        log.info("Modify Amenities.");

        Amenities modifyAmenities = accommodation.getAmenities()
                .setWifi(amenities.contains(ConstAmenities.WIFI) ? HasOrNot.HAS : HasOrNot.NOT)
                .setTv(amenities.contains(ConstAmenities.TV) ? HasOrNot.HAS : HasOrNot.NOT)
                .setHeater(amenities.contains(ConstAmenities.HEATER) ? HasOrNot.HAS : HasOrNot.NOT)
                .setAirConditioner(amenities.contains(ConstAmenities.AIR_CONDITIONER) ? HasOrNot.HAS : HasOrNot.NOT)
                .setIron(amenities.contains(ConstAmenities.IRON) ? HasOrNot.HAS : HasOrNot.NOT)
                .setShampoo(amenities.contains(ConstAmenities.SHAMPOO) ? HasOrNot.HAS : HasOrNot.NOT)
                .setHairDryer(amenities.contains(ConstAmenities.HAIR_DRYER) ? HasOrNot.HAS : HasOrNot.NOT)
                .setBreakfast(amenities.contains(ConstAmenities.BREAKFAST) ? HasOrNot.HAS : HasOrNot.NOT)
                .setBusinessSpace(amenities.contains(ConstAmenities.BUSINESS_SPACE) ? HasOrNot.HAS : HasOrNot.NOT)
                .setFireplace(amenities.contains(ConstAmenities.FIREPLACE) ? HasOrNot.HAS : HasOrNot.NOT)
                .setCloset(amenities.contains(ConstAmenities.CLOSET) ? HasOrNot.HAS : HasOrNot.NOT)
                .setGuestEntrance(amenities.contains(ConstAmenities.GUEST_ENTRANCE) ? HasOrNot.HAS : HasOrNot.NOT);

        log.info("Amenities modify: " + modifyAmenities);
        amenitiesRepository.save(modifyAmenities);
    }
}
