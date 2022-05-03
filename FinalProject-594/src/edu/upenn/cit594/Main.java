package edu.upenn.cit594;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import edu.upenn.cit594.data.ParkViolations;
import edu.upenn.cit594.data.PopulationData;
import edu.upenn.cit594.data.PropertyValues;
import edu.upenn.cit594.datamanagement.ParkingCsv;
import edu.upenn.cit594.datamanagement.ParkingJson;
import edu.upenn.cit594.datamanagement.PopulationTxt;
import edu.upenn.cit594.datamanagement.PropertyCsv;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.AdditionalFeature;
import edu.upenn.cit594.processor.AverageLivableArea;
import edu.upenn.cit594.processor.AverageMarketValue;
import edu.upenn.cit594.processor.TotalFinesPerCapita;
import edu.upenn.cit594.processor.TotalMarketValuePerCapita;
import edu.upenn.cit594.processor.TotalPopulation;
import edu.upenn.cit594.ui.UserInterface;

public class Main {

	public static void main(String[] args) throws Exception {
		
		if(args.length != 5) {
			System.out.println("Arugment number must be 5");
			return;
		}
		
		if (!args[0].equals("csv") && !args[0].equals("json")) {
			System.out.println("Invalid file type. Must be csv or json.");
			System.exit(0);
		}
		
		String parkingViolationsFileType = args[0];
		String parkingViolationsFileName = args[1];
		String propertyValuesFileName = args[2];
		String populationsFileName = args[3];
		String logFile = args[4];
		
		try {
			
			Logger.setFilePath(logFile);
			Logger.getInstance();
			String startLogging = Long.toString(System.currentTimeMillis()) + " " + parkingViolationsFileType + " " + parkingViolationsFileName
					+ " " + propertyValuesFileName + " " + populationsFileName + " " + logFile;
			Logger.getInstance().log(startLogging);
			
			//log time and name of file whenever a file is opened for reading
			File parkingViolationsFile = new File(parkingViolationsFileName);
			Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + parkingViolationsFileName);
			File propertyFile = new File(propertyValuesFileName);
			Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + propertyValuesFileName);
			File populationsFile = new File(populationsFileName);
			Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + populationsFileName);
			
			Reader parkingViolationsReader;
			
			if(parkingViolationsFileType.equals("json")){
				 parkingViolationsReader = new ParkingJson(parkingViolationsFileName);
			} else {
				 parkingViolationsReader = new ParkingCsv(parkingViolationsFileName);
			}
	
			PropertyCsv propertyReader = new PropertyCsv(propertyValuesFileName);
			PopulationTxt populationsReader = new PopulationTxt(populationsFileName);
			TotalPopulation totalPopulation = new TotalPopulation(populationsReader);
			TotalFinesPerCapita totalFinesPerCapita = new TotalFinesPerCapita(populationsReader, parkingViolationsReader);
			AverageLivableArea averageLivableArea = new AverageLivableArea(propertyReader);
			AverageMarketValue averageMarketValue = new AverageMarketValue(propertyReader);
			TotalMarketValuePerCapita totalMarketValuePerCapita = new TotalMarketValuePerCapita(propertyReader,populationsReader);
			AdditionalFeature additionalFeature = new AdditionalFeature(populationsReader, parkingViolationsReader, propertyReader);
			UserInterface ui = new UserInterface(totalPopulation, totalMarketValuePerCapita, totalFinesPerCapita, averageMarketValue, averageLivableArea, additionalFeature);
			
			ui.start();
			
			
		} catch (FileNotFoundException e) {
			throw new Exception("One of the input files is invalid");
        } catch (IOException e) {
            throw new Exception("Cannot create log file");
        } 
		
//		ParkingCsv test = new ParkingCsv("parking.csv");
//		List<ParkViolations> csvData = new ArrayList<ParkViolations>();
//		csvData = test.getParkViolations();
//		
//		for (ParkViolations parkVio : csvData) {System.out.println(parkVio.getZip());}
		
//		ParkingJson test = new ParkingJson("parking.json");
//		List<ParkViolations> jsonData = new ArrayList<ParkViolations>();
//		jsonData = test.getParkViolations();
//		
//		for (ParkViolations parkVio : jsonData) {System.out.println(parkVio.getZip());}
		
//		PopulationTxt popTest = new PopulationTxt("population.txt");
//		TotalPopulation totalPopulation = new TotalPopulation(popTest);
//		int popData;
//		popData = totalPopulation.getTotalPopulation(1);
//		TotalFinesPerCapita totalFines = new TotalFinesPerCapita(popTest, test);
//		
//		SortedMap<Integer, Double> testTwo = new TreeMap<>();
//		testTwo = totalFines.getTotalFinesPerCapita(2);
//		for (int zip : testTwo.keySet()) {
//			System.out.println("this is value: " + testTwo.get(zip));
//			
//		}
		
//		System.out.println("pop total: " + popData);
		
//		popData = popTest.getData();
//		for (PopulationData popObj : popData) {System.out.println(popObj.getPopulation());}
		
//		PropertyCsv propTest = new PropertyCsv("properties.csv");
//		List<PropertyValues> propData = new ArrayList<PropertyValues>();
//		propData = propTest.getProperty();
		
//		for (PropertyValues propObj : propData) {System.out.println(propObj.getMarketValue());}
		
		
		
	}

}