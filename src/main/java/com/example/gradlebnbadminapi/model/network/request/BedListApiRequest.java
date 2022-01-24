package com.example.gradlebnbadminapi.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BedListApiRequest {

    private Long id;

    private List<BedApiRequest> beds;
}
