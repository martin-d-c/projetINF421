import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class CyclesAndChainsMatching extends Matching {
	
	AssignationGraph graph;
	boolean[] kidneyAvailable;
	HashMap<Integer, Patient> patientsById;
	
	CyclesAndChainsMatching(Patient[] T) {
		super(T);
		graph = new AssignationGraph();
		kidneyAvailable = new boolean[this.n+1];
		for (int i = 1; i < n+1; i++) kidneyAvailable[i] = true;
		patientsById = new HashMap<Integer, Patient>();
	}
	
	public CyclesAndChainsMatching(String path) throws IOException {
		graph = new AssignationGraph(path);
		this.n = graph.n;
		this.nbNotAssigned = graph.n;
		this.notAssigned = graph.getVertices();
		this.assigned = new HashSet<Patient>();
		kidneyAvailable = new boolean[this.n+1];
		for (int i = 1; i < n+1; i++) kidneyAvailable[i] = true;
		patientsById = new HashMap<Integer, Patient>();
		for (Patient p: notAssigned) {
			patientsById.put(p.id, p);
		}
	}
	
	
	HashSet<Patient> match(boolean ruleB) {
		// 1er tour
		for (Patient p: notAssigned) {
			int fav = 0;
			int priority = p.P[0];
			for (int i = 1; i < n+1; i++)
				if (p.P[i] < priority && this.kidneyAvailable[i] == true) {
					fav = i;
					priority = p.P[i];
				}
			p.kidney = fav;
			graph.addEdge(p, patientsById.get(fav));
		}
		
		while (!notAssigned.isEmpty()) {
			
			for (Patient p: notAssigned) {
				if (!this.kidneyAvailable[p.kidney]) {
					graph.removeEdge(p, patientsById.get(p.kidney));
					// Là il faut trouver un moyen efficace de trouver son kidney préféré encore disponible
					int fav = 0;
					int priority = p.P[0];
					for (int i = 1; i < n+1; i++)
						if (p.P[i] < priority && this.kidneyAvailable[i] == true) {
							fav = i;
							priority = p.P[i];
						}
					p.kidney = fav;
					graph.addEdge(p, patientsById.get(fav));
				}
			}
			
			Patient cycle = graph.getCycle();
			if (cycle != null) {
				Patient p = (Patient) graph.adj.get(cycle).toArray()[0];
				Patient pred = cycle;
				while (p != cycle) {
					assign(p);
					this.kidneyAvailable[p.kidney] = false;
					graph.removeEdge(pred, p);
					pred = p;
					p = (Patient) graph.adj.get(p).toArray()[0];
				}
				assign(p);
				this.kidneyAvailable[p.kidney] = false;
				graph.removeEdge(pred, p);
			}
			else {
				Patient tail;
				if (ruleB) tail = selectChainRuleB();
				else tail = selectChainRuleA();
				while (tail.kidney != 0) {
					assign(tail);
					this.kidneyAvailable[tail.kidney] = false;
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
