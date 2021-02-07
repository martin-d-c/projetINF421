
public class TestILP {

	public static void main(String[] args) throws Exception {
		System.out.println("ILP Matching");
		ILPMatching test5 = new ILPMatching("test1.txt");
		test5.match();
		System.out.println(test5);

	}

}
