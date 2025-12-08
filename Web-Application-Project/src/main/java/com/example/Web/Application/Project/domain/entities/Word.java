package com.example.Web.Application.Project.domain.entities;



import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "words")
@EqualsAndHashCode
public class Word {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;


   @Column(nullable = false)
   private String word;
    
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "category_id")
   private Category category;

   @OneToMany(mappedBy = "word" , cascade = CascadeType.ALL , orphanRemoval = true)
   private List<UserWord> userWords;

}
