package com.noesis.polar.PolarPlotProcessor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;

import com.noesis.csv.CsvColumn;
import com.noesis.csv.CsvParserException;

/**
 * Java class to create a Polar Chart
 *
 */

public class PolarCharPlotter extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JComboBox<Object> theta;
	JComboBox<Object> radius;
	DataSeriesCreater dataSeriesCreator;
	XYSeriesCollection dataset;

	public PolarCharPlotter(String title) throws IOException, CsvParserException {
		super(title);

		dataSeriesCreator = new DataSeriesCreater();
		dataset = new XYSeriesCollection();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(createPanel(), BorderLayout.SOUTH);

		createDataset();
		JFreeChart polarChart = createChart("Polar Chart");
		ChartPanel chartPanel = new ChartPanel(polarChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		add(chartPanel, BorderLayout.CENTER);
	}

	/**
	 * creates a panel with a Flow layout with drop downs for the @Theta and @Radius values for the chart
	 * @return a panel with the drop downs
	 */
	private JPanel createPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel labelTheta = new JLabel("Theta");
		JLabel labelRadius = new JLabel("Radius");

		theta = new JComboBox<Object>(dataSeriesCreator.getColumns());
		theta.setRenderer(new ColumnCellRenderer());
		theta.addActionListener(this);

		radius = new JComboBox<Object>(dataSeriesCreator.getColumns());
		radius.setRenderer(new ColumnCellRenderer());
		radius.setSelectedIndex(1);
		radius.addActionListener(this);

		panel.add(labelTheta);
		panel.add(theta);
		panel.add(labelRadius);
		panel.add(radius);
		return panel;
	}

	/**
	 * clears the current DataSet of the Polar plot and populates it with the vales in selected JComboBox items
	 */
	private void createDataset() {
		dataset.removeAllSeries();
		CsvColumn thetaColumn = (CsvColumn) theta.getSelectedItem();
		CsvColumn radiusColumn = (CsvColumn) radius.getSelectedItem();
		dataset.addSeries(dataSeriesCreator.creatXYSeries(thetaColumn.getColumnIndex(), radiusColumn.getColumnIndex()));
	}

	private JFreeChart createChart(String chartTitle) {
		return ChartFactory.createPolarChart(chartTitle, dataset, true, true, false);
	}

	/**
	 *Listener for the actions performed on the JComboBox.
	 *Changes the DataSet of the chart.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		createDataset();
	}

}
