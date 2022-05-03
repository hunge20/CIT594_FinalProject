package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkViolations;


public class ParkingJson implements Reader {
	
	protected String filename;
	
	
	public ParkingJson(String name) {
		filename = name;
	}
	
	@Override
	public List<ParkViolations> getParkViolations() {
		
		List<ParkViolations> parkViolations = new ArrayList<ParkViolations>();
		
		JSONParser parser = new JSONParser();

		JSONArray parkViolationJson;
		
		try {
			parkViolationJson = (JSONArray)parser.parse(new FileReader(filename));
			Iterator iter = parkViolationJson.iterator();
			
			while (iter.hasNext()) {

				JSONObject parkViolation = (JSONObject) iter.next();
				
				//checks for valid zip format
				int zip;
				try {
					zip = Integer.parseInt(parkViolation.get("zip_code").toString());
					if (zip < 0 || zip > 99999){ 
						continue;
					}
				} catch(NumberFormatException e) {
					continue;
				} catch(ArrayIndexOutOfBoundsException e) {
					continue;
				}
				
				String time = parkViolation.get("date").toString();
				
				double fine;
				//checks for proper fine formatting
				try {
					fine = Double.parseDouble(parkViolation.get("fine").toString());
					if (fine < 0) {
						continue;} 
				} catch(NumberFormatException e) {
					continue;
				}
				
				String description = parkViolation.get("violation").toString();
				String vehicleId = parkViolation.get("plate_id").toString();
				String violationId = parkViolation.get("ticket_number").toString();
				String state = parkViolation.get("state").toString();
				if(checkStateFormat(state)== false) {state = " ";} // checks for valid state format
				
				ParkViolations parkVioObj = new ParkViolations(time, fine, description, vehicleId, state, violationId, zip);
				parkViolations.add(parkVioObj);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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