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
	static double[] frequencies = {0.46,0.85,0.96};
	public static  void generateConfiguration() throws IOException{
		for(int i = 1;i<N+1;i++) {
			
			List<Integer> ACompatible = new LinkedList<Integer>();
			List<Integer> BCompatible = new LinkedList<Integer>();
			List<Integer> ABCompatible = new LinkedList<Integer>();
			List<Integer> OCompatible = new LinkedList<Integer>();
			String[] bloodType = new String[n+1];
			
			for(int j = 1;j<n+1;j++) {
				double prop = Math.random();
				
				if(prop<frequencies[0]) {  bloodType[j] = "O";OCompatible.add(j); ACompatible.add(j);BCompatible.add(j);ABCompatible.add(j);}
				else if(prop<frequencies[1]) {bloodType[j] = "A"; ACompatible.add(j);ABCompatible.add(j);}
				else if(prop<frequencies[2]) {bloodType[j] = "B";BCompatible.add(j);ABCompatible.add(j);}
				else { bloodType[j] = "AB";ABCompatible.add(j);}
			}
			
			PrintWriter writer = new PrintWriter(new File("testfile"+i+".txt"));
			writer.println(n);
			
			for(int j = 1;j<n+1;j++) {
				
				if(bloodType[j] == "O") {
					
					
					Collections.shuffle(OCompatible);
					for(int k : OCompatible) {
						if(k==j) {break;}
						writer.print(k);
						writer.print(" ");
					}
					
					if(Math.random() <0.5) {writer.print(0);writer.print(" ");}
					else {writer.print(j);writer.print(" ");}
					writer.println();
				}
				else if(bloodType[j] == "A") {
				
					
					Collections.shuffle(ACompatible);
					for(int k : ACompatible) {
						if(k==j) {break;}
						writer.print(k);
						writer.print(" ");
					}
					
					if(Math.random() <0.5) {writer.print(0);writer.print(" ");}
					else {writer.print(j);writer.print(" ");}
					writer.println();
				}
				else if(bloodType[j] == "B") {
					
					
					Collections.shuffle(BCompatible);
					for(int k : BCompatible) {
						if(k==j) {break;}
						writer.print(k);
						writer.print(" ");
					}
					
					if(Math.random() <0.5) {writer.print(0);writer.print(" ");}
					else {writer.print(j);writer.print(" ");}
					writer.println();
				}
				else {
					
					
					
					Collections.shuffle(ABCompatible);
					for(int k : ABCompatible) {
						if(k==j) {break;}
						writer.print(k);
						writer.print(" ");
					}
					
					if(Math.random() <0.5) { writer.print(0); writer.print(" ");}
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
