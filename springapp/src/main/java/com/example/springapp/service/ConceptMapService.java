package com.example.springapp.service;

import com.example.springapp.model.ConceptMapEntry;
import com.example.springapp.model.NamasteTerm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConceptMapService {

    private final ICD11Service icd11Service;
    private final TerminologyService terminologyService;

    private final List<ConceptMapEntry> mappings = new ArrayList<>();

    public ConceptMapService(ICD11Service icd11Service, TerminologyService terminologyService) {
        this.icd11Service = icd11Service;
        this.terminologyService = terminologyService;

        // For demonstration, create mock mappings
        List<NamasteTerm> namasteTerms = terminologyService.getNamasteTerms();
        List<ICD11Service.ICD11Entry> icdTerms = icd11Service.getAllEntries(); // get all mock ICD entries

        // Simple mapping: match by term name contains (mock logic)
        for (NamasteTerm n : namasteTerms) {
            for (ICD11Service.ICD11Entry i : icdTerms) {
                String firstWord = n.getNamcTerm().split(" ")[0].toLowerCase();
                if (i.getDisplay().toLowerCase().contains(firstWord)) {
                    mappings.add(new ConceptMapEntry(
                            n.getNamcCode(),
                            n.getNamcTerm(),
                            i.getCode(),
                            i.getDisplay()
                    ));
                    break; // only one ICD mapping per NAMASTE term for simplicity
                }
            }
        }
    }

    public List<ConceptMapEntry> getMappings() {
        return mappings;
    }

    public List<ConceptMapEntry> searchByNamaste(String namasteCodeOrTerm) {
        String lower = namasteCodeOrTerm.toLowerCase();
        List<ConceptMapEntry> results = new ArrayList<>();
        for (ConceptMapEntry m : mappings) {
            if (m.getNamasteCode().toLowerCase().contains(lower) ||
                m.getNamasteTerm().toLowerCase().contains(lower)) {
                results.add(m);
            }
        }
        return results;
    }

    public List<ConceptMapEntry> searchByICD(String icdCodeOrTerm) {
        String lower = icdCodeOrTerm.toLowerCase();
        List<ConceptMapEntry> results = new ArrayList<>();
        for (ConceptMapEntry m : mappings) {
            if (m.getIcdCode().toLowerCase().contains(lower) ||
                m.getIcdTerm().toLowerCase().contains(lower)) {
                results.add(m);
            }
        }
        return results;
    }
}
