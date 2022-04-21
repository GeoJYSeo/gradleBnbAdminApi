package com.example.gradlebnbadminapi.service;

import com.example.gradlebnbadminapi.model.entity.Accommodation;
import com.example.gradlebnbadminapi.model.entity.Location;
import com.example.gradlebnbadminapi.model.network.request.AccommodationApiRequest;
import com.example.gradlebnbadminapi.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Service
@Slf4j
public class LocationApiLogicServiceImpl implements LocationApiLogicService {

    @Value("${gcp.geo.key}")
    private String geoKey;

    @Value("${gcp.geo.url}")
    private String geoUrl;

    @Value("${gcp.place.url}")
    private String placeUrl;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public String getLocation(String latitude, String longitude) throws Exception {

        String reqUrl = makeReqUrl(geoUrl, "?latlng=" + latitude + "," + longitude + "&language=en&key=" + geoKey);

        log.info("Google Geometry Url: " + reqUrl);

        HttpResponse<String> response = request(reqUrl);

        log.info("Responded Geometry info: " + response.body());

        return response.body();
    }

    @Override
    public String getPlaces(String searchKeyword) throws Exception {
        log.info("places: " + searchKeyword);

        String reqUrl = makeReqUrl(placeUrl, "?key=" + geoKey + "&language=en&input=" + urlEncoding(searchKeyword));

        log.info("Google Places Url: " + reqUrl);

        HttpResponse<String> response = request(reqUrl);

        log.info("Responded Places info:" + response.body());

        return response.body();
    }

    @Override
    public String getPlacesWithPlaceId(String placeId) throws Exception {
        log.info("place_id: " + placeId);

        String reqUrl = makeReqUrl(geoUrl, "?key=" + geoKey + "&language=en&place_id=" + urlEncoding(placeId));

        log.info("Google Places Url: " + reqUrl);

        HttpResponse<String> response = request(reqUrl);

        log.info("Responded Places info:" + response.body());

        return response.body();
    }

    private String makeReqUrl(String baseUrl, String params) {
        return baseUrl + params;
    }

    private String urlEncoding(String param) {
        return URLEncoder.encode(param, StandardCharsets.UTF_8);
    }

    private HttpResponse<String> request(String reqUrl) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(reqUrl)).build();

        return client.send(request, ofString());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void create(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("Location builder.");

        Location location = Location.builder()
                .latitude(accommodationApiRequest.getLatitude())
                .longitude(accommodationApiRequest.getLongitude())
                .country(accommodationApiRequest.getCountry())
                .state(accommodationApiRequest.getState())
                .city(accommodationApiRequest.getCity())
                .streetAddress(accommodationApiRequest.getStreetAddress())
                .detailAddress(accommodationApiRequest.getDetailAddress())
                .postcode(accommodationApiRequest.getPostcode())
                .accommodation(accommodation)
                .build();

        log.info("Location save: " + location);
        locationRepository.save(location);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void put(AccommodationApiRequest accommodationApiRequest, Accommodation accommodation) throws Exception {
        log.info("Modify Location.");

        Location modifyLocation = accommodation.getLocation()
                .setLatitude(accommodationApiRequest.getLatitude())
                .setLongitude(accommodationApiRequest.getLongitude())
                .setCountry(accommodationApiRequest.getCountry())
                .setState(accommodationApiRequest.getState())
                .setCity(accommodationApiRequest.getCity())
                .setStreetAddress(accommodationApiRequest.getStreetAddress())
                .setDetailAddress(accommodationApiRequest.getDetailAddress())
                .setPostcode(accommodationApiRequest.getPostcode());

        log.info("Location modify: " + modifyLocation);
        locationRepository.save(modifyLocation);
    }
}
