package fr.jeromelesaux.app.ardrone.compute;

import fr.jeromelesaux.app.ardrone.csv.CsvElement;
import fr.jeromelesaux.app.ardrone.csv.CsvElementCollection;
import fr.jeromelesaux.app.ardrone.csv.CsvElementValue;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlesaux on 15/07/15.
 * File ${FILE}
 */
public class Compute {
    private static final Logger LOG =  Logger.getLogger(Compute.class.getName());

    public static CsvElementValue computeAltitude(CsvElementValue altitudeElement) {
        final Float altitude = altitudeElement.getFloatValue();

        CsvElementValue value = new CsvElementValue(new CsvElement(ColumnConstants.ALTITUDE_METER,8),String.valueOf(altitude/1000));
        LOG.trace("Compute altitude for " + altitude  + " : " + value.getValue());
        return value;
    }
    public static CsvElementValue computeSpeed(CsvElementValue xSpeed, CsvElementValue ySpeed, CsvElementValue zSpeed) {
        final Float x = xSpeed.getFloatValue();
        final Float y = ySpeed.getFloatValue();
        final Float z = zSpeed.getFloatValue();
        final double speed = Math.pow((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)), 0.5)/1000;
        CsvElementValue value = new CsvElementValue(new CsvElement(ColumnConstants.SPEED,9),String.valueOf(speed));
        LOG.trace("Compute speed " + value);
        return value;
    }

    public static Double computeDistance(CsvElementCollection collection) throws ParseException {
        final List<CsvElementValue> times = collection.getValuesForHeaderName(ColumnConstants.TIME);
        final List<CsvElementValue> speeds = collection.getValuesForHeaderName(ColumnConstants.SPEED);
        Long currentSecond = 0L;
        List<Double> speedTmp = new ArrayList<Double>();
        Double distance = 0.0;
        for (int i = 0;i<times.size();i++) {
            final CsvElementValue time = times.get(i);
            final String[] secondValues = time.getValue().split(":");


            Long second = Long.valueOf(secondValues[0]) + (Long.valueOf(secondValues[1])*60);
            if (second.equals(0L) || second.equals(currentSecond)) {
                speedTmp.add(speeds.get(i).getDoubleValue());
            }else {
                currentSecond = second;
                Double currentDistance = 0.0;
                for (Double v : speedTmp) {
                    currentDistance += v;
                }
                distance += (currentDistance/speedTmp.size());
                speedTmp.clear();
            }

            LOG.trace("distance : " + distance);

        }

        return distance;
    }
}
