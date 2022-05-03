package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.datamanagement.PopulationTxt;
import edu.upenn.cit594.datamanagement.PropertyCsv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public class TotalMarketValuePerCapita {

    protected HashMap<Integer, ArrayList<PropertyValues>> propertyValues;
    protected SortedMap<Integer, Integer> populationData;
    int input;
    
    //for memoization
    private Map<Integer, Integer> results = new HashMap<>();

    public TotalMarketValuePerCapita(PropertyCsv propertyCsv, PopulationTxt populationTxt) {
        this.propertyValues = propertyCsv.getProperty();
        this.populationData = populationTxt.getData();
    }

    //memoization
	public int getTotalMarketValue(int input){
		
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
    
    public int doCalculation(int zipcode) {
        int population = populationData.get(zipcode);
        ArrayList<PropertyValues> houses = propertyValues.get(zipcode);

        double totalMarketValue = 0;
        for (PropertyValues house : houses) {
            totalMarketValue += house.getMarketValue();
        }
        int total =  (int) (totalMarketValue / population);
        return total;
    }
}