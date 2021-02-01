import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import it.ssc.log.SscLogger;
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
           int index;
           for(Variable var:solution.getVariables()) {
        	   if(Math.round(var.getValue()) == var.getValue()) {
        		   index = Integer.parseInt(var.getName());
        		   isInteger = false;
        		   break;
        	   }
           }
           if(isInteger) {
        	   return interRes;
           }
           return 1; //à finir
        } 
        else {
        	return interRes;
        }
	}
}
