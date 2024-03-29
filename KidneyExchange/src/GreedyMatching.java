import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class GreedyMatching extends Matching {
	/*
	 * Matching class to perform Greedy Matching.
	 * */


	GreedyMatching(String path) throws IOException {
		graph = new CompatibilityGraph(path);
		this.n = graph.n;
		this.nbNotAssigned = graph.n;
		this.notAssigned = graph.getVertices();
		this.assigned = new HashSet<Patient>();
	}
	
	public void runDirectDonation() {
		HashSet<Patient> notAssignedCopy = new HashSet<Patient>(notAssigned);
		for (Patient P : notAssignedCopy)
			if(P.isCompatible(P.kidney))
				assign(P); // assigning ki to ti
	}
	
	public void match() { // O(n(d+log(n))
		
		// Initialization O(nlog(n))
		Patient[] T = new Patient[this.nbNotAssigned];
		int j = 0;
		for(Patient P : this.notAssigned) {
			T[j] = P;
			j++;
			
		}
		Arrays.sort(T);
		
		// Assigning the preferred kidneys O(nd)
		for (int i = 0; i < T.length; i++) {
			Patient preferredPatient = T[i]; 
			if(!T[i].isAssigned) {
				// Looking for the preferred kidney of T[i]
				for (Patient P2 : this.graph.adj.get(T[i]))
					if (T[i].K[P2.id] && T[i].P[P2.id] < T[i].P[preferredPatient.id] && !P2.isAssigned)
						preferredPatient = P2;
				// Assignment
				if (preferredPatient != T[i]) {
					this.assign(T[i], preferredPatient.id);
					this.assign(preferredPatient, T[i].id);
					this.graph.removeEdge(T[i], preferredPatient);
					this.graph.removeEdge(preferredPatient, T[i]); // Ajout�, ne change pas le r�sultat final...
				}
			}
		}
		
		// Final step O(n)
		HashSet<Patient> notAssignedCopy = new HashSet<Patient>(notAssigned);
		for (Patient P : notAssignedCopy) {
			if (P.K[P.id]) {
				this.assign(P);
			}
			else {
				this.assign(P, 0);
			}
		}
	}
}
