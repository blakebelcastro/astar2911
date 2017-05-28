import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * This trip planner program uses an A* search to find an optimal journey around
 * a map generated from input cities. It will complete the input trips using the
 * shortest path.
 * 
 * HEURISTIC ANALYSIS: Currently the program's heuristic runs in O(n) time where
 * n is the number of trips required to be completed. This is because the
 * heuristic is based on looping through the trips and summing their values.
 * 
 * @author - Blake Belcastro
 *
 */
public class TripPlanner {

	private Map tripMap;
	private ArrayList<Route> reqTrips;

	private TripPlanner() {
		this.tripMap = new Map(); // Map of the cities available for the trip
		this.reqTrips = new ArrayList<Route>(); // list of edges that must be
												// traversed in the plan
	}

	public static void main(String[] args) throws ParseException {

		String currLine;
		TripPlanner system = new TripPlanner();

		// read in data to create map and record necessary trips
		Scanner sc = null;
		try {
			sc = new Scanner(new FileReader(args[0]));
			while (sc.hasNextLine()) {
				currLine = sc.nextLine();
				system.processInput(currLine);
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (sc != null)
				sc.close();
		}

		/*
		 * Print map for testing system.getMap().printMap();
		 */
		State endState = system.getMap().optimalJourney("London", system.getReqTrips());
		system.printJourney(endState);

	}

	/**
	 * 
	 * @return the map being used for the trip plan
	 */
	private Map getMap() {
		return this.tripMap;
	}

	/**
	 * 
	 * @return a list of the trips that must be completed for the trip plan
	 */
	private ArrayList<Route> getReqTrips() {
		return this.reqTrips;
	}

	/**
	 * Put a city and its accompanying transfer time on the map
	 * 
	 * @param time
	 *            the transfer time of the city
	 * @param city
	 *            the unique name of the city
	 */
	private void mapCity(int time, String city) {
		tripMap.addCity(time, city);
	}

	/**
	 * Adds a route with a specified travel time between 2 cities (edge between
	 * 2 nodes)
	 * 
	 * @param time
	 *            the time needed to traverse the route
	 * @param cityA
	 *            one of the cities the route joins
	 * @param cityB
	 *            the other city the route joins
	 */
	private void addRoute(int time, String cityA, String cityB) {
		tripMap.addRoute(cityA, cityB, time);
	}

	/**
	 * Adds a trip to the list of trips that need to be undertaken as part of
	 * the plan
	 * 
	 * @param fromCity
	 *            the name of the city the trip starts in
	 * @param toCity
	 *            the name of the city the trip ends in
	 */
	private void addTrip(String fromCity, String toCity) {
		Route trip = this.tripMap.getCity(fromCity).getRoute(toCity);
		reqTrips.add(new Route(trip.getFromCity(), trip.getToCity(), trip.getTravelTime()));
	}

	// performs actions depending on the input command
	private void processInput(String commandLine) throws ParseException {
		String[] input = commandLine.split(" ");

		switch (input[0]) {
		case "Transfer":
			this.mapCity(Integer.parseInt(input[1]), input[2]);
			break;
		case "Time":
			this.addRoute(Integer.parseInt(input[1]), input[2], input[3]);
			break;
		case "Trip":
			this.addTrip(input[1], input[2]);
			break;
		default:
			// A line of input was entered that didn't begin with one of the
			// expected commands
			System.out.println("Invalid command");
		}
	}

	/**
	 * Starting with the last State on a path, backtrace through its parent
	 * States (using a stack) to print a journey beginning from the starting
	 * city.
	 * 
	 * @param endState
	 *            the last State on a path
	 */
	private void printJourney(State endState) {
		Stack<String> tripStack = new Stack<String>();
		tripStack.push(endState.getName());
		String fromCity, toCity;

		State prevState = endState.getParent();

		while (prevState != null) {
			tripStack.push(prevState.getName());
			prevState = prevState.getParent();
		}

		fromCity = tripStack.pop();
		while (!tripStack.isEmpty()) {
			toCity = tripStack.peek();
			System.out.println("Trip " + fromCity + " to " + toCity);
			fromCity = tripStack.pop();
		}
	}
}
