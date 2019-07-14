package com.noesis.polar.PolarPlotProcessor;

import com.noesis.csv.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class NoesisCsvParserTest {

    @org.junit.Test
    public void fill_column_float_values_1() {
        NoesisCsvParser parser = new NoesisCsvParser("src/test/resources/SUMMARY.csv", NoesisCsvColumnCollection.createDefaultTemplate());
        List<Integer> selectedColumns = new ArrayList<Integer>(Arrays.asList(0,1));

        try {
            parser.parse(selectedColumns);
        } catch (IOException e) {
            fail("No exception expected!");
        } catch (CsvParserException pe){
            fail("No parser exception expected!");
        }
        
        NoesisCsvColumnCollection columns = parser.getColumnTemplate();
        assertEquals(columns.getColumn(0).NumberOfPoints(), columns.getColumn(1).NumberOfPoints());
    }

    @org.junit.Test
    public void fill_column_float_values_2() {
        NoesisCsvParser parser = new NoesisCsvParser("src/test/resources/SUMMARY.csv", NoesisCsvColumnCollection.createDefaultTemplate());
        List<Integer> selectedColumns = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6));

        try {
            parser.parse(selectedColumns);
        } catch (IOException e) {
            fail("No exception expected!");
        } catch (CsvParserException pe){
            fail("No parser exception expected!");
        }

        NoesisCsvColumnCollection columns = parser.getColumnTemplate();
        
        assertEquals(columns.getColumn(0).NumberOfPoints(), columns.getColumn(1).NumberOfPoints());
        assertEquals(columns.getColumn(1).NumberOfPoints(), columns.getColumn(2).NumberOfPoints());
        assertEquals(columns.getColumn(2).NumberOfPoints(), columns.getColumn(3).NumberOfPoints());
        assertEquals(columns.getColumn(3).NumberOfPoints(), columns.getColumn(4).NumberOfPoints());
        assertEquals(columns.getColumn(4).NumberOfPoints(), columns.getColumn(5).NumberOfPoints());
        assertEquals(columns.getColumn(5).NumberOfPoints(), columns.getColumn(6).NumberOfPoints());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @org.junit.Test
    public void fill_column_float_values_3() throws IOException, CsvParserException {
        NoesisCsvParser parser = new NoesisCsvParser("src/test/resources/SUMMARY_NON_EXISTING.csv", NoesisCsvColumnCollection.createDefaultTemplate());
        List<Integer> selectedColumns = new ArrayList<Integer>(Arrays.asList(5,6));

        thrown.expect(IOException.class);
        parser.parse(selectedColumns);
    }

    @org.junit.Test
    public void fill_column_float_values_NaN() {
        NoesisCsvParser parser = new NoesisCsvParser("src/test/resources/SUMMARY_with_NAN.csv", NoesisCsvColumnCollection.createDefaultTemplate());

        List<Integer> selectedColumns = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6));

        try {
            parser.parse(selectedColumns);
        } catch (IOException e) {
            fail("No exception expected!");
        } catch (CsvParserException pe){
            fail("No parser exception expected!");
        }

        NoesisCsvColumnCollection columns = parser.getColumnTemplate();
        
        assertEquals(columns.getColumn(0).NumberOfPoints(), columns.getColumn(1).NumberOfPoints());
        assertEquals(columns.getColumn(1).NumberOfPoints(), columns.getColumn(2).NumberOfPoints());
        assertEquals(columns.getColumn(2).NumberOfPoints(), columns.getColumn(3).NumberOfPoints());
        assertEquals(columns.getColumn(3).NumberOfPoints(), columns.getColumn(4).NumberOfPoints());
        assertEquals(columns.getColumn(4).NumberOfPoints(), columns.getColumn(5).NumberOfPoints());
        assertEquals(columns.getColumn(5).NumberOfPoints(), columns.getColumn(6).NumberOfPoints());
    }


    @org.junit.Test
    public void fill_column_float_values_garbage() {
        NoesisCsvParser parser = new NoesisCsvParser("src/test/resources/SUMMARY_with_garbage.csv", NoesisCsvColumnCollection.createDefaultTemplate());
        List<Integer> selectedColumns = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6));

        try {
            parser.parse(selectedColumns);
        } catch (IOException e) {
            fail("No exception expected!");
        } catch (CsvParserException pe){
            fail("No parser exception expected!");
        }

        NoesisCsvColumnCollection columns = parser.getColumnTemplate();
        
        assertEquals(columns.getColumn(0).NumberOfPoints(), columns.getColumn(1).NumberOfPoints());
        assertEquals(columns.getColumn(1).NumberOfPoints(), columns.getColumn(2).NumberOfPoints());
        assertEquals(columns.getColumn(2).NumberOfPoints(), columns.getColumn(3).NumberOfPoints());
        assertEquals(columns.getColumn(3).NumberOfPoints(), columns.getColumn(4).NumberOfPoints());
        assertEquals(columns.getColumn(4).NumberOfPoints(), columns.getColumn(5).NumberOfPoints());
        assertEquals(columns.getColumn(5).NumberOfPoints(), columns.getColumn(6).NumberOfPoints());

        assertEquals(columns.getColumn(2).DataPoints().get(1), CsvFloatColumn.DEFAULT_FLOAT_UNPARSED);
    }

    @org.junit.Test
    public void fill_column_float_values_unbalanced() throws IOException, CsvParserException {
        NoesisCsvParser parser = new NoesisCsvParser("src/test/resources/SUMMARY_unbalanced.csv", NoesisCsvColumnCollection.createDefaultTemplate());
        List<Integer> selectedColumns = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6));

        thrown.expect(CsvParserException.class);
        parser.parse(selectedColumns);
    }
}