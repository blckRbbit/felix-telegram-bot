package com.blck_rbbit.felix.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "telegram_name")
    private String login;
    
    @Column(name = "password")
    private String password;
    
}
