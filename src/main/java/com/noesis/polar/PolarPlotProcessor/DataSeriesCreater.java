package com.noesis.polar.PolarPlotProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jfree.data.xy.XYSeries;

import com.noesis.csv.CsvColumn;
import com.noesis.csv.CsvParserException;
import com.noesis.csv.NoesisCsvColumnCollection;
import com.noesis.csv.NoesisCsvParser;

/**
 * Java class for handling the creation of data series for the PolarChartPlotter object
 *
 */
public class DataSeriesCreater {
	NoesisCsvParser parser;

	public DataSeriesCreater() throws IOException, CsvParserException {	
		NoesisCsvColumnCollection collection = NoesisCsvColumnCollection.createDefaultTemplate();
		parser = new NoesisCsvParser("/SUMMARY.csv", collection, true);		
		parser.parse(collection.getAllIndices());
	}

	/**
	 * Gets the columns from the csv parser
	 * @return array of CsvColumn objects
	 */
	public Object[] getColumns() {
		return parser.getColumnTemplate().toList().toArray();
	}

	/**
	 * @param thetaIndex index of the CsvColumn as theta values
	 * @param radiusIndex index of the CsvColumn as radius values
	 * @return XY series created with selected csv columns
	 */
	public XYSeries creatXYSeries(int thetaIndex, int radiusIndex) {
		CsvColumn columnTheta = parser.getColumnTemplate().getColumn(thetaIndex);
		CsvColumn columnRadius = parser.getColumnTemplate().getColumn(radiusIndex);

		XYSeries series = new XYSeries(columnTheta.getDescription() + " vs " + columnRadius.getDescription());

		Iterator<Float> thetaIterator = columnTheta.DataPoints().iterator();
		Iterator<Float> radiusIterator = columnRadius.DataPoints().iterator();

		while (thetaIterator.hasNext() && radiusIterator.hasNext()) {
			series.add(thetaIterator.next(), radiusIterator.next());
		}
		return series;
	}

}
