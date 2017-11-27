package pl.edu.agh;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextTest {


    @Test
    public void test() {

        Text text = new Text("C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\korpus-pan.txt");

        text.getLines();

    }


    @Test
    public void replaceTest(){

        String word = "wwww,.!.";

        System.out.println(word.replaceAll("[,!.?;:)(+*&^%$#@]", ""));
    }

}