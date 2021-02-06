import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class SimpleMatching extends Matching {
	
	CompatibilityGraph graph;
	
	SimpleMatching(Patient[] T) {
		super(T);
		graph = new CompatibilityGraph();
	}
	
	SimpleMatching(String path) throws IOException {
		graph = new CompatibilityGraph(path);
		this.n = graph.n;
		this.nbNotAssigned = graph.n;
		this.notAssigned = graph.getVertices();
		this.assigned = new HashSet<Patient>();
	}
	
	HashSet<Patient> directDonation() {
		@SuppressWarnings("unchecked")
		HashSet<Patient> notAssignedCopy = (HashSet<Patient>) this.notAssigned.clone();
		for (Patient P : notAssignedCopy) {
			if(P.isCompatible(P.kidney))
				assign(P); // On assigne ki à ti
			else
				assign(P, 0); // Liste d'attente
		}
		return assigned;
	}
	
	HashSet<Patient> greedyMatching() {
		
		// Initialization
		Patient[] T = new Patient[this.nbNotAssigned];
		int j = 0;
		for(Patient P : this.notAssigned) {
			T[j] = P;
			j++;
			
		}
		Arrays.sort(T);
		
		// Assigning the preferred kidneys
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
					// POURQUOI PAS DANS L'AUTRE SENS ???
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		HashSet<Patient> notAssignedCopy = (HashSet<Patient>)this.notAssigned.clone();
		for (Patient P : notAssignedCopy) {
			if (P.K[P.id]) {
				this.assign(P);
			}
			else {
				this.assign(P, 0);
			}
		}
		return this.assigned;
	}

}
