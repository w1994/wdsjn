package pl.edu.agh;

import org.junit.Test;
import pl.edu.agh.association.Associations;

/**
 * Created by wojci on 06.12.2017.
 */
public class AssociationsTest {

    @Test
    public void test() {

        Associations associations = new Associations("C:\\Users\\wojci\\Desktop\\STUDIA\\wdsjn\\morze.csv");

        associations.clear();
        associations.save();

        System.out.println(associations);

    }


}