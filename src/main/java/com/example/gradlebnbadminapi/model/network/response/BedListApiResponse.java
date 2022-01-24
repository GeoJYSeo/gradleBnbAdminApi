package com.example.gradlebnbadminapi.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BedListApiResponse {

    private int id;

    private List<BedApiResponse> beds;
}
