import java.util.ArrayList;

public class State implements Comparable<State> {
	private City city; // the city this state represents within a certain path
	private State parent; // state on the path before this state
	private int totalCost; // total travelTime required to reach this point
	private ArrayList<Route> remainingTrips; // trips that haven't been covered
												// by this state's path

	public State(City sourceCity) {
		this.city = sourceCity;
		this.parent = null;
		this.totalCost = sourceCity.getTransferTime(); // initially just the
														// transfer time, but
														// when a
														// parent is added its
														// total cost is added
														// on
		this.remainingTrips = new ArrayList<Route>();
	}

	/**
	 * 
	 * @return the city this state represents
	 */
	public City getCity() {
		return this.city;
	}

	/**
	 * 
	 * @return the state that preceded this one on its path
	 */
	public State getParent() {
		return this.parent;
	}

	/**
	 * 
	 * @return the total cost stored in this state
	 */
	public int getTotalCost() {
		return this.totalCost;
	}

	/**
	 * 
	 * @return the name of the city this state represents
	 */
	public String getName() {
		return this.city.getName();
	}

	/**
	 * 
	 * @return a list of the trips this state is still required to make in order
	 *         to finish the trip plan
	 */
	public ArrayList<Route> getRemainingTrips() {
		return this.remainingTrips;
	}

	/**
	 * Checks whether a city needs to be visited by seeing if it is either the
	 * beginning or end of any remaining trips
	 * 
	 * @param city
	 *            the name of the city that is being checked
	 * @return true if the city must be visited to complete the plan, false
	 *         otherwise
	 */
	public boolean needsToVisit(String city) {
		for (Route r : this.remainingTrips) {
			if (r.getToCity().equals(city))
				return true;
			if (r.getFromCity().equals(city))
				return true;
		}
		return false;
	}

	/**
	 * Updates the list of remaining trips necessary for this State
	 * 
	 * @param remaining
	 *            the list of trips this State is yet to complete on this path
	 */
	public void setRemainingTrips(ArrayList<Route> remaining) {
		this.remainingTrips.addAll(remaining);
	}

	/**
	 * Sets the parent State for this state
	 * 
	 * @param parent
	 *            the State which preceded this State on the path
	 */
	public void setParent(State parent) {
		this.parent = parent;
	}

	/**
	 * Adds a cost to this states total cost
	 * 
	 * @param cost
	 *            the value of the cost to be added
	 */
	public void addCost(int cost) {
		this.totalCost += cost;
	}

	/**
	 * Compares States in the priority queue. Returns -1 if the other State has
	 * a larger h(n) + g(n) cost than the current state and returns 1 if vice
	 * versa. Returs 0 if they have equal values.
	 */
	public int compareTo(State other) {
		if ((this.totalCost + this.heuristic()) < (other.getTotalCost() + other.heuristic()))
			return -1;
		else if ((this.totalCost + this.heuristic()) > (other.getTotalCost() + other.heuristic()))
			return 1;
		else
			return 0;
	}

	/**
	 * The heuristic function used to increase the efficiency of the a* search.
	 * 
	 * @return a heuristic value
	 */
	private int heuristic() {
		int sumRemainingTrips = 0;
		for (Route r : this.remainingTrips) {
			sumRemainingTrips += r.getTravelTime();
		}
		// if moving to this state would complete a trip, return 0
		for (Route r : this.remainingTrips) {
			if (r.joinsAtoB(this.parent.getName(), this.getName()))
				return (sumRemainingTrips - r.getTravelTime());
		}
		// otherwise if a neighbour of this state would complete a trip,
		return (sumRemainingTrips);
	}
}
