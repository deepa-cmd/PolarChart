package com.noesis.polar.PolarPlotProcessor;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.noesis.csv.CsvColumn;

/**
 * Custom cell renderer for the drop downs. 
 *
 */
public class ColumnCellRenderer extends DefaultListCellRenderer {	
	private static final long serialVersionUID = 6100365526690475301L;

	/**
	 *gets the description of the Csv column to be rendered as value in the drop down.
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (value instanceof CsvColumn) {
			value = ((CsvColumn) value).getDescription();
		}
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		return this;		
	}
}
