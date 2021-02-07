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
	
	abstract void cancelWaitingList();
	
	Matching(Matching M) {
		this.assigned = new HashSet<Patient>(M.assigned);
		this.notAssigned = new HashSet<Patient>(M.notAssigned);
		this.n = M.n;
		this.nbNotAssigned = M.nbNotAssigned;
	}
	
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
	
	
	
	int getNbTransplantations(int[] waitingList) { // waitingList contains the number of cadavers for each bloodType  (O,A,B,AB)
		int nbTransplantations = 0;
		for(Patient P : this.assigned) {
			if(P.kidney==0) {
				if(P.bloodType.equals("O") && waitingList[0]>0) { nbTransplantations++; waitingList[0]--;}
				else if(P.bloodType.equals("A") && waitingList[1]>0) { nbTransplantations++; waitingList[1]--;}
				else if(P.bloodType.equals("B") && waitingList[2]>0) { nbTransplantations++; waitingList[2]--;}
				else if(P.bloodType.equals("AB") &&waitingList[3]>0) { nbTransplantations++; waitingList[3]--;}
			}
			else {
				nbTransplantations++;
			}
		}
		return nbTransplantations;
	}
	
	
	public abstract void match();
	
	
	public String toString() {
		StringBuffer bf = new StringBuffer();
		for (Patient p: assigned)
			bf.append("Patient " + p.id + " --> Kidney " + p.kidney + "\n");
		return bf.toString();
	}
	
}
