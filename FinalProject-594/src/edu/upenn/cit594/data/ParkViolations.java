package edu.upenn.cit594.data;

public class ParkViolations {
	
	private final String time;
	private final double fine;
	private final String description;
	private final String vehicleId;
	private final String state;
	private final String violationId;
	private final int zip;

	public ParkViolations(String time, Double fine, String description, String vehicleId, String state, String violationId, int zip){
		this.time = time;
		this.fine = fine;
		this.description = description;
		this.vehicleId = vehicleId;
		this.state = state;
		this.violationId = violationId;
		this.zip = zip;
	}
	
	public String getTime() {return time;}
	public double getFine() {return fine;}
	public String getDescription() {return description;}
	public String getVehicleId() {return vehicleId;}
	public String getState() {return state;}
	public String getViolationId() {return violationId;}
	public int getZip() {return zip;}

}