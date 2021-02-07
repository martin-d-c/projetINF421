import java.io.File;
import java.io.PrintWriter;

public class TestILP {
	/*
	 * Test class to test ILPMatching.
	 * */

	public static void main(String[] args) throws Exception {
		long startTime1 = System.currentTimeMillis();
		ILPMatching test1 = new ILPMatching("test1.txt");
		test1.match();
		long endTime1 = System.currentTimeMillis();
		
		long startTime2 = System.currentTimeMillis();
		ILPMatching test2 = new ILPMatching("test2.txt");
		test2.match();
		long endTime2 = System.currentTimeMillis();
		
		PrintWriter writer = new PrintWriter(new File("results_ILP.txt"));
		writer.println("Test 1");
		writer.println(test1);
		writer.print("Achieved objective value: ");
		writer.println(test1.solutionILP.getOptimumValue());
		writer.print("CPU time: ");
		writer.print(endTime1-startTime1);
		writer.println(" ms");
		
		writer.println("---------------------------------");
		
		writer.println("Test 2");
		writer.println(test2);
		writer.print("Achieved objective value: ");
		writer.println(test2.solutionILP.getOptimumValue());
		writer.print("CPU time: ");
		writer.print(endTime2-startTime2);
		writer.println(" ms");
		
		writer.close();
	}

}
