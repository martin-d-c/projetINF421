import java.io.IOException;

public class ILPMatching extends Matching {
	
	DirectedCompatibilityGraph graph;
	
	public ILPMatching(String path) throws IOException {
		graph = new DirectedCompatibilityGraph(path);
		this.n = graph.n;
	}
	
}
