import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class DirectedCompatibilityGraph extends Graph {
	
	DirectedCompatibilityGraph() {
		this.adj = new HashMap<Patient,HashSet<Patient>>();
		this.n = 0;
	}
	
	DirectedCompatibilityGraph(String path) throws IOException {
		readFile(path);
		for(Patient p: adj.keySet()) {
	    	for(Patient q: adj.keySet())
	    		if(q.K[p.id])
	    			addEdge(p, q);
	    }
	}
	
	
	@SuppressWarnings("unchecked")
	private void computeMinimalInfeasiblePathsRec(Patient p, int threshold, int count, LinkedList<LinkedList<Patient>> paths, LinkedList<Patient> current, HashSet<Patient> visited) {
		if (count == threshold + 1) return;
		if (visited.contains(p)) return;
		visited.add(p);
		current.addLast(p);
		if (count == threshold)
			paths.add((LinkedList<Patient>) current.clone());
		for (Patient q: adj.get(p)) {
				computeMinimalInfeasiblePathsRec(q, threshold, count + 1, paths, current, visited);
		}
		current.removeLast();
		visited.remove(p);
	}
	

	LinkedList<LinkedList<Patient>> computeAllMinimalInfeasiblePaths(int threshold) {
		LinkedList<LinkedList<Patient>> paths = new LinkedList<LinkedList<Patient>>();
		HashSet<Patient> visited = new HashSet<Patient>();
		LinkedList<Patient> current = new LinkedList<Patient>(); // pile
		for (Patient p: adj.keySet()) {
			computeMinimalInfeasiblePathsRec(p, threshold, 0, paths, current, visited);
		}
		return paths;
	}
	
	static LinkedList<LinkedList<Integer>> toInt(LinkedList<LinkedList<Patient>> paths) {
		LinkedList<LinkedList<Integer>> intPaths = new LinkedList<LinkedList<Integer>>();
		for (LinkedList<Patient> list: paths) {
			LinkedList<Integer> intList = new LinkedList<Integer>();
			for (Patient p: list)
				intList.add(p.id);
			intPaths.add(intList);
		}
		return intPaths;
	}
	
}
