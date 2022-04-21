package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Accommodation;
import com.example.gradlebnbadminapi.model.entity.Room;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;
import com.example.gradlebnbadminapi.model.network.request.BedApiRequest;
import com.example.gradlebnbadminapi.model.network.request.BedListApiRequest;
import com.example.gradlebnbadminapi.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RoomApiLogicServiceImpl implements RoomApiLogicService {

    @Autowired
    private BedApiLogicService bedApiLogicService;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void create(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("email: " + accommodationApiRequest.getEmail());

        savePrivateRoom(accommodationApiRequest, accommodation);
        savePublicSpace(accommodationApiRequest, accommodation);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void put(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("email: " + accommodationApiRequest.getEmail());

        List<Room> allRoomList = accommodation.getRoomList();
//        bedApiLogicService.delete(allRoomList);
        allRoomList.forEach(room -> roomRepository.delete(room));

        savePrivateRoom(accommodationApiRequest, accommodation);
        savePublicSpace(accommodationApiRequest, accommodation);
    }

    private void savePrivateRoom(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("Private bed builder.");

        List<BedListApiRequest> reqBedList = accommodationApiRequest.getBedList();

        reqBedList.forEach(beds -> {
            Room room = Room.builder()
                    .number(Math.toIntExact((Long) beds.getId()))
                    .accommodation(accommodation)
                    .build();
            roomRepository.save(room);

            beds.getBeds().forEach(privateBed -> bedApiLogicService.create(room, privateBed));
        });
    }


    public void savePublicSpace(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) {
        log.info("Public bed builder");

        List<BedApiRequest> bedList = accommodationApiRequest.getPublicBedList();

        bedList.forEach(publicBed -> {
            Room room = Room.builder()
                    .number(0)
                    .accommodation(accommodation)
                    .build();
            roomRepository.save(room);

            bedApiLogicService.create(room, publicBed);
        });
    }
}
