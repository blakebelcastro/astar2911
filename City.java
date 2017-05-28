import java.util.ArrayList;

public class City {
	private String name;
	private int transferTime;
	private ArrayList<Route> connections;

	public City(int transferTime, String name) {
		this.name = name;
		this.transferTime = transferTime;
		this.connections = new ArrayList<Route>();
	}

	/**
	 * 
	 * @return the unique name of the city
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return the transfer time of the city
	 */
	public int getTransferTime() {
		return this.transferTime;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Route> getConnections() {
		return this.connections;
	}

	/**
	 * Gets the route between this city and another city
	 * 
	 * @param toCity
	 *            the city the route from this city ends at
	 * @return the route if it exists, null otherwise
	 */
	public Route getRoute(String toCity) {
		for (Route r : this.connections) {
			if (r.getToCity().equals(toCity))
				return r;
		}
		return null;
	}

	/**
	 * Adds a new route to connect this city to another
	 * 
	 * @param otherCity
	 *            the name of the city to be connected to
	 * @param travelTime
	 *            the time required to traverse the the new route
	 */
	public void connectTo(String otherCity, int travelTime) {
		connections.add(new Route(this.name, otherCity, travelTime));
	}

	// for testing purposes, prints the routes attached to this city
	public void printConnections() {
		for (Route r : connections) {
			System.out.println(this.name + " is connected to " + r.getToCity() + " by a route that takes "
					+ r.getTravelTime() + "mins.");
		}
	}
}
