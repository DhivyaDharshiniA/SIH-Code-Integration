package com.example.springapp.controller;

import com.example.springapp.model.Patient;
import com.example.springapp.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // -------------------------
    // Add new patient
    // Only Doctor or Admin
    // -------------------------
    @PostMapping
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Patient saved = patientService.addPatient(patient);
        return ResponseEntity.ok(saved);
    }

    // -------------------------
    // Get patient by ID
    // Doctor/Admin can access all
    // Patient can access own profile
    // -------------------------
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN','PATIENT')")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // -------------------------
    // Update patient info
    // Only Doctor or Admin
    // -------------------------
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient updated) {
        return patientService.updatePatient(id, updated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // -------------------------
    // List all patients
    // Only Doctor or Admin
    // -------------------------
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // -------------------------
    // Optional: Get logged-in patient profile
    // Patient can access own profile via ABHA ID
    // -------------------------
    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Patient> getMyProfile(@RequestParam String abhaId) {
        return patientService.getPatientByAbhaId(abhaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
