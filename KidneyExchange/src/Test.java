import java.io.IOException;
import java.util.LinkedList;

public class Test {

	public static void main(String[] args) throws IOException,Exception {
		System.out.println("Direct Donation");
		DirectDonationMatching test1 = new DirectDonationMatching("testFile1.txt");
		test1.match();
		System.out.println(test1);
		
		System.out.println("Greedy Matching");
		GreedyMatching test2 = new GreedyMatching("testFile1.txt");
		test2.match();
		System.out.println(test2);
		
		System.out.println("Cycles and Chains Matching - Rule A");
		CyclesAndChainsMatching test3 = new CyclesAndChainsMatching("testFile1.txt");
		test3.match();
		System.out.println(test3);
		
		System.out.println("Cycles and Chains Matching - Rule B");
		CyclesAndChainsMatching test4 = new CyclesAndChainsMatching("testFile1.txt");
		test4.match();
		System.out.println(test4);
		
		System.out.println("Minimum infeasible paths");
		DirectedCompatibilityGraph graph = new DirectedCompatibilityGraph("test1.txt");
		LinkedList<LinkedList<Patient>> paths = graph.computeAllMinimalInfeasiblePaths(3);
		System.out.println(DirectedCompatibilityGraph.toId(paths));
		
		System.out.println("ILP Matching");
		ILPMatching test5 = new ILPMatching("test1.txt");
		test5.match();
		System.out.println(test5);
	}

}