package edu.upenn.cit594.datamanagement;

import java.util.List;

import edu.upenn.cit594.data.ParkViolations;

public interface Reader {

	public List<ParkViolations> getParkViolations();
	
	public boolean checkStateFormat(String state);
	
}
