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

    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> getPatientByAbhaId(String abhaId) {
        return patientRepository.findByUserAbhaId(abhaId);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient updatePatient(String abhaId, Patient updatedPatient) {
        return patientRepository.findByUserAbhaId(abhaId).map(patient -> {
            patient.setName(updatedPatient.getName());
            patient.setAge(updatedPatient.getAge());
            patient.setGender(updatedPatient.getGender());
            patient.setContact(updatedPatient.getContact());
            patient.setBloodGroup(updatedPatient.getBloodGroup());
            patient.setMedicalHistory(updatedPatient.getMedicalHistory());
            patient.setAllergies(updatedPatient.getAllergies());
            patient.setActiveMedications(updatedPatient.getActiveMedications());
            patient.setCurrentConditions(updatedPatient.getCurrentConditions());
            return patientRepository.save(patient);
        }).orElseThrow(() -> new RuntimeException("Patient not found with ABHA ID: " + abhaId));
    }
}
