package com.example.springapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // generates default constructor
@AllArgsConstructor // generates constructor with all fields
public class ConceptDtoo {
    private String code;
    private String display;
    private String definition;
}
