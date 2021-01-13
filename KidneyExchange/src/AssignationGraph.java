import java.util.HashMap;
import java.io.*;
import java.util.HashSet;

public class AssignationGraph extends Graph {
	
	// directed graph => adj contains reachable vertices
	
	AssignationGraph() {
		this.adj = new HashMap<Patient,HashSet<Patient>>();
	}
	
	
	// create a graph without edges
	AssignationGraph(String path) throws IOException {
		BufferedReader br = null;
	    

	    try  {
		br = new BufferedReader(new FileReader(new File(path)));
	    }
	    catch(FileNotFoundException exc) {
		System.out.println("Erreur d'ouverture");
	    }
	   
	    int n = br.readLine().toCharArray()[0];
	    
	    for(int i =0; i<n;i++) {
	    	String[] ligne = br.readLine().split(" ");
	    	boolean[] K = new boolean[n];
	    	int[] P = new int[n];
	    	int[] compatible = new int[ligne.length];
	    	for(int j = 0;j<ligne.length;j++) {
	    		
	    		int k = ligne[j].toCharArray()[0];
	    		K[k] = true;
	    		P[k] = j;
	    		compatible[j] = k;
	    	}
	    	this.adj.put(new Patient(i,K,P), new HashSet<Patient>());
	    	
	    }
	    
	    br.close();
	}
	
	Patient getCycle(){
		HashSet<Patient> visited = new HashSet<Patient>();
		for (Patient P : this.adj.keySet()) {
			if (!visited.contains(P)) {
				
				while(P.kidney != 0) {
					if (visited.contains(P)) {
						return P;
					}
					visited.add(P);
					P = (Patient) this.adj.get(P).toArray()[0];
				}
				
				
			}
		}
		return null;
	}
	
	// HYPOTHESE : il n'y a pas de cycle dans le graphe
	// A OPTIMISER : stocker les distances des visited
	int[] chainSizeAndPriority(Patient p, HashSet<Patient> visited) {
		int[] res = new int[] {1, p.id}; // size, priority
		visited.add(p);
		while(p.kidney != 0) { // termine par hypothèse
			p = (Patient) this.adj.get(p).toArray()[0];
			visited.add(p);
			res[0]++;
			if (p.id < res[1])
				res[1]++;
		}
		return res;
	}
}
