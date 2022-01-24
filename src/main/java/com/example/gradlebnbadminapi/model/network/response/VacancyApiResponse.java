package com.example.gradlebnbadminapi.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacancyApiResponse {

    private String price;

    private String startDate;

    private String endDate;
}
