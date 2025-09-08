package com.example.springapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NamasteTerm {

    private String srNo;
    private String namcId;
    private String namcCode;
    private String namcTerm;
    private String namcTermDiacritical;
    private String namcTermDevanagari;
    private String shortDefinition;
    private String longDefinition;
    private String ontologyBranches;

}
