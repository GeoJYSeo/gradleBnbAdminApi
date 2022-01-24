package com.example.gradlebnbadminapi.model.network.response;

import com.example.gradlebnbadminapi.model.enumClass.UserAccess;
import com.example.gradlebnbadminapi.model.enumClass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserApiResponse {

    private Long id;

    private String email;

    private String firstname;

    private String lastname;

    private String birthday;

    private String status;

    private String access;
}
