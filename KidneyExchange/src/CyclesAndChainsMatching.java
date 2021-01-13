import java.util.HashMap;
import java.util.HashSet;

public class CyclesAndChainsMatching extends Matching {
	
	AssignationGraph graph;
	
	CyclesAndChainsMatching(Patient[] T) {
		super(T);
		graph = new AssignationGraph();
	}
	
	/*Matching(Graph G) {
		// Construire l'objet à partir d'un graphe
		// Problème : deux types de graphes (parties 2 et 3)
		// Donc faire une classe par partie, ou donner le graphe en argument des méthodes ?
		// Pour la partie 2 il faut undirected, pour la 3 directed
	}*/
	
	
	
	HashSet<Patient> match(boolean ruleB) {
		while (!notAssigned.isEmpty()) {
			for (Patient p: notAssigned) {
				// Là il faut trouver un moyen efficace de trouver son kidney préféré encore disponible
			}
			Patient cycle = graph.getCycle();
			if (cycle != null) {
				Patient p = (Patient) graph.adj.get(cycle).toArray()[0];
				Patient pred = cycle;
				while (pred != cycle) {
					assign(p);
					graph.removeEdge(pred, p);
					pred = p;
					p = (Patient) graph.adj.get(p).toArray()[0];
				}
			}
			else {
				Patient tail;
				if (ruleB) tail = selectChainRuleB();
				else tail = selectChainRuleA();
				while (tail.kidney != 0) {
					assign(tail);
					tail = (Patient) graph.adj.get(tail).toArray()[0];
				}
				assign(tail); // le dernier qui pointe sur w
			}
		}
		return this.assigned;
	}
	
	
	Patient selectChainRuleA() {
		HashSet<Patient> visited = new HashSet<Patient>(); // size en O(1)
		HashMap<Integer, Patient> patients = new HashMap<Integer, Patient>(); // size en O(1)
		int d_max = 0;
		if (notAssigned.isEmpty()) return null;
		for (Patient p: notAssigned)
			if (!visited.contains(p)) {
				int[] dp = graph.chainSizeAndPriority(p, visited);
				int d = dp[0];
				int priority = dp[1];
				if (d > d_max) {
					d_max = d;
					patients.clear();
					patients.put(priority, p);
				}
				else if (d == d_max)
					patients.put(priority, p);
			}
		// Sélection de la w-chain parmi les plus longues
		if (patients.size() == 1)
			return (Patient) patients.keySet().toArray()[0];
		for (int i = 1;;i++) {
			Patient p = patients.get(i);
			if (p != null)
				return p;
		}
	}
	
	Patient selectChainRuleB() {
		Patient p = null;
		for (Patient q: notAssigned)
			if (p == null || q.compareTo(p) < 0)
				p = q;
		return p;
	}

}
