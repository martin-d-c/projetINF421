import java.util.HashSet;

public abstract class Matching {
	
	int n;
	int nbNotAssigned;
	HashSet<Patient> assigned;
	HashSet<Patient> notAssigned;
	
	Matching(Patient[] T) {
		this.assigned = new HashSet<Patient>();
		this.notAssigned = new HashSet<Patient>();
		for (int i =0;i<T.length;i++) {
			notAssigned.add(T[i]);
		}
		this.n = T.length;
		this.nbNotAssigned = T.length;
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
	
}
