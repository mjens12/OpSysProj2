package main;

import java.util.ArrayList;
import java.util.List;

/*
 * This class 
 */

public class Vertex { 
	
	private String label;
    private boolean beingVisited;
    private boolean visited;
    private boolean isResource;
    private boolean claimed;
	private List<Vertex> adjacencyList;
	
    public Vertex(String label,boolean type) {
        this.label = label;
        this.adjacencyList = new ArrayList<>();
        this.isResource = type;
        this.visited = false;
        this.beingVisited = false;
        this.claimed=false;
    }
    
    public Vertex(String label) {
        this.label = label;
        this.adjacencyList = new ArrayList<>();
        this.visited = false;
        this.beingVisited = false;
        this.isResource = false;
        this.claimed=false;
    }
    
    public Vertex(Vertex temp) {
		super();
		this.label = temp.label;
		this.beingVisited = temp.beingVisited;
		this.visited = temp.visited;
		this.isResource = temp.isResource;
		this.adjacencyList = temp.adjacencyList;
		this.claimed=temp.claimed;
	}

	public Vertex() {
        this.label = null;
        this.adjacencyList = new ArrayList<>();
        this.visited = false;
        this.beingVisited = false;
        this.isResource = false;
        this.claimed=false;
    }
	
	public boolean isClaimed() {
		return claimed;
	}

	public void setClaimed(boolean claimed) {
		this.claimed = claimed;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isBeingVisited() {
		return beingVisited;
	}

	public void setBeingVisited(boolean beingVisited) {
		this.beingVisited = beingVisited;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public List<Vertex> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(List<Vertex> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

	public boolean isResource() {
		return isResource;
	}

	public void setResource(boolean isResource) {
		this.isResource = isResource;
	}
 
    public void addNeighbor(Vertex adjacent) {
        this.adjacencyList.add(adjacent);
    }
    
    public void removeNeighbor(Vertex adjacent) {
        this.adjacencyList.remove(adjacent);
    }
}
