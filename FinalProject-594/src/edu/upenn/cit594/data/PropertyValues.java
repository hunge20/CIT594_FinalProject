package edu.upenn.cit594.data;

public class PropertyValues {
	
	private final double marketValue;
	private final double totalLivableArea;
	private final int zipProperty;
	
	//constructor
	public PropertyValues(double marketValue, double totalLivableArea, int zipProperty) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.zipProperty = zipProperty;
	}

	public double getMarketValue() {return marketValue;}
	public double getTotalLivableArea() {return totalLivableArea;}
	public int getZipProperty() {return zipProperty;}
	
	
}