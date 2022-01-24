package com.example.gradlebnbadminapi.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsPrivate {

    Yes(0, "YES"),
    NO(1, "NO");

    private final Integer id;
    private final String title;
}
