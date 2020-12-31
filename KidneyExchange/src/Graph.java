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
	
	void removeEdeg(Patient P, Patient Q) {
		this.adj.get(P).remove(Q);
	}
	
	boolean hasEdge(Patient P,Patient Q) {
		return this.adj.get(P).contains(Q);
	}
	
	boolean containCycle(){
		HashSet<Patient> visited = new HashSet<Patient>();
		for (Patient P : this.adj.keySet()) {
			if(!visited.contains(P)) {
				
				while(P.kidney!=0) {
					if(visited.contains(P)) {
						return true;
					}
					visited.add(P);
					P = (Patient)this.adj.get(P).toArray()[0];
				}
				
				
			}
		}
		return false;
	}
}
