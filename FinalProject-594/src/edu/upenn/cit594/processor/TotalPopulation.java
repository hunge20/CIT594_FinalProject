package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import edu.upenn.cit594.data.PopulationData;
import edu.upenn.cit594.datamanagement.PopulationTxt;

public class TotalPopulation {
	
	protected PopulationTxt populationTxt;
	protected SortedMap<Integer, Integer> populationData;
	int input;
	
	private int result;
	
	public TotalPopulation(PopulationTxt populationTxt) {
		this.populationTxt = populationTxt;
		populationData = populationTxt.getData();
	}
	
	//memoization
	public int getTotalPopulation(int input){
		if (this.input == input) {
			return result;
		} else {
			result = findTotalPopulation();
			this.input = input;
			return result;
		}
	}
	
	private int findTotalPopulation(){
		for (int zip : populationData.keySet()) {
			result += populationData.get(zip);
		}
		return result;
	}
}