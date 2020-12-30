import java.util.HashSet;

public class Matching {
	int nbNotAssigned; 
	HashSet<Patient> Assigned;
	HashSet<Patient> notAssigned;
	
	Matching(Patient[] T){
		this.Assigned = new HashSet<Patient>();
		this.notAssigned = new HashSet<Patient>();
		for (int i =0;i<T.length;i++) {
			notAssigned.add(T[i]);
		}
		this.nbNotAssigned = T.length;
	}
	
	void assignement(Patient P,int i){
		notAssigned.remove(P);
		Assigned.add(P);
		P.assignement(i);
		nbNotAssigned --;
	}
	
	void directDonation(){
		for (Patient P : notAssigned){
			if(!P.compatible(P.kidney)) {
				assignement(P,0);
			}
		}
	}
}
