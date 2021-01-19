import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class CompatibilityGraph extends Graph {
	
	// undirected graph : p and q connected iff compatible
	
	CompatibilityGraph() {
		this.adj = new HashMap<Patient,HashSet<Patient>>();
		this.n = 0;
	}

	CompatibilityGraph(String path) throws IOException {
		readFile(path);
	    for(Patient p: adj.keySet()) {
	    	for(Patient q: adj.keySet()) {
	    		/*if(listPatient[i].K[j]) {
	    			addEdge(listPatient[i],listPatient[j]);
	    		}*/
	    		// je pense qu'il faut plutôt écrire, vu l'énoncé :
	    		if (p.K[q.id] && q.K[p.id]) {
	    			addEdge(p, q);
	    			addEdge(q, p);
	    		}	
	    	}
	    }
	}
}
