import java.io.*;

public class AssignationGraph extends Graph {
	
	// directed graph => adj contains reachable vertices
	
	
	public AssignationGraph() {
		super();
	}
	
	public AssignationGraph(Graph g) {
		super(g);
	}
	
	// create a graph without edges
	AssignationGraph(String path) throws IOException {
		readFile(path);
	}
	
	
	
	/*Patient getCycle(){
		HashSet<Patient> visited = new HashSet<Patient>();
		HashSet<Patient> visitedCycle = new HashSet<Patient>();
		for (Patient P : this.adj.keySet()) {
			visitedCycle.clear();
			if (!visited.contains(P)) {
				
				while(P.kidney != 0) {
					if (visitedCycle.contains(P)) {
						return P;
					}
					visited.add(P);
					visitedCycle.add(P);
					if (this.adj.get(P).toArray().length == 0) break;
					P = (Patient) this.adj.get(P).toArray()[0];
				}
				
				
			}
		}
		return null;
	}*/
	
	/*// HYPOTHESE : il n'y a pas de cycle dans le graphe
	// A OPTIMISER : stocker les distances et priorités des visited
	LinkedList<Integer> chainSizeAndPriority(Patient p, HashSet<Patient> visited) {
		LinkedList<Integer> res = new LinkedList<Integer>(); // size, priority
		res.add(1);
		visited.add(p);
		while(p.kidney != 0) { // termine par hypothèse
			p = (Patient) this.adj.get(p).toArray()[0];
			visited.add(p);
			res.addFirst(res.pollFirst() + 1);
			res.add(p.id);
		}
		return res;
	}*/
}
