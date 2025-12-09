package com.example.Web.Application.Project.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_achievement" ,  uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "achievement_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAchievement {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id" )
        private User user;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "achievement_id" )
        private Achievement achievement;

        @Column(name = "unlockedAt" , nullable = false)
        private final LocalDateTime unlockedAt = LocalDateTime.now();

}
