package com.example.gradlebnbadminapi.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsSetUpForGuest {

    NO(0, "NO"),
    YES(1, "YES");

    private final Integer id;
    private final String title;
}
