package sk3m3l1io.duisburg.memogame.services;

import java.util.Locale;

import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;

public final class ScoreDetailMessage {
    private static final String GREEK = "ell";
    private static final String GERMAN = "deu";

    private final int rank;
    private final ScoreData sd;

    public ScoreDetailMessage(ScoreData sd, int rank) {
        this.sd = sd;
        this.rank = rank + 1;
    }

    public String getLocalizedMessage() {
        String language = Locale.getDefault().getISO3Language();
        switch (language) {
            case GREEK:
                return getGreekMessage();
            case GERMAN:
                return getGermanMessage();
            default:
                return getDefaultMessage();
        }
    }

    private String getDefaultMessage() {
        return getFirstSentence() + " " + getSecondSentence() + " " + getThirdSentence();
    }

    private String getFirstSentence() {
        String gameLiteral = "games";
        if (sd.getGamesCompleted() == 1)
            gameLiteral = "game";
        if (sd.getAccuracyPercent() != 0) {
            return String.format("%s completed successfully %d %s with %.0f%% accuracy.",
                    getFirstName(), sd.getGamesCompleted(), gameLiteral, sd.getAccuracyPercent());
        } else {
            return String.format("%s completed successfully %d %s.",
                    getFirstName(), sd.getGamesCompleted(), gameLiteral);
        }
    }

    private String getSecondSentence() {
        if (sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "He/She gained honors because of excellence shown in \"Time\" and \"Survival\" modes.";
        } else if (sd.isTimeCompleted() && !sd.isSurvivalCompleted()) {
            return "He/She gained an honor because of excellence shown in \"Time\" mode.";
        } else if (!sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "He/She gained an honor because of excellence shown in \"Survival\" mode.";
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
        String gameLiteral = "παιχνίδια";
        if (sd.getGamesCompleted() == 1)
            gameLiteral = "παιχνίδι";
        if (sd.getAccuracyPercent() != 0) {
            return String.format("O/H %s ολοκλήρωσε με επιτυχία %d %s με ακρίβεια %.0f%%.",
                    getFirstName(), sd.getGamesCompleted(), gameLiteral, sd.getAccuracyPercent());
        } else {
            return String.format("O/H %s ολοκλήρωσε με επιτυχία %d %s.",
                    getFirstName(), sd.getGamesCompleted(), gameLiteral);
        }
    }

    private String getSecondSentenceInGreek() {
        if (sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "Κέρδισε επαίνους λόγω τέλειας επίδοσης στους γύρους \"Χρόνος\" και \"Επιβίωση\".";
        } else if (sd.isTimeCompleted() && !sd.isSurvivalCompleted()) {
            return "Κέρδισε έπαινο λόγω τέλειας επίδοσης στο γύρο \"Χρόνος\".";
        } else if (!sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "Κέρδισε έπαινο λόγω τέλειας επίδοσης στο γύρο \"Επιβίωση\".";
        } else {
            return "";
        }
    }

    private String getThirdSentenceInGreek() {
        return String.format("Αυτή τη στιγμή βρίσκεται στην %dη θέση.", rank);
    }

    private String getGermanMessage() {
        return getFirstSetenceInGerman() + " " + getSecondSetenceInGerman() + " " + getThirdSetenceInGerman();
    }

    private String getFirstSetenceInGerman() {
        String gameLiteral = "Spiele";
        if (sd.getGamesCompleted() == 1)
            gameLiteral = "Spiel";
        if (sd.getAccuracyPercent() != 0) {
            return String.format("%s beendete erfolgreich %d %s mit %.0f%% Richtigkeit.",
                    getFirstName(), sd.getGamesCompleted(), gameLiteral, sd.getAccuracyPercent());
        } else {
            return String.format("%s beendete erfolgreich %d %s.",
                    getFirstName(), sd.getGamesCompleted(), gameLiteral);
        }
    }

    private String getSecondSetenceInGerman() {
        if (sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "Er/Sie erhielt Auszeichnungen aufgrund der hervorragenden Leistungen in den Modi \"Zeit\" und \"Überleben.\"";
        } else if (sd.isTimeCompleted() && !sd.isSurvivalCompleted()) {
            return "Er/Sie erhielt eine Auszeichnung aufgrund der hervorragenden Leistung im Modus \"Zeit\".";
        } else if (!sd.isTimeCompleted() && sd.isSurvivalCompleted()) {
            return "Er/Sie erhielt eine Auszeichnung aufgrund der hervorragenden Leistung im Modus \"Überleben\".";
        } else {
            return "";
        }
    }

    private String getThirdSetenceInGerman() {
        return String.format("Aktuell Rang %d.", rank);
    }

    private String getFirstName() {
        return sd.getPlayer().getName().split(" ")[0];
    }
}
