import fr.jeromelesaux.app.ardrone.Main;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by jlesaux on 15/07/15.
 * File ${FILE}
 */
public class LoadExample3Test {

    @Test
    public void load4059FileTest() throws IOException {
        fr.jeromelesaux.app.ardrone.Main main = new Main();
        String[] args = new String[1];
        args[0] = LoadExampleTest.class.getResource("/2015_07_12_10_40_59.csv").getPath();
        main.main(args);
    }
}
