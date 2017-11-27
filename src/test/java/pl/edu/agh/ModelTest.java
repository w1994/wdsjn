package pl.edu.agh;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by wojci on 25.11.2017.
 */
public class ModelTest {

    @Test
    public void test() {

        String stimulus = "spór";
        System.out.println("text");
        Corpus corpus = new Corpus("C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\korpus-pan.txt", 12, stimulus);
        System.out.println("corpus");
        Model model = new Model(corpus);
        System.out.println("model");
        model.generateFrequencies(Arrays.asList(stimulus));
        System.out.println("model generated");

    }


}