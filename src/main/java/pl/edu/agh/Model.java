package pl.edu.agh;

import org.nd4j.shade.jackson.core.JsonProcessingException;
import org.nd4j.shade.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    private static final double ALPHA = 0.66;
    private static final double BETA = 0.00002;
    private static final double GAMMA = 0.00002;

    private Corpus corpus;

    public Model(Corpus corpus) {
        this.corpus = corpus;
    }

    public void generateFrequencies(List<String> stimuluses) {

        Map<String, Map<String, Double>> results = new HashMap<>();
        System.out.println("generateFrequencies");
        for (String stimulus : stimuluses) {
            results.put(stimulus, new HashMap<>());
            for (String response : corpus.getWordsCooccurrences().get(stimulus).keySet()) {
                results.get(stimulus).put(response, calculateFrequencies(stimulus, response));
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(results));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
            return GAMMA * corpus.getWordCount();
        }
    }


}
