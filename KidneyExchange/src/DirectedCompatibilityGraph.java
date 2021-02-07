import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class DirectedCompatibilityGraph extends Graph {
	
	int K; // threshold
	int[][] adjMatrix ;

	DirectedCompatibilityGraph() {
		this.adj = new HashMap<Patient,HashSet<Patient>>();
		this.n = 0;
	}
	
	DirectedCompatibilityGraph(String path) throws IOException {
		this.adj = new HashMap<Patient,HashSet<Patient>>();
		BufferedReader br = null;
		
	    try  {
		br = new BufferedReader(new FileReader(new File(path)));
	    }
	    catch(FileNotFoundException exc) {
		System.out.println("Erreur d'ouverture");
	    }
	    this.n =(int) Double.parseDouble(br.readLine());

	    this.K = (int) Double.parseDouble(br.readLine());
	    
	    // Construct the adjacency matrix of the graph
	    this.adjMatrix = new int[n+1][n+1];
	    for(int i =1; i<n+1;i++) {
	    	String[] ligne = br.readLine().split("   ");
	    	for(int j = 1;j<ligne.length+1;j++) {
	    		adjMatrix[i][j] = (int)Double.parseDouble(ligne[j-1]);
	    	}
	    }
	    
	    for(int i =1; i<n+1;i++) {
	    	boolean[] K = new boolean[n+1];
	    	int[] P = new int[n+1];
	    	for(int j = 1;j<n+1;j++) {
	    		if( adjMatrix[j][i] == 1) {
	    			K[j] = true;
	    		}
	    	}
	    	addPatient(new Patient(i,K,P));
	    }
	    br.close();
		for(Patient p: adj.keySet()) {
	    	for(Patient q: adj.keySet())
	    		if(q.K[p.id])
	    			addEdge(p, q);
	    }
	}
	
	
	static private<E> LinkedList<E> copy(LinkedList<E> list) {
		LinkedList<E> cop = new LinkedList<E>();
		cop.addAll(list);
		return cop;
	}
	
	
	private void computeMinimalInfeasiblePathsRec(Patient p, int threshold, int count, LinkedList<LinkedList<Patient>> paths, LinkedList<Patient> current, HashSet<Patient> visited) {
		if (count == threshold + 1) return;
		if (visited.contains(p)) return;
		visited.add(p);
		current.addLast(p);
		if (count == threshold)
			paths.add(copy(current));
		for (Patient q: adj.get(p)) {
				computeMinimalInfeasiblePathsRec(q, threshold, count + 1, paths, current, visited);
		}
		current.removeLast();
		visited.remove(p);
	}
	
	
	LinkedList<LinkedList<Patient>> computeAllMinimalInfeasiblePaths(int threshold) {
		LinkedList<LinkedList<Patient>> paths = new LinkedList<LinkedList<Patient>>();
		HashSet<Patient> visited = new HashSet<Patient>();
		LinkedList<Patient> current = new LinkedList<Patient>(); // pile
		for (Patient p: adj.keySet()) {
			computeMinimalInfeasiblePathsRec(p, threshold, 0, paths, current, visited);
		}
		return paths;
	}
	
	static LinkedList<LinkedList<Integer>> toId(HashMap<Patient, LinkedList<LinkedList<Patient>>> paths) {
		LinkedList<LinkedList<Integer>> intPaths = new LinkedList<LinkedList<Integer>>();
		for (LinkedList<LinkedList<Patient>> list_p: paths.values())
			for (LinkedList<Patient> list: list_p) {
				LinkedList<Integer> intList = new LinkedList<Integer>();
				for (Patient p: list)
					intList.add(p.id);
				intPaths.add(intList);
			}
		return intPaths;
	}
	
	static LinkedList<LinkedList<Integer>> toId(LinkedList<LinkedList<Patient>> paths) {
		LinkedList<LinkedList<Integer>> intPaths = new LinkedList<LinkedList<Integer>>();
		for (LinkedList<Patient> list: paths) {
			LinkedList<Integer> intList = new LinkedList<Integer>();
			for (Patient p: list)
				intList.add(p.id);
			intPaths.add(intList);
		}
		return intPaths;
	}
	
	static LinkedList<Integer> pathToId(LinkedList<Patient> path) {
		LinkedList<Integer> intList = new LinkedList<Integer>();
		for (Patient p: path)
			intList.add(p.id);
		return intList;
	}
	
	static LinkedList<Integer> toId(HashSet<Patient> path) {
		LinkedList<Integer> intList = new LinkedList<Integer>();
		for (Patient p: path)
			intList.add(p.id);
		return intList;
	}
}