package edu.upenn.cit594.ui;
import java.io.IOException;
import java.util.Scanner;
import java.util.SortedMap;

import javax.naming.OperationNotSupportedException;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.*;

public class UserInterface {

	protected TotalPopulation totalPopulation;
	protected TotalMarketValuePerCapita totalMarketValuePerCapita;
	protected TotalFinesPerCapita totalFinesPerCapita;
	protected PropertiesStrategy propertiesStrategy;
	protected AverageMarketValue averageMarketValue;
	protected AverageLivableArea averageLivableArea;
	protected AdditionalFeature additionalFeature;
	//	protected MarketValueAverage marketValueAverage;
	protected Scanner in;


	public static void print() {
		System.out.println("Hello, please select a number between 0 and 6:");
		System.out.println("0, Goodbye");
		System.out.println("1, Total Population for All Zipcodes");
		System.out.println("2, Total Parking Fines Per Capita for Each Zipcode");
		System.out.println("3, Average Market Value for Residences in a Specified Zipcode");
		System.out.println("4, Average Total Livable Area for Residences in a Specified Zipcode");
		System.out.println("5, Total Residential Market Value Per Capita for a Specified Zipcode");
		System.out.println("6. Lowest Fine and Highest Market Value Per Capita by Specified Zipcode");	
	}

	public UserInterface(TotalPopulation totalPopulation, TotalMarketValuePerCapita totalMarketValuePerCapita, TotalFinesPerCapita totalFinesPerCapita,
			AverageMarketValue averageMarketValue, AverageLivableArea averageLivableArea, AdditionalFeature additionalFeature) {
		this.totalPopulation = totalPopulation;
		this.totalMarketValuePerCapita = totalMarketValuePerCapita;
		this.totalFinesPerCapita = totalFinesPerCapita;
		this.averageMarketValue = averageMarketValue;
		this.averageLivableArea = averageLivableArea;
		this.additionalFeature = additionalFeature;
		this.in = new Scanner(System.in);
	}

	public void start() throws OperationNotSupportedException, IOException {

		print();

		while(in.hasNextInt()) {

			int chosenNumber = in.nextInt();
			in.nextLine();
			//			Logger logger = Logger.getInstance();
			Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + chosenNumber);

			if(chosenNumber == 0) {
				System.out.println("Goodbye. The program is exiting.");
				return;
			}else if(chosenNumber == 1) {
				System.out.println("Total population is: " + totalPopulation.getTotalPopulation(chosenNumber));
			}else if(chosenNumber == 2) {
				SortedMap<Integer, Double> totalFinesperCapita = totalFinesPerCapita.getTotalFinesPerCapita(chosenNumber);
				for(int zip : totalFinesperCapita.keySet()) {
					System.out.println(zip + " " + totalFinesperCapita.get(zip));
				}
				System.out.println();
			}else if(chosenNumber == 3) {
				System.out.println("Please enter a zipcode:");
				String zipcode = in.nextLine();
				int zipInt = Integer.parseInt(zipcode);
				Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + zipInt);
				if(averageMarketValue.getAverageMarketValue(zipInt) != -1) {
					System.out.println(averageMarketValue.getAverageMarketValue(zipInt));
				}else {
					while(averageMarketValue.getAverageMarketValue(zipInt) == -1) {
						System.out.println("Please enter a valid zipcode:");
						zipcode = in.nextLine();
						zipInt = Integer.parseInt(zipcode);
						Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + zipInt);

					}
					System.out.println(averageMarketValue.getAverageMarketValue(zipInt));
				}
			}
			else if(chosenNumber == 4) {
				System.out.println("Please enter a zipcode:");
				String zipcode = in.nextLine();
				int zipInt = Integer.parseInt(zipcode);
				Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + zipInt);
				if(averageLivableArea.getAverageLivableArea(zipInt) != -1) {
					System.out.println(averageLivableArea.getAverageLivableArea(zipInt));
				}else {
					while(averageLivableArea.getAverageLivableArea(zipInt) == -1) {
						System.out.println("Please enter a valid zipcode:");
						zipcode = in.nextLine();
						zipInt = Integer.parseInt(zipcode);
						Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + zipInt);
					}
					System.out.println(averageLivableArea.getAverageLivableArea(zipInt));
				}
			}else if(chosenNumber == 5) {
				System.out.println("Please enter a zipcode:");
				String zipcode = in.nextLine();
				int zipInt = Integer.parseInt(zipcode);
				Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + zipInt);
				if(totalMarketValuePerCapita.getTotalMarketValue(zipInt) != -1) {
					System.out.println(totalMarketValuePerCapita.getTotalMarketValue(zipInt));
				}else {
					while(totalMarketValuePerCapita.getTotalMarketValue(zipInt) == -1) {
						System.out.println("Please enter a valid zipcode:");
						zipcode = in.nextLine();
						zipInt = Integer.parseInt(zipcode);
						Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + zipInt);
					}
					System.out.println(totalMarketValuePerCapita.getTotalMarketValue(zipInt));
				}

			}else if(chosenNumber == 6) {
				System.out.println("Please enter a zipcode:");

				String zipcode = in.nextLine();
				int zipInt = Integer.parseInt(zipcode);
				Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + zipInt);
				if(additionalFeature.lowestFineAndHighestMarketValue(zipInt) != null) {
					System.out.println(additionalFeature.lowestFineAndHighestMarketValue(zipInt));
				}else{
					while (additionalFeature.lowestFineAndHighestMarketValue(zipInt) == null) {
						System.out.println("Please enter a valid zipcode:");
						zipcode = in.nextLine();
						zipInt = Integer.parseInt(zipcode);
						Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + zipInt);
					}
					System.out.println(additionalFeature.lowestFineAndHighestMarketValue(zipInt));
				}
			}else {
				System.out.println("Please enter a valid number");
				continue;
			}

			print();

		}
		in.close();
	}
}