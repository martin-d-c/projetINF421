
public class Patient implements Comparable<Patient> {
	final int id; //égal à la priorité du patient, on l'appelle id car la priorité a peu de sens pour le kidney associé; le patient 1 est le premier de la liste de priorité
	boolean isAssigned;
	int kidney; //0 si waiting list, >0 : kidney associé
	String bloodType;
	final boolean[] K; //K[i] == true ssi le kidney i est compatible
	final int[] P; //P[i] donne le rang du kidney i (ou de w si i==0) 
	
	Patient(int priority, boolean[] K, int[]P) {
		this.id = priority;
		this.isAssigned = false;
		this.kidney = id;
		this.K = K;
		this.P = P;
		this.bloodType = null;
	}
	
	Patient(int priority, boolean[] K, int[]P, String bloodType) {
		this.id = priority;
		this.isAssigned = false;
		this.kidney = id;
		this.K = K;
		this.P = P;
		this.bloodType = bloodType;
	}
	
	void assign(int i) {
		this.kidney = i;
		this.isAssigned = true;
	}
	
	boolean isCompatible(int i) {
		return K[i];
	}
	
	public int compareTo(Patient that) {
		/*this < that ssi this est plus prioritaire que that -> permettra de trier éventuellement par priorité décroissante*/
		return this.id - that.id;
	}
}
