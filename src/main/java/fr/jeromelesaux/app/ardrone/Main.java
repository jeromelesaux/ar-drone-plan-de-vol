package fr.jeromelesaux.app.ardrone;

import fr.jeromelesaux.app.ardrone.compute.ColumnConstants;
import fr.jeromelesaux.app.ardrone.compute.Compute;
import fr.jeromelesaux.app.ardrone.csv.CsvElementCollection;
import fr.jeromelesaux.app.ardrone.csv.CsvElementValue;
import fr.jeromelesaux.app.ardrone.csv.CsvFileReader;
import fr.jeromelesaux.app.ardrone.graphic.Graphic;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

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
                LOG.severe("Can't read or open file " + filepath);
            }
        }
        else {
            LOG.severe("No input csv file ");
            return;
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

        Double distance = null;
        try {
            distance = Compute.computeDistance(collection);
        } catch (ParseException e) {
            LOG.severe(e.getMessage());
        }

        try {
            String graphicFilepath = filepath.substring(0,filepath.lastIndexOf(".")) + ".png";
            String titleToAppend = "";
            if (distance!=null) {
                NumberFormat numberFormat = new DecimalFormat("#0.00");
                titleToAppend = " distance range " + numberFormat.format(distance) + " meters.";
            }
            Graphic.generateGraph(collection, graphicFilepath, titleToAppend);
        } catch (IOException e) {
            LOG.severe("Can't generate grahic.");
        } catch (ParseException e) {
            LOG.severe("Cannot parse date format.");
        }

        return;

    }
}
