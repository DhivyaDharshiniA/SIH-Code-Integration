package com.example.springapp.controller;

import com.example.springapp.service.ICD11Service;
import com.example.springapp.service.ICD11Service.ICD11Entry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ICDController {

    private final ICD11Service icd11Service;

    public ICDController(ICD11Service icd11Service) {
        this.icd11Service = icd11Service;
    }

    /**
     * Search ICD-11 mock data
     * Example: http://localhost:8080/icd/search?keyword=fever
     */
    @GetMapping("/icd/search")
    public List<ICD11Entry> searchICD(@RequestParam String keyword) {
        return icd11Service.searchICD11ByKeyword(keyword);
    }
}
