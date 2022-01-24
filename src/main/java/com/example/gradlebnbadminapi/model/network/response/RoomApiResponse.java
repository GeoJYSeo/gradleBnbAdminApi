package com.example.gradlebnbadminapi.model.network.response;

import com.example.gradlebnbadminapi.model.entity.Amenities;
import com.example.gradlebnbadminapi.model.entity.Bed;
import com.example.gradlebnbadminapi.model.network.request.BedApiRequest;
import com.example.gradlebnbadminapi.model.network.request.BedListApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomApiResponse {

    // Room
    private String roomType;

    private int bedroomCount;

    private boolean isSetUpForGuest;

    private BigDecimal bathroomCount;

    private String bathroomType;

    // Bed
    private List<BedListApiResponse> bedList;

    private List<BedApiResponse> publicBedList;

    // Amenities
    private List<String> amenities;
}
