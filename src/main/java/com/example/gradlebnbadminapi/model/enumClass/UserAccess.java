package com.example.gradlebnbadminapi.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserAccess {

    ADMINISTRATOR(9, "ADMINISTRATOR"),
    MANAGER(8, "MANAGER");

    private final Integer id;
    private final String title;
}
