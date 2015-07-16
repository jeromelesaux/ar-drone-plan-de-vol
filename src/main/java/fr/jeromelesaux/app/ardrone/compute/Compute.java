package fr.jeromelesaux.app.ardrone.compute;

import fr.jeromelesaux.app.ardrone.csv.CsvElement;
import fr.jeromelesaux.app.ardrone.csv.CsvElementValue;
import org.apache.log4j.Logger;

/**
 * Created by jlesaux on 15/07/15.
 * File ${FILE}
 */
public class Compute {
    private static final Logger LOG =  Logger.getLogger(Compute.class.getName());

    public static CsvElementValue computeAltitude(CsvElementValue altitudeElement) {
        final Float altitude = Float.valueOf(altitudeElement.getValue());

        CsvElementValue value = new CsvElementValue(new CsvElement(ColumnConstants.ALTITUDE_METER,8),String.valueOf(altitude/1000));
        LOG.trace("Compute altitude for " + altitude  + " : " + value.getValue());
        return value;
    }
    public static CsvElementValue computeSpeed(CsvElementValue xSpeed, CsvElementValue ySpeed, CsvElementValue zSpeed) {
        final Float x = Float.valueOf(xSpeed.getValue());
        final Float y = Float.valueOf(ySpeed.getValue());
        final Float z = Float.valueOf(zSpeed.getValue());
        final double speed = Math.pow((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)), 0.5)/1000;
        CsvElementValue value = new CsvElementValue(new CsvElement(ColumnConstants.SPEED,9),String.valueOf(speed));
        LOG.trace("Compute speed " + value);
        return value;
    }
}
