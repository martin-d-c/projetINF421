import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class CyclesAndChainsMatching extends Matching {

	boolean[] kidneyAvailable;
	HashMap<Integer, Patient> patientsById;
	boolean ruleB; // true in all constructors, should be modified later if rule A wanted
	
	CyclesAndChainsMatching(HashSet<Patient> T) {
		super(T);
		graph = new AssignationGraph();
		kidneyAvailable = new boolean[this.n+1];
		for (int i = 1; i < n+1; i++) kidneyAvailable[i] = true;
		patientsById = graph.patientsById;
		ruleB = true;
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
		ruleB = true;
	}
	
	
	public void runDirectDonation() {
		HashSet<Patient> notAssignedCopy = new HashSet<Patient>(notAssigned);
		for (Patient P : notAssignedCopy)
			if(P.isCompatible(P.kidney)) {
				assign(P); // assigning ki to ti
				graph.removeAllEdgesFrom(P);
				this.kidneyAvailable[P.kidney] = false;
			}
	}
	
	
	/*Rule A: O(n^3)
	 *Rule B: O(n^3)*/
	public void match() {

		boolean firstRound = true;
		
		while (!notAssigned.isEmpty()) { // O(n^3)
			
			for (Patient p: notAssigned) { // O(n^2)
				// 1st round: each patient has to point towards his most preferred kidney
				// Other rounds: changing only if current preferred kidney not available
				if (!this.kidneyAvailable[p.kidney] || firstRound) {
					graph.removeEdge(p, patientsById.get(p.kidney));
					// Finding p's most preferred kidney which is still available
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
			firstRound = false;
			
			Patient cycle = graph.getCycle(); // O(n^2)
			if (cycle != null) { // Cycle: assign --> O(n)
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
			else { // No cycle: looking for the right w-chain according to the selected rule --> O(n^2) / O(n)
				Patient tail;
				if (ruleB) tail = selectChainRuleB();
				else tail = selectChainRuleA();
				while (tail.kidney != 0) {
					assign(tail);
					this.kidneyAvailable[tail.kidney] = false;
					tail = (Patient) graph.adj.get(tail).toArray()[0];
				}
				assign(tail); // assign the last one, which points towards 0 (w-chain)
			}
		}
	}
	
	
	
	
	Patient selectChainRuleA() { // O(n^2)
		HashSet<Patient> visited = new HashSet<Patient>(); // size -> O(1)
		// patients: {(tail_of_the_w-chain, priorities_in_the_w-chain)}
		HashMap<Patient, LinkedList<Integer>> patients = new HashMap<Patient, LinkedList<Integer>>(); // size -> O(1)
		int d_max = 0;
		if (notAssigned.isEmpty()) return null;
		for (Patient p: notAssigned) // O(n^2)
			if (!visited.contains(p)) {
				LinkedList<Integer> dp = graph.chainSizeAndPriority(p, visited); // O(n)
				int d = dp.poll();
				LinkedList<Integer> priority = dp; // List of priorities of all the elements of the w-chain
				if (d > d_max) {
					d_max = d;
					patients.clear();
					patients.put(p, priority);
				}
				else if (d == d_max)
					patients.put(p, priority);
			}
		// Selecting the w-chain among the longest
		if (patients.size() == 1)
			return (Patient) patients.keySet().toArray()[0];
		for (Patient p: patients.keySet()) // O(nlog(n))
			Collections.sort(patients.get(p));
		LinkedList<Patient> candidates = new LinkedList<Patient>(patients.keySet()); // size en O(1)
		LinkedList<Patient> new_candidates = new LinkedList<Patient>();
		
		// Looking for the candidate containing the highest(s) priority patients
		while (candidates.size() > 1) { // O(max(candidate_size) nb_candidates) --> O(n^2)
			int min_prio = n + 1;
			for (Patient p: candidates) {
				Integer prio = patients.get(p).poll();
				if (prio == null) continue; // This should not happen since all candidates have the same length
				if (prio < min_prio) {
					min_prio = prio;
					new_candidates.clear();
					new_candidates.add(p);
				}
				else if (prio == min_prio) {
					new_candidates.add(p);
				}
			}
			candidates = new_candidates;
			new_candidates = new LinkedList<Patient>();
		}
		return candidates.element();
	}
	
	Patient selectChainRuleB() { // O(n)
		Patient p = null;
		for (Patient q: notAssigned)
			if (p == null || q.compareTo(p) < 0)
				p = q;
		return p;
	}

}
