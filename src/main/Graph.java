package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class implements a graph and the methods necessary to implement the graph. It uses a list of vertices to represent a graph.
 * It also implements methods to check the graph for cycles (indicating deadlock), create an empty graph, and print the
 * graph itself with all its edges and vertices.
 * See the vertex class for more information.
 */

public class Graph {

	// arrayList that holds the vertices in the graph
	static List<Vertex> vertices;

	//Constructor
	public Graph() {
		Graph.vertices = new ArrayList<>();
	}

	// Various small mutator methods
	public void addVertex(Vertex vertex) {
		Graph.vertices.add(vertex);
	}

	public void addEdge(Vertex from, Vertex to) {
		from.addNeighbor(to);

	}

	public void removeEdge(Vertex from, Vertex to) {
		from.removeNeighbor(to);

	}

	public List<Vertex> getAdjVertices() {
		return vertices;
	}

	void setVertexType(Vertex v, boolean b) {
		v.setResource(b);
	}

	//Checks if the isItAdj vertex is adjacent to subject vertex
	private boolean hasAdj(Vertex subject, Vertex isItAdj) {
		if (subject.getAdjacencyList().contains(isItAdj))
			return true;
		else
			return false;
	}

	// Runs hasCycle check for every vertex
	public static boolean hasCycleFull() {
		for (Vertex vertex : vertices) {
			if (!vertex.isVisited() && hasCycle(vertex)) {
				return true;
			}
		}
		return false;
	}

	// Runs cycle check on a specific vertex
	public static boolean hasCycle(Vertex sourceVertex) {
		sourceVertex.setBeingVisited(true);

		for (Vertex neighbor : sourceVertex.getAdjacencyList()) {
			if (neighbor.isBeingVisited()) {
				// backward edge exists
				return true;
			} else if (!neighbor.isVisited() && hasCycle(neighbor)) {
				return true;
			}
		}

		sourceVertex.setBeingVisited(false);
		sourceVertex.setVisited(true);
		return false;
	}

	//Prints the graph out to the console
	void printGraph(int processes, int resources) {

		for (int x = 0; x < processes; x++) {
			System.out.print("Process " + vertices.get(x).getLabel() + " has ");
			if (vertices.get(x).getAdjacencyList().size() == 0)
				System.out.print("nothing ");
			for (int y = 0; y < vertices.get(x).getAdjacencyList().size(); y++) {
				int temp = 0;
				temp = Integer.parseInt(vertices.get(x).getAdjacencyList().get(y).getLabel());
				temp = temp-processes;
				System.out.print("(resource " + temp + ") ");
			}
			boolean printed = false;
			for (int i = processes; i < processes + resources; i++) {
				if (hasAdj(vertices.get(i), vertices.get(x))) {
					if (!printed) {
						System.out.print("and is waiting on");
						printed = true;
					}
					System.out.print(" [resource " + (Integer.parseInt(vertices.get(i).getLabel()) - processes) + "]");
				}
			}

			System.out.print("\n");
		}

	}

	//Builds the basic graph out of vertices based on processes and resource numbers
	void buildGraph(int proc, int res) {
		int x = 0;
		boolean type = false;

		String typeToPrint = "";

		while (x < (proc + res)) {

			String temp = Integer.toString(x);

			if (x < proc) {
				type = false;
				Vertex tempV = new Vertex(temp, type);
				typeToPrint = "process";
				vertices.add(tempV);

			} else {
				type = true;
				Vertex tempV = new Vertex(temp, type);
				typeToPrint = "resource";
				vertices.add(tempV);
			}

			System.out.print("||Creating a new vertex: " + x + ". It is a: " + typeToPrint + "||\n");
			x++;
		}

	}
	
	// Method that runs cycle/deadlock checks and prints the result
	public boolean detectDeadlock() {
		Vertex temp = new Vertex(vertices.get(0));
		if (hasCycleFull()) {
			System.out.print("!*WARNING: Deadlock detected!*!\n");
			return true;
		} else {
			System.out.print("*NO Deadlock detected*\n");
			return false;
		}
	}
}