package com.example.Web.Application.Project.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_achievement")
@Getter
@Setter
@NoArgsConstructor
public class UserAchievement {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id" , unique = true)
        private User user;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "achievement_id" , unique = true)
        private Achievement achievement;

        @Column(name = "unlockedAt" , nullable = false)
        private final LocalDateTime unlockedAt = LocalDateTime.now();

}
