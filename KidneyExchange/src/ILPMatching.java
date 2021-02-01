import java.io.IOException;
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
	
}
