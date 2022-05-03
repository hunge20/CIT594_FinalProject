package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedMap;

import edu.upenn.cit594.data.ParkViolations;
import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.datamanagement.PopulationTxt;
import edu.upenn.cit594.datamanagement.PropertyCsv;
import edu.upenn.cit594.datamanagement.Reader;

public class AdditionalFeature {
	//additional feature in step 6
	//find lowest fine and highest market value per capita based on user's input of zip
	
	protected PopulationTxt populationTxt;
	protected SortedMap<Integer, Integer> populationData;
	protected Reader reader;
	protected List<ParkViolations> violationsData;
	protected PropertyCsv propertyCsv;
	protected HashMap<Integer, ArrayList<PropertyValues>> propertyData;
	
	//for memoization
	private Map<Integer, ArrayList<Object>> results;
	
	public AdditionalFeature(PopulationTxt populationTxt, Reader reader, PropertyCsv propertyCsv) {
		this.populationTxt = populationTxt;
		populationData = populationTxt.getData();
		this.reader = reader;
		violationsData = reader.getParkViolations();
		this.propertyCsv = propertyCsv;
		propertyData = propertyCsv.getProperty();
		this.results = new HashMap<>();
	}
	
	//memoization
	public ArrayList<Object> lowestFineAndHighestMarketValue(int input){
		
		if (!propertyData.containsKey(input)) {
			return null;
		}
		if (results.containsKey(input)) {
			return results.get(input);
		} else {
			ArrayList<Object> result = findLowestFineAndHighestMarketValue(input);
			results.put(input, result);
			return result;
		}
	}
	
	private ArrayList<Object> findLowestFineAndHighestMarketValue(int inputInt){
		
		ArrayList<Object> result = new ArrayList<Object>();
		PriorityQueue<Double> sortedFine = new PriorityQueue<>();
		ArrayList<Double> sortedMarketValue = new ArrayList<>();
		
		for (ParkViolations violations : violationsData) {
			if (violations.getZip() == inputInt && violations.getFine() > 0) {
				sortedFine.add(violations.getFine());
			}
		}
		//get lowest fine
		double lowestFine = sortedFine.peek();
		
		//get market value of each house 
		List<PropertyValues> houses = propertyData.get(inputInt);
		
		for (PropertyValues house : houses) {
			sortedMarketValue.add(house.getMarketValue());
		}
		
		//sort the market value
		Collections.sort(sortedMarketValue, Collections.reverseOrder());
		double highestMarketValue = sortedMarketValue.get(0);
		
		//find population of zip
		int population = populationData.get(inputInt);
		//get highestMarketValue per capita
		highestMarketValue = highestMarketValue / population;
		int highestMarketValueInt = (int) highestMarketValue;
		
		result.add("Lowest Fine: " + lowestFine);
		result.add("Highest Market Value: " + highestMarketValueInt);
		
		return result;
	}
	
}