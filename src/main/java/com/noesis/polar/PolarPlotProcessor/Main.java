package com.noesis.polar.PolarPlotProcessor;

import java.io.IOException;

import com.noesis.csv.CsvParserException;

public class Main {

	public static void main(String[] args) throws IOException, CsvParserException {
		PolarCharPlotter plotter = new PolarCharPlotter("Noesis Polar Chart");
		plotter.pack();
		plotter.setVisible(true);
	}
}
