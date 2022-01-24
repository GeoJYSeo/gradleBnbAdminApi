package com.example.gradlebnbadminapi.model.network;

import com.example.gradlebnbadminapi.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Header<T> {

    private LocalDateTime transactionTime;

    private String resultCode;

    private String description;

    @Valid
    private T data;

    private Pagination pagination;

    // OK
    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK() {
        return (Header<T> )Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    // OK DATA
    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(T data) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK with data")
                .data(data)
                .build();

    }

    // OK DATA Pagination
    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(T data, Pagination pagination) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK with data")
                .data(data)
                .pagination(pagination)
                .build();

    }

    // Error
    @SuppressWarnings("unchecked")
    public static <T> Header<T> ERROR(String description) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();

    }
}
