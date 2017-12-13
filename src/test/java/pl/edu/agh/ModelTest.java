package pl.edu.agh;

import org.junit.Test;
import pl.edu.agh.app.Corpus;
import pl.edu.agh.app.Model;
import pl.edu.agh.app.Text;
import pl.edu.agh.app.Word;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ModelTest {

    @Test
    public void test() {
        List<String> stimuluses = Arrays.asList("morze", "ocean", "spokojny", "woda");
        Corpus corpus = new Corpus("C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\02.12.2017\\korpus-pan.txt", 12, stimuluses);
        corpus.generate();
        Model model = new Model(corpus);
        model.generateFrequencies(stimuluses);
    }

    @Test
    public void test2() {


        List<String> a = Arrays.asList("Łazowski");

        List<String> aa = a.stream().filter(line -> !line.startsWith("#"))
                .map(Text::split)
                .flatMap(Arrays::stream)
                .map(Text::replace)
                .map(Text::stem)
                .map(String::toLowerCase)
                .map(Text::replacePolishLetters).collect(Collectors.toList());

        System.out.println(aa.get(0));
    }

}