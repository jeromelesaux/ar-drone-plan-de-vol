package fr.jeromelesaux.app.ardrone.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;


/**
 * Created by jlesaux on 04/03/15.
 * File ${FILE}
 */
public class CsvFileReader {

    private static final Logger LOG = Logger.getLogger(CsvFileReader.class.getName());
    private static CsvFileReader csvFileReader;
    private static CsvElementCollection collection = new CsvElementCollection();;
    public static String filename;
    public static String filepath;

    private CsvFileReader() {

    }

    public static CsvFileReader newInstance() {
        LOG.info("newInstance constructor call.");
        if (csvFileReader == null) {
            csvFileReader = new CsvFileReader();
        }
        return csvFileReader;
    }


    public static void setSeparator(String separator) {
        collection.separator  = separator;
    }

    public static CsvElementCollection readFile(String filepath) throws Exception {


        LOG.info("readFile with filepath : " + filepath);
        CsvFileReader.filepath = filepath;

        Path path = Paths.get((new File(filepath)).toURI());
        CsvFileReader.filename = path.getFileName().toString();
        LOG.info("get the filepath : " + CsvFileReader.filepath);
        LOG.info("get the filename : " + CsvFileReader.filename);

        BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"UTF8"));
        String line;
        Integer position = 0;
        Integer linePosition = 0;

        LOG.info("read the headers.");
        // lecture des entetes
        line = buffer.readLine();
        if (line == null) {
            LOG.severe("Pas d'entetes dans le fichier csv.");
            return collection;
        }
        String[] splitResults = line.split(collection.separator);
        LOG.info(splitResults.length + " columns found.");

        for (String result : splitResults) {
            LOG.info("at position " + position + " header value : " + result);
            collection.addColumn(result,position);
            position++;
        }


        // lecture des valeurs
        LOG.info("read the values");
        while ((line = buffer.readLine()) != null) {
            splitResults = line.split(collection.separator);
            position=0;

            for (String value : splitResults) {

                CsvElementValue elementValue = new CsvElementValue(collection.getColumnAtPosition(position),value,linePosition);
                LOG.info("Found at position : " + position + " line number : " + linePosition + " value : " + elementValue.getValue());
                collection.setValuesForElement(collection.getColumnAtPosition(position),elementValue);
                position++;
            }
            linePosition++;
        }
        buffer.close();
        LOG.info("summarize number of headers : " + collection.getValues().size() + " and number of lines : " + linePosition);
        return collection;
    }

    public CsvElementCollection getCollection() {
        return collection;
    }
}
