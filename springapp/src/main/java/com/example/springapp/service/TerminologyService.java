package com.example.springapp.service;

import com.example.springapp.dto.ConceptDtoo;
import com.example.springapp.model.NamasteTerm;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.hl7.fhir.r4.model.CodeSystem;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;


@Service
public class TerminologyService {

    // Step 1: Read all NAMASTE terms from Excel
    public List<NamasteTerm> getNamasteTerms() {
        List<NamasteTerm> terms = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream("/terminologies/NATIONAL AYURVEDA MORBIDITY CODES.xls");
             Workbook workbook = new HSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header row
                Row row = sheet.getRow(i);
                if (row == null) continue;

                NamasteTerm term = new NamasteTerm();
                term.setSrNo(getCellValue(row.getCell(0)));
                term.setNamcId(getCellValue(row.getCell(1)));
                term.setNamcCode(getCellValue(row.getCell(2)));
                term.setNamcTerm(getCellValue(row.getCell(3)));
                term.setNamcTermDiacritical(getCellValue(row.getCell(4)));
                term.setNamcTermDevanagari(getCellValue(row.getCell(5)));
                term.setShortDefinition(getCellValue(row.getCell(6)));
                term.setLongDefinition(getCellValue(row.getCell(7)));
                term.setOntologyBranches(getCellValue(row.getCell(8)));

                terms.add(term);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel file", e);
        }

        return terms;
    }

    // Helper method to handle different cell types
   private String getCellValue(Cell cell) {
    if (cell == null) return "";   // Instead of null, return empty string
    switch (cell.getCellType()) {
        case STRING: return cell.getStringCellValue().trim();
        case NUMERIC: return String.valueOf((int) cell.getNumericCellValue());
        case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
        case FORMULA: return cell.getCellFormula();
        case BLANK: return "";        // handle blank cells
        default: return "";
    }
}

public List<ConceptDtoo> searchConceptsByKeyword(String keyword) {
    return createNamasteCodeSystem().getConcept().stream()
            .filter(c -> c.getDisplay() != null && c.getDisplay().toLowerCase().contains(keyword.toLowerCase()))
            .map(c -> new ConceptDtoo(c.getCode(), c.getDisplay(), c.getDefinition()))
            .collect(Collectors.toList());
}
    // Step 2: Create FHIR CodeSystem from NAMASTE terms
   
public CodeSystem createNamasteCodeSystem() {
    List<NamasteTerm> terms = getNamasteTerms();

    CodeSystem cs = new CodeSystem();
    cs.setId("namaste");
    cs.setUrl("http://example.org/fhir/CodeSystem/namaste");
    cs.setName("NAMASTE Terminology");
    cs.setStatus(PublicationStatus.ACTIVE); // fixed
    cs.setContent(CodeSystem.CodeSystemContentMode.COMPLETE);

    for (NamasteTerm t : terms) {
        cs.addConcept()
          .setCode(t.getNamcCode())
          .setDisplay(t.getNamcTerm())
          .setDefinition(t.getLongDefinition());
    }

    return cs;
}

}
