package pl.edu.agh.app;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Model {

    private static final double ALPHA = 0.66;
    private static final double BETA = 0.00002;
    private static final double GAMMA = 0.00002;

    private Corpus corpus;

    public Model(Corpus corpus) {
        this.corpus = corpus;
    }

    public void generateFrequencies(List<String> stimuluses) {

        Map<String, List<Word>> results = new HashMap<>();

        for (String stimulus : stimuluses) {
            results.put(stimulus, new ArrayList<>());
            for (String responseWord : corpus.getWordsCooccurrences().get(stimulus).keySet()) {
                results.get(stimulus).add(new Word(responseWord, calculateFrequencies(stimulus, responseWord)));
            }
        }


        for (String stimulus : stimuluses) {
            try {
                List<Word> words = results.get(stimulus);
                words.sort(Collections.reverseOrder());
                Files.write(Paths.get("C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\02.12.2017\\"+stimulus+".txt"), words.stream().map(Object::toString).collect(Collectors.toList()));
                words.forEach(word -> System.out.println("\t" + word.getWord() + " : " + word.getFrequency()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private Double calculateFrequencies(String stimulus, String response) {
        return Math.pow(corpus.getWordCount(), ALPHA) / corpus.getWordsOccurrences().get(stimulus)
                * weakenedFunction(stimulus, response);
    }

    private double weakenedFunction(String stimulus, String response) {
        Long Hij = corpus.getWordsCooccurrences().get(stimulus).get(response);
        if (corpus.getWordsOccurrences().get(response) > BETA * corpus.getWordCount()) {
            return Hij / Math.pow(corpus.getWordsOccurrences().get(response), ALPHA);
        } else {
            return Hij / GAMMA * corpus.getWordCount();
        }
    }

}
