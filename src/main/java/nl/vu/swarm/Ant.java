package nl.vu.swarm;

public class Ant {
	private final Graph graph;
	private GraphNode location;

	/**
	 * @param graph
	 */
	public Ant(Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * @param location
	 */
	public void setLocation(GraphNode location) {
		this.location = location;
	}
}
