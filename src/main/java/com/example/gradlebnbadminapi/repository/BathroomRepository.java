package com.example.gradlebnbadminapi.repository;

import com.example.gradlebnbadminapi.model.entity.Bathroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BathroomRepository extends JpaRepository<Bathroom, Long> {
}
