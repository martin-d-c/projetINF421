import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class GreedyMatching extends Matching {
	
	GreedyMatching(HashSet<Patient> T) {
		super(T);
		graph = new CompatibilityGraph();
	}
	
	GreedyMatching(Matching M) {
		graph = new CompatibilityGraph(M.graph);
		this.assigned = new HashSet<Patient>();
		for (Patient p: M.assigned)
			this.assigned.add(graph.patientsById.get(p.id));
		this.notAssigned = new HashSet<Patient>();
		for (Patient p: M.notAssigned)
			this.notAssigned.add(graph.patientsById.get(p.id));
		this.n = M.n;
		this.nbNotAssigned = M.nbNotAssigned;
		this.cancelWaitingList();
	}
	
	GreedyMatching(String path) throws IOException {
		graph = new CompatibilityGraph(path);
		this.n = graph.n;
		this.nbNotAssigned = graph.n;
		this.notAssigned = graph.getVertices();
		this.assigned = new HashSet<Patient>();
	}
	
	void cancelWaitingList() {
		for (Patient p: new LinkedList<Patient>(assigned))
			if (p.kidney == 0) {
				assigned.remove(p);
				notAssigned.add(p);
				for (Patient q: graph.adj.keySet())
					if (p.K[q.id] && q.K[p.id]) {
						graph.addEdge(p, q);
						graph.addEdge(q, p);
					}
				nbNotAssigned++;
				p.isAssigned = false;
			}
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
					this.graph.removeEdge(preferredPatient, T[i]); // Ajouté, ne change pas le résultat final...
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
