package com.example.Web.Application.Project.domain.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "achievement")
@Getter
@Setter
@NoArgsConstructor
public class Achievement {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;


            @Column(name = "achievement_code" ,nullable = false, unique = true)
            private String code;
           
            @OneToMany(mappedBy = "achievement" , cascade = CascadeType.ALL , orphanRemoval = true)
            private List<UserAchievement> userAchievements;
           
            private String name;
            private String description;
            @Column(name = "`condition`" , nullable = false)
            private String conditionType; // ex: "WPM_50", "ACCURACY_95"
}
