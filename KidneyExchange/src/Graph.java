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
		BufferedReader br = null;
		
	    try  {
		br = new BufferedReader(new FileReader(new File(path)));
	    }
	    catch(FileNotFoundException exc) {
		System.out.println("Erreur d'ouverture");
	    }
	   
	    this.n = br.readLine().toCharArray()[0];
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
	    	addPatient(new Patient(i,K,P));
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
}
