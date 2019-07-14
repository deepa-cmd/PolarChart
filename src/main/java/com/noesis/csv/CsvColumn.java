package com.noesis.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to represent a column and its data in the CSV file.
 *
 * @param <T> type of data in the column
 */
public abstract class CsvColumn<T> {
    private int columnIndex;
    private String columnName;
    private String description;

    private List<T> columnData;

    public CsvColumn(int columnIndex, String columnName, String description){
        this.columnIndex = columnIndex;
        this.columnName = columnName;
        this.description = description;
        this.columnData = new ArrayList<T>();
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public String getColumn_name() {
        return columnName;
    }

    public String getDescription() {
        return description;
    }    

    public void ResetData(){
        this.columnData = new ArrayList<T>();
    }

    public void AddDataPoint(final T point){
        this.columnData.add(point);
    }

    public void AddDataPoint(final String pointAsString){
        this.columnData.add(this.Parse(pointAsString));
    }

    public List<T> DataPoints(){
        return this.columnData;
    }

    public abstract T Parse(final String stringValue);

    public boolean HasData(){
        return this.NumberOfPoints() > 0;
    }

    public int NumberOfPoints(){
        return this.columnData.size();
    }
}
