package com.source.lucentobjectiveenglish.service;

import com.source.lucentobjectiveenglish.entity.Question;
import com.source.lucentobjectiveenglish.repository.QuestionRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelServiceImp implements ExcelService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public List<Question> processExcelFile(InputStream inputStream) {
        List<Question> questionList = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0); // First sheet

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Skip header row

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Question question = new Question();

                question.setPyq(getCellValueAsString(row.getCell(1)));
                // Read Question Text
                Cell questionCell = row.getCell(2); // Column B
                question.setQuestion(getCellValueAsString(questionCell));

                // Read Options
                question.setOptionA(getCellValueAsString(row.getCell(3))); // C
                question.setOptionB(getCellValueAsString(row.getCell(4))); // D
                question.setOptionC(getCellValueAsString(row.getCell(5))); // E
                question.setOptionD(getCellValueAsString(row.getCell(6))); // F

                // Read Answer
//                question.setAnswer(getCellValueAsString(row.getCell(6))); // G

                // Read Correct Answer
                question.setCorrectAnswer(getCellValueAsString(row.getCell(7))); // H

                question.setExplanation(getCellValueAsString(row.getCell(8)));

                questionList.add(question);
            }
            questionRepository.saveAll(questionList);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionList;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue()); // Convert Numeric to String
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

}
