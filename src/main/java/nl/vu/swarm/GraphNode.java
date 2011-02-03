package nl.vu.swarm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.yars.nx.Node;

public class GraphNode {
	private final Node node;
	private final Set<GraphEdge> edges = new HashSet<GraphEdge>();

	/**
	 * @param node
	 */
	public GraphNode(Node node) {
		this.node = node;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return node.toN3();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return node.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return node.equals(obj);
	}

	/**
	 * @param target
	 * @param label
	 */
	public void connectTo(GraphNode target, Node label) {
		edges.add(new GraphEdge(label, target));
	}

	/**
	 * @return
	 */
	public Collection<GraphEdge> edges() {
		return edges;
	}
}
