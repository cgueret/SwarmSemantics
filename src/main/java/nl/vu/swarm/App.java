package nl.vu.swarm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Start");
		
		// Load the graph
		Graph graph = new Graph();
		graph.loadFrom("data/mini.nt");

		// Create the ants
		List<Ant> ants = new ArrayList<Ant>();
		for (int i = 0; i < 1; ++i) {
			Ant ant = new Ant(graph);
			ants.add(ant);
		}

		// Run the experiments
		for (int run = 0; run < 10; run++) {
			for (Ant ant : ants)
				ant.setLocation(graph.getRandomNode());
			for (int i = 0; i < 100000; i++)
				for (Ant ant : ants)
					ant.doSomething();
		}
		System.out.println("End");

		graph.printResult();
	}
}

// store number of time a triple is inferred
// report this with the degree of the nodes concerned (start, middle, end)

