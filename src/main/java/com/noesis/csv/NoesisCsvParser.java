package com.noesis.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Java class to parse a CSV file and return the read values into a collection.
 *
 */
public class NoesisCsvParser {
	private String csvFileName;
	private NoesisCsvColumnCollection columnTemplate;
	private boolean fromJar = false;

	public NoesisCsvParser(String csvFile, final NoesisCsvColumnCollection columnTemplate) {
		this.csvFileName = csvFile;
		this.columnTemplate = columnTemplate;
	}

	public NoesisCsvParser(String csvFile, final NoesisCsvColumnCollection columnTemplate, boolean fromJar) {
		this.csvFileName = csvFile;
		this.columnTemplate = columnTemplate;
		this.fromJar = fromJar;
	}

	/**
	 * Gets the columns collection object.
	 * @return
	 */
	public NoesisCsvColumnCollection getColumnTemplate() {
		return this.columnTemplate;
	}

	private CSVParser createParser() throws IOException {
		Reader reader = createReader();
		return new CSVParser(reader, CSVFormat.newFormat(',').withFirstRecordAsHeader().withIgnoreHeaderCase()
				.withTrim().withQuoteMode(QuoteMode.MINIMAL));
	}

	private Reader createReader() throws IOException {

		if (fromJar) {
			InputStream inputStream = getClass().getResourceAsStream(csvFileName);
			return new BufferedReader(new InputStreamReader(inputStream));
		} else
			return Files.newBufferedReader(Paths.get(this.csvFileName));
	}

	/**
	 * Parse the CSV file for the given indices
	 * @param columnSelection indices of the columns to be parsed
	 * @throws IOException
	 * @throws CsvParserException
	 */
	public void parse(final List<Integer> columnSelection) throws IOException, CsvParserException {
		this.columnTemplate.ValidateColumnIndices(columnSelection);

		// clear columns with data to free space
		this.columnTemplate.resetColumnsNotInList(columnSelection);

		boolean allColumnsHaveData = true;

		// keep track of columns that initially have data
		List<Integer> columnsWithData = new ArrayList<Integer>();

		for (int columnIndex : columnSelection) {
			if (!this.columnTemplate.getColumn(columnIndex).HasData()) {
				allColumnsHaveData = false;
			} else {
				columnsWithData.add(columnIndex);
			}
		}

		if (allColumnsHaveData)
			return;

		// Not all columns have data, load the ones that need to be reloaded.
		for (CSVRecord csvRecord : this.createParser()) {
			parseCsvRecord(columnSelection, columnsWithData, csvRecord);
		}
	}

	/**
	 * Parse the csv record for the columns which do not have data.
	 * @param columnSelection columns to be read
	 * @param columnsWithData columns which are already filled
	 * @param csvRecord the record in the CSV file
	 * @throws CsvParserException
	 */
	private void parseCsvRecord(final List<Integer> columnSelection, final List<Integer> columnsWithData, final CSVRecord csvRecord)
			throws CsvParserException {
		if (!csvRecord.isConsistent()) {
			throw new CsvParserException("Inconsistent CSV file! Record " + csvRecord.getRecordNumber()
					+ " is inconsistent and does not match the header size. Record data = " + csvRecord.toString());
		}

		for (int columnIndex : columnSelection) {
			if (columnIndex < 0 || columnIndex >= csvRecord.size()) {
				throw new CsvParserException(
						"Invalid column index " + columnIndex + " for record " + csvRecord.getRecordNumber());
			}

			if (columnsWithData.contains(columnIndex)) {
				continue;
			}

			this.columnTemplate.getColumn(columnIndex).AddDataPoint(csvRecord.get(columnIndex));
		}
	}
}
