package com.example.springapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String abhaId;  // ABHA-linked ID

    private String name;
    private int age;
    private String gender;
    private String contact;
    private String medicalHistory; // Optional field
}
