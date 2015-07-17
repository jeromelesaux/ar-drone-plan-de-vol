package fr.jeromelesaux.app.ardrone.csv;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jlesaux on 04/03/15.
 * File ${FILE}
 */
public class CsvElementValue implements Serializable {
    private CsvElement element;
    private String value;
    private String type;
    private Integer rowIndex;

    public CsvElementValue(CsvElement element, String value) {
        if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
           value = value.substring(1,value.length()-1);
        }
        this.rowIndex = null;
        this.element = element;
        this.value = value;
    }

    public CsvElementValue(CsvElement element, String value, Integer rowIndex) {
        this(element,value);
        this.rowIndex = rowIndex;
    }

    public CsvElement getElement() {
        return element;
    }

    public void setElement(CsvElement element) {
        this.element = element;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getIntegerValue() {
        return Integer.valueOf(value);
    }
    public Float getFloatValue() {
        return Float.valueOf(value);
    }

    public Double getDoubleValue() {
        return Double.valueOf(value);
    }

    public Date getDateValue(String format)
            throws ParseException {
        if (format != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(value);
        }else {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            return simpleDateFormat.parse(value);
        }
    }

    public Boolean getBooleanValue() {
        return Boolean.parseBoolean(value);
    }
}
