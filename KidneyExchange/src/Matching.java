import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
public abstract class Matching {
	
	int n;
	int nbNotAssigned;
	HashSet<Patient> assigned;
	HashSet<Patient> notAssigned;
	Graph graph;
	
	Matching() {
		n = 0;
		nbNotAssigned = 0;
		assigned = new HashSet<Patient>();
		notAssigned = new HashSet<Patient>();
	}
	
	Matching(HashSet<Patient> T) {
		this.assigned = new HashSet<Patient>();
		this.notAssigned = new HashSet<Patient>(T);
		this.n = T.size();
		this.nbNotAssigned = n;
	}
		
	public abstract void runDirectDonation();
	
	public abstract void match() throws Exception;
	
	
	void assign(Patient P,int i){
		notAssigned.remove(P);
		assigned.add(P);
		P.assign(i);
		nbNotAssigned --;
	}
	
	// Assigne à P le rein vers lequel il pointe
	void assign(Patient P) {
		assign(P, P.kidney);
	}
	
	
	
	static String[] getBloodType(String path) throws IOException {
		
		BufferedReader br = null;
	    try  {br = new BufferedReader(new FileReader(new File(path)));}
	    catch(FileNotFoundException exc) {System.out.println("Erreur d'ouverture");}
	    
	    int n = Integer.parseInt(br.readLine());
	    
	    for(int i =0; i<n;i++) {br.readLine();}
	    String[] bloodType = br.readLine().split(" ");
	    
	    br.close();
		return bloodType;
	}
	
	
	// waitingList will not be modified
	int getNbTransplantations(int[] waitingList) { // waitingList contains the number of cadavers for each bloodType  (O,A,B,AB)
		int nbTransplantations = 0;
		int Ocount = waitingList[0];
		int Acount = waitingList[1];
		int Bcount = waitingList[2];
		int ABcount = waitingList[3];
		for(Patient P : this.assigned) {
			if(P.kidney==0) {
				if(P.bloodType.equals("O") && Ocount>0) { nbTransplantations++; Ocount--;}
				else if(P.bloodType.equals("A") && Acount>0) { nbTransplantations++; Acount--;}
				else if(P.bloodType.equals("B") && Bcount>0) { nbTransplantations++; Bcount--;}
				else if(P.bloodType.equals("AB") &&ABcount>0) { nbTransplantations++; ABcount--;}
			}
			else {
				nbTransplantations++;
			}
		}
		return nbTransplantations;
	}
		
	
	public String toString() {
		StringBuffer bf = new StringBuffer();
		for (Patient p: assigned)
			bf.append("Patient " + p.id + " --> Kidney " + p.kidney + "\n");
		return bf.toString();
	}
	
}
