import java.util.HashMap;
import java.util.HashSet;

public class CompatibilityGraph extends Graph {
	
	// undirected graph : p and q connected iff compatible
	
	CompatibilityGraph() {
		this.adj = new HashMap<Patient,HashSet<Patient>>();
	}

}
