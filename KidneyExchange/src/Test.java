import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Test {
	/*
	 * Test class to test DirectDonationMatching, GreedyMatching, CyclesAndChainsMatching and computeAllMinimalInfeasiblePaths.
	 * */

	public static void main(String[] args) throws IOException,Exception {
		
		String path = "testPrimaire.txt";
		
		long startTime1 = System.currentTimeMillis();
		DirectDonationMatching test1 = new DirectDonationMatching(path);
		test1.match();
		long endTime1 = System.currentTimeMillis();
		
		long startTime2 = System.currentTimeMillis();
		GreedyMatching test2 = new GreedyMatching(path);
		test2.match();
		long endTime2 = System.currentTimeMillis();
		
		long startTime3 = System.currentTimeMillis();
		CyclesAndChainsMatching test3 = new CyclesAndChainsMatching(path);
		test3.ruleB = false;
		test3.match();
		long endTime3 = System.currentTimeMillis();
		
		long startTime4 = System.currentTimeMillis();
		CyclesAndChainsMatching test4 = new CyclesAndChainsMatching(path);
		test4.match();
		long endTime4 = System.currentTimeMillis();
		
		long startTime5 = System.currentTimeMillis();
		DirectedCompatibilityGraph graph = new DirectedCompatibilityGraph("test2.txt");
		LinkedList<LinkedList<Patient>> paths = graph.computeAllMinimalInfeasiblePaths(3);
		long endTime5 = System.currentTimeMillis();
		
		long startTime6 = System.currentTimeMillis();
		DirectedCompatibilityGraph graph2 = new DirectedCompatibilityGraph("test3.txt");
		LinkedList<LinkedList<Patient>> paths2 = graph2.computeAllMinimalInfeasiblePaths(3);
		long endTime6 = System.currentTimeMillis();
		
		
		PrintWriter writer = new PrintWriter(new File("main_results.txt"));
		
		writer.println("Direct Donation");
		writer.println();
		writer.println(test1);
		writer.print("CPU time: ");
		writer.print(endTime1 - startTime1);
		writer.println(" ms");
		
		writer.println();
		writer.println("---------------------------------");
		writer.println();

		writer.println("Greedy Matching");
		writer.println();
		writer.println(test2);
		writer.print("CPU time: ");
		writer.print(endTime2 - startTime2);
		writer.println(" ms");
		
		writer.println();
		writer.println("---------------------------------");
		writer.println();
		
		writer.println("Cycles and Chains Matching - Rule A");
		writer.println();
		writer.println(test3);
		writer.print("CPU time: ");
		writer.print(endTime3 - startTime3);
		writer.println(" ms");
		
		writer.println();
		writer.println("---------------------------------");
		writer.println();
		
		writer.println("Cycles and Chains Matching - Rule B");
		writer.println();
		writer.println(test4);
		writer.print("CPU time: ");
		writer.print(endTime4 - startTime4);
		writer.println(" ms");
		
		writer.println();
		writer.println("---------------------------------");
		writer.println();
		
		writer.println("Minimum infeasible paths - test2.txt");
		writer.println();
		writer.println(DirectedCompatibilityGraph.toId(paths));
		writer.println();
		writer.print("CPU time: ");
		writer.print(endTime5 - startTime5);
		writer.println(" ms");
		
		writer.println();
		writer.println("---------------------------------");
		writer.println();
		
		writer.println("Minimum infeasible paths - test3.txt");
		writer.println();
		writer.println(DirectedCompatibilityGraph.toId(paths2));
		writer.println();
		writer.print("CPU time: ");
		writer.print(endTime6 - startTime6);
		writer.println(" ms");
		
		writer.close();
	}

}