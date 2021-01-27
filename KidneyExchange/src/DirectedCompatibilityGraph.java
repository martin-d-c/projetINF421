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
	LinkedList<LinkedList<Patient>> computeAllMinimalInfeasiblePaths(int threshold) {
		LinkedList<LinkedList<Patient>> paths = new LinkedList<LinkedList<Patient>>();
		HashSet<Patient> visited = new HashSet<Patient>();
		LinkedList<Patient> current = new LinkedList<Patient>(); // pile FIFO
		int count = 0; // on va maintenir l'invariant de boucle count <= threshold + 1
		for (Patient p: adj.keySet())
			if (!visited.contains(p)) {
				boolean go = true;
				while (go) {
					visited.add(p);
					if (count >= 1 && current.getFirst() == p) {
						// On a un cycle
						current.clear();
						count = 0;
					}
					else {
						current.addLast(p);
						count++;
						if (count == threshold + 1) {
							paths.add((LinkedList<Patient>) current.clone());
							current.removeFirst();
							count--;
						}
					}
					if (adj.get(p).toArray().length > 0 && !visited.contains(adj.get(p).toArray()[0]))
						p = (Patient) adj.get(p).toArray()[0];
					else
						go = false;
				}
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
