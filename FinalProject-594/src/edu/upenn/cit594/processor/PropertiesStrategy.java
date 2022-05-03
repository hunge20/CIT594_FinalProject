package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.datamanagement.PropertyCsv;

public abstract class PropertiesStrategy {
	
	protected HashMap<Integer, ArrayList<PropertyValues>> propertyValues;
	
	//Memoization
	protected Map<Integer, Integer> averageLivableArea = new HashMap<>();
	protected Map<Integer, Integer> averageMarketValue = new HashMap<>();
	
	public PropertiesStrategy(PropertyCsv propertyCsv) {
		propertyValues = propertyCsv.getProperty();
	}

	public abstract int doCalculation(int zipcode);

	public int doAverage(List<Double> values) {
		if (values.size() == 0) {
			return 0;
		}

		double total = 0;
		for (Double val : values) {
			total += val;
		}

		return (int) (total / values.size());
	}

}