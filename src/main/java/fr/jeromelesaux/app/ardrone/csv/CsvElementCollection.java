package fr.jeromelesaux.app.ardrone.csv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jlesaux on 04/03/15.
 * File ${FILE}
 */
public class CsvElementCollection implements Serializable {

    private Integer length;
    public String separator = ";";

    private HashMap<CsvElement,List<CsvElementValue>> values;

    public CsvElementCollection() {
        values = new HashMap<CsvElement, List<CsvElementValue>>();
    }

    public HashMap<CsvElement, List<CsvElementValue>> getValues() {
        return values;
    }

    public void setValues(HashMap<CsvElement, List<CsvElementValue>> values) {
        this.values = values;
    }

    public CsvElement addColumn(String name, Integer position) throws Exception {
        CsvElement element = new CsvElement(name,position);
        if (values.containsKey(element)) {
            throw new Exception("Element déjà existant dans la collection " + element.toString());
        }
        values.put(element,new ArrayList<CsvElementValue>());
        return element;
    }

    public void addCsvElementValue(CsvElementValue value) {
        if (!values.containsKey(value.getElement())) {
            values.put(value.getElement(),new ArrayList<CsvElementValue>());
        }
        values.get(value.getElement()).add(value);
    }

    public int getLastColumnIndex() {
        int lastColumnIndex = 0;
        for (CsvElement e : values.keySet()) {
            if (lastColumnIndex < e.getPosition()) {
                lastColumnIndex = e.getPosition();
            }
        }
        return lastColumnIndex;
    }

    public int getLastRowIndex() {
        int lastRowIndex = 0;
        for (CsvElement e : values.keySet()) {

            if (lastRowIndex < values.get(e).size()) {
                lastRowIndex = values.get(e).size();
            }
        }
        return lastRowIndex;
    }

    public CsvElement getColumnAtPosition(Integer position) {

        for (CsvElement e : values.keySet()) {
            if (e.getPosition().equals(position)) {
                return e;
            }
        }
        return null;
    }

    public List<CsvElementValue> getValuesAtLinenumber(Integer position) {
        for (CsvElement e : values.keySet()) {
            if (e.getPosition().equals(position)) {
               return values.get(e);
            }
        }
        return null;
    }

    public CsvElement getElementByName(String name) {
        for (CsvElement element : values.keySet()) {
            if (element.getName().equals(name)) {
                return element;
            }
        }


        return null;
    }

    public List<CsvElementValue> getValuesForHeaderName(String name) {

        for (CsvElement element : values.keySet()) {
            if (element.getName().equals(name)) {
                return values.get(element);
            }
        }

        return null;
    }


    public void setValuesForElement(CsvElement element, CsvElementValue value) {
        for (CsvElement e : values.keySet()) {
            if (e.equals(element)) {
                values.get(e).add(value);
            }
        }
    }

    public Integer getLength() {
        Integer max = 0;
        if (length != null) {
            return length;
        }

        for (CsvElement e : values.keySet()) {
            if (values.get(e).size() > max ) {
                max = values.get(e).size();
            }
        }

        length = max ;
        return length;

    }


}
