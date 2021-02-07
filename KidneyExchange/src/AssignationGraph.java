import java.io.*;

public class AssignationGraph extends Graph {
	
	// directed graph => adj contains reachable vertices
	
	
	public AssignationGraph() {
		super();
	}
	
	// create a graph without edges
	AssignationGraph(String path) throws IOException {
		readFile(path);
	}
}
