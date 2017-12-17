package pl.edu.agh.app;

import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;

import java.text.Normalizer;
import java.util.List;

public class Text {

    private static final String SPLIT_REGEX = "\\s+";
    private static final String COMMA = ",";
    private static final String REPLACE = "[,!?;:)(+=/\\&^*%$#@]";
    private static final String REPLACE2 = "[\"'._~/˝<>\\[\\]]";
    private static final String EMPTY = "";

    public static String[] split(String line) {
        return line.split(SPLIT_REGEX);
    }
    public static String[] splitByComma(String line) {
        return line.split(COMMA);
    }


    public static String replace(String word) {
        return word.replaceAll(REPLACE, EMPTY).replaceAll(REPLACE2, EMPTY);
    }

    public static String replacePolishLetters(String word) {
        word = word.replaceAll("ł", "l");
        return Normalizer.normalize(word, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String stem(String word) {
        PolishStemmer polishStemmer = new PolishStemmer();
        List<WordData> wordData = polishStemmer.lookup(word);
        if (wordData.size() > 0) {
            return wordData.get(0).getStem().toString();
        } else {
            return word;
        }
    }

}
