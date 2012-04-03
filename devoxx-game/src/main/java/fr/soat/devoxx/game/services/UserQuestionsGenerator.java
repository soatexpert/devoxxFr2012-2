package fr.soat.devoxx.game.services;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionPackType;
import fr.soat.devoxx.game.model.UserQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class UserQuestionsGenerator {
    
    @Autowired
    private QuestionServices questionServices;
    
    public List<UserQuestion> generateQuestionsListForUser(DevoxxUser user) {
        List<UserQuestion> questions = new ArrayList<UserQuestion>();

        
        for(QuestionPackType currentPack : QuestionPackType.values()) {
            int nbOfQuestionsToGenerate = currentPack.getNbOfQuestionsToGenerate();
            
            List<Question> questionsForTheCurrentPack = questionServices.getQuestionsByPack(currentPack);
            
            List<Integer> indexes = getRandomIndexes(nbOfQuestionsToGenerate,questionsForTheCurrentPack.size());

            int hourOfAppearence = currentPack.getStartHour();
            for(Integer index : indexes) {
                questions.add(new UserQuestion(questionsForTheCurrentPack.get(index),user,hourOfAppearence));
                hourOfAppearence++;
            }
        }
        
        return questions;
    }

    private List<Integer> getRandomIndexes(int nbOfIndexes, int maxValue) {
        List<Integer> indexes = new ArrayList<Integer>();

        if (nbOfIndexes > 0 && maxValue > 0 && maxValue >= nbOfIndexes) {

            int indexesLeftTogenerate = nbOfIndexes;

            Random randomizer = new Random();
            while(indexesLeftTogenerate > 0) {
                final Integer randomIndex = randomizer.nextInt(maxValue);
                if(!indexes.contains(randomIndex)) {
                    indexes.add(randomIndex);
                    indexesLeftTogenerate--;
                }
            }
        } 
        return indexes;
    }


}
