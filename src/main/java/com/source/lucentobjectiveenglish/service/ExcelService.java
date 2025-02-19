package com.source.lucentobjectiveenglish.service;

import com.source.lucentobjectiveenglish.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    public List<Question> processExcelFile(InputStream file);

    List<Question> getAllQuestions();
}
