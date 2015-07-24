package fr.jeromelesaux.app.ardrone.csv;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by jlesaux on 05/03/15.
 * File ${FILE}
 */
public class CsvFileWriter {

    private static final Logger LOG = Logger.getLogger(CsvFileWriter.class.getName());
    private static CsvFileWriter writer ;
    public static String filepathout;


    private CsvFileWriter() {

    }

    public static CsvFileWriter newIntance() {
        if (writer == null) {
            writer = new CsvFileWriter();
        }
        return writer;
    }

    public static void writeFile(String filepath, CsvElementCollection collection) throws IOException {
        LOG.info("writeFile with filepath : " + filepath);
        File file = new File(filepath.replaceAll("\\.csv$","-results.csv"));

        filepathout = file.getAbsolutePath();
        BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()),"UTF8"));
        Integer indexColumn = 0;
        Integer indexRow = 0;

        // ecriture des entetes
        StringBuffer sBuffer = new StringBuffer("");
        Integer nbElements = collection.getValues().keySet().size();

        while (indexColumn < nbElements) {
            CsvElement theElement  = null;
            for (CsvElement element : collection.getValues().keySet()){
                if (element.getPosition().equals(indexColumn)) {
                    theElement = element;
                    break;
                }
            }
            if (theElement != null) {
                sBuffer.append(theElement.getName() + ";");
            }
            indexColumn++;
        }
        sBuffer.append("\r\n");
        buffer.write(sBuffer.toString());
        sBuffer.delete(0,sBuffer.length());
        // ecriture des données
        indexColumn=0;
        Integer maxRows = collection.getLastRowIndex();
        while (indexRow < maxRows) {

            while (indexColumn < nbElements) {
                CsvElement theElement = null;
                for (CsvElement element : collection.getValues().keySet()) {
                    if (element.getPosition().equals(indexColumn)) {
                        theElement = element;
                        break;
                    }
                }
                if (theElement != null) {
                    List<CsvElementValue> values = collection.getValues().get(theElement);

                    CsvElementValue theElementValue = null;
                    for (CsvElementValue elValue : values) {
                        if (elValue.getRowIndex() != null && elValue.getRowIndex().equals(indexRow)) {
                            theElementValue = elValue;
                            break;
                        }
                    }
                    if (theElementValue != null) {
                        sBuffer.append(theElementValue.getValue() + ";");
                    }
                    else {
                        sBuffer.append(";");
                    }

                } else {
                    sBuffer.append(";");
                }
                indexColumn++;
            }
            sBuffer.append("\r\n");
            buffer.write(sBuffer.toString());
            sBuffer.delete(0,sBuffer.length());
            indexColumn=0;
            indexRow++;
        }
        buffer.flush();
        buffer.close();
    }
}
