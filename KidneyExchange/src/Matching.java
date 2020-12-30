import java.util.HashSet;

public class Matching {
	
	int n;
	int nbNotAssigned;
	HashSet<Patient> assigned;
	HashSet<Patient> notAssigned;
	
	Matching(Patient[] T){
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
	
	HashSet<Patient> directDonation(){
		for (Patient P : notAssigned){
			if(!P.isCompatible(P.kidney)) {
				assign(P,0);
			}
		}
		return assigned;
	}
	
	Patient selectChainRuleB() {
		Patient p = null;
		for (Patient q: notAssigned)
			if (p == null || q.compareTo(p) < 0)
				p = q;
		return p;
	}
}
