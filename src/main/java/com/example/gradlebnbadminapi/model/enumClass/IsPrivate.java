package com.example.gradlebnbadminapi.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsPrivate {

    NO(0, "NO"),
    Yes(1, "YES");

    private final Integer id;
    private final String title;
}
