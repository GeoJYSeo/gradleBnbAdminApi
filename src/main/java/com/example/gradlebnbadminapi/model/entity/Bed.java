package com.example.gradlebnbadminapi.model.entity;

import com.example.gradlebnbadminapi.model.enumClass.IsPrivate;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
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
public class Bed {
//    `id` BIGINT NOT NULL AUTO_INCREMENT,
//    `bed_type` VARCHAR(50) NOT NULL,
//    `count` INT NOT NULL,
//    `is_private` TINYINT NOT NULL,
//    `created_at` DATETIME NOT NULL,
//    `update_at` DATETIME NULL,
//    `room_id` BIGINT NOT NULL,
//    `room_accommdation_id` BIGINT NOT NULL,
//    `room_accommdation_user_id` BIGINT NOT NULL,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;

    @NotNull
    private int count;

    private IsPrivate isPrivate;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    private Room room;
}
