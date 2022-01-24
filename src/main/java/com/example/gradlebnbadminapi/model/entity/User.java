package com.example.gradlebnbadminapi.model.entity;

import com.example.gradlebnbadminapi.model.enumClass.UserAccess;
import com.example.gradlebnbadminapi.model.enumClass.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert // insert 시 null 값 제외(DB는 default 값)
@DynamicUpdate
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"photoList", "accommodationList"})
public class User {
//    `id` BIGINT NOT NULL AUTO_INCREMENT,
//    `email` VARCHAR(50) NOT NULL,
//    `password` VARCHAR(100) NOT NULL,
//    `firstname` VARCHAR(30) NULL DEFAULT NULL,
//    `lastname` VARCHAR(30) NULL DEFAULT NULL,
//    `birthday` VARCHAR(20) NULL DEFAULT NULL,
//    `status` VARCHAR(10) NOT NULL,
//    `access` TINYINT NOT NULL,
//    `last_login_at` DATETIME NULL,
//    `created_at` DATETIME NOT NULL,
//    `updated_at` DATETIME NULL DEFAULT NULL,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private String birthday;

    private UserStatus status;

    private UserAccess access;

    private LocalDateTime lastLoginAt;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Photo> photoList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Accommodation> accommodationList;
}
