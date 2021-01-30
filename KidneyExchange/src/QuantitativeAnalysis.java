import java.io.PrintWriter;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class QuantitativeAnalysis {

	
	static int c = 3;
	static int n = 30;
	static int N = 5; // number of kidney exchange problem generated
	static int[] frequencies = {46,85,96};
	public static  void generateConfiguration() throws IOException{
		for(int i = 0;i<N;i++) {
			List<Integer> AType = new LinkedList<Integer>();
			List<Integer> BType = new LinkedList<Integer>();
			List<Integer> ABType = new LinkedList<Integer>();
			List<Integer> OType = new LinkedList<Integer>();
			String[] bloodType = new String[n];
			
			for(int j = 0;j<n;j++) {
				double prop = Math.random();
				if(prop<1/frequencies[0]) { OType.add(j); bloodType[j] = "O"; }
				else if(prop<1/frequencies[1]) { AType.add(j);bloodType[j] = "A";}
				else if(prop<1/frequencies[2]) { BType.add(j);bloodType[j] = "B";}
				else { ABType.add(j);bloodType[j] = "AB";}
			}
			
			PrintWriter writer = new PrintWriter(new File("testfile"+i+".txt"));
			writer.println(n);
			
			for(int j = 0;j<n;j++) {
				if(bloodType[j] == "O") {
					OType.remove(j);
					
					Collections.shuffle(OType);
					for(int k : OType) {
						writer.print(k);
						writer.print(" ");
					}
					OType.add(j);
					if(Math.random() <1/2) {writer.print(0);writer.print(" ");}
					else {writer.print(j);writer.print(" ");}
					writer.println();
				}
				else if(bloodType[j] == "A") {
					AType.remove(j);
					
					Collections.shuffle(AType);
					for(int k : AType) {
						writer.print(k);
						writer.print(" ");
					}
					AType.add(j);
					if(Math.random() <1/2) {writer.print(0);writer.print(" ");}
					else {writer.print(j);writer.print(" ");}
					writer.println();
				}
				else if(bloodType[j] == "B") {
					BType.remove(j);
					
					Collections.shuffle(BType);
					for(int k : BType) {
						writer.print(k);
						writer.print(" ");
					}
					BType.add(j);
					if(Math.random() <1/2) {writer.print(0);writer.print(" ");}
					else {writer.print(j);writer.print(" ");}
					writer.println();
				}
				else {
					ABType.remove(j);
					
					Collections.shuffle(ABType);
					for(int k : ABType) {
						writer.print(k);
						writer.print(" ");
					}
					ABType.add(j);
					if(Math.random() <1/2) { writer.print(0); writer.print(" ");}
					else {writer.print(j);writer.print(" ");}
					writer.println();
				}
				
			}
			writer.close();
		}
		

	}
	public static void main(String[] args) throws IOException{
		generateConfiguration();
	}
}
