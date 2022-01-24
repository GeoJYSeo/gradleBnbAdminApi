package com.example.gradlebnbadminapi.repository;

import com.example.gradlebnbadminapi.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
