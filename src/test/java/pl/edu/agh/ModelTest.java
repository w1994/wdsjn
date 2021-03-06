package pl.edu.agh;

import org.junit.Test;
import pl.edu.agh.app.Corpus;
import pl.edu.agh.app.Model;

import java.util.Arrays;
import java.util.List;


public class ModelTest {

    @Test
    public void test() {
        List<String> stimuluses = Arrays.asList("morze", "ocean", "spokojny", "woda");
        Corpus corpus = new Corpus("C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\02.12.2017\\korpus-pan.txt", 12, stimuluses);
        corpus.generate();
        Model model = new Model(corpus);
        model.generateFrequencies(stimuluses);
    }

}