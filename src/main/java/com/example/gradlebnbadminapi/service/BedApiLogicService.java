package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Room;
import com.example.gradlebnbadminapi.model.network.request.BedApiRequest;

import java.util.List;

public interface BedApiLogicService {

    void create(Room room, BedApiRequest bedApiRequest);

    void delete(List<Room> allRoomList) throws Exception;
}
