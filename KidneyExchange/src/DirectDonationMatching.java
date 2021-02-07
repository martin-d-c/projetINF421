import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

public class DirectDonationMatching extends Matching {
	
	public DirectDonationMatching(HashSet<Patient> T) {
		super(T);
		graph = new CompatibilityGraph();
	}
	
	public DirectDonationMatching(String path) throws IOException {
		graph = new CompatibilityGraph(path);
		this.n = graph.n;
		this.nbNotAssigned = graph.n;
		this.notAssigned = graph.getVertices();
		this.assigned = new HashSet<Patient>();
	}
	
	void cancelWaitingList() {
		for (Patient p: new LinkedList<Patient>(assigned))
			if (p.kidney == 0) {
				assigned.remove(p);
				notAssigned.add(p);
				nbNotAssigned++;
				p.isAssigned = false;
			}
	}
	
	public void runDirectDonation() {
		match();
	}
	
	public void match() {
		HashSet<Patient> notAssignedCopy = new HashSet<Patient>(notAssigned);
		for (Patient P : notAssignedCopy) {
			if(P.isCompatible(P.kidney))
				assign(P); // assigning ki to ti
			else
				assign(P, 0); // waiting list
		}
	}
	
}
