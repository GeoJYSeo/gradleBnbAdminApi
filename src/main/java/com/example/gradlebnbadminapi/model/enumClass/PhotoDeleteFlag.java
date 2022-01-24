package com.example.gradlebnbadminapi.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhotoDeleteFlag {

    STORED(0, "STORED"),
    DELETED(1, "DELETED");

    private final Integer id;
    private final String title;
}
