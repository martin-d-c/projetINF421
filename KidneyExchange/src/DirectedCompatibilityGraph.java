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
	    int[][] adjMatrix = new int[n+1][n+1];
	    this.adjMatrix = new int[n+1][n+1];
	    for(int i =1; i<n+1;i++) {
	    	String[] ligne = br.readLine().split("   ");
	    	
	    	
	    	for(int j = 0;j<ligne.length;j++) {
	    		adjMatrix[i][j] = (int)Double.parseDouble(ligne[j]);
	    	}
	    }
	    for(int i =1; i<n;i++) {
	    	
	    	boolean[] K = new boolean[n+1];
	    	int[] P = new int[n+1];
	    	
	    	for(int j = 1;j<n+1;j++) {
	    		
	    		if( adjMatrix[j][i] == 1) {
	    			K[j] = true;
	    		}
	    		
	    		
	    	}
	    	addPatient(new Patient(i+1,K,P));
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
	
	
	/*private*/ LinkedList<LinkedList<Patient>> finishPaths(int threshold, int count, LinkedList<LinkedList<Patient>> list_p, LinkedList<Patient> current, HashSet<Patient> visited) {
		LinkedList<LinkedList<Patient>> complete = new LinkedList<LinkedList<Patient>>();
		LinkedList<Patient> curr = new LinkedList<Patient>();
		for (LinkedList<Patient> after: list_p) {
			int i = count;
			for (Patient q: after) {
				if (i == threshold + 1) {
					curr.addAll(0, current);
					complete.add(copy(curr));
					curr.clear();
					break;
				}
				if (visited.contains(q)) {
					curr.clear();
					break;
				}
				curr.addLast(q);
				i++;
			}
		}
		return complete;
	}
	
	
	private void computeMinimalInfeasiblePathsRecMemo(Patient p, int threshold, int count, HashMap<Patient, LinkedList<LinkedList<Patient>>> paths, LinkedList<Patient> current, HashSet<Patient> visited) {
		if (count == threshold + 1) return;
		if (visited.contains(p)) return;
		
		visited.add(p);
		
		LinkedList<LinkedList<Patient>> list_p = paths.get(p);
		if (list_p != null) { // si on a déjà calculé les chemins partant de p;
			System.out.println(p.id);
			LinkedList<LinkedList<Patient>> finished = finishPaths(threshold, count, list_p, current, visited);
			LinkedList<LinkedList<Patient>> list_head = paths.get(current.element());
			if (list_head != null) list_head.addAll(finished);
			else paths.put(current.element(), finished);
			return;
		}
		
		current.addLast(p);
		if (count == threshold) {
			// On est arrivés au bout sans mémoïsation
			LinkedList<LinkedList<Patient>> list_head = paths.get(current.element());
			if (list_head != null) { System.out.println(toId(list_head));list_head.add(copy(current));}
			else {
				System.out.println("null");
				list_head = new LinkedList<LinkedList<Patient>>();
				list_head.add(copy(current));
				paths.put(current.element(), list_head);
			}
			System.out.println(toId(list_head));
			current.removeLast();
			visited.remove(p);
			return;
		}
		
		for (Patient q: adj.get(p)) {
				computeMinimalInfeasiblePathsRecMemo(q, threshold, count + 1, paths, current, visited);
		}
		current.removeLast();
		visited.remove(p);
	}
	

	HashMap<Patient, LinkedList<LinkedList<Patient>>> computeAllMinimalInfeasiblePathsMemo(int threshold) {
		HashMap<Patient, LinkedList<LinkedList<Patient>>> paths = new HashMap<Patient, LinkedList<LinkedList<Patient>>>();
		HashSet<Patient> visited = new HashSet<Patient>();
		LinkedList<Patient> current = new LinkedList<Patient>(); // pile
		for (Patient p: adj.keySet()) {
			if (p.id==1)
				System.out.println(toId(paths));
			computeMinimalInfeasiblePathsRecMemo(p, threshold, 0, paths, current, visited);
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