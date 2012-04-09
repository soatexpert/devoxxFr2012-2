package fr.soat.devoxx.game.services;

import fr.soat.devoxx.game.exceptions.QuestionImportingError;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/import.xml" })
public class QuestionImporterTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    //@Ignore
    public void testImport() throws QuestionImportingError {
        QuestionsImporter importer = new QuestionsImporter("/test.xls",0);
        importer.setQuestionServices((QuestionServices)applicationContext.getBean(QuestionServices.class));

        importer.importQuestions();
    }
}
