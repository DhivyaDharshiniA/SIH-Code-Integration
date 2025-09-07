package com.example.springapp.service;

import com.example.springapp.model.Patient;
import com.example.springapp.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Add a new patient
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Get patient by ID
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    // Get patient by ABHA ID (for /patient/me)
    public Optional<Patient> getPatientByAbhaId(String abhaId) {
        return patientRepository.findByAbhaId(abhaId);
    }

    // Update patient
    public Optional<Patient> updatePatient(Long id, Patient updated) {
        return patientRepository.findById(id).map(patient -> {
            patient.setName(updated.getName());
            patient.setAge(updated.getAge());
            patient.setGender(updated.getGender());
            patient.setContact(updated.getContact());
            patient.setMedicalHistory(updated.getMedicalHistory());
            return patientRepository.save(patient);
        });
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}
