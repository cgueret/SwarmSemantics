package nl.vu.swarm;

import java.util.Collection;

import org.semanticweb.yars.nx.Node;

public class InferredEdge extends GraphEdge {
	private Collection<GraphNode> steps;
	
	/**
	 * @param source
	 * @param label
	 * @param target
	 * @param steps 
	 */
	public InferredEdge(GraphNode source, Node label, GraphNode target, Collection<GraphNode> steps) {
		super(source, label, target);
		this.steps = steps;
	}

	public Collection<GraphNode> getSteps() {
		return steps;
	}
}
