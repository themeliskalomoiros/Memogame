package sk3m3l1io.duisburg.memogame.services;

import java.util.Locale;

import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;

public final class ScoreDetailMessage {
    private static final String GREEK = "ell";

    private final int rank;
    private final ScoreData sd;

    public ScoreDetailMessage(ScoreData sd, int rank) {
        this.sd = sd;
        this.rank = rank;
    }

    public String getLocalizedMessage() {
        String language = Locale.getDefault().getISO3Language();
        switch (language) {
            case GREEK:
                return getGreekMessage();
            // TODO: implement a german message
            default:
                return getDefaultMessage();
        }
    }

    private String getDefaultMessage() {
        return getFirstSentence() + " " + getSecondSentence() + " " + getThirdSentence();
    }

    private String getFirstSentence() {
        return String.format("%s completed successfully %d games with %.1f%% accuracy.",
                getFirstName(), sd.getGamesCompleted(), sd.getAccuracyPercent());
    }

    private String getSecondSentence() {
        if (sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "He/She gained two badges because of excellence shown in Time and Survival modes.";
        } else if (sd.isTimeCompleted() && !sd.isSurvivalCompleted()) {
            return "He/She gained a badge because of excellence shown in Time mode.";
        } else if (!sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "He/She gained a badge because of excellence shown in Survival mode.";
        } else {
            return "";
        }
    }

    private String getThirdSentence() {
        return String.format("Currently ranks %d.", rank);
    }

    private String getGreekMessage() {
        return getFirstSentenceInGreek() + " " + getSecondSentenceInGreek() + " " + getThirdSentenceInGreek();
    }

    private String getFirstSentenceInGreek() {
        return String.format("O/H %s ολοκλήρωσε με επιτυχία %d παιχνίδια με ακρίβεια %.1f%%.",
                getFirstName(), sd.getGamesCompleted(), sd.getAccuracyPercent());
    }

    private String getSecondSentenceInGreek() {
        if (sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "Κέρδισε δύο παράσημα λόγω τέλειας επίδοσης στους γύρους Χρόνου και Επιβίωσης.";
        } else if (sd.isTimeCompleted() && !sd.isSurvivalCompleted()) {
            return "Κέρδισε ένα παράσημο λόγω τέλειας επίδοσης στο γύρο Χρόνος.";
        } else if (!sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "Κέρδισε ένα παράσημο λόγω τέλειας επίδοσης στο γύρο Επιβίωση.";
        } else {
            return "";
        }
    }

    private String getThirdSentenceInGreek() {
        return String.format("Αυτή τη στιγμή βρίσκεται στην θέση %dη θέση.", rank);
    }

    private String getFirstName() {
        return sd.getPlayer().getName().split(" ")[0];
    }
}
