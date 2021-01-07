import java.util.HashMap;
import java.util.HashSet;

public class Matching {
	
	int n;
	int nbNotAssigned;
	HashSet<Patient> assigned;
	HashSet<Patient> notAssigned;
	Graph graph;
	
	Matching(Patient[] T){
		this.assigned = new HashSet<Patient>();
		this.notAssigned = new HashSet<Patient>();
		for (int i =0;i<T.length;i++) {
			notAssigned.add(T[i]);
		}
		this.n = T.length;
		this.nbNotAssigned = T.length;
		graph = new Graph();
	}
	
	void assign(Patient P,int i){
		notAssigned.remove(P);
		assigned.add(P);
		P.assign(i);
		nbNotAssigned --;
	}
	
	// Assigne à P le rein vers lequel il pointe
	void assign(Patient P) {
		assign(P, P.kidney);
	}
	
	HashSet<Patient> directDonation(){
		for (Patient P : notAssigned){
			if(P.isCompatible(P.kidney))
				assign(P); // On assigne ki à ti
			else
				assign(P, 0); // Liste d'attente
		}
		return assigned;
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
