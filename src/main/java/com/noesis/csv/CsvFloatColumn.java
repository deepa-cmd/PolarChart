package com.noesis.csv;

import static java.lang.Float.parseFloat;

/**
 * Implementation of the generic column type for the Float data values.
 *
 */
public class CsvFloatColumn extends CsvColumn<Float> {
    public final static Float DEFAULT_FLOAT_UNPARSED = Float.NaN;

    public CsvFloatColumn(int columnIndex, String columnName, String description){
        super(columnIndex, columnName, description);
    }

    /**
     * Parse the data point into float. 
     * @return float value or the NaN if the parsing fails
     */
    @Override
    public Float Parse(final String stringVal) {
        try {
            return parseFloat(stringVal);
        }
        catch (NumberFormatException e){
            return DEFAULT_FLOAT_UNPARSED;
        }
    }
}
