import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

//weighted but undirected graph

public class Map {
	private HashMap<String, City> cities; // stored using a unique String name
											// as the key

	public Map() {
		this.cities = new HashMap<String, City>();
	}

	/**
	 * 
	 * @param cityName
	 *            the name of the city to be retrieved from the map
	 * @return the city represented by the unique String name supplied
	 */
	public City getCity(String cityName) {
		return this.cities.get(cityName);
	}

	/**
	 * Adds a route to the graph between two cities (effectively an edge between
	 * two nodes) Since there routes are undirected, they are added to the city
	 * at either end of the route
	 * 
	 * @param cityA
	 *            one of the cities the route joins
	 * @param cityB
	 *            the other city the route joins
	 * @param travelTime
	 *            the time (edge weight) required to traverse the route
	 */
	public void addRoute(String cityA, String cityB, int travelTime) {
		cities.get(cityA).connectTo(cityB, travelTime);
		cities.get(cityB).connectTo(cityA, travelTime);
	}

	/**
	 * Adds a city to the map
	 * 
	 * @param time
	 *            the transfer time of the city
	 * @param name
	 *            the unique name used to represent the city
	 */
	public void addCity(int time, String name) {
		cities.put(name, new City(time, name));
	}

	/**
	 * Uses an a* search to find the optimal journey for a given set of trips:
	 * that is, a journey which will complete the desired set of trips in the
	 * lowest amount of time. Prints the nodes expanded and total cost at the
	 * end.
	 * 
	 * @param startCity
	 *            the name of the city to begin the journey from
	 * @param reqTrips
	 *            a list of the trips that are required to be completed
	 * @return the last State on the optimal path discovered during the search
	 */
	public State optimalJourney(String startCity, ArrayList<Route> reqTrips) {
		PriorityQueue<State> toVisit = new PriorityQueue<State>();
		int numExpanded = 0;
		int cost = 0;
		State currState, nextState;
		currState = new State(this.getCity(startCity));
		currState.setRemainingTrips(reqTrips);
		toVisit.add(currState);

		// Keep searching until all the required trips have been covered
		while (!currState.getRemainingTrips().isEmpty()) {
			currState = toVisit.remove();
			numExpanded++;
			// if the path just traversed was a require trip, remove it
			if (currState.getParent() != null) {
				for (Route r : currState.getRemainingTrips()) {
					if (r.joinsAtoB(currState.getParent().getName(), currState.getName())) {
						currState.getRemainingTrips().remove(r);
						break;
					}
				}
			}
			// Add neighbour states to the priority queue
			for (Route r : currState.getCity().getConnections()) {
				// if the city doesn't have to be visited, don't add a state for
				// it
				if (!currState.needsToVisit(r.getToCity()))
					continue;
				nextState = new State(this.getCity(r.getToCity()));
				nextState.setParent(currState);
				nextState.addCost(currState.getTotalCost() + r.getTravelTime());
				nextState.setRemainingTrips(currState.getRemainingTrips());
				toVisit.add(nextState);
			}
		}
		// Total cost of the last node minus both its and the starting city's
		// transfer times
		cost = (currState.getTotalCost() - this.getCity(startCity).getTransferTime()
				- currState.getCity().getTransferTime());
		System.out.println(numExpanded + " nodes expanded");
		System.out.println("cost = " + cost);
		nextState = currState;
		return currState;
	}

	// For testing purposes, prints all cities and their attached routes
	public void printMap() {
		for (City c : cities.values()) {
			System.out.println(c.getName() + " exists on the map!");
			c.printConnections();
		}

	}
}
