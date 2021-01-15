import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class CompatibilityGraph extends Graph {
	
	// undirected graph : p and q connected iff compatible
	
	CompatibilityGraph() {
		this.adj = new HashMap<Patient,HashSet<Patient>>();
		this.n = 0;
	}

	CompatibilityGraph(String path) throws IOException {
		BufferedReader br = null;
	    

	    try  {
		br = new BufferedReader(new FileReader(new File(path)));
	    }
	    catch(FileNotFoundException exc) {
		System.out.println("Erreur d'ouverture");
	    }
	   
	    this.n = br.readLine().toCharArray()[0];
	    Patient[] listPatient = new Patient[n];
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
	    	listPatient[i] = new Patient(i,K,P);
	    	
	    }
	    for(int i = 0;i<n;i++) {
	    	this.adj.put(listPatient[i], new HashSet<Patient>());
	    	for(int j = 0;j<n;j++) {
	    		if(listPatient[i].K[j]) {
	    			addEdge(listPatient[i],listPatient[j]);
	    		}
	    			
	    	}
	    }
	    
	    
	    br.close();
	}
}
