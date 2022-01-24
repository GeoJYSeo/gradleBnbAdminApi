package com.example.gradlebnbadminapi.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionApiResponse {

    private String accessToken;

    private UserApiResponse userApiResponse;
}
