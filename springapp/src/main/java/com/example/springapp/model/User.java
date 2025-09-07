package com.example.springapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String abhaId;  // acts like username

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
