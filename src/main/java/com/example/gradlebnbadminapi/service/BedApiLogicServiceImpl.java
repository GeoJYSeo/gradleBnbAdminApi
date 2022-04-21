package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Bed;
import com.example.gradlebnbadminapi.model.entity.Room;
import com.example.gradlebnbadminapi.model.enumClass.IsPrivate;
import com.example.gradlebnbadminapi.model.network.request.BedApiRequest;
import com.example.gradlebnbadminapi.repository.BedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class BedApiLogicServiceImpl implements BedApiLogicService {

    @Autowired
    private BedRepository bedRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void create(Room room, BedApiRequest bedApiRequest) {
        log.info("Create Beds.");

        Bed newBed = Bed.builder()
                .type(bedApiRequest.getType())
                .count(bedApiRequest.getCount())
                .isPrivate(room.getNumber() == 0 ? IsPrivate.NO : IsPrivate.Yes)
                .room(room)
                .build();

        log.info("Bed saved: " + newBed);
        bedRepository.save(newBed);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(List<Room> roomList) throws Exception {
        roomList.forEach(room -> room.getBedList().forEach(bed ->  bedRepository.delete(bed)));
    }
}
