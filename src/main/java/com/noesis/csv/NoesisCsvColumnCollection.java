package com.noesis.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Java Class handling the operations on the columns collection
 *
 */
public class NoesisCsvColumnCollection implements Iterable<CsvColumn>{
    private List<CsvColumn> columns;

	public NoesisCsvColumnCollection(final List<CsvColumn> columns){
        this.columns = columns;
    }

    /**
     * Resets all the column data in the collection
     */
    public void ResetAllData() {
        for (CsvColumn column : this.columns) {
            column.ResetData();
        }
    }

    /**
     * validates if a column with the given index is present in the collection.
     * @param index column index to be validated
     * @throws IllegalArgumentException
     */
    public void ValidateColumnIndex(final int index) throws IllegalArgumentException {
        boolean found = false;

        for(CsvColumn csvColumn : this.columns){
            if(csvColumn.getColumnIndex() == index){
                found = true;
                break;
            }
        }

        if( ! found){
            throw new IllegalArgumentException("Invalid column index value " + index);
        }
    }

    /**
     * Validates the list of column indices
     * @param columnIndices
     */
    public void ValidateColumnIndices(final List<Integer> columnIndices){
        for(int columnIndex : columnIndices){
            this.ValidateColumnIndex(columnIndex);
        }
    }

    /**
     * Resets the columns which are NOT in the given indices.  The columns with index in the given columnIndices list
     * will NOT be reset.
     * @param columnIndices
     */
    public void resetColumnsNotInList(final List<Integer> columnIndices){
        this.ValidateColumnIndices(columnIndices);

        for(CsvColumn csvColumn : this.columns){
            if(!columnIndices.contains(csvColumn.getColumnIndex())){
                csvColumn.ResetData();
            }
        }
    }

    /**
     * Gets the column with index from the collection
     * @param columnIndex
     * @return CsvVolumn object at this index
     */
    public CsvColumn getColumn(final int columnIndex){
        this.ValidateColumnIndex(columnIndex);

        for(CsvColumn csvColumn : this.columns){
            if(columnIndex == csvColumn.getColumnIndex()){
                return csvColumn;
            }
        }

        throw new RuntimeException("Invalid column index!");
    }

    /**
     * Creates a default template to read the data from the CSV file
     * @return 
     */
    public static NoesisCsvColumnCollection createDefaultTemplate(){
        List<CsvColumn> defaultList = new ArrayList<CsvColumn>(
        		Arrays.asList(
                        new CsvFloatColumn(0, "Section1", "Section 1"),
                        new CsvFloatColumn(1, "Section3", "Section 3"),
                        new CsvFloatColumn(2, "First_Mode", "First mode"),
                        new CsvFloatColumn(3, "Max_Stress", "Maximum stress"),
                        new CsvFloatColumn(4, "Mass", "Mass"),
                        new CsvFloatColumn(5, "Max_Displacement", "Max displacement"),
                        new CsvFloatColumn(6, "Cost", "Cost")
                ));

        return new NoesisCsvColumnCollection(defaultList);
    }      
    
    
	/**
	 *  Create an iterator to iterate over all CsvColumn objects.
	 */
	public Iterator<CsvColumn> iterator() {	
		return this.columns.iterator();
	}	
	
	/**
	 * @return an unmodifiable view of the collection
	 */
	public List<CsvColumn> toList() {
		return Collections.unmodifiableList(this.columns);
	}

	/**
	 * Gets a list of all the column indices in the columns collection
	 * @return
	 */
	public List<Integer> getAllIndices() {
		List<Integer> list = new ArrayList<Integer>();
		for(CsvColumn column: columns) {
			list.add(column.getColumnIndex());
		}
		return list;
	}
}
