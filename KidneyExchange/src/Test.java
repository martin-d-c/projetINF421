import java.io.IOException;
import java.util.HashSet;

public class Test {

	public static void main(String[] args) throws IOException {
		System.out.println("Direct Donation");
		SimpleMatching test1 = new SimpleMatching("Test1");
		HashSet<Patient> matching = test1.directDonation();
		for(Patient P : matching) {
			System.out.print(P.id);System.out.print(" --> ");
			System.out.println(P.kidney);
		}
		
		System.out.println("Greedy Matching");
		SimpleMatching test2 = new SimpleMatching("Test1");
		HashSet<Patient> matching2 = test2.greedyMatching();
		for(Patient P : matching2) {
			System.out.print(P.id);System.out.print(" --> ");
			System.out.println(P.kidney);
		}
	}

}
