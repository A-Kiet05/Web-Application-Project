package com.example.Web.Application.Project.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user_word")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserWord {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id" , unique = true)
        private User user;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "word_id" , unique = true)
        private Word word;

        @Column(name = "mistake_count" ,  nullable = false)
        private Integer errorCount;
}