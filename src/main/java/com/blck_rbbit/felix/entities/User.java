package com.blck_rbbit.felix.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name="telegram_name")
    private String name;
    @Column(name="telegram_id")
    private Long telegramId;
    
    @OneToOne
    @JoinColumn(name = "details_id")
    private UserProfile details;
    
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<UserIdea> ideas;
    
    public User(Long telegramId, String name) {
        this.name = name;
        this.telegramId = telegramId;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getTelegramId() {
        return telegramId;
    }
    
    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }
    
    public UserProfile getDetails() {
        return details;
    }
    
    public void setDetails(UserProfile details) {
        this.details = details;
    }
    
    public List<UserIdea> getIdeas() {
        return ideas;
    }
    
    public void setIdeas(List<UserIdea> ideas) {
        this.ideas = ideas;
    }
    
}
