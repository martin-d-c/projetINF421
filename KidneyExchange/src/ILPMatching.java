import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import it.ssc.pl.milp.ConsType;
import it.ssc.pl.milp.Constraint;
import it.ssc.pl.milp.GoalType;
import it.ssc.pl.milp.LP;
import it.ssc.pl.milp.LinearObjectiveFunction;
import it.ssc.pl.milp.Solution;
import it.ssc.pl.milp.SolutionType;
import it.ssc.pl.milp.Variable;


public class ILPMatching extends Matching {
	/*
	 * Matching class to perform Branch-and-Bound.
	 * */
	
	DirectedCompatibilityGraph graph;
	Solution solutionILP;
	HashMap<Integer, Patient> patientsById;
	public ILPMatching(String path) throws IOException {
		graph = new DirectedCompatibilityGraph(path);
		this.n = graph.n;
		this.nbNotAssigned = graph.n;
		this.notAssigned = graph.getVertices();
		this.assigned = new HashSet<Patient>();
		patientsById = new HashMap<Integer, Patient>();
		for (Patient p: notAssigned) {
			patientsById.put(p.id, p);
		}
	}
	
	public void runDirectDonation() {
		HashSet<Patient> notAssignedCopy = new HashSet<Patient>(notAssigned);
		for (Patient P : notAssignedCopy)
			if(P.isCompatible(P.kidney)) {
				assign(P); // assigning ki to ti
			}
	}
	
	int branchAndBound(double[][] A,double[] b,double[]c,ConsType[] rel,double bound, int interRes) throws Exception {
		// Maximises cTx with constraints Ax rel b where rel is a vector of type of constraints  (<=,>= or =)
		
        LinearObjectiveFunction fo = new LinearObjectiveFunction(c, GoalType.MAX);
 
        ArrayList< Constraint > constraints = new ArrayList< Constraint >();
        for(int i=0; i < A.length; i++) {
            constraints.add(new Constraint(A[i], rel[i], b[i]));
        }
        
        LP lp = new LP(fo,constraints); 
        SolutionType solution_type=lp.resolve();
        
        if(solution_type==SolutionType.OPTIMUM) { 
           Solution solution=lp.getSolution();
           double r = solution.getOptimumValue();
           bound = Math.min(bound,r);
           
           if(r <= interRes) { //branch not further explored
        	   return interRes;
           }
           
           //test if the LP solution is integer
           boolean isInteger =true;
           int index =0;
           for(Variable var:solution.getVariables()) {
        	   if(Math.round(var.getValue()) != var.getValue()) {
        		   isInteger = false;
        		   break;
        	   }
        	   index++;
           }
          
           if(isInteger) {
        	   this.solutionILP = solution;
        	   return interRes;
           }
           
           //add new constraints : x(index) =0 or x(index) =1
           double[][] A1 = new double[A.length+1][A[0].length];
           double[] b1 = new double[b.length+1];
           double[] b2 = new double[b.length+1];
           ConsType[] rel1 = new ConsType[rel.length +1];
           for(int i = 0;i<A.length;i++) {
        	   for(int j =0;j<A[0].length;j++) {
        		   A1[i][j] = A[i][j];
        	   }
        	   b1[i] = b[i];
        	   b2[i] = b[i];
        	   rel1[i]= rel[i];
           }
           b1[b1.length-1] = 0; b2[b2.length-1] = 0;
           rel1[rel1.length-1] = ConsType.EQ;
           double[] newLine = new double[A[0].length];
           newLine[index]=1;
           A1[A1.length-1]=newLine;
           
           return Math.max(branchAndBound(A1,b1,c,rel1,bound,interRes),branchAndBound(A1,b2,c,rel1,bound,interRes));
        } 
        else { //problem infeasible : branch not further explored
        	
        	return interRes;
        }
	}
	public void match()  throws Exception{
		LinkedList<LinkedList<Integer>> infeasiblePaths = DirectedCompatibilityGraph.toId(this.graph.computeAllMinimalInfeasiblePaths(this.graph.K));
		int p = infeasiblePaths.size()+2*n;
		
		//Computes the number of eges
		int nbEdges = 0;
		for(int[] ligne : this.graph.adjMatrix) { for(int i :ligne) { if(i==1) {nbEdges++;} }}
		
		int[][] listEdges = new int[nbEdges][2]; //Associates each variable number with an edge
		HashMap<Integer,HashMap<Integer,Integer>> edges = new HashMap<Integer,HashMap<Integer,Integer>>(); //Associates each edge with a variable number
		int k=0;
		for(int i =1;i<n+1;i++) {
			for(int j =1;j<n+1;j++) {
				if(this.graph.adjMatrix[i][j] ==1) {
					listEdges[k][0] = i;
					listEdges[k][1] = j;
					if(edges.containsKey(i)) {
						edges.get(i).put(j,k);
					}
					else {
					HashMap<Integer,Integer> num = new HashMap<Integer,Integer>();
					num.put(j,k);
					edges.put(i, num);
					}
					k++;
				}
			}
		}
		
		double[] c = new double[nbEdges]; // the objective function is cTx
		for(int i = 0;i< nbEdges;i++) { c[i] = 1; }
		
		//Constraints
		double[][] A = new double[p+n][nbEdges];
		double[] b = new double[p+n];
		ConsType[] rel = new ConsType[p+n];
		
		for(int i =1;i<n+1;i++) {
			int numVariable = 0;
			for(int[] edge : listEdges) {
				if(edge[0] ==i) {A[i-1][numVariable] = 1;}
				if(edge[1]==i) {A[n+i-1][numVariable]=1; }
				numVariable++;
			}
			b[i-1]=1;
			b[n+i-1]=1;
			rel[i-1]=ConsType.LE;
			rel[n+i-1]=ConsType.LE;
		}
		k = 0;
		
		for(LinkedList<Integer> L : infeasiblePaths) {
			b[2*n+k] = this.graph.K;
			rel[2*n+k] = ConsType.LE;
			int prev = L.getFirst();
			L.removeFirst();
			for(int i :L) {
				
				
				int numVariable = edges.get(prev).get(i);
				A[k+2*n][numVariable] =1;
				prev=i;
			}
			k++;
		}
		
		for(int i =1;i<n+1;i++) {
			for(int[]L : listEdges) {
				if(L[0]==i) {
					A[p+i-1][edges.get(i).get(L[1])] = 1;
				}
				if(L[1] ==i) {
					A[p+i-1][edges.get(L[0]).get(i)] = -1;
				}
			}
			b[p+i-1] =0;
			rel[p+i-1] = ConsType.EQ;
		}
		
		branchAndBound(A,b,c,rel,Double.POSITIVE_INFINITY,0);
		
		//Makes assignments according to the solution of the ILP
		int numVariable=0;
		HashSet<Integer> assignedKidneys = new HashSet<Integer>();
		k=0;
		for(Variable var : solutionILP.getVariables()) {
			if(var.getValue() ==1) {
				int i = listEdges[numVariable][0];
				int j = listEdges[numVariable][1];
				
				this.assign(this.patientsById.get(j),i);
				
				assignedKidneys.add(i);
			}
			
			numVariable++;
		}
		
		//Assigns lasting patients
		HashSet<Patient> notAssignedCopy = new HashSet<Patient>(notAssigned);
		for(Patient P : notAssignedCopy) {
			if(this.graph.adjMatrix[P.id][P.id] == 1 && !assignedKidneys.contains(P.id)) {
				assign(P,P.id);
			}
			else {
				assign(P,0);
			}
		}
		
	}
}
