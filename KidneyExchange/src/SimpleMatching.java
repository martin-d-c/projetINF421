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
		Patient[] T = new Patient[this.nbNotAssigned];
		int j = 0;
		for(Patient P : this.notAssigned) {
			T[j] = P;
			j++;
			
		}
		Arrays.sort(T);
		
		for (int i = 0; i < T.length; i++) {
			
			
			Patient preferedPatient = T[i]; 
			if(!T[i].isAssigned) {
				
				for (Patient P2 : this.graph.adj.get(T[i])) {
					
					if (T[i].K[P2.id] && T[i].P[P2.id] < T[i].P[preferedPatient.id] && !P2.isAssigned) {
						preferedPatient = P2;
						}
					}
				
				if (preferedPatient !=T[i]) {
					this.assign(T[i], preferedPatient.id);
					this.assign( preferedPatient, T[i].id);
					this.graph.removeEdge(T[i], preferedPatient);
					
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
