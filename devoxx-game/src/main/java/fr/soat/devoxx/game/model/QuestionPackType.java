package fr.soat.devoxx.game.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public enum QuestionPackType {
    
    JOUR_1("02/04/2011",10,16),
    JOUR_2("03/04/2011",10,16),
    JOUR_3("04/04/2011",10,12);
    
    private Date packDate;
    private int StartHour;
    private int endHour;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private QuestionPackType(String packDateStr, int startHour, int endHour) {
        try {
            this.packDate = sdf.parse(packDateStr);
        } catch (ParseException e) {
            this.packDate = new Date();
        }
        StartHour = startHour;
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

}
