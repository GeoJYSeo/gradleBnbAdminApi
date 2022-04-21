package com.example.gradlebnbadminapi.model.entity;

import com.example.gradlebnbadminapi.model.enumClass.IsPrivate;
import com.example.gradlebnbadminapi.model.network.response.BedApiResponse;
import com.example.gradlebnbadminapi.model.network.response.BedListApiResponse;
import constant.ConstCommon;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    private Room room;

    public static BedListApiResponse makeResponse(Room room, String isPrivate) {
        System.out.println("------------------------------------");
        System.out.println(room.getId());
        System.out.println("------------------------------------");
        List<Bed> allBedList = room.getBedList();

        List<BedApiResponse> bedList = allBedList.stream()
                .filter(bed -> isPrivate.equals(bed.getIsPrivate().getTitle()))
                .map(privateBed -> BedApiResponse.builder()
                        .type(privateBed.getType())
                        .count(privateBed.getCount())
                        .build())
                .collect(Collectors.toList());

        return ConstCommon.YES.equals(isPrivate) ?
                BedListApiResponse.builder()
                    .id(Math.toIntExact(room.getNumber()))
                    .beds(bedList)
                    .build()
                :
                BedListApiResponse.builder()
                    .beds(bedList)
                    .build();
    }
}
