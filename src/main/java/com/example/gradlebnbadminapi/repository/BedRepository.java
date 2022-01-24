package com.example.gradlebnbadminapi.repository;

import com.example.gradlebnbadminapi.model.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
}
