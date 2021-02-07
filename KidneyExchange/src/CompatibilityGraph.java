import java.io.IOException;

public class CompatibilityGraph extends Graph {
	
	// undirected graph : p and q connected iff compatible
	
	public CompatibilityGraph() {
		super();
	}
	
	public CompatibilityGraph(Graph g) {
		super(g);
	}
	
	CompatibilityGraph(String path) throws IOException {
		readFile(path);
	    for(Patient p: adj.keySet())
	    	for(Patient q: adj.keySet())
	    		if (p.K[q.id] && q.K[p.id]) {
	    			addEdge(p, q);
	    			addEdge(q, p);
	    		}
	}
}
