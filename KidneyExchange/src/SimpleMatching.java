import java.util.Arrays;
import java.util.HashSet;

public class SimpleMatching extends Matching {
	
	CompatibilityGraph graph;
	
	SimpleMatching(Patient[] T) {
		super(T);
		graph = new CompatibilityGraph();
	}
	
	/*SimpleMatching(CompatibilityGraph G) {
		// Construire l'objet � partir d'un graphe
		// Probl�me : deux types de graphes (parties 2 et 3)
		// Donc faire une classe par partie, ou donner le graphe en argument des m�thodes ?
		// Pour la partie 2 il faut undirected, pour la 3 directed
	}*/
	
	HashSet<Patient> directDonation() {
		for (Patient P : notAssigned) {
			if(P.isCompatible(P.kidney))
				assign(P); // On assigne ki � ti
			else
				assign(P, 0); // Liste d'attente
		}
		return assigned;
	}
	
	HashSet<Patient> greedyMatching() {
		// Patient[] T = new Patient[this.nbNotAssigned];
		Patient[] T = (Patient[]) notAssigned.toArray();
		Arrays.sort(T);
		for (int i = 0; i < nbNotAssigned; i++) {
			Patient preferedPatient = T[i]; 
			for (Patient P2 : this.graph.adj.get(T[i])) {
				if (T[i].P[P2.id] < T[i].P[preferedPatient.id] ) {
					preferedPatient = P2;
					}
				}
			if (preferedPatient !=T[i]) {
				this.assign(T[i], preferedPatient.id);
				this.graph.removeEdge(T[i], preferedPatient);
			}
		}
		for (Patient P : this.notAssigned) {
			if (P.P[P.id] > P.P[0]) {
				this.assign(P, 0);
			}
			else {
				this.assign(P, P.id);
			}
		}
		return this.assigned;
	}

}