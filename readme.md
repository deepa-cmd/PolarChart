# PolarPlotProcessor


## Overview

Creates a Polar Chart using the SUMMARY.csv. Provides the user to select two columns from the csv at runtime with drop downs.
Uses JFreeChart APIs to create the chart and Apache

You can find this on [GitHub](https://www.github.com).


## Building

### Using maven

Issue `mvn clean package` to package with maven (and run the unit tests using JUnit).

## Running

Run the command `Java -jar ExecutableJAR` from the directory of the executable Jar.
Target Java version : 1.8 or later

### Dependencies

 [Apache commons csv](https://github.com/apache/commons-csv)
 
 [JfreeChart](https://github.com/jfree/jfreechart)