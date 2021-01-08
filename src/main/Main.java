package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 * Max Jensen
 * 11/24/20
 * Operating Systems
 * 
 * This program models an operating system resource manager. It takes the infomation it
 * needs (the number of processes and resources, and the resource request and free steps) from a .txt file.
 * It uses a custom graph class as well as a custom vertex class to track the available processes
 * and resources. All steps in the program provide user feedback to keep the user updated as to the state of the program 
 * and the graph it uses to track the relevant details. See the methods below for more implementation information.
 * 
 * NOTE: I have spent many many hours working on this program! The cycle/deadlock algorith works fine in the unit tests I've run,
 * but for some reason I can't get it to work in the actual implementation of the program and for the life of me I can't figure out why.
 * I've included one of the unit tests to show
 * that it works properly. I also didn't create a GUI to show the graph with shapes,
 * but instead I print the graph out to the console after every step in an easily readable way.
 */

//Main function that runs the program
public class Main {

	// Instance variables
	static int processes = 0;
	static int resources = 0;
	static ArrayList<ArrayList> steps = new ArrayList<ArrayList>();
	static int state = 0;
	static Graph simGraph = new Graph();

	public static void main(String[] args) {
		Main temp = new Main();
		// Read file
		try {
			temp.readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Build basic graph
		constructGraph();

		// Print the inital graph
		simGraph.printGraph(processes, resources);

		//Loop that runs the sim steps, prints the new graph, sleeps between for readability, and checks for deadlock after each step
		while (state < steps.size()) {
			stepSim();
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			simGraph.detectDeadlock();
			simGraph.printGraph(processes, resources);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Setters and getters
	public int getProcesses() {
		return processes;
	}

	public void setProcesses(int processes) {
		this.processes = processes;
	}

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}

	public static ArrayList<ArrayList> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<ArrayList> steps) {
		this.steps = steps;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	// Method that reads the specified file into the steps arrayList
	private void readFile() throws FileNotFoundException {
		// Scenario file path goes here
		File file = new File("src\\scenario-2.txt");
		// File file = new File("src\\testRelease.txt");

		Scanner sc = new Scanner(file);

		setProcesses(sc.nextInt());

		setResources(sc.nextInt());

		while (sc.hasNext()) {

			ArrayList<Character> temp = new ArrayList<Character>();

			temp.add(sc.next().charAt(0));

			temp.add(Character.forDigit(sc.nextInt(), 10));

			temp.add(Character.forDigit(sc.nextInt(), 10));

			steps.add(temp);

		}
		sc.close();
	}

	// Main worker method that steps through the steps of process and resource
	// requests and frees
	private static void stepSim() {
		// use state to track which step we're on
		ArrayList<Character> tempStep = new ArrayList<Character>(steps.get(state));

		if (tempStep.get(0) == 'r') {
			// If resource isn't claimed, create an edge from process to resource and mark
			// resource as claimed.
			System.out.print("^^Process " + tempStep.get(1) + " wants resource " + tempStep.get(2) + "^^\n");
			if (!simGraph.vertices.get(Character.getNumericValue(tempStep.get(2))).isClaimed()) {
				simGraph.addEdge(simGraph.vertices.get(Character.getNumericValue(tempStep.get(1))),
						simGraph.vertices.get(Character.getNumericValue(tempStep.get(2)) + processes));
				simGraph.vertices.get(Character.getNumericValue(tempStep.get(2))).setClaimed(true);
				System.out.print("Creating an edge between process " + tempStep.get(1) + " and resource " + tempStep.get(2) + "\n");
			}
			// Otherwise the resource is taken, so we create an edge from the resource to
			// the waiting process to mark it as being waited on
			else {
				simGraph.addEdge(simGraph.vertices.get(Character.getNumericValue(tempStep.get(2) + processes)),
						simGraph.vertices.get(Character.getNumericValue(tempStep.get(1))));
				System.out.print("The resource is already taken, creating a waiting edge between resource " + tempStep.get(2) + " and process "
						+ tempStep.get(1) + "\n");
			}

		} else {
			// Free the resource
			System.out.print("Process " + tempStep.get(1) + " is releasing resource " + tempStep.get(2) + "\n");
			simGraph.removeEdge(simGraph.vertices.get(Character.getNumericValue(tempStep.get(1))),
					simGraph.vertices.get(Character.getNumericValue(tempStep.get(2)) + processes));
			simGraph.vertices.get(Character.getNumericValue(tempStep.get(2)) + processes).setClaimed(false);

			System.out.print("Removing an edge between process " + tempStep.get(1) + " and resource " + tempStep.get(2) + "\n");

			// If the released resource has something waiting on it, tell the user, and
			// assign that waiting process to the resource
			if (!simGraph.vertices.get(Character.getNumericValue(tempStep.get(2) + processes)).getAdjacencyList().isEmpty()) {
				System.out.print("Resource " + tempStep.get(2) + " is now free.\n");
				System.out.print("Process "
						+ simGraph.vertices.get(Character.getNumericValue(tempStep.get(2) + processes)).getAdjacencyList().get(0).getLabel()
						+ " wants that resource. Giving that process the resource now!\n");
				simGraph.addEdge(simGraph.vertices.get(Character.getNumericValue(tempStep.get(2) + processes)).getAdjacencyList().get(0),
						simGraph.vertices.get(Character.getNumericValue(tempStep.get(2)) + processes));
				simGraph.removeEdge(simGraph.vertices.get(Character.getNumericValue(tempStep.get(2) + processes)),
						simGraph.vertices.get(Character.getNumericValue(tempStep.get(2)) + processes).getAdjacencyList().get(0));
				simGraph.vertices.get(Character.getNumericValue(tempStep.get(2)) + processes).setClaimed(true);

			}
		}

		state++;
	}

	// Method that tells the graph to construct itself based on the number of
	// processes and resources
	private static void constructGraph() {
		simGraph.buildGraph(processes, resources);
	}

}