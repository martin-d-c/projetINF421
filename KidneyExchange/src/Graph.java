import java.util.HashSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public abstract class Graph {
	
	HashMap<Patient,HashSet<Patient>> adj;
	int n; // number of vertices
	
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
	    br.close();
	}
	
	void addPatient(Patient P){
		this.adj.put(P, new HashSet<Patient>());
	}
	
	void addEdge(Patient P, Patient Q) {
		this.adj.get(P).add(Q);
	}
	
	void removeEdge(Patient P, Patient Q) {
		this.adj.get(P).remove(Q);
	}
	
	boolean hasEdge(Patient P,Patient Q) {
		return this.adj.get(P).contains(Q);
	}
	
	HashSet<Patient> getVertices(){
		HashSet<Patient> H = new HashSet<Patient>();
		H.addAll(this.adj.keySet());
		return H;
	}
}
