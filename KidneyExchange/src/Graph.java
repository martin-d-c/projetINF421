import java.util.HashSet;
import java.util.HashMap;
public class Graph {
	
	HashMap<Patient,HashSet<Patient>> adj;
	
	void addPatient(Patient P){
		this.adj.put(P, new HashSet<Patient>());
	}
	
	void addEdge(Patient P, Patient Q) {
		this.adj.get(P).add(Q);
	}
	
	void removeEdge(Patient P, Patient Q) {
		this.adj.get(P).remove(Q);
	}
	
	boolean hasEdge(Patient P,Patient Q) {
		return this.adj.get(P).contains(Q);
	}
	
	Patient getCycle(){
		HashSet<Patient> visited = new HashSet<Patient>();
		for (Patient P : this.adj.keySet()) {
			if (!visited.contains(P)) {
				
				while(P.kidney != 0) {
					if (visited.contains(P)) {
						return P;
					}
					visited.add(P);
					P = (Patient) this.adj.get(P).toArray()[0];
				}
				
				
			}
		}
		return null;
	}
	
	// HYPOTHESE : il n'y a pas de cycle dans le graphe
	int[] chainSizeAndPriority(Patient p, HashSet<Patient> visited) {
		int[] res = new int[] {1, p.id}; // size, priority
		visited.add(p);
		while(p.kidney != 0) { // termine par hypothèse
			p = (Patient) this.adj.get(p).toArray()[0];
			visited.add(p);
			res[0]++;
			if (p.id < res[1])
				res[1]++;
		}
		return res;
	}
}
