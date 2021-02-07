import java.util.HashSet;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public abstract class Graph {
	
	HashMap<Patient,HashSet<Patient>> adj;
	int n; // number of vertices
	
	Graph() {
		this.adj = new HashMap<Patient,HashSet<Patient>>();
		this.n = 0;
	}
	
	Graph(Graph g) { // creates a copy of g
		if (g == null) {
			this.adj = new HashMap<Patient,HashSet<Patient>>();
			this.n = 0;
			return;
		}
		this.adj = new HashMap<Patient,HashSet<Patient>>();
		for (Patient p: g.adj.keySet()) {
			this.adj.put(p, new HashSet<Patient>(g.adj.get(p)));
		}
		this.n = g.n;
	}
	
	void readFile(String path) throws IOException {
		this.adj = new HashMap<Patient,HashSet<Patient>>();
		BufferedReader br = null;
		
	    try  {
	    	br = new BufferedReader(new FileReader(new File(path)));
	    }
	    catch(FileNotFoundException exc) {
	    	System.out.println("Erreur d'ouverture");
	    }
	    
	    this.n = Integer.parseInt(br.readLine());
	    
	    for(int i =0; i<n;i++) {
	    	String[] ligne = br.readLine().split(" ");
	    	boolean[] K = new boolean[n+1];
	    	int[] P = new int[n+1];
	    	for(int j= 0;j<P.length;j++) { P[j] = n+2; }
	    	int[] compatible = new int[ligne.length];
	    	for(int j = 0;j<ligne.length;j++) {
	    		int k = Integer.parseInt(ligne[j]);
	    		K[k] = true;
	    		P[k] = j;
	    		compatible[j] = k;
	    	}
	    	addPatient(new Patient(i+1,K,P));
	    }
	    String[] bloodType = br.readLine().split(" ");
	    for (Patient p: adj.keySet())
	    	p.bloodType = bloodType[p.id-1];
	    br.close();
	}
	
	void addPatient(Patient P){
		if (!adj.containsKey(P))
			this.adj.put(P, new HashSet<Patient>());
	}
	
	void addEdge(Patient P, Patient Q) {
		this.adj.get(P).add(Q);
	}
	
	void removeEdge(Patient P, Patient Q) {
		this.adj.get(P).remove(Q);
	}
	
	void removeAllEdges() {
		for (Patient p: adj.keySet())
			adj.get(p).clear();
	}
	
	boolean hasEdge(Patient P,Patient Q) {
		return this.adj.get(P).contains(Q);
	}
	
	HashSet<Patient> getVertices(){
		HashSet<Patient> H = new HashSet<Patient>();
		H.addAll(this.adj.keySet());
		return H;
	}
	
	
	Patient getCycle(){
		HashSet<Patient> visited = new HashSet<Patient>();
		HashSet<Patient> visitedCycle = new HashSet<Patient>();
		for (Patient P : this.adj.keySet()) {
			visitedCycle.clear();
			if (!visited.contains(P)) {
				
				while(P.kidney != 0) {
					if (visitedCycle.contains(P)) {
						return P;
					}
					visited.add(P);
					visitedCycle.add(P);
					if (this.adj.get(P).toArray().length == 0) break;
					P = (Patient) this.adj.get(P).toArray()[0];
				}
				
				
			}
		}
		return null;
	}
	
	
	// HYPOTHESE : il n'y a pas de cycle dans le graphe
		// A OPTIMISER : stocker les distances et priorit�s des visited
		LinkedList<Integer> chainSizeAndPriority(Patient p, HashSet<Patient> visited) {
			LinkedList<Integer> res = new LinkedList<Integer>(); // size, priority
			res.add(1);
			visited.add(p);
			while(p.kidney != 0) { // termine par hypoth�se
				p = (Patient) this.adj.get(p).toArray()[0];
				visited.add(p);
				res.addFirst(res.pollFirst() + 1);
				res.add(p.id);
			}
			return res;
		}
}
