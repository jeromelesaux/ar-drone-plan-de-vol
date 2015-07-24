package fr.jeromelesaux.app.ardrone.graphic;

import fr.jeromelesaux.app.ardrone.compute.ColumnConstants;
import fr.jeromelesaux.app.ardrone.csv.CsvElementCollection;
import fr.jeromelesaux.app.ardrone.csv.CsvElementValue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by jlesaux on 15/07/15.
 * File ${FILE}
 */
public class Graphic {
    private static final Logger LOG = Logger.getLogger(Graphic.class.getName());
    public static void generateGraph(CsvElementCollection collection, String graphFilepath, String titleToAppend)
            throws IOException, ParseException {
        LOG.info("Generate graphic");
        Calendar flyDate;
        String title = "Ar drone vol fly ";
        final XYSeries altitudeSeries = new XYSeries(ColumnConstants.ALTITUDE_METER);
        final XYSeries speedSeries = new XYSeries(ColumnConstants.SPEED);
        final List<CsvElementValue> timeElements = collection.getValuesForHeaderName(ColumnConstants.TIME);
        final List<CsvElementValue> altitudeElements = collection.getValuesForHeaderName(ColumnConstants.ALTITUDE_METER);
        final List<CsvElementValue> speedElements = collection.getValuesForHeaderName(ColumnConstants.SPEED);
        for (CsvElementValue timeElement : timeElements) {
            final String[] times = timeElement.getValue().split(":");
            final Double minutes = Double.valueOf(times[0]);
            final Double seconds = Double.valueOf(times[1]) + (minutes*60);

            LOG.info("Time : " + seconds);
            altitudeSeries.add(seconds,Double.valueOf(altitudeElements.get(timeElement.getRowIndex()).getValue()));
            speedSeries.add(seconds,Double.valueOf(speedElements.get(timeElement.getRowIndex()).getValue()));
        }

        // just for test :

        final Path path = Paths.get(graphFilepath);
        final Path fileName = path.getFileName();
        final String[] elementsName = fileName.toFile().getName().substring(0, fileName.toFile().getName().lastIndexOf(".")).split("_");
        if (elementsName.length >= 6) {
            flyDate = new GregorianCalendar();
            final int year = Integer.parseInt(elementsName[0]);
            final int month = Integer.parseInt(elementsName[1]);
            final int day = Integer.parseInt(elementsName[2]);
            final int hours = Integer.parseInt(elementsName[3]);
            final int minutes = Integer.parseInt(elementsName[4]);
            final int seconds = Integer.parseInt(elementsName[5]);

            flyDate.set(year,month,day,hours,minutes,seconds);
            final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            title += format.format(flyDate.getTime()).toString();
        }



        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(altitudeSeries);


        final JFreeChart chart =  ChartFactory.createXYLineChart(title + titleToAppend , "Time", "Altitude", dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.white);
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final NumberAxis axis = new NumberAxis(ColumnConstants.ALTITUDE_METER);
        axis.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(0,axis);

        final NumberAxis axis2 = new NumberAxis(ColumnConstants.SPEED);
        axis2.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, new XYSeriesCollection(speedSeries));
        plot.mapDatasetToRangeAxis(1, 1);
        final XYLineAndShapeRenderer renderer =new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        plot.setRenderer(0, renderer);


        final XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        renderer2.setSeriesPaint(0, Color.yellow.brighter());
        plot.setRenderer(1, renderer2);

        LOG.info("Save into file " + graphFilepath);
        ChartUtilities.saveChartAsPNG(new File(graphFilepath),chart,1200,800);

    }
}
