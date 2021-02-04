import java.io.IOException;
import java.util.LinkedList;

public class Test {

	public static void main(String[] args) throws IOException {
		System.out.println("Direct Donation");
		SimpleMatching test1 = new SimpleMatching("testPrimaire.txt");
		test1.directDonation();
		System.out.println(test1);
		
		System.out.println("Greedy Matching");
		SimpleMatching test2 = new SimpleMatching("testPrimaire.txt");
		test2.greedyMatching();
		System.out.println(test2);
		
		System.out.println("Cycles and Chains Matching - Rule A");
		CyclesAndChainsMatching test3 = new CyclesAndChainsMatching("testPrimaire.txt");
		test3.match(false);
		System.out.println(test3);
		
		System.out.println("Cycles and Chains Matching - Rule B");
		CyclesAndChainsMatching test4 = new CyclesAndChainsMatching("testPrimaire.txt");
		test4.match(true);
		System.out.println(test4);
		
		System.out.println("Minimum infeasible paths");
		DirectedCompatibilityGraph graph = new DirectedCompatibilityGraph("test1.txt");
		LinkedList<LinkedList<Patient>> paths = graph.computeAllMinimalInfeasiblePaths(3);
		System.out.println(DirectedCompatibilityGraph.toInt(paths));
	}

}
