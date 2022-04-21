package com.example.gradlebnbadminapi.model.entity;

import com.example.gradlebnbadminapi.model.enumClass.PhotoDeleteFlag;
import com.example.gradlebnbadminapi.model.network.response.PhotoApiResponse;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = "user")
public class Photo {
//    `id` BIGINT NOT NULL AUTO_INCREMENT,
//    `name` VARCHAR(100) NOT NULL,
//    `size` INT NOT NULL,
//    `url` VARCHAR(200) NOT NULL,
//    `del_flg` TINYINT NOT NULL,
//    `created_at` DATETIME NOT NULL,
//    `updated_at` DATETIME NULL DEFAULT NULL,
//    `user_id` BIGINT NOT NULL,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Long size;

    private String url;

    private PhotoDeleteFlag delFlg;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime UpdatedAt;

    @ManyToOne
    private User user;

    public static List<PhotoApiResponse> makeResponse(List<Photo> photoList) {
        return photoList.stream().map(photo -> PhotoApiResponse.builder()
                        .id(photo.getId())
                        .url(photo.getUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
