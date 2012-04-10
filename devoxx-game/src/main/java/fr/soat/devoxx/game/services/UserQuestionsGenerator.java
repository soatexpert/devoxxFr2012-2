package fr.soat.devoxx.game.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.soat.devoxx.game.model.DevoxxUser;
import fr.soat.devoxx.game.model.Question;
import fr.soat.devoxx.game.model.QuestionPackType;
import fr.soat.devoxx.game.model.UserQuestion;

@Component
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

            int indexesLeftToGenerate = nbOfIndexes;

            Random randomizer = new Random();
            while(indexesLeftToGenerate > 0) {
                final Integer randomIndex = randomizer.nextInt(maxValue);
                if(!indexes.contains(randomIndex)) {
                    indexes.add(randomIndex);
                    indexesLeftToGenerate--;
                }
            }
        } 
        return indexes;
    }
}