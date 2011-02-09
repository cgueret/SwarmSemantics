package nl.vu.swarm;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import org.semanticweb.yars.nx.Literal;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.parser.NxParser;

public class Graph {
	private final static Random rand = new Random();
	private final Map<Node, GraphNode> nodesIndex = new HashMap<Node, GraphNode>();
	private final Map<GraphNode, Set<GraphEdge>> edgesIndex = new HashMap<GraphNode, Set<GraphEdge>>();
	private final Set<GraphNode> nodes = new HashSet<GraphNode>();
	private final Set<GraphEdge> edges = new HashSet<GraphEdge>();

	// Count the number of inferred edges
	private final Map<InferredEdge, Integer> inferredEdges = new HashMap<InferredEdge, Integer>();

	/**
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void loadFrom(String fileName) throws FileNotFoundException, IOException {
		NxParser nxp = new NxParser(new FileInputStream(fileName), false);

		while (nxp.hasNext()) {
			Node[] ns = nxp.next();
			if (ns[2] instanceof Literal)
				continue;

			// Create and store the edge
			GraphNode subject = getGraphNode(ns[0]);
			GraphNode object = getGraphNode(ns[2]);
			GraphEdge edge = new GraphEdge(subject, ns[1], object);
			edges.add(edge);

			// Index the edge
			if (!edgesIndex.containsKey(subject))
				edgesIndex.put(subject, new HashSet<GraphEdge>());
			edgesIndex.get(subject).add(edge);
			if (!edgesIndex.containsKey(object))
				edgesIndex.put(object, new HashSet<GraphEdge>());
			edgesIndex.get(object).add(edge);
		}

	}

	/**
	 * @param fileName
	 * @throws IOException
	 */
	public void saveTo(String fileName) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		out.write("Source,Target,Label,Type\n");
		for (GraphEdge edge : edges) {
			out.write(edge.getSource().toString());
			out.write(',');
			out.write(edge.getTarget().toString());
			out.write(',');
			out.write(edge.toString());
			out.write(",directed\n");
		}
		out.close();
	}

	/**
	 * @param node
	 * @return
	 */
	private GraphNode getGraphNode(Node node) {
		GraphNode graphNode = nodesIndex.get(node);
		if (graphNode == null) {
			graphNode = new GraphNode(node);
			nodesIndex.put(node, graphNode);
			nodes.add(graphNode);
		}
		return graphNode;
	}

	/**
	 * @return
	 */
	public GraphNode getRandomNode() {
		return (GraphNode) nodes.toArray()[rand.nextInt(nodes.size())];
	}

	/**
	 * @param node
	 * @return
	 */
	public Collection<GraphEdge> getEdges(GraphNode node) {
		return edgesIndex.get(node);
	}

	/**
	 * @param source
	 * @param label
	 * @param target
	 */
	public void addInferredEdge(GraphNode source, Node label, GraphNode target, Collection<GraphNode> steps) {
		InferredEdge edge = new InferredEdge(source, label, target, steps);
		Integer count = 0;
		if (inferredEdges.containsKey(edge))
			count = inferredEdges.get(edge);
		inferredEdges.put(edge, count + 1);

		// if (edge.getSource().toString().contains("FullProfessor")) {
		// System.out.println(Integer.toString(inferredEdges.size()) + " " +
		// edge);
		// }
	}
	
	/**
	 * @param node
	 * @return
	 */
	public int getDegree (GraphNode node ) {
		return edgesIndex.get(node).size();
	}

	public void printResult() {
		// Sort the map according to the counter value
		List<Entry<InferredEdge, Integer>> list = new Vector<Entry<InferredEdge, Integer>>(inferredEdges.entrySet());
		java.util.Collections.sort(list, new Comparator<Entry<InferredEdge, Integer>>() {
			public int compare(Entry<InferredEdge, Integer> a, Entry<InferredEdge, Integer> b) {
				return (b.getValue().compareTo(a.getValue()));
			}
		});

		for (Entry<InferredEdge, Integer> entry : list) {
			if (entry.getValue() > 1) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(entry.getValue()).append(" ");
				buffer.append(getDegree(entry.getKey().getSource())).append(" ");
				for (GraphNode node: entry.getKey().getSteps())
					buffer.append(getDegree(node)).append(" ");
				buffer.append(getDegree(entry.getKey().getTarget())).append(" ");
				buffer.append(entry.getKey()).append(" ");
				System.out.println(buffer.toString());
			}
		}
	}
}
