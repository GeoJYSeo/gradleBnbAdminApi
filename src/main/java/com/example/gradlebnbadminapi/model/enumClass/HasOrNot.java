package com.example.gradlebnbadminapi.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HasOrNot {

    HAS(0, "HAS"),
    NOT(1, "NOT");

    private final Integer id;
    private final String title;
}
