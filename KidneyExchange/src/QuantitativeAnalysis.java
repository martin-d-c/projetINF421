import java.io.PrintWriter;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

//import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class QuantitativeAnalysis {

	
	static int c = 3;
	static int n = 30;
	static int N = 100; // number of kidney exchange problem generated
	static double[] frequencies = {0.46,0.85,0.96}; // cumulative frequencies  O,A,B,AB
	
	
	private static void writePatient(int id, PrintWriter writer, List<Integer> comp) {
		Collections.shuffle(comp);
		for(int k : comp) {
			if(k==id) {break;}
			writer.print(k);
			writer.print(" ");
		}
		if(Math.random() <0.5) {writer.print(0);writer.print(" ");}
		else {writer.print(id);writer.print(" ");}
		writer.println();
	}
	
	public static  void generateConfiguration() throws IOException{
		for(int i = 1;i<N+1;i++) {
			
			List<Integer> ACompatible = new LinkedList<Integer>();
			List<Integer> BCompatible = new LinkedList<Integer>();
			List<Integer> ABCompatible = new LinkedList<Integer>();
			List<Integer> OCompatible = new LinkedList<Integer>();
			String[] bloodType = new String[n+1];
			
			for(int j = 1;j<n+1;j++) {
				double prop = Math.random();
				
				if(prop<frequencies[0]) {bloodType[j] = "O";OCompatible.add(j); ACompatible.add(j);BCompatible.add(j);ABCompatible.add(j);}
				else if(prop<frequencies[1]) {bloodType[j] = "A"; ACompatible.add(j);ABCompatible.add(j);}
				else if(prop<frequencies[2]) {bloodType[j] = "B";BCompatible.add(j);ABCompatible.add(j);}
				else {bloodType[j] = "AB";ABCompatible.add(j);}
			}
			
			PrintWriter writer = new PrintWriter(new File("testfile"+i+".txt"));
			writer.println(n);
			
			for(int j = 1;j<n+1;j++) {
				if(bloodType[j].equals("O"))
					writePatient(j, writer, OCompatible);
				else if(bloodType[j].equals("A"))
					writePatient(j, writer, ACompatible);
				else if(bloodType[j].equals("B"))	
					writePatient(j, writer, BCompatible);
				else // AB
					writePatient(j, writer, ABCompatible);
			}
			
			StringBuffer bloodTypeToPrint = new StringBuffer();
			for(int k =1;k<n+1;k++)
				bloodTypeToPrint.append(bloodType[k] + " ");
			writer.println(bloodTypeToPrint.toString());
			writer.close();
		}
	}
	
	
	static int[] generateCadavers() {
		int[] typeCadavers = new int[4];
		for(int j = 0;j<c;j++) {
			double prop = Math.random();
			if(prop<frequencies[0]) {  typeCadavers[0]++;}
			else if(prop<frequencies[1]) {typeCadavers[1]++;}
			else if(prop<frequencies[2]) {typeCadavers[2]++;}
			else { typeCadavers[3]++;}
		}
		return typeCadavers;
	}
	
	
	public static void main(String[] args) throws IOException{
		
		generateConfiguration();
		
		/*CyclesAndChainsMatching M = new CyclesAndChainsMatching("testfile1.txt");
		M.match(); // rule B
		System.out.println(M);
		
		System.out.println(M.getNbTransplantations(generateCadavers()));*/
		
		PrintWriter writer = new PrintWriter(new File("results.txt"));
		
		Matching[][] matchings = new Matching[3][N];
		for (int i = 0; i < N; i++) {
			int[] waitingList = generateCadavers();
			String path = "testfile" + (i+1) + ".txt";
			matchings[0][i] = new DirectDonationMatching(path);
			matchings[0][i].match();
			writer.print(matchings[0][i].getNbTransplantations(waitingList) + " ");
			matchings[1][i] = new GreedyMatching(path);
			//matchings[1][i] = new GreedyMatching(matchings[0][i]);
			matchings[1][i].match();
			matchings[2][i] = new CyclesAndChainsMatching(path);
			//matchings[2][i] = new CyclesAndChainsMatching(matchings[0][i]);
			matchings[2][i].match(); // rule B
			System.out.println("0 "+matchings[0][i]);
			writer.print((matchings[1][i].getNbTransplantations(waitingList)) + " ");
			System.out.println("1 "+matchings[1][i]);
			writer.print((matchings[2][i].getNbTransplantations(waitingList)) + "");

			writer.println();
		}
		writer.close();
	}
}
