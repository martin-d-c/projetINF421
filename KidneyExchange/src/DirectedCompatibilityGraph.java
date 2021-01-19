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
	
	@SuppressWarnings("unchecked") // on doit pouvoir faire plus propre
	LinkedList<LinkedList<Patient>> computeMinimalInfeasiblePaths(int threshold) {
		LinkedList<LinkedList<Patient>> paths = new LinkedList<LinkedList<Patient>>();
		// On doit pouvoir faire mieux que ci-dessous pour obtenir la liste des patients
		Patient[] tab_patients = (Patient[]) adj.keySet().toArray();
		LinkedList<Patient> patients = new LinkedList<Patient>();
		for (int i = 0; i < n; i++) {
			patients.add(tab_patients[i]);
		}
		LinkedList<Patient> current = new LinkedList<Patient>();
		int count = 0; // on va maintenir l'invariant de boucle count <= threshold + 1
		while (!patients.isEmpty()) {
			Patient p = patients.poll();
			if (count >= 1 && current.getFirst() == p) {
				// On a un cycle
				current.clear();
			}
			else {
				current.addLast(p);
				count++;
				if (count == threshold + 1) {
					paths.add((LinkedList<Patient>) current.clone());
					current.removeFirst();
				}
			}
		}
		return paths;
	}
	
}
