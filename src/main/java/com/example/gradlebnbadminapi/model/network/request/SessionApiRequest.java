package com.example.gradlebnbadminapi.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionApiRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
