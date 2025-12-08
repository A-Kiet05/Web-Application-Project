package com.example.Web.Application.Project.domain.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category {

                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;


                @Column(nullable = false, unique = true)
                private String name;

               @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
               private List<Word> words;


                @Column(name = "description")
                private String description;
}
