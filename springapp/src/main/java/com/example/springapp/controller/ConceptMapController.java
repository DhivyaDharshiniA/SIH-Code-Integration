package com.example.springapp.controller;

import com.example.springapp.model.ConceptMapEntry;
import com.example.springapp.service.ConceptMapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConceptMapController {

    private final ConceptMapService conceptMapService;

    public ConceptMapController(ConceptMapService conceptMapService) {
        this.conceptMapService = conceptMapService;
    }

    @GetMapping("/conceptmap")
    public List<ConceptMapEntry> getAllMappings() {
        return conceptMapService.getMappings();
    }

    @GetMapping("/conceptmap/searchNamaste")
    public List<ConceptMapEntry> searchByNamaste(@RequestParam String term) {
        return conceptMapService.searchByNamaste(term);
    }

    @GetMapping("/conceptmap/searchICD")
    public List<ConceptMapEntry> searchByICD(@RequestParam String term) {
        return conceptMapService.searchByICD(term);
    }
}
