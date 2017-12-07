package pl.edu.agh.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Window {

    private int windowSize;
    private List<String> words;

    public Window(int windowSize) {
        this.windowSize = windowSize;
        this.words = new ArrayList<>(windowSize * 2 + 1);
        IntStream.range(0, windowSize * 2 + 1).forEach(i -> words.add(null));
    }

    public void move(String word) {
        words.remove(0);
        words.add(word);
    }

    public List<String> getWords() {
        return words;
    }

    public String getCurrentWord(){
        return words.get(windowSize);
    }

    public int getWindowSize() {
        return windowSize;
    }
}
