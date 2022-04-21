package com.example.gradlebnbadminapi.util;

import com.example.gradlebnbadminapi.model.network.response.AmenitiesApiResponse;
import com.example.gradlebnbadminapi.model.network.response.ConveniencesApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Common {

    public static List<String> getStringList(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Boolean> result = objectMapper.convertValue(object, HashMap.class);
        return result.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toList()).stream().map(value -> {
            String responseValue = "";

            if (object instanceof AmenitiesApiResponse) {
                responseValue = AmenitiesApiResponse.convertValue(value);
            }

            if (object instanceof ConveniencesApiResponse) {
                responseValue = ConveniencesApiResponse.convertValue(value);
            }

            return responseValue;
        }).collect(Collectors.toList());
    }
}
