package pl.edu.agh.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private String path;
    private Window window;
    private List<String> stimuluses;

    public Corpus(String path, int windowSize, List<String> stimuluses) {
        this.path = path;
        this.window = new Window(windowSize);
        this.stimuluses = stimuluses;
    }

    public void generate() {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            Stream.concat(Stream.generate(() -> null).limit(window.getWindowSize()),
                    Stream.concat(stream.filter(line -> !line.startsWith("#"))
                                        .map(Text::split)
                                        .flatMap(Arrays::stream)
                                        .map(Text::replace)
                                        .map(String::toLowerCase)
                                        .map(Text::stem)
                                        .map(Text::replacePolishLetters),
                                  Stream.generate(() -> null).limit(window.getWindowSize())))
                    .peek(this::updateOccurrences)
                    .forEach(word -> updateCooccurencesAndMoveWindow(word));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateOccurrences(Object word) {
        Long value = wordsOccurrences.getOrDefault(word, 0L);
        wordsOccurrences.put((String) word, value + 1);
    }

    private void updateCooccurencesAndMoveWindow(Object newWord) {
        String currentWord = window.getCurrentWord();

        if (currentWord != null && stimuluses.contains(currentWord)) {
            for (String neighbourWord : window.getWords()) {
                if (neighbourWord != null && !neighbourWord.equals(currentWord)) {
                    Map<String, Long> currentWordToNeighbours = wordsCooccurrences.getOrDefault(currentWord, new HashMap<>());
                    Long value = currentWordToNeighbours.getOrDefault(neighbourWord, 0L);
                    currentWordToNeighbours.put(neighbourWord, value + 1);
                    wordsCooccurrences.put(currentWord, currentWordToNeighbours);
                }
            }
        }

        window.move((String) newWord);
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
