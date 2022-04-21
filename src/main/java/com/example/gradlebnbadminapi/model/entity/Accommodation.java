package com.example.gradlebnbadminapi.model.entity;

import com.example.gradlebnbadminapi.model.enumClass.IsSetUpForGuest;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"conveniences", "location", "vacancy", "bathroom", "amenities", "roomList", "user"})
public class Accommodation {
//    `id` BIGINT NOT NULL AUTO_INCREMENT,
//    `title` VARCHAR(100) NOT NULL,
//    `large_building_type` VARCHAR(50) NOT NULL,
//    `building_type` VARCHAR(50) NOT NULL,
//    `maximum_guest_count` INT NOT NULL,
//    `description` VARCHAR(300) NOT NULL,
//    `created_at` DATETIME NOT NULL,
//    `updated_at` DATETIME NULL,
//    `user_id` BIGINT NOT NULL,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String largeBuildingType;

    @NotBlank
    private String buildingType;

    @NotBlank
    private String roomType;

    @NotNull
    private int roomCount;

    @NotNull
    private int maximumGuestCount;

    private IsSetUpForGuest isSetUpForGuest;

    @NotBlank
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "accommodation")
    private Conveniences conveniences;

    @OneToOne(mappedBy = "accommodation")
    private Location location;

    @OneToOne(mappedBy = "accommodation")
    private Vacancy vacancy;

    @OneToOne(mappedBy = "accommodation")
    private Bathroom bathroom;

    @OneToOne(mappedBy = "accommodation")
    private Amenities amenities;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodation")
    private List<Room> roomList;
}
