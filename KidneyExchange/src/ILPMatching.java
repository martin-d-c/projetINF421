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
        	   if(Math.round(var.getValue()) == var.getValue()) {
        		   index = Integer.parseInt(var.getName());
        		   isInteger = false;
        		   break;
        	   }
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
	HashSet<Patient> match(){
		LinkedList<LinkedList<Integer>> infeasiblePaths = this.graph.toInt(this.graph.computeAllMinimalInfeasiblePaths(this.graph.K));
		return this.assigned;
	}
}