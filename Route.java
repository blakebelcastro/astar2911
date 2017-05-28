
public class Route {
	private String fromCity, toCity;
	private int travelTime;

	public Route(String from, String to, int weight) {
		this.fromCity = from;
		this.toCity = to;
		this.travelTime = weight;
	}

	/**
	 * 
	 * @return the name of the city from which the route begins
	 */
	public String getFromCity() {
		return this.fromCity;
	}

	/**
	 * 
	 * @return the name of the city the route ends at
	 */
	public String getToCity() {
		return this.toCity;
	}

	/**
	 * 
	 * @return the time required to traverse the route
	 */
	public int getTravelTime() {
		return this.travelTime;
	}

	/**
	 * Checks whether a given route joins two cities
	 * 
	 * @param cityA
	 *            the name of the starting city
	 * @param cityB
	 *            the name of the ending city
	 * @return true if the route does join the two cities, false otherwise
	 */
	public boolean joinsAtoB(String cityA, String cityB) {
		if (fromCity.equals(cityA) && toCity.equals(cityB))
			return true;
		else
			return false;
	}
}
