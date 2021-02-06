import java.io.IOException;
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
	
	DirectedCompatibilityGraph graph;
	Solution solutionILP;
	
	public ILPMatching(String path) throws IOException {
		graph = new DirectedCompatibilityGraph(path);
		this.n = graph.n;
		this.nbNotAssigned = graph.n;
		this.notAssigned = graph.getVertices();
	}

	int branchAndBound(double[][] A,double[] b,double[]c,ConsType[] rel,double bound, int interRes) throws Exception {
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
           if(r <= interRes) {
        	   return interRes;
           }
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
           double[][] A1 = new double[A.length][A[0].length];
           double[] b1 = new double[b.length];
           double[] b2 = new double[b.length];
           ConsType[] rel1 = new ConsType[rel.length +1];
           
           for(int i = 0;i<A.length;i++) {
        	   for(int j =0;j<A[0].length;j++) {
        		   A1[i][j] = A[i][j];
        	   }
        	   b1[i] = b[i];
        	   b2[i] = b[i];
        	   rel1[i]= rel[i];
           }
           b1[b.length-1] = 0; b2[b.length-1] = 0;
           rel1[c.length-1] = ConsType.EQ;
           double[] newLine = new double[A[0].length];
           newLine[index]=1;
           A1[A.length-1]=newLine;
           return Math.max(branchAndBound(A1,b1,c,rel1,bound,interRes),branchAndBound(A1,b2,c,rel1,bound,interRes));
        } 
        else {
        	return interRes;
        }
	}
	
	HashSet<Patient> match()  throws Exception{
		LinkedList<LinkedList<Integer>> infeasiblePaths = DirectedCompatibilityGraph.toId(this.graph.computeAllMinimalInfeasiblePaths(this.graph.K));
		int p = infeasiblePaths.size()+2*n;

		double[] c = new double[n*n];
		for(int i = 0;i< (n*n);i++) { c[i] = 1; }

		double[][] A = new double[p][n*n];
		double[] b = new double[p];
		ConsType[] rel = new ConsType[p];
		for(int i =1;i<n+1;i++) { 
			for(int j =1;j<n+1;j++) {
				if(this.graph.adjMatrix[i][j] ==1) { A[i-1][n*(i-1)+j-1] =1; A[n+j-1][n*(i-1)+j-1]=1;}
			}
			b[i-1]=1;
			b[n+i-1]=1;
			rel[i-1]=ConsType.LE;
			rel[n+i-1]=ConsType.LE;
		}
		int k = 0;
		for(LinkedList<Integer> L : infeasiblePaths) {
			b[2*n+k] = this.graph.K;
			rel[2*n+k] = ConsType.LE;
			int prev = L.getFirst();
			L.removeFirst();
			for(int i :L) {
				A[k+2*n][n*(prev-1)+i-1] =1;
				prev=i;
			}
			k++;
		}

		branchAndBound(A,b,c,rel,Double.POSITIVE_INFINITY,0);
		int i=1,j =1;
		for(Variable var : solutionILP.getVariables()) {
			if(var.getValue() ==1) {
				Patient P = new Patient(j,new boolean[n],new int [n]);
				P.assign(i);
				this.assigned.add(P);
			}

			if(j==n) {i++;j=1;}
			else {j++;}
		}
		return this.assigned;
	}
}
