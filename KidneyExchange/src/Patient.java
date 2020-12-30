
public class Patient {
	int priority; //égale à l'id du patient
	boolean isAssigned;
	int kidney; //0 si waiting list, >0 : kidney associé 
	boolean[] K; //K[i] == true ssi le kidney i est compatible
	int[] P; //P[i] donne le rang du kidney i (ou de w si i==0) 
	
	Patient(int id, boolean[] K, int[]P){
		this.priority = id;
		this.isAssigned = false;
		this.kidney = id;
		this.K = K;
		this.P = P;
	}
	
	void assignement(int i) {
		this.kidney = i;
		this.isAssigned = true;
	}
	
	boolean compatible(int i) {
		return K[i];
	}
}
