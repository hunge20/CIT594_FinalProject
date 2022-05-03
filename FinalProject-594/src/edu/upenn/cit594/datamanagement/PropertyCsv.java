package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import edu.upenn.cit594.data.PropertyValues;

public class PropertyCsv {
	
	protected String filename;
	
	public PropertyCsv(String name) {
		filename = name;
	}
	
	public HashMap<Integer, ArrayList<PropertyValues>> getProperty() {

		HashMap<Integer, ArrayList<PropertyValues>> property = new HashMap<>();

		if (filename == null) {
			throw new RuntimeException("File not specified");
		}
		
		File file = new File(filename);
		BufferedReader bufferedReader = null;
		
		try {
			
			bufferedReader = new BufferedReader(new FileReader(file));
			
			String line;
			
			//gets the index of each required data
			int marketValueIndex = 0;
			int livableAreaIndex = 0;
			int zipCodeIndex = 0;
			if ((line = bufferedReader.readLine()) != null) {
				String[] splitSentence = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				for (int i = 0; i < splitSentence.length; i++) {
					if (splitSentence[i].equals("market_value")) {
						marketValueIndex = i;
					}
					if (splitSentence[i].equals("total_livable_area")) {
						livableAreaIndex = i;
					} 
					if (splitSentence[i].equals("zip_code")) {
						zipCodeIndex = i;
					} 
				}
			}
			
			int count = 0; // for counting how many missing data there are
			
			while ((line = bufferedReader.readLine()) != null) {
				
				if(!line.isEmpty()) {
					
					String[] splitSentence = line.split(",");
					
					//checks for valid marketValue format
//					String marketValStr = splitSentence[marketValueIndex];
					double marketValue = -1;
					try {
						marketValue = Double.parseDouble(splitSentence[marketValueIndex]);
						if (marketValue < 0){ 
							count++;
						}
					} catch(NumberFormatException e) {
						count++;
					} catch(ArrayIndexOutOfBoundsException e) {
						count++;
					}
				
					//checks for proper totalLivableArea formatting
					double totalLivableArea = -1;
					try {
						totalLivableArea = Double.parseDouble(splitSentence[livableAreaIndex]);
						if (totalLivableArea < 0) {
							count++;
						} 
					} catch(NumberFormatException e) {
						count++;
					} catch(ArrayIndexOutOfBoundsException e) {
						count++;
					}
					
					//retrieve first 5 digits of the zip 
					int zip = getFirstFiveZip(splitSentence[zipCodeIndex]);
					
					//if invalid zip, ignore this entry
					if (zip == -1) {
						continue;
					}
					
					//if invalid zip and marketValue & totalLivableArea are malformed, ignore this entry
					if (zip == -1 && count == 2) {continue;}
					
					PropertyValues propertyValObj = new PropertyValues(marketValue, totalLivableArea, zip);

					if (property.containsKey(zip)) {
						property.get(zip).add(propertyValObj);
					} else {
						ArrayList<PropertyValues> arr = new ArrayList<>();
						arr.add(propertyValObj);
						property.put(zip, arr);
					}
				}
			}
		} catch (FileNotFoundException e) {
			return property;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		return property;
		
	}
	
	public int getFirstFiveZip(String zipCodeStr) {
		
		String zipStr;

		int zip =-1;
		
		try {
			zipStr = zipCodeStr.substring(0,5);
			zip = Integer.parseInt(zipStr);
//			System.out.println("zipStr " + zip);
		} catch(InputMismatchException e) {
			return zip;
		} catch(StringIndexOutOfBoundsException e) {
			return zip;
		} catch(NumberFormatException e) {
			return zip;
		} 
		
		return zip;
	}
}