package com.example.gradlebnbadminapi.model.entity;

import com.example.gradlebnbadminapi.model.enumClass.HasOrNot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Conveniences {
//    `id` BIGINT NOT NULL AUTO_INCREMENT,
//    `kitchen` TINYINT NOT NULL,
//    `laundry_room` TINYINT NOT NULL,
//    `parking_lot` TINYINT NOT NULL,
//    `gym` TINYINT NOT NULL,
//    `swimming_pool` TINYINT NOT NULL,
//    `jacuzzi` TINYINT NOT NULL,
//    `created_at` DATETIME NOT NULL,
//    `updated_at` DATETIME NULL,
//    `accommdation_id` BIGINT NOT NULL,
//    `accommdation_user_id` BIGINT NOT NULL,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private HasOrNot kitchen;

    private HasOrNot laundryRoom;

    private HasOrNot parkingLot;

    private HasOrNot gym;

    private HasOrNot swimmingPool;

    private HasOrNot jacuzzi;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne
    private Accommodation accommodation;
}
