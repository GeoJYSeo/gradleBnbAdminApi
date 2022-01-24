package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.*;
import com.example.gradlebnbadminapi.model.enumClass.HasOrNot;
import com.example.gradlebnbadminapi.model.enumClass.IsPrivate;
import com.example.gradlebnbadminapi.model.enumClass.IsSetUpForGuest;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.request.BedApiRequest;
import com.example.gradlebnbadminapi.model.network.request.BedListApiRequest;
import com.example.gradlebnbadminapi.model.network.request.RoomApiRequest;
import com.example.gradlebnbadminapi.model.network.response.*;
import com.example.gradlebnbadminapi.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.ConstAmenities;
import constant.ConstConveniences;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomApiLogicServiceImpl implements RoomApiLogicService {

    private static final int PUBLIC_BED = 0;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AmenitiesRepository amenitiesRepository;

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private ConveniencesRepository conveniencesRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private BathroomRepository bathroomRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Header create(Header<RoomApiRequest> request) throws Exception {
        RoomApiRequest roomApiRequest = request.getData();
        log.info("email: " + roomApiRequest.getEmail());

        try {
            Accommodation storedAccommodation = saveAccommodation(roomApiRequest);
            saveConveniences(roomApiRequest, storedAccommodation);
            saveLocation(roomApiRequest, storedAccommodation);
            saveVacancy(roomApiRequest, storedAccommodation);

            List<Room> storedRoomList = savePrivateRoomBed(roomApiRequest, storedAccommodation);
            savePublicSpaceBed(roomApiRequest);
            saveBathroom(roomApiRequest, storedRoomList);
            saveAmenities(roomApiRequest, storedRoomList);
        } catch (DataAccessException e) {
            log.warn("Database Error: " + e);
            return Header.ERROR("Database Error.");
        }

        return Header.OK();
    }

    @Override
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

    private Accommodation saveAccommodation(RoomApiRequest roomApiRequest) {
        log.info("Conveniences builder.");

        User user = userRepository.findByEmail(roomApiRequest.getEmail()).orElseThrow(NoSuchElementException::new);

        Accommodation accommodation = Accommodation.builder()
                .title(roomApiRequest.getTitle())
                .largeBuildingType(roomApiRequest.getLargeBuildingType())
                .buildingType(roomApiRequest.getBuildingType())
                .roomType(roomApiRequest.getRoomType())
                .roomCount(roomApiRequest.getBedroomCount())
                .maximumGuestCount(roomApiRequest.getMaximumGuestCount())
                .isSetUpForGuest(roomApiRequest.getIsSetUpForGuest() ? IsSetUpForGuest.YES : IsSetUpForGuest.NO)
                .description(roomApiRequest.getDescription())
                .user(user)
                .build();

        return accommodationRepository.save(accommodation);
    }

    private void saveConveniences(RoomApiRequest roomApiRequest, Accommodation accommodation) {
        log.info("Conveniences builder.");

        Conveniences conveniences = Conveniences.builder()
                .kitchen(roomApiRequest.getConveniences().contains(ConstConveniences.KITCHEN) ? HasOrNot.HAS : HasOrNot.NOT)
                .laundryRoom(roomApiRequest.getConveniences().contains(ConstConveniences.LAUNDRY_ROOM) ? HasOrNot.HAS : HasOrNot.NOT)
                .parkingLot(roomApiRequest.getConveniences().contains(ConstConveniences.PARKING_LOT) ? HasOrNot.HAS : HasOrNot.NOT)
                .gym(roomApiRequest.getConveniences().contains(ConstConveniences.GYM) ? HasOrNot.HAS : HasOrNot.NOT)
                .swimmingPool(roomApiRequest.getConveniences().contains(ConstConveniences.SWIMMING_POOL) ? HasOrNot.HAS : HasOrNot.NOT)
                .jacuzzi(roomApiRequest.getConveniences().contains(ConstConveniences.JACUZZI) ? HasOrNot.HAS : HasOrNot.NOT)
                .accommodation(accommodation)
                .build();

        log.info("Conveniences save: " + conveniences);
        conveniencesRepository.save(conveniences);
    }

    private void saveLocation(RoomApiRequest roomApiRequest, Accommodation accommodation) {
        log.info("Location builder.");

        Location location = Location.builder()
                .latitude(roomApiRequest.getLatitude())
                .longitude(roomApiRequest.getLongitude())
                .country(roomApiRequest.getCountry())
                .state(roomApiRequest.getState())
                .city(roomApiRequest.getCity())
                .streetAddress(roomApiRequest.getStreetAddress())
                .detailAddress(roomApiRequest.getDetailAddress())
                .postcode(roomApiRequest.getPostcode())
                .accommodation(accommodation)
                .build();

        log.info("Location save: " + location);
        locationRepository.save(location);
    }

    private void saveVacancy(RoomApiRequest roomApiRequest, Accommodation accommodation) {
        log.info("Vacancy builder.");

        Vacancy vacancy = Vacancy.builder()
                .price(roomApiRequest.getPrice())
                .startDate(roomApiRequest.getStartDate())
                .endDate(roomApiRequest.getEndDate())
                .accommodation(accommodation)
                .build();

        log.info("Vacancy save: " + vacancy);
        vacancyRepository.save(vacancy);
    }

    private List<Room> savePrivateRoomBed(RoomApiRequest roomApiRequest, Accommodation accommodation) {
        log.info("Private bed builder.");

        List<BedListApiRequest> reqBedList = roomApiRequest.getBedList();

        return reqBedList.stream().map(beds -> {
            Room room = Room.builder()
                    .number(Math.toIntExact((Long) beds.getId()))
                    .accommodation(accommodation)
                    .build();
            roomRepository.save(room);

            beds.getBeds().forEach(privateBed -> {
                Bed newBed = Bed.builder()
                    .type(privateBed.getType())
                    .count(privateBed.getCount())
                    .isPrivate(IsPrivate.Yes)
                    .room(room)
                    .build();

                log.info("Private bed save: " + newBed);
                bedRepository.save(newBed);

            });
            return room;
        }).collect(Collectors.toList());
    }

    private void savePublicSpaceBed(RoomApiRequest roomApiRequest) {
        log.info("Public bed builder");

        Room room = roomRepository.findByNumber(PUBLIC_BED);

        List<BedApiRequest> bedList = roomApiRequest.getPublicBedList();

        bedList.forEach(publicBed -> {
            Bed bed = Bed.builder()
                    .type(publicBed.getType())
                    .count(publicBed.getCount())
                    .isPrivate(IsPrivate.NO)
                    .room(room)
                    .build();

            log.info("Public bed save: " + bed);
            bedRepository.save(bed);
        });
    }

    private void saveBathroom(RoomApiRequest roomApiRequest, List<Room> roomList) {
        log.info("Bathroom builder");

        roomList.forEach(room -> {
            Bathroom bathroom = Bathroom.builder()
                    .count(roomApiRequest.getBathroomCount())
                    .type(roomApiRequest.getBathroomType())
                    .room(room)
                    .build();

            log.info("Bathroom save");
            bathroomRepository.save(bathroom);
        });
    }

    private void saveAmenities(RoomApiRequest roomApiRequest, List<Room> roomList) {
        log.info("Amenities builder.");

        roomList.forEach(room -> {
            Amenities amenities = Amenities.builder()
                    .wifi(roomApiRequest.getAmenities().contains(ConstAmenities.WIFI) ? HasOrNot.HAS : HasOrNot.NOT)
                    .tv(roomApiRequest.getAmenities().contains(ConstAmenities.TV) ? HasOrNot.HAS : HasOrNot.NOT)
                    .heater(roomApiRequest.getAmenities().contains(ConstAmenities.HEATER) ? HasOrNot.HAS : HasOrNot.NOT)
                    .airConditioner(roomApiRequest.getAmenities().contains(ConstAmenities.AIR_CONDITIONER) ? HasOrNot.HAS : HasOrNot.NOT)
                    .iron(roomApiRequest.getAmenities().contains(ConstAmenities.IRON) ? HasOrNot.HAS : HasOrNot.NOT)
                    .shampoo(roomApiRequest.getAmenities().contains(ConstAmenities.SHAMPOO) ? HasOrNot.HAS : HasOrNot.NOT)
                    .hairDryer(roomApiRequest.getAmenities().contains(ConstAmenities.HAIR_DRYER) ? HasOrNot.HAS : HasOrNot.NOT)
                    .breakfast(roomApiRequest.getAmenities().contains(ConstAmenities.BREAKFAST) ? HasOrNot.HAS : HasOrNot.NOT)
                    .businessSpace(roomApiRequest.getAmenities().contains(ConstAmenities.BUSINESS_SPACE) ? HasOrNot.HAS : HasOrNot.NOT)
                    .fireplace(roomApiRequest.getAmenities().contains(ConstAmenities.FIREPLACE) ? HasOrNot.HAS : HasOrNot.NOT)
                    .closet(roomApiRequest.getAmenities().contains(ConstAmenities.CLOSET) ? HasOrNot.HAS : HasOrNot.NOT)
                    .guestEntrance(roomApiRequest.getAmenities().contains(ConstAmenities.GUEST_ENTRANCE) ? HasOrNot.HAS : HasOrNot.NOT)
                    .room(room)
                    .build();

            log.info("Amenities save: " + amenities);
            amenitiesRepository.save(amenities);
        });
    }

    private AccommodationApiResponse response(Accommodation accommodation) {
        List<RoomApiResponse> roomApiResponseList = accommodation.getRoomList().stream().map(room -> {
            List<Bed> allBedList = room.getBedList();

            List<BedApiResponse> publicBedList = allBedList.stream()
                    .filter(bed -> "NO".equals(bed.getIsPrivate().getTitle()))
                    .map(publicBed -> BedApiResponse.builder()
                            .type(publicBed.getType())
                            .count(publicBed.getCount())
                            .build())
                    .collect(Collectors.toList());

            List<BedApiResponse> privateBedList = allBedList.stream()
                            .filter(bed -> "YES".equals(bed.getIsPrivate().getTitle()))
                            .map(privateBed -> BedApiResponse.builder()
                                    .type(privateBed.getType())
                                    .count(privateBed.getCount())
                                    .build())
                            .collect(Collectors.toList());

            List<BedListApiResponse> bedList = privateBedList.stream()
                    .map(publicBed -> BedListApiResponse.builder()
                            .id(room.getNumber())
                            .beds(privateBedList)
                            .build()).collect(Collectors.toList());

            Amenities amenities = room.getAmenities();
            AmenitiesApiResponse amenitiesApiResponse = AmenitiesApiResponse.builder()
                    .wifi("HAS".equals(amenities.getWifi().getTitle()))
                    .tv("HAS".equals(amenities.getTv().getTitle()))
                    .heater("HAS".equals(amenities.getHeater().getTitle()))
                    .airConditioner("HAS".equals(amenities.getAirConditioner().getTitle()))
                    .iron("HAS".equals(amenities.getIron().getTitle()))
                    .shampoo("HAS".equals(amenities.getShampoo().getTitle()))
                    .hairDryer("HAS".equals(amenities.getHairDryer().getTitle()))
                    .breakfast("HAS".equals(amenities.getBreakfast().getTitle()))
                    .businessSpace("HAS".equals(amenities.getBusinessSpace().getTitle()))
                    .fireplace("HAS".equals(amenities.getFireplace().getTitle()))
                    .closet("HAS".equals(amenities.getCloset().getTitle()))
                    .guestEntrance("HAS".equals(amenities.getGuestEntrance().getTitle()))
                    .build();


            return RoomApiResponse.builder()
                    .bedList(bedList)
                    .publicBedList(publicBedList)
                    .amenities(getAmenitiesList(amenitiesApiResponse))
                    .build();
        }).collect(Collectors.toList());

        Location location = accommodation.getLocation();

        Conveniences conveniences = accommodation.getConveniences();
        ConveniencesApiResponse conveniencesApiResponse = ConveniencesApiResponse.builder()
                .kitchen("HAS".equals(conveniences.getKitchen().getTitle()))
                .laundryRoom("HAS".equals(conveniences.getLaundryRoom().getTitle()))
                .parkingLot("HAS".equals(conveniences.getParkingLot().getTitle()))
                .gym("HAS".equals(conveniences.getGym().getTitle()))
                .swimmingPool("HAS".equals(conveniences.getSwimmingPool().getTitle()))
                .jacuzzi("HAS".equals(conveniences.getJacuzzi().getTitle()))
                .build();

        Vacancy vacancy = accommodation.getVacancy();

        List<PhotoApiResponse> photoApiResponseList = accommodation.getUser().getPhotoList().stream()
                .map(photo -> PhotoApiResponse.builder()
                        .url(photo.getUrl())
                        .build())
                .collect(Collectors.toList());

        return AccommodationApiResponse.builder()
                .id(accommodation.getId())
                .title(accommodation.getTitle())
                .largeBuildingType(accommodation.getLargeBuildingType())
                .buildingType(accommodation.getBuildingType())
                .maximumGuestCount(accommodation.getMaximumGuestCount())
                .description(accommodation.getDescription())
                .bedroomCount(accommodation.getRoomCount())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .country(location.getCountry())
                .state(location.getState())
                .city(location.getCity())
                .streetAddress(location.getStreetAddress())
                .detailAddress(location.getDetailAddress())
                .postcode(location.getPostcode())
                .conveniences(getAmenitiesList(conveniencesApiResponse))
                .price(vacancy.getPrice())
                .startDate(vacancy.getStartDate())
                .endDate(vacancy.getEndDate())
                .photos(photoApiResponseList)
                .created_at(accommodation.getCreatedAt())
                .updated_at(accommodation.getUpdatedAt())
                .build();
    }

    public List<String> getAmenitiesList(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Boolean> result = objectMapper.convertValue(object, HashMap.class);
        return result.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
