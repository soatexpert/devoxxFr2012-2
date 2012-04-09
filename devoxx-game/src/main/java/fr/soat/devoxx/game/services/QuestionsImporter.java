package fr.soat.devoxx.game.services;

import fr.soat.devoxx.game.exceptions.QuestionImportingError;
import fr.soat.devoxx.game.model.QuestionPackType;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionChoice;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class QuestionsImporter {

    private String questionFile;
    private final int startingRow;

    private QuestionServices questionServices;

    public QuestionsImporter(String questionFile, int startingRow) {
        this.questionFile = questionFile;
        this.startingRow = startingRow;
    }

    public void importQuestions() throws QuestionImportingError {
        try {
            HSSFWorkbook wb = new HSSFWorkbook(QuestionsImporter.class.getResourceAsStream(questionFile));

            final HSSFSheet sheet = wb.getSheetAt(0);

            final int nbOfRows = sheet.getPhysicalNumberOfRows();

            for (int r = startingRow; r < nbOfRows; r++) {
                HSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                final int questionId = extractIntCellValueAtIndex(row, 0);
                final String questionLabel = extractCellValueAtIndex(row, 1);
                final String answer1 = extractCellValueAtIndex(row, 2);
                final String answer2 = extractCellValueAtIndex(row, 3);
                final String answer3 = extractCellValueAtIndex(row, 4);
                final String answer4 = extractCellValueAtIndex(row, 5);
                final int correctAnswerIndex = extractIntCellValueAtIndex(row, 6);
                final String packName = extractCellValueAtIndex(row,7);

                final Question question = buildQuestion(
                        questionId,
                        questionLabel, 
                        answer1, 
                        answer2, 
                        answer3, 
                        answer4, 
                        correctAnswerIndex);

                question.setPack(QuestionPackType.findPackByName(packName));
                
                questionServices.saveQuestion(question);
            }

        } catch (IOException e) {
            throw new QuestionImportingError(e.getMessage(),e);
        }
    }

    private Question buildQuestion(int questionId, String questionLabel, String answer1, String answer2, String answer3, String answer4, int correctAnswerIndex) {
        Long questionIdAsLong = Long.valueOf(questionId);
        Question question = questionServices.getQuestionById(questionIdAsLong);
        if(question == null) {
            question = new Question();
        }
        question.setIdQuestion(questionIdAsLong);
        question.setQuestionLabel(questionLabel);

        List<QuestionChoice> choices = new ArrayList<QuestionChoice>();
        choices.add(buildChoice(answer1, questionIdAsLong, 1));
        choices.add(buildChoice(answer2, questionIdAsLong, 2));
        choices.add(buildChoice(answer3, questionIdAsLong, 3));
        choices.add(buildChoice(answer4, questionIdAsLong, 4));
        question.setChoices(choices);

        question.setCorrectAnswer(choices.get(correctAnswerIndex - 1));
        return question;
    }

    private QuestionChoice buildChoice(String answer, long questionId, long answerId) {
        final QuestionChoice choice = new QuestionChoice();
        choice.setQuestionChoiceId(questionId*10+answerId);
        choice.setChoiceLabel(answer);
        return choice;
    }

    private String extractCellValueAtIndex(HSSFRow row, int index) {
        final HSSFCell cell = row.getCell(index);
        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            DecimalFormat df = new DecimalFormat("#.####");
            return df.format(cell.getNumericCellValue());
        }
        
        return cell.getStringCellValue();
    }

    private int extractIntCellValueAtIndex(HSSFRow row, int index) {
        return (int)Math.floor(row.getCell(index).getNumericCellValue());
    }

    public void setQuestionServices(QuestionServices questionServices) {
        this.questionServices = questionServices;
    }
}
