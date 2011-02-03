package nl.vu.swarm;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Start");
		Graph graph = new Graph();
		graph.loadFrom("data/mini.nt");
		graph.saveTo("/tmp/edges.csv");
		System.out.println("End");
	}
}
