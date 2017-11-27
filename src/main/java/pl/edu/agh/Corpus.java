package pl.edu.agh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Corpus {

    private Map<String, Long> wordsOccurrences = new HashMap<>();
    private Map<String, Map<String, Long>> wordsCooccurrences = new HashMap<>();

    public Corpus(String path, int windowSize, String stimulus) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            Window window = new Window(windowSize);
            Stream.concat(Stream.generate(() -> null).limit(windowSize),
                    Stream.concat(
                            stream
                                    .map(Text::split)
                                    .flatMap(Arrays::stream)
                                    .map(Text::replace)
                                    .map(Text::stem)
                            ,
                            Stream.generate(() -> null).limit(windowSize)))
                    .peek(this::updateOccurrences)
                    .forEach(word -> updateCooccurencesAndMoveWindow(word, window, stimulus));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Corpus(List<String> words, int windowSize) {
        System.out.println("CALCULATED1");
        calculateWordOccurences(words);
        System.out.println("CALCULATED2");
        generate(words, windowSize);
        System.out.println("CALCULATED3");
    }

    private void calculateWordOccurences(Stream<String> words) {
        words.forEach(this::updateOccurrences);
    }

    private void calculateWordOccurences(List<String> words) {
        words.forEach(this::updateOccurrences);
    }

    private void updateOccurrences(Object word) {
        Long value = wordsOccurrences.getOrDefault(word, 0L);
        wordsOccurrences.put((String) word, value + 1);
    }

    private void generate(Stream<String> words, int windowSize) {
        Window window = new Window(windowSize);
        Stream.concat(Stream.generate(() -> null).limit(windowSize),
                Stream.concat(words,
                        Stream.generate(() -> null).limit(windowSize)))
                .forEach(word -> updateCooccurencesAndMoveWindow(word, window, ""));
    }


    private void generate(List<String> words, int windowSize) {
        Window window = new Window(windowSize);
        Stream.concat(Stream.generate(() -> null).limit(windowSize),
                Stream.concat(words.stream(),
                        Stream.generate(() -> null).limit(windowSize)))
                .forEach(word -> updateCooccurencesAndMoveWindow(word, window, ""));
    }

    private void updateCooccurencesAndMoveWindow(Object obj, Window window, String stimulus) {
        if (window.getCurrentWord() != null && window.getCurrentWord().equals(stimulus)) {
            for (String neighbourWord : window.getWords()) {
                if (neighbourWord != null && !neighbourWord.equals(window.getCurrentWord())) {
                    Map<String, Long> currentWordToNeighbours = wordsCooccurrences.getOrDefault(window.getCurrentWord(), new HashMap<>());
                    Long value = currentWordToNeighbours.getOrDefault(neighbourWord, 0L);
                    currentWordToNeighbours.put(neighbourWord, value + 1);
                    wordsCooccurrences.put(window.getCurrentWord(), currentWordToNeighbours);
                }
            }
        }

        window.move((String) obj);
    }

    public Map<String, Map<String, Long>> getWordsCooccurrences() {
        return wordsCooccurrences;
    }

    public Map<String, Long> getWordsOccurrences() {
        return wordsOccurrences;
    }

    public int getWordCount() {
        return this.wordsOccurrences.keySet().size();
    }
}
