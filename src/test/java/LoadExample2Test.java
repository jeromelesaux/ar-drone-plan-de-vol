import fr.jeromelesaux.app.ardrone.Main;
import org.junit.Test;

/**
 * Created by jlesaux on 15/07/15.
 * File ${FILE}
 */
public class LoadExample2Test {
    @Test
    public void load2340FileTest() {
        fr.jeromelesaux.app.ardrone.Main main = new Main();
        String[] args = new String[1];
        args[0] = LoadExampleTest.class.getResource("/2015_07_12_10_23_40.csv").getPath();
        main.main(args);

    }
}
