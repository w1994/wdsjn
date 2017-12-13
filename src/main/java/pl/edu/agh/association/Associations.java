package pl.edu.agh.association;

import pl.edu.agh.app.Text;
import pl.edu.agh.app.Word;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Associations {

    public static final int MAX_SIZE = 20;
    private List<String> paths;
    private Map<String, List<Word>> associationsWords;

    public Associations(List<String> paths) {
        associationsWords = new HashMap<>();
        this.paths = paths;
    }

    public void clear() {
        paths.forEach(path -> {
            try (Stream<String> stream = Files.lines(Paths.get(path))) {
                this.associationsWords.put(path, stream.map(Text::splitByComma)
                        .map(Word::new)
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void save() {
        try {
            for (String path : associationsWords.keySet()) {
                Files.write(Paths.get(path + "clean"), prepareToSave(path).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String prepareToSave(String path) {

        Map<String, Double> wordToOccurrence = new HashMap<>();

        associationsWords.get(path).forEach(
                word -> {
                    Double occurrence = wordToOccurrence.getOrDefault(word, 0D);
                    wordToOccurrence.put(word.getWord(), occurrence + word.getFrequency());
                }
        );

        List<Word> words = wordToOccurrence.entrySet().stream()
                .map(entry -> new Word(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        words.sort(Collections.reverseOrder());

        StringBuilder stringBuilder = new StringBuilder();
        words.stream().limit(MAX_SIZE).forEach(word -> {
            stringBuilder.append(word.getWord());
            stringBuilder.append(",");
            stringBuilder.append(word.getFrequency());
            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String path : associationsWords.keySet()) {
            for (Word word : associationsWords.get(path)) {
                result.append(word.toString());
                result.append("\n");
            }
        }
        return result.toString();
    }

}
