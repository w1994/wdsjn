package pl.edu.agh;

import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;

import java.util.List;


public class Text {

    private static final String SPLIT_REGEX = "\\s+";
    private static final String REPLACE = "[,!.?;:)(+=/\\&^*%$#@]";
    private static final String REPLACE2 = "[\"\"-/]";
    public static final String EMPTY = "";

    private String path;

    public Text(String path) {
        this.path = path;
    }


    public static String[] split(String line) {
        return line.split(SPLIT_REGEX);
    }

    public static String replace(String word) {
        return word.replaceAll(REPLACE, EMPTY).replaceAll(REPLACE2, EMPTY);
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
