import java.io.*;

public class AssignationGraph extends Graph {
	/*
	 * Represents a directed graph adapted for CyclesAndChainsMatching.
	 * */
	
	
	public AssignationGraph() {
		super();
	}
	
	// Creates a graph without edges
	AssignationGraph(String path) throws IOException {
		readFile(path);
	}
}
