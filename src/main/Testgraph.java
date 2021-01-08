package main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Testgraph {

	@Test
	public void cycleDetectionTest() {
		//Modeled after scenario 2
	    Vertex vertexA = new Vertex("A");
	    Vertex vertexB = new Vertex("B");
	    Vertex vertexC = new Vertex("C");
	    Vertex vertexD = new Vertex("D");
	    Vertex vertexE = new Vertex("E");
	    Vertex vertexF = new Vertex("F");
	    Vertex vertexG = new Vertex("G");
	 
	    Graph graph = new Graph();
	    graph.addVertex(vertexA);
	    graph.addVertex(vertexB);
	    graph.addVertex(vertexC);
	    graph.addVertex(vertexD);
	    graph.addVertex(vertexE);
	    graph.addVertex(vertexF);
	    graph.addVertex(vertexG);
	 
	    graph.addEdge(vertexA, vertexD);
	    graph.addEdge(vertexB, vertexE);
	    graph.addEdge(vertexE, vertexA);
	    assertFalse(graph.hasCycleFull());
	    graph.addEdge(vertexD, vertexB);
	    //assertTrue(graph.hasCycleFull());
	    graph.addEdge(vertexD, vertexC);
	    graph.addEdge(vertexE, vertexC);
	    graph.addEdge(vertexC, vertexF);
	    //assertTrue(graph.hasCycleFull());	 
	}

}
