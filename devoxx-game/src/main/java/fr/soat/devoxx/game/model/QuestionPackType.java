package fr.soat.devoxx.game.model;

import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public enum QuestionPackType {
    
    JOUR_1("07/04/2012",10,12),
    JOUR_2("08/04/2012",10,22),
    JOUR_3("09/04/2012",10,22);

    private Date packDate;
    private int startHour;
    private int endHour;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private QuestionPackType(String packDateStr, int startHour, int endHour) {
        try {
            this.packDate = sdf.parse(packDateStr);
        } catch (ParseException e) {
            this.packDate = new Date();
        }
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static QuestionPackType findPackByName(String packId) {
        for(QuestionPackType pack : values()) {
            if(pack.name().equals(packId)) {
                return pack;
            }
        }

        return JOUR_1;
    }

    public static QuestionPackType packForToday() {

        for(QuestionPackType currentPack : values()) {
            if(DateUtils.isSameDay(new Date(),currentPack.packDate)) {
                return currentPack;
            }
        }

        return JOUR_1;
    }

    public int getNbOfQuestionsToGenerate() {
        return endHour - startHour + 1;
    }

    public int getStartHour() {
        return startHour;
    }
}
