package com.example.springapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user; // Link to User entity

    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private String contact;
    private String medicalHistory;

    @ElementCollection
    private List<String> allergies;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @ElementCollection
    private List<String> activeMedications;

    @ElementCollection
    private List<String> currentConditions;
}
