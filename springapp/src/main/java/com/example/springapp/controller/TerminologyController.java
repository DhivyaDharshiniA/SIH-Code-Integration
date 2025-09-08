package com.example.springapp.controller;

import com.example.springapp.dto.ConceptDtoo;
import com.example.springapp.model.NamasteTerm;
import com.example.springapp.service.TerminologyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TerminologyController {

    private final TerminologyService terminologyService;

    public TerminologyController(TerminologyService terminologyService) {
        this.terminologyService = terminologyService;
    }

    // Endpoint 1: Return clean list of NAMASTE concepts
   @GetMapping("/terminologies/codesystem")
public List<ConceptDtoo> getNamasteConcepts() {
    return terminologyService.createNamasteCodeSystem()
            .getConcept()
            .stream()
            .map((org.hl7.fhir.r4.model.CodeSystem.ConceptDefinitionComponent c) -> 
                 new ConceptDtoo(c.getCode(), c.getDisplay(), c.getDefinition()))
            .collect(Collectors.toList());
}


    // Endpoint 2: Search NAMASTE terms by keyword (auto-complete)
    @GetMapping("/terminologies/search")
    public List<NamasteTerm> searchNamasteTerms(@RequestParam("term") String term) {
        return terminologyService.getNamasteTerms().stream()
                .filter(t -> t.getNamcTerm().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }

    @GetMapping("/terminologies/searchByName")
public List<ConceptDtoo> searchNamasteByName(@RequestParam("name") String name) {
    return terminologyService.createNamasteCodeSystem()
            .getConcept()
            .stream()
            .filter(c -> c.getDisplay() != null && c.getDisplay().toLowerCase().contains(name.toLowerCase()))
            .map(c -> new ConceptDtoo(c.getCode(), c.getDisplay(), c.getDefinition()))
            .collect(Collectors.toList());
}
@GetMapping("/terminologies/searchConcept")
public List<ConceptDtoo> searchConcepts(@RequestParam("keyword") String keyword) {
    return terminologyService.searchConceptsByKeyword(keyword);
}
}
