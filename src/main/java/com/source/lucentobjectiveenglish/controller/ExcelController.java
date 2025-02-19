package com.source.lucentobjectiveenglish.controller;

import com.source.lucentobjectiveenglish.entity.Question;
import com.source.lucentobjectiveenglish.service.ExcelServiceImp;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    private final ExcelServiceImp excelService;

    public ExcelController(ExcelServiceImp excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is missing");
        }

        try {
            excelService.processExcelFile(file.getInputStream());
            return ResponseEntity.ok("File uploaded and processed successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }

    @GetMapping("/getAllQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions =excelService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }
}
