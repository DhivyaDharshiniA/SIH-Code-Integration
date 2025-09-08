// package com.example.springapp.service;

// import org.springframework.http.HttpHeaders;
// import org.springframework.stereotype.Service;
// import org.springframework.web.reactive.function.client.WebClient;

// import java.util.Base64;

// @Service
// public class ICD11Service {

//     private final WebClient webClient;

//     // WHO ICD-11 API credentials
//     private final String clientId = "9c1a70b8-74b7-4417-916f-9f8c24a5879b_76ba0915-ef2d-4e22-bbdd-6eb82ded3ac8";
//     private final String clientSecret = "oChWYuiJ/ZuCCNADOr2ubSOZcNNmZbur5m4BNlUwt2Y=";

//     public ICD11Service(WebClient.Builder builder) {
//         this.webClient = builder
//                 .baseUrl("https://id.who.int/icd/sandbox/release/11/mms")
//                 .build();
//     }

//     // Helper to generate Basic Auth header
//     private String basicAuthHeader() {
//         String auth = clientId + ":" + clientSecret;
//         return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
//     }

//     /**
//      * Search ICD-11 by keyword using Basic Auth
//      * Example endpoint: /json/search?q=fever&linelength=200
//      */
//     public String searchICD11ByKeyword(String keyword) {
//         return webClient.get()
//                 .uri(uriBuilder -> uriBuilder
//                         .path("/json/search")
//                         .queryParam("q", keyword)
//                         .queryParam("linelength", 200)
//                         .build())
//                 .header(HttpHeaders.AUTHORIZATION, basicAuthHeader())
//                 .header(HttpHeaders.ACCEPT, "application/json")
//                 .retrieve()
//                 .bodyToMono(String.class)
//                 .block(); // blocking for simplicity
//     }
// }


// package com.example.springapp.service;

// import org.springframework.stereotype.Service;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.http.HttpHeaders;
// import java.util.Base64;

// @Service
// public class ICD11Service {

//     private final WebClient webClient;

//     private final String clientId = "9c1a70b8-74b7-4417-916f-9f8c24a5879b_76ba0915-ef2d-4e22-bbdd-6eb82ded3ac8";
//     private final String clientSecret = "oChWYuiJ/ZuCCNADOr2ubSOZcNNmZbur5m4BNlUwt2Y=";

//     public ICD11Service(WebClient.Builder builder) {
//         this.webClient = builder.baseUrl("https://id.who.int/icd/release/11/mms").build();
//     }

//   private String basicAuthHeader() {
//     String auth = clientId + ":" + clientSecret;
//     return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
// }

//     public String searchICD11ByKeyword(String keyword) {
//         return webClient.get()
//                 .uri(uriBuilder -> uriBuilder
//                         .path("/json/search")
//                         .queryParam("q", keyword)
//                         .queryParam("linelength", 200) // optional
//                         .build())
//                 .header(HttpHeaders.AUTHORIZATION, basicAuthHeader())
//                 .retrieve()
//                 .bodyToMono(String.class)
//                 .block(); // for simplicity
//     }
// }
package com.example.springapp.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ICD11Service {

    private final List<ICD11Entry> icd11Entries = new ArrayList<>();

    public ICD11Service() {
        // Load Excel file from resources folder
        try (InputStream is = getClass().getResourceAsStream("/icd11_mock_30rows.xlsx");
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                Cell codeCell = row.getCell(0);
                Cell displayCell = row.getCell(1);

                String code = codeCell.getStringCellValue();
                String display = displayCell.getStringCellValue();

                icd11Entries.add(new ICD11Entry(code, display));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Search ICD-11 entries by keyword
    public List<ICD11Entry> searchICD11ByKeyword(String keyword) {
        List<ICD11Entry> results = new ArrayList<>();
        if (!StringUtils.hasText(keyword)) return results;

        String lowerKeyword = keyword.toLowerCase();
        for (ICD11Entry entry : icd11Entries) {
            if (entry.getCode().toLowerCase().contains(lowerKeyword) ||
                entry.getDisplay().toLowerCase().contains(lowerKeyword)) {
                results.add(entry);
            }
        }
        return results;
    }
    public List<ICD11Entry> getAllEntries() {
    return icd11Entries;
}


    // Inner class to represent an ICD-11 entry
    public static class ICD11Entry {
        private final String code;
        private final String display;

        public ICD11Entry(String code, String display) {
            this.code = code;
            this.display = display;
        }

        public String getCode() { return code; }
        public String getDisplay() { return display; }
    }
}
