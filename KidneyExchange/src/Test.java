import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		System.out.println("Direct Donation");
		SimpleMatching test1 = new SimpleMatching("Test1");
		test1.directDonation();
		System.out.println(test1);
		
		System.out.println("Greedy Matching");
		SimpleMatching test2 = new SimpleMatching("Test1");
		test2.greedyMatching();
		System.out.println(test2);
		
		System.out.println("Cycles and Chains Matching - Rule A");
		CyclesAndChainsMatching test3 = new CyclesAndChainsMatching("Test1");
		test3.match(false);
		System.out.println(test3);
		
		System.out.println("Cycles and Chains Matching - Rule B");
		CyclesAndChainsMatching test4 = new CyclesAndChainsMatching("Test1");
		test4.match(true);
		System.out.println(test4);
	}

}
