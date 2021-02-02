import java.io.PrintWriter;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class QuantitativeAnalysis {

	
	static int c = 3;
	static int n = 30;
	static int N = 5; // number of kidney exchange problem generated
	static double[] frequencies = {0.46,0.85,0.96}; // cumulative frequencies  O,A,B,AB
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
			String bloodTypeToPrint = new String();
			for(int k =1;k<n+1;k++) {
				bloodTypeToPrint+= bloodType[k];
				bloodTypeToPrint+= " ";
			}
			writer.println(bloodTypeToPrint);
			writer.close();
		}
		

	}
	static int getNbTransplantations(Matching M, int[] waitingList,String[] bloodType) { //waitingList contains the number of cadaver for each bloodType  (O,A,B,AB)
		int nbTransplantations = 0;
		for(Patient P : M.assigned) {
			if(P.kidney==0) {
				if(bloodType[P.id-1].compareTo("O") ==0 && waitingList[0]>0) { nbTransplantations++; waitingList[0]--;}
				else if(bloodType[P.id-1].compareTo("A") ==0 && waitingList[1]>0) { nbTransplantations++; waitingList[1]--;}
				else if(bloodType[P.id-1].compareTo("B") ==0 && waitingList[2]>0) { nbTransplantations++; waitingList[2]--;}
				else if(bloodType[P.id-1].compareTo("AB") ==0 &&waitingList[3]>0) { nbTransplantations++; waitingList[3]--;}
			}
			else {
				nbTransplantations++;
			}
		}
		return nbTransplantations;
	}
	
	static String[] getBloodType(String path) throws IOException {
		BufferedReader br = null;
		
	    try  {
		br = new BufferedReader(new FileReader(new File(path)));
	    }
	    catch(FileNotFoundException exc) {
		System.out.println("Erreur d'ouverture");
	    }
	    
	    int n = Integer.parseInt(br.readLine());
	    
	    for(int i =0; i<n;i++) {br.readLine();}
	    String[] bloodType = br.readLine().split(" ");
	    br.close();
		return bloodType;
	}
	
	static int[] getTypeCadavers() {
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
		
		CyclesAndChainsMatching M = new CyclesAndChainsMatching("testfile1.txt");
		M.match(true);
		System.out.println(M);
		
		System.out.println(getNbTransplantations(M, getTypeCadavers(),getBloodType("testfile1.txt")));
	}
}
