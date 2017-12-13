package pl.edu.agh.app;

import java.util.stream.Stream;

public class Word implements Comparable {

    private String word;
    private Double frequency;

    public Word(String word, Double frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public Word(String[] values) {
        if (values.length < 2) {
            throw new IllegalStateException();
        }
        this.frequency = Double.valueOf(values[0]);
        this.word = preprocessWord(values[1]);

    }

    private String preprocessWord(String value) {
        return Stream.of(value)
                .map(String::toLowerCase)
                .map(Text::replace)
                .map(Text::stem)
                .map(Text::replacePolishLetters)
                .findFirst()
                .get();
    }

    public String getWord() {
        return word;
    }

    public Double getFrequency() {
        return frequency;
    }

    @Override
    public int compareTo(Object o) {
        Word word = (Word) o;
        if (this.frequency < word.getFrequency()) {
            return -1;
        } else if (this.frequency > word.getFrequency()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return word + " : " + String.format("%f", frequency);
    }
}
