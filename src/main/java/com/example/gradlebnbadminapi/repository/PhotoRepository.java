package com.example.gradlebnbadminapi.repository;

import com.example.gradlebnbadminapi.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
