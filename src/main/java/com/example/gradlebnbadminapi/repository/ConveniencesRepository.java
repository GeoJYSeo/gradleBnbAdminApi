package com.example.gradlebnbadminapi.repository;

import com.example.gradlebnbadminapi.model.entity.Conveniences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConveniencesRepository extends JpaRepository<Conveniences, Long> {
}
