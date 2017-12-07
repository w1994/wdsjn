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

    private String path;
    private List<Word> associationsWords;

    public Associations(String path) {
        this.path = path;
    }

    public void clear() {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            this.associationsWords = stream.map(Text::splitByComma)
                    .map(Word::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.write(Paths.get(path + "clean"), prepareToSave().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String prepareToSave() {

        Map<String, Double> wordToOccurrence = new HashMap<>();

        associationsWords.forEach(
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
        words.forEach((word -> {
            stringBuilder.append(word.getWord());
            stringBuilder.append(",");
            stringBuilder.append(word.getFrequency());
            stringBuilder.append("\n");
        }));

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Word word : associationsWords) {
            result.append(word.toString());
            result.append("\n");
        }
        return result.toString();
    }

}
