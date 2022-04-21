package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.Pagination;
import com.example.gradlebnbadminapi.model.entity.*;
import com.example.gradlebnbadminapi.model.enumClass.IsSetUpForGuest;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;
import com.example.gradlebnbadminapi.model.network.response.*;
import com.example.gradlebnbadminapi.repository.AccommodationRepository;
import com.example.gradlebnbadminapi.repository.UserRepository;
import com.example.gradlebnbadminapi.util.Common;
import constant.ConstCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccommodationApiLogicServiceImpl implements AccommodationApiLogicService {

    private static final int PUBLIC_BED = 0;
    private static final int PRIVATE_BED = 1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private RoomApiLogicService roomApiLogicService;

    @Autowired
    private ConveniencesApiLogicService conveniencesApiLogicService;

    @Autowired
    private LocationApiLogicService locationApiLogicService;

    @Autowired
    private VacancyApiLogicService vacancyApiLogicService;

    @Autowired
    private BathroomApiLogicService bathroomApiLogicService;

    @Autowired
    private AmenitiesApiLogicService amenitiesApiLogicService;

    @Autowired
    private UserApiLogicServiceImpl userApiLogicService;

    @Override
    public Header<List<AccommodationApiResponse>> index(@PageableDefault(sort = "createdAt", size = 20, direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        log.info("Get all accommodations.");

        Page<Accommodation> allAccommodation = accommodationRepository.findAll(pageable);

        List<AccommodationApiResponse> accommodationApiResponseList = allAccommodation.stream()
                .map(accommodation -> {
                    AccommodationApiResponse accommodationApiResponse = response(accommodation);
                    accommodationApiResponse.setUser(userApiLogicService.response(accommodation.getUser()));
                    return accommodationApiResponse;
                })
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(allAccommodation.getTotalPages())
                .totalElements(allAccommodation.getTotalElements())
                .currentPage(allAccommodation.getNumber())
                .currentElements(allAccommodation.getNumberOfElements())
                .build();

        return Header.OK(accommodationApiResponseList, pagination);
    }

    @Override
    @Transactional(readOnly = true)
    public Header<List<AccommodationApiResponse>> get(String email) throws Exception {
        log.info("Email: " + email);

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return null;
        }

        List<AccommodationApiResponse> accommodationApiResponseList = user.get().getAccommodationList().stream()
                .map(this::response).collect(Collectors.toList());

        log.info("AccommodationList" + accommodationApiResponseList);

        return Header.OK(accommodationApiResponseList);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Header create(Header<AccommodationApiRequest> request) throws Exception {
        AccommodationApiRequest accommodationApiRequest = request.getData();
        log.info("email: " + accommodationApiRequest.getEmail());

        try {
            Accommodation storedAccommodation = saveAccommodation(accommodationApiRequest);
            conveniencesApiLogicService.create(accommodationApiRequest, storedAccommodation);
            locationApiLogicService.create(accommodationApiRequest, storedAccommodation);
            vacancyApiLogicService.create(accommodationApiRequest, storedAccommodation);
            bathroomApiLogicService.create(accommodationApiRequest, storedAccommodation);
            amenitiesApiLogicService.create(accommodationApiRequest, storedAccommodation);

            roomApiLogicService.create(accommodationApiRequest, storedAccommodation);
        } catch (DataAccessException e) {
            log.warn("Database Error: " + e);
            return Header.ERROR("Database Error.");
        }

        return Header.OK();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Header put(Header<AccommodationApiRequest> request) throws Exception {
        AccommodationApiRequest accommodationApiRequest = request.getData();
        log.info("email: " + accommodationApiRequest.getEmail());

        try {
            Accommodation storedAccommodation = saveAccommodation(accommodationApiRequest);
            conveniencesApiLogicService.put(accommodationApiRequest.getConveniences(), storedAccommodation);
            locationApiLogicService.put(accommodationApiRequest, storedAccommodation);
            vacancyApiLogicService.put(accommodationApiRequest, storedAccommodation);
            bathroomApiLogicService.put(accommodationApiRequest, storedAccommodation);
            amenitiesApiLogicService.put(accommodationApiRequest.getAmenities(), storedAccommodation);

            roomApiLogicService.put(accommodationApiRequest, storedAccommodation);
        } catch (DataAccessException e) {
            log.warn("Database Error: " + e);
            return Header.ERROR("Database Error.");
        }

        return Header.OK();
    }

    private Accommodation saveAccommodation(AccommodationApiRequest accommodationApiRequest) {
        if (accommodationApiRequest.getId() == null) {
            log.info("Create Accommodation.");

            User user = userRepository.findByEmail(accommodationApiRequest.getEmail()).orElseThrow(NoSuchElementException::new);

            Accommodation accommodation = Accommodation.builder()
                    .title(accommodationApiRequest.getTitle())
                    .largeBuildingType(accommodationApiRequest.getLargeBuildingType())
                    .buildingType(accommodationApiRequest.getBuildingType())
                    .roomType(accommodationApiRequest.getRoomType())
                    .roomCount(accommodationApiRequest.getRoomCount())
                    .maximumGuestCount(accommodationApiRequest.getMaximumGuestCount())
                    .isSetUpForGuest(accommodationApiRequest.getIsSetUpForGuest() ? IsSetUpForGuest.YES : IsSetUpForGuest.NO)
                    .description(accommodationApiRequest.getDescription())
                    .user(user)
                    .build();
            return accommodationRepository.save(accommodation);
        } else {
            log.info("Modify Accommodation.");

            Accommodation accommodation = accommodationRepository.findById(accommodationApiRequest.getId()).orElseThrow(NoSuchElementException::new)
                    .setTitle(accommodationApiRequest.getTitle())
                    .setLargeBuildingType(accommodationApiRequest.getLargeBuildingType())
                    .setBuildingType(accommodationApiRequest.getBuildingType())
                    .setRoomType(accommodationApiRequest.getRoomType())
                    .setRoomCount(accommodationApiRequest.getRoomCount())
                    .setIsSetUpForGuest(accommodationApiRequest.getIsSetUpForGuest() ? IsSetUpForGuest.YES : IsSetUpForGuest.NO)
                    .setMaximumGuestCount(accommodationApiRequest.getMaximumGuestCount())
                    .setDescription(accommodationApiRequest.getDescription());
            return accommodationRepository.save(accommodation);
        }
    }

    private AccommodationApiResponse response(Accommodation accommodation) {

        List<Room> allRoomList = accommodation.getRoomList();

        List<Room> privateRoomList = allRoomList.stream().filter(room -> room.getNumber() != PUBLIC_BED).collect(Collectors.toList());
        List<Room> publicRoomList = allRoomList.stream().filter(room -> room.getNumber() == PUBLIC_BED).collect(Collectors.toList());

        List<BedListApiResponse> privateBedListApiResponseList = privateRoomList.stream().map(room -> Bed.makeResponse(room, ConstCommon.YES)).collect(Collectors.toList());
        List<BedListApiResponse> bedListApiResponseList = publicRoomList.stream().map(room -> Bed.makeResponse(room, ConstCommon.NO)).collect(Collectors.toList());

        Bathroom bathroom = accommodation.getBathroom();

        Amenities amenities = accommodation.getAmenities();
        AmenitiesApiResponse amenitiesApiResponse = Amenities.makeResponse(amenities);

        Location location = accommodation.getLocation();

        Conveniences conveniences = accommodation.getConveniences();
        ConveniencesApiResponse conveniencesApiResponse = Conveniences.makeResponse(conveniences);

        Vacancy vacancy = accommodation.getVacancy();

        List<PhotoApiResponse> photoApiResponseList = Photo.makeResponse(accommodation.getUser().getPhotoList());

        return AccommodationApiResponse.builder()
                .id(accommodation.getId())
                .title(accommodation.getTitle())
                .largeBuildingType(accommodation.getLargeBuildingType())
                .buildingType(accommodation.getBuildingType())
                .maximumGuestCount(accommodation.getMaximumGuestCount())
                .description(accommodation.getDescription())
                .roomType(accommodation.getRoomType())
                .isSetUpForGuest(ConstCommon.YES.equals(accommodation.getIsSetUpForGuest().getTitle()))
                .bedroomCount(accommodation.getRoomCount() + " Bedroom(s)")
                .bedList(privateBedListApiResponseList)
                .publicBedList(bedListApiResponseList.get(0).getBeds())
                .bathroomCount(bathroom.getCount())
                .bathroomType(bathroom.getType())
                .amenities(Common.getStringList(amenitiesApiResponse))
                .country(location.getCountry())
                .state(location.getState())
                .city(location.getCity())
                .streetAddress(location.getStreetAddress())
                .detailAddress(location.getDetailAddress())
                .postcode(location.getPostcode())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .conveniences(Common.getStringList(conveniencesApiResponse))
                .price(vacancy.getPrice())
                .startDate(vacancy.getStartDate())
                .endDate(vacancy.getEndDate())
                .photos(photoApiResponseList)
                .created_at(accommodation.getCreatedAt())
                .updated_at(accommodation.getUpdatedAt())
                .build();
    }
}
