package nl.vu.swarm;

import org.semanticweb.yars.nx.Node;

public class GraphEdge {
	protected final GraphNode source;
	protected final GraphNode target;
	protected final Node label;

	/**
	 * @param label
	 * @param target
	 */
	public GraphEdge(GraphNode source, Node label, GraphNode target) {
		this.source = source;
		this.label = label;
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphEdge other = (GraphEdge) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	public GraphNode getSource() {
		return source;
	}

	public Node getLabel() {
		return label;
	}
	
	/**
	 * @return
	 */
	public GraphNode getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return source.toString() + " " + label.toN3() + " " + target.toString();
	}

	/**
	 * @param location
	 * @return
	 */
	public GraphNode getOtherEnd(GraphNode location) {
		return (location == source ? target : source);
	}

}
