package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.data.ParkViolations;


public class ParkingCsv implements Reader{
	
	protected String filename;
	
	public ParkingCsv(String name) {
		filename = name;
	}
	
	@Override
	public List<ParkViolations> getParkViolations() {
		
		List<ParkViolations> parkViolations = new ArrayList<ParkViolations>();
		
		if (filename == null) {
			return parkViolations;
		}
		
		File file = new File(filename);
		BufferedReader bufferedReader = null;
		
		try {
			
			bufferedReader = new BufferedReader(new FileReader(file));
			
			String line = null;
			boolean quotes = false;

			StringBuilder sb = new StringBuilder(line);

			for (int i = 0; i < sb.length(); i++) { 
				if (sb.charAt(i) == '\"') {
					quotes = true; 
				}

				if (sb.charAt(i) == ',' && quotes == true) {
					sb.setCharAt(sb.charAt(i), ' ');
				}
			}

			
			while ((line = bufferedReader.readLine()) != null) {
				
				if(!line.isEmpty()) {
					String[] splitSentence = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
					
					//checks for valid zip format
					int zip;
					try {
						zip = Integer.parseInt(splitSentence[6]);
						if (zip < 0 || zip > 99999){ 
							continue;
						}
					} catch(NumberFormatException e) {
						continue;
					} catch(ArrayIndexOutOfBoundsException e) {
						continue;
					}
				
					
					String time = splitSentence[0];
					
					double fine;
					//checks for proper fine formatting
					try {
						fine = Double.parseDouble(splitSentence[1]);
						if (fine < 0) {
							continue;} 
					} catch(NumberFormatException e) {
						continue;
					}
					
					String description = splitSentence[2];
					String vehicleId = splitSentence[3];
					
					String state = splitSentence[4];
					if(checkStateFormat(state)== false) {state = " ";} // checks for valid state format
					
					String violationId = splitSentence[5];
					
					ParkViolations parkVioObj = new ParkViolations(time, fine, description, vehicleId, state, violationId, zip);
					parkViolations.add(parkVioObj);
				}
			}
		} catch (FileNotFoundException e) {
			return parkViolations;
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
		
		return parkViolations;
		
	}

	@Override
	public boolean checkStateFormat(String state) {
		
		String regex = "[a-zA-Z]{2}";
		Pattern pattern =  Pattern.compile(regex);
		Matcher matcher =  pattern.matcher(state);
	
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
	

}