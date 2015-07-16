package fr.jeromelesaux.app.ardrone.csv;

import java.io.Serializable;

/**
 * Created by jlesaux on 04/03/15.
 * File ${FILE}
 */
public class CsvElement implements Serializable{

    private Integer position;
    private String name;


    public CsvElement(String name,Integer position) {
        this.name = name;
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        byte[] bytes = name.getBytes();
        for (byte b : bytes) {
            hash <<= b;
        }
        hash += position;
        return hash;
    }

    @Override
    public boolean equals(Object objet) {
        if (objet == null) {
            return false;
        }
        if (!(objet instanceof CsvElement)) {
            return false;
        }
        if (((CsvElement) objet).name != null && ((CsvElement) objet).name.equals(this.name)) {
            if (((CsvElement) objet).position != null && ((CsvElement) objet).position.equals(this.position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Position " + position + " name " + name;
    }
}
