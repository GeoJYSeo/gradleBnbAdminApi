package com.example.gradlebnbadminapi.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    DELETE_PENDING(0, "DELETE_PENDING"),
    ACTIVATED(1, "ACTIVATED"),
    DELETED(9, "DELETED");

    private final Integer id;
    private final String title;
}
