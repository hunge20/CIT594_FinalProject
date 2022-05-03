package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkViolations;
import edu.upenn.cit594.data.PopulationData;
import edu.upenn.cit594.datamanagement.PopulationTxt;
import edu.upenn.cit594.datamanagement.Reader;

public class TotalFinesPerCapita {
	
	protected PopulationTxt populationTxt;
	protected SortedMap<Integer, Integer> populationData;
	protected Reader reader;
	protected List<ParkViolations> violationsData;
	
	int input;
	//for memoization 
	private SortedMap<Integer, Double> result = new TreeMap<>();
	
	public TotalFinesPerCapita(PopulationTxt populationTxt, Reader reader) {
		this.populationTxt = populationTxt;
		this.populationData = populationTxt.getData();
		this.reader = reader;
		this.violationsData = reader.getParkViolations();
	}
	
	//memoization
	public SortedMap<Integer, Double> getTotalFinesPerCapita(int input){
		if (this.input == input) {
			return result;
		} else {
			result = findTotalFines();
			this.input = input;
			return result;
		}
	}
	
	private SortedMap<Integer, Double> findTotalFines(){
		
		for (int zip : populationData.keySet()) {
//			System.out.println("this is ZIP STOP");
			double totalFine = 0;
			for (ParkViolations violations : violationsData) {
				int violationsZip = violations.getZip();
				if (!violations.getState().equals("PA")) {continue;}
				if (zip == violationsZip) {
					double fine = violations.getFine(); //get the fine of the zip in parking violation data
//					System.out.println("this is fine: " + fine);
					if (result.get(zip) == null) {
						result.put(zip, fine);
					} else {
						totalFine = result.get(zip) + fine; //add the fine to the total fine
						result.put(zip, totalFine);
//						System.out.println("this is total fine: " + totalFine);		
					}
				} else {
					continue;
				}
			}
			
			//calculate total fines per capita
			if (totalFine > 0) { //do not include ZIP that has a fine of 0
				
				totalFine = totalFine / populationData.get(zip); // divide aggregated fine by population of given zip
				BigDecimal bd = new BigDecimal(totalFine); //truncate after 4 digits after decimal point
				bd = bd.setScale(4, RoundingMode.DOWN);
				totalFine= bd.doubleValue();
				
				result.put(zip, totalFine);
			}
		}
		
		return result;
		
	}
}