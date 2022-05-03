package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.datamanagement.PropertyCsv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AverageLivableArea extends PropertiesStrategy {

    //for memoization
    private Map<Integer, Integer> results = new HashMap<>();
    
    public AverageLivableArea(PropertyCsv propertyCsv) {
        super(propertyCsv);
    }
    
    //memoization
	public int getAverageLivableArea(int input){
		
		if (!propertyValues.containsKey(input)) {
			return -1;
		}
		if (results.containsKey(input)) {
			return results.get(input);
		} else {
			int result = doCalculation(input);
			results.put(input, result);
			return result;
		}
	}
    
    @Override
    public int doCalculation(int zipcode) {
        if (averageLivableArea.containsKey(zipcode)) {
            return averageLivableArea.get(zipcode);
        } else {
            List<Double> values = new ArrayList<>();
            List<PropertyValues> houses = propertyValues.get(zipcode);

            if (houses.size() == 0) {
                return 0;
            }

            for (PropertyValues house : houses) {
                values.add(house.getTotalLivableArea());
            }

            int average = doAverage(values);
            averageLivableArea.put(zipcode, average);
            return average;
        }
    }
}
/*
    @Override
    public int doCalculation(int zipcode) {
        if (averageLivableArea.containsKey(zipcode)) {
            return averageLivableArea.get(zipcode);
        } else {
            int totalLivableArea = 0;
            List<PropertyValues> houses = propertyValues.get(zipcode);
            if (houses.size() == 0) {
                return 0;
            }
            for (PropertyValues house: houses) {
                totalLivableArea += house.getTotalLivableArea();
            }
            int avgMktVal = totalLivableArea / houses.size();
            averageLivableArea.put(zipcode, avgMktVal);
            return avgMktVal;
        }
    }
*/