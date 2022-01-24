package com.example.gradlebnbadminapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Slf4j
@Service
public class LocationApiLogicServiceImpl implements LocationApiLogicService {

    @Value("${gcp.geo.key}")
    private String geoKey;

    @Value("${gcp.geo.url}")
    private String geoUrl;

    @Value("${gcp.place.url}")
    private String placeUrl;

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
}
