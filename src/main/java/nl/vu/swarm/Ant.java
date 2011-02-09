package nl.vu.swarm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;

public class Ant {
	protected final static Node TYPE = new Resource("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	protected final static Node SUBCLASS = new Resource("http://www.w3.org/2000/01/rdf-schema#subClassOf");
	private final static Random rand = new Random();
	private final Graph graph;
	private GraphNode location;
	private GraphEdge lastEdge;

	/**
	 * @param graph
	 */
	public Ant(Graph graph) {
		this.graph = graph;
		this.lastEdge = null;
	}

	/**
	 * @param location
	 */
	public void setLocation(GraphNode location) {
		this.location = location;
	}

	/**
	 * 
	 */
	public void doSomething() {
		// Decide on the next move to take
		Collection<GraphEdge> edges = graph.getEdges(location);
		int size = edges.size();
		GraphEdge edge = lastEdge;
		// while (edge == lastEdge)
		edge = (GraphEdge) edges.toArray()[rand.nextInt(size)];
		// if (edge.toString().startsWith("<http://www.w3.org"))
		// System.out.println(edge.getSource() + " " + edge + " " +
		// edge.getTarget());

		// See if we can infer something at destination
		// <http://www.Department0.University0.edu/GraduateStudent137>
		// <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>
		// <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#ResearchAssistant>
		// <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#ResearchAssistant>
		// <http://www.w3.org/2000/01/rdf-schema#subClassOf>
		// <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Student>

		if (lastEdge != null) {

			// Rule 9
			if (lastEdge.getLabel().equals(SUBCLASS) && edge.getLabel().equals(TYPE)
					&& lastEdge.getSource() == edge.getTarget()) {
				List<GraphNode> steps = new ArrayList<GraphNode>();
				steps.add(lastEdge.getSource());
				graph.addInferredEdge(edge.getSource(), TYPE, lastEdge.getTarget(), steps);
			}
			if (lastEdge.getLabel().equals(TYPE) && edge.getLabel().equals(SUBCLASS)
					&& lastEdge.getTarget() == edge.getSource()) {
				List<GraphNode> steps = new ArrayList<GraphNode>();
				steps.add(lastEdge.getTarget());
				graph.addInferredEdge(lastEdge.getSource(), TYPE, edge.getTarget(), steps);
			}
		}

		// Move
		location = edge.getOtherEnd(location);
		lastEdge = edge;
	}
}
