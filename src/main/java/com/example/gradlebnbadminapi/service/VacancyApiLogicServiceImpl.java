package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Accommodation;
import com.example.gradlebnbadminapi.model.entity.Vacancy;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;
import com.example.gradlebnbadminapi.repository.VacancyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class VacancyApiLogicServiceImpl implements VacancyApiLogicService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void create(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("Vacancy builder.");

        Vacancy vacancy = Vacancy.builder()
                .price(accommodationApiRequest.getPrice())
                .startDate(accommodationApiRequest.getStartDate())
                .endDate(accommodationApiRequest.getEndDate())
                .accommodation(accommodation)
                .build();

        log.info("Vacancy save: " + vacancy);
        vacancyRepository.save(vacancy);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void put(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("Modify Vacancy.");

        Vacancy modifyVacancy = accommodation.getVacancy()
                .setPrice(accommodationApiRequest.getPrice())
                .setStartDate(accommodationApiRequest.getStartDate())
                .setEndDate(accommodationApiRequest.getEndDate());

        log.info("Vacancy modify: " + modifyVacancy);
        vacancyRepository.save(modifyVacancy);
    }
}
