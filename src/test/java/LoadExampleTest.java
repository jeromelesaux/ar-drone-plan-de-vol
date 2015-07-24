import fr.jeromelesaux.app.ardrone.Main;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by jlesaux on 15/07/15.
 * File ${FILE}
 */
public class LoadExampleTest {

    @Test
    public void loadExampleFileTest() throws IOException {
        fr.jeromelesaux.app.ardrone.Main main = new Main();
        String[] args = new String[1];
        args[0] = LoadExampleTest.class.getResource("/ExampleData.csv").getPath();
        main.main(args);
    }

}
