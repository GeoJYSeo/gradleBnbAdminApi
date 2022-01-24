package com.example.gradlebnbadminapi.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert // insert 시 null 값 제외(DB는 default 값)
@DynamicUpdate
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Vacancy {
//    `id` BIGINT NOT NULL AUTO_INCREMENT,
//    `price` DECIMAL(10,2) NOT NULL,
//    `start_date` VARCHAR(15) NOT NULL,
//    `end_date` VARCHAR(15) NOT NULL,
//    `created_at` DATETIME NOT NULL,
//    `updated_at` DATETIME NULL,
//    `accommodation_id` BIGINT NOT NULL,
//    `accommodation_user_id` BIGINT NOT NULL,

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String price;

    @NotBlank
    private String startDate;

    @NotBlank
    private String endDate;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne
    private Accommodation accommodation;
}
