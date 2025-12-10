package com.example.Web.Application.Project.domain.entities;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

   
    @Column(name = "total_words_typed" ,nullable = false)
    private Integer totalWordsTyped;

    
    @Column( name = "best_wpm" , nullable = false)
    private Double bestWpm;

    
    @Column( name = "average_accuracy" , nullable = false)
    private Double averageAccuracy;
}

