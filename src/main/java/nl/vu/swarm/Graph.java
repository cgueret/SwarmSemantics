package nl.vu.swarm;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.yars.nx.Literal;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.parser.NxParser;

public class Graph {
	private final Map<Node, GraphNode> nodesIndex = new HashMap<Node, GraphNode>();

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
			
			GraphNode subject = getGraphNode(ns[0]);
			GraphNode object = getGraphNode(ns[2]);
			subject.connectTo(object, ns[1]);
		}

	}

	/**
	 * @param fileName
	 * @throws IOException
	 */
	public void saveTo(String fileName) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		out.write("Source,Target,Label,Type\n");
		for (GraphNode node : nodesIndex.values()) {
			for (GraphEdge edge : node.edges()) {
				out.write(node.toString());
				out.write(',');
				out.write(edge.getTarget().toString());
				out.write(',');
				out.write(edge.toString());
				out.write(",directed\n");
			}
		}
		out.close();
	}

	/**
	 * @param node
	 * @return
	 */
	public GraphNode getGraphNode(Node node) {
		GraphNode graphNode = nodesIndex.get(node);
		if (graphNode == null) {
			graphNode = new GraphNode(node);
			nodesIndex.put(node, graphNode);
		}
		return graphNode;
	}
}
