package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import edu.upenn.cit594.data.PopulationData;


public class PopulationTxt {
	
	protected String filename;
	
	public PopulationTxt(String name) {
		filename = name;
	}
	
	public SortedMap<Integer, Integer> getData() {
		
		SortedMap<Integer, Integer> populations = new TreeMap<>(); 
		
		if (filename == null) {
			return populations;
		}
		
		File file = new File(filename);
		BufferedReader bufferedReader = null;
		
		try {
			
			bufferedReader = new BufferedReader(new FileReader(file));
			
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				
				if(!line.isEmpty()) {
					
					String[] splitSentence = line.split(" ");
					
					// gets proper zip formatting
					int zip;
					try {
						zip = Integer.parseInt(splitSentence[0]);
						if (zip < 0 || zip > 99999){ 
							continue;
						}
					} catch(NumberFormatException e) {
						continue;
					}
					
					
					int population = Integer.parseInt(splitSentence[1]);
					if (population < 0) {population = 0;}
				
					populations.put(zip, population);
				}
			}
		} catch (FileNotFoundException e) {
			return populations;
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
		
		return populations;
		
	}
}