package com.example.springapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConceptMapEntry {
    private String namasteCode;
    private String namasteTerm;
    private String icdCode;
    private String icdTerm;
}
