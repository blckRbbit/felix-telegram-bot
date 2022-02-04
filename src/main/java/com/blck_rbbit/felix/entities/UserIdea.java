package com.blck_rbbit.felix.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "user_ideas")
public class UserIdea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idea_id", nullable = false)
    private Long id;
    
    @Column(name = "additionally")
    private String additionally;
    
    @Column(name = "ideaTitle")
    private String ideaTitle; // заголовок идеи
    
    @Column(name = "kindOfIdea")
    private String kindOfIdea; // техническая, организационная, охрана труда
    
    @Column(name = "idea")
    private String idea; // описание идеи
    
    @Column(name = "estimated_costs", columnDefinition = "integer default 0")
    private Integer estimatedCosts; // предполагаемые затраты
    
    @Column(name = "economic_effect", columnDefinition = "integer default 0")
    private Integer estimatedEconomicEffect; // предполагаемый экономический эффект
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
}
