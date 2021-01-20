import java.io.IOException;
import java.util.HashSet;

public class Test {

	public static void main(String[] args) throws IOException {
		SimpleMatching test1 = new SimpleMatching("Test1");
		HashSet<Patient> matching = test1.directDonation();
		for(Patient P : matching) {
			System.out.print(P.id);
			System.out.println(P.kidney);
		}
	}

}
