package com.example.gradlebnbadminapi.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    ACTIVATED(0, "ACTIVATED"),
    DELETE_PENDING(1, "DELETE_PENDING"),
    DELETED(9, "DELETED");

    private final Integer id;
    private final String title;
}
