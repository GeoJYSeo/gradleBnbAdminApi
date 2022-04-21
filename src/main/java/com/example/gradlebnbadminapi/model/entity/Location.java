package com.example.gradlebnbadminapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Location {
//    `id` BIGINT NOT NULL AUTO_INCREMENT,
//    `latitude` INT NOT NULL,
//    `longitude` INT NOT NULL,
//    `state` VARCHAR(50) NOT NULL,
//    `city` VARCHAR(50) NOT NULL,
//    `street_address` VARCHAR(50) NOT NULL,
//    `detail_address` VARCHAR(100) NOT NULL,
//    `postcode` VARCHAR(20) NOT NULL,
//    `created_at` DATETIME NOT NULL,
//    `updated_at` DATETIME NULL,
//    `accommdation_id` BIGINT NOT NULL,
//    `accommdation_user_id` BIGINT NOT NULL,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int latitude;

    @NotNull
    private int longitude;

    @NotBlank
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String city;

    @NotBlank
    private String streetAddress;

    @NotBlank
    private String detailAddress;

    @NotBlank
    private String postcode;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne
    private Accommodation accommodation;
}
