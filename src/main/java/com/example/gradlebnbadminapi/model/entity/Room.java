package com.example.gradlebnbadminapi.model.entity;

import com.example.gradlebnbadminapi.model.enumClass.IsSetUpForGuest;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ToString(exclude = {"amenities", "bedList"})
public class Room {
//    `id` BIGINT NOT NULL AUTO_INCREMENT,
//    `number` INT NOT NULL,
//    `type` VARCHAR(50) NOT NULL,
//    `is_set_up_for_guest` TINYINT NOT NULL,
//    `bathroom_count` INT NOT NULL,
//    `bathroom_type` VARCHAR(50) NOT NULL,
//    `created_at` DATETIME NOT NULL,
//    `updated_at` DATETIME NULL,
//    `accommdation_id` BIGINT NOT NULL,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int number;

    @NotBlank
    private String type;

    private IsSetUpForGuest isSetUpForGuest;

    @NotNull
    private BigDecimal bathroomCount;

    @NotBlank
    private String bathroomType;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    private Accommodation accommodation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Bed> bedList;

    @OneToOne(mappedBy = "room")
    private Amenities amenities;
}
