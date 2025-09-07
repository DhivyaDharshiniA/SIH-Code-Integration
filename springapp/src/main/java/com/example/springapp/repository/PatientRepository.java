package com.example.springapp.repository;

import com.example.springapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUserAbhaId(String abhaId);
}
