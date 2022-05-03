package edu.upenn.cit594.data;

public class PopulationData {
	
	private final int population;
	private final int zip;
	
	public PopulationData(int population, int zip) {
		this.population = population;
		this.zip = zip;
	}
	
	public int getPopulation() {return population;}
	public int getZip() {return zip;}
	
}