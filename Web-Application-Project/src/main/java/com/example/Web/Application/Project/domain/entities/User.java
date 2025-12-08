package com.example.Web.Application.Project.domain.entities;

import java.util.List;

import com.example.Web.Application.Project.domain.enums.UserRole;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
@EqualsAndHashCode
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(name = "username" ,unique = true, nullable = false)
  private String username;


  @Column( name = "password" , nullable = false)
  private String password; // hashed

  @Column(name = "email" , unique = true , nullable = false)
  private String email;

  @Column(name = "full_name" , nullable = false)
  private String fullName;

  @Column(name = "role" , columnDefinition = "ENUM('USER', 'ADMIN')")
  private UserRole role;

  @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
  private List<Score> scores;

  @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
  private List<GameSession> gameSessions;

  @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
  private List<UserAchievement> userAchievements;

  @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
  private List<UserWord> userWords; 

  @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
  private UserStats userStats;
}
