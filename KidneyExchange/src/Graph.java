import java.util.HashSet;
import java.util.HashMap;

public abstract class Graph {
	
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
}
