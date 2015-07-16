package fr.jeromelesaux.app.ardrone;

import fr.jeromelesaux.app.ardrone.compute.ColumnConstants;
import fr.jeromelesaux.app.ardrone.compute.Compute;
import fr.jeromelesaux.app.ardrone.csv.CsvElementCollection;
import fr.jeromelesaux.app.ardrone.csv.CsvElementValue;
import fr.jeromelesaux.app.ardrone.csv.CsvFileReader;
import fr.jeromelesaux.app.ardrone.graphic.Graphic;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by jlesaux on 15/07/15.
 * File ${FILE}
 */
public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    private static AppVersion mavenVersion = new AppVersion();
    private static final CsvFileReader reader = CsvFileReader.newInstance();

    public static void main(String[] args) {

        LOG.info("Version : " + mavenVersion.getVersion());
        final String filepath = args[0];
        if (filepath != null) {
            try {
                reader.setSeparator(",");
                reader.readFile(filepath);
            } catch (Exception e) {
                LOG.error("Can't read or open file " + filepath);
            }
        }
        else {
            LOG.error("No input csv file ");
            Runtime.getRuntime().exit(-1);
        }

        final CsvElementCollection collection = reader.getCollection();

        final List<CsvElementValue> altitudeElements = collection.getValuesForHeaderName(ColumnConstants.ALTITUDE);
        for (CsvElementValue altitudeElement : altitudeElements) {
            final CsvElementValue value = Compute.computeAltitude(altitudeElement);
            value.setRowIndex(altitudeElement.getRowIndex());
            collection.addCsvElementValue(value);
        }


        final List<CsvElementValue> xSpeedElements = collection.getValuesForHeaderName(ColumnConstants.XSPEED);
        final List<CsvElementValue> ySpeedElements = collection.getValuesForHeaderName(ColumnConstants.YSPEED);
        final List<CsvElementValue> zSpeedElements = collection.getValuesForHeaderName(ColumnConstants.ZSPEED);
        for (int i =0; i < xSpeedElements.size(); i++){
            final CsvElementValue yValue = ySpeedElements.get(i);
            final CsvElementValue xValue = xSpeedElements.get(i);
            final CsvElementValue zvalue = zSpeedElements.get(i);
            final CsvElementValue speedValue = Compute.computeSpeed(xValue, yValue, zvalue);
            speedValue.setRowIndex(yValue.getRowIndex());
            collection.addCsvElementValue(speedValue);
        }

        try {
            String graphicFilepath = filepath.substring(0,filepath.lastIndexOf(".")) + ".png";
            Graphic.generateGraph(collection,graphicFilepath);
        } catch (IOException e) {
            LOG.error("Can't generate grahic.");
        } catch (ParseException e) {
            LOG.error("Cannot parse date format.");
        }

        Runtime.getRuntime().exit(0);

    }
}
