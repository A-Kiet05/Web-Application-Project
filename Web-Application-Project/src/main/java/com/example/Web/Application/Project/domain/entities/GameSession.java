package com.example.Web.Application.Project.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.time.LocalDateTime;

import com.example.Web.Application.Project.domain.enums.Mode;


@Entity
@Table(name = "game_session")
@Getter
@Setter
@NoArgsConstructor
public class GameSession {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user; // nullable for guest


        private int duration; // 15s / 30s / 60s
        private int wordsTyped;
        private int correctWords;
        private int incorrectWords;


        private double rawWpm;
        private double wpm;
        private double accuracy;
        
        @Enumerated(EnumType.STRING)
        @Column(name = "mode", nullable = false)
        private Mode mode;

        @Column(name = "createdAt")
        private final LocalDateTime createdAt = LocalDateTime.now();
}