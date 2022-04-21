package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Accommodation;
import com.example.gradlebnbadminapi.model.entity.Conveniences;
import com.example.gradlebnbadminapi.model.enumClass.HasOrNot;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;
import com.example.gradlebnbadminapi.repository.ConveniencesRepository;
import constant.ConstConveniences;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ConveniencesApiLogicServiceImpl implements ConveniencesApiLogicService {

    @Autowired
    private ConveniencesRepository conveniencesRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void create(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("Conveniences builder.");

        Conveniences conveniences = Conveniences.builder()
                .kitchen(accommodationApiRequest.getConveniences().contains(ConstConveniences.KITCHEN) ? HasOrNot.HAS : HasOrNot.NOT)
                .laundryRoom(accommodationApiRequest.getConveniences().contains(ConstConveniences.LAUNDRY_ROOM) ? HasOrNot.HAS : HasOrNot.NOT)
                .parkingLot(accommodationApiRequest.getConveniences().contains(ConstConveniences.PARKING_LOT) ? HasOrNot.HAS : HasOrNot.NOT)
                .gym(accommodationApiRequest.getConveniences().contains(ConstConveniences.GYM) ? HasOrNot.HAS : HasOrNot.NOT)
                .swimmingPool(accommodationApiRequest.getConveniences().contains(ConstConveniences.SWIMMING_POOL) ? HasOrNot.HAS : HasOrNot.NOT)
                .jacuzzi(accommodationApiRequest.getConveniences().contains(ConstConveniences.JACUZZI) ? HasOrNot.HAS : HasOrNot.NOT)
                .accommodation(accommodation)
                .build();

        log.info("Conveniences save: " + conveniences);
        conveniencesRepository.save(conveniences);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void put(List<String> conveniences, Accommodation accommodation) throws Exception {
        log.info("Modify Conveniences.");

        Conveniences modifyConveniences = accommodation.getConveniences()
                .setKitchen(conveniences.contains(ConstConveniences.KITCHEN) ? HasOrNot.HAS : HasOrNot.NOT)
                .setLaundryRoom(conveniences.contains(ConstConveniences.LAUNDRY_ROOM) ? HasOrNot.HAS : HasOrNot.NOT)
                .setParkingLot(conveniences.contains(ConstConveniences.PARKING_LOT) ? HasOrNot.HAS : HasOrNot.NOT)
                .setGym(conveniences.contains(ConstConveniences.GYM) ? HasOrNot.HAS : HasOrNot.NOT)
                .setSwimmingPool(conveniences.contains(ConstConveniences.SWIMMING_POOL) ? HasOrNot.HAS : HasOrNot.NOT)
                .setJacuzzi(conveniences.contains(ConstConveniences.JACUZZI) ? HasOrNot.HAS : HasOrNot.NOT);

        log.info("Conveniences modify: " + conveniences);
        conveniencesRepository.save(modifyConveniences);
    }
}
