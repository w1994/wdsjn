package pl.edu.agh;

import org.junit.Test;
import pl.edu.agh.association.Associations;

import java.util.Arrays;

public class AssociationsTest {

    @Test
    public void test() {

        Associations associations = new Associations(Arrays.asList("C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\given\\morze.csv",
                    "C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\given\\woda.csv",
                "C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\given\\spokojny.csv",
                "C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\given\\ocean.csv"));

        associations.clear();
        associations.save();

        System.out.println(associations);

    }


}