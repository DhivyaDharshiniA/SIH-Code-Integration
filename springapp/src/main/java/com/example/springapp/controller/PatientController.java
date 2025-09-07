package com.example.springapp.controller;

import com.example.springapp.model.Patient;
import com.example.springapp.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'GOVT')")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.registerPatient(patient));
    }

    @GetMapping("/{abhaId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'INSURER', 'GOVT', 'PATIENT')")
    public ResponseEntity<Patient> getPatient(@PathVariable String abhaId) {
        return patientService.getPatientByAbhaId(abhaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GOVT')")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PutMapping("/{abhaId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<Patient> updatePatient(@PathVariable String abhaId, @RequestBody Patient updatedPatient) {
        return ResponseEntity.ok(patientService.updatePatient(abhaId, updatedPatient));
    }

    
}
