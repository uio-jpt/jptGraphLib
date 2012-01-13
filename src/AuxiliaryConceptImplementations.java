/* Denne template'en inneholder to implementasjoner av Visitor-interfacet.

*/

template AuxiliaryConceptImplementations {
	
	inst AuxiliaryConcepts;
	
	public class mutable_queue<Compare extends GeneralConceptsP.StrictWeakOrdering<Vertex > >
	    implements GeneralConceptsP.MutableQueue<Vertex> {

	  private java.util.ArrayList<Vertex> vertices_;
	  private Compare compare_;

	  public mutable_queue(Compare compare) {
	    compare_ = compare;
	    vertices_ = new java.util.ArrayList<Vertex>();
	  }
	  assumed mutable_queue(Compare compare);

	  public void push(Vertex v) {
	    vertices_.add(v);
	  }

	  public Vertex pop() {
	    if (empty()) return null;
	    Vertex best = vertices_.get(0);
	    int best_idx = 0;
	    for (int i = 1; i < vertices_.size(); ++i)
	      if (compare_.less(vertices_.get(i), best)) {
		best = vertices_.get(i);
		best_idx = i;
	      }
	    vertices_.remove(best_idx);
	    return best;
	  }

	  public boolean empty() {
	    return vertices_.isEmpty();
	  }

	  public void update(Vertex v) {}
	}
	
	
	public class dijkstra_visitor<
		      QueueType extends GeneralConceptsP.MutableQueue<Vertex>,
		      WeightMap extends GeneralConceptsP.ReadablePropertyMap<Edge, Distance>,
		      PredecessorMap extends GeneralConceptsP.ReadWritePropertyMap<Vertex, Vertex>,
		      DistanceMap extends GeneralConceptsP.ReadWritePropertyMap<Vertex, Distance>,
		      DistanceCombine extends GeneralConceptsP.BinaryFunction<Distance>,
		      DistanceCompare extends GeneralConceptsP.StrictWeakOrdering<Distance>,
		      Distance>		
	       implements Visitor {
	  private WeightMap m_weight;
	  private DistanceCompare m_compare;
	  private DistanceCombine m_combine;
	  private Distance m_zero;
	  private DistanceMap m_distance;
	  private PredecessorMap m_predecessor;
	  private QueueType m_Q;

	  public dijkstra_visitor(QueueType Q, WeightMap weight, PredecessorMap predecessor, DistanceMap distance, DistanceCombine combine, DistanceCompare compare, Distance zero) {
	    m_Q = Q;
	    m_weight = weight;
	    m_predecessor = predecessor;
	    m_distance = distance;
	    m_combine = combine;
	    m_compare = compare;
	    m_zero = zero;
	  }
	  assumed dijkstra_visitor(QueueType Q, WeightMap weight, PredecessorMap predecessor, DistanceMap distance, DistanceCombine combine, DistanceCompare compare, Distance zero);

	  public void initialize_vertex(Vertex u, Graph g) {}
	  public void discover_vertex(Vertex u, Graph g) {}
	  public void examine_edge(Edge e, Graph g) {
	    if (m_compare.less(m_weight.get(e), m_zero))
	      {} // throw new negative_edge(); Requirement to catch all exceptions could make this painful to use
	  }
	  public void tree_edge(Edge e, Graph g) {
	    relax.relax(e, m_weight, m_distance, m_predecessor, m_combine, m_compare);
	  }
	  public void non_tree_edge(Edge e, Graph g) {}
	  public void gray_target(Edge e, Graph g) {
	    boolean relaxed = relax.relax(e, m_weight, m_distance, m_predecessor, m_combine, m_compare);
	    if (relaxed)
	      m_Q.update(e.target());
	  }
	  public void black_target(Edge e, Graph g) {}
	  public void finish_vertex(Vertex u, Graph g) {}
	}
	
	
	
	public class printing_visitor 
	      implements Visitor {
		
	  assumed printing_visitor();
	  public void initialize_vertex(Vertex u, Graph g) {
	    System.out.println("initialize "+u+" in "+g);
	  }

	  public void discover_vertex(Vertex u, Graph g) {
	    System.out.println("discover "+u+" in "+g);
	  }

	  public void finish_vertex(Vertex u, Graph g) {
	    System.out.println("finish "+u+" in "+g);
	  }

	  public void examine_edge(Edge e, Graph g) {
	    System.out.println("examine "+e+" in "+g);
	  }

	  public void tree_edge(Edge e, Graph g) {
	    System.out.println("tree "+e+" in "+g);
	  }

	  public void non_tree_edge(Edge e, Graph g) {
	    System.out.println("nontree "+e+" in "+g);
	  }

	  public void gray_target(Edge e, Graph g) {
	    System.out.println("gray target "+e+" in "+g);
	  }

	  public void black_target(Edge e, Graph g) {
	    System.out.println("black target "+e+" in "+g);
	  }
	}
	
	
	
	public class relax {
	  public static
	    <WeightMap extends GeneralConceptsP.ReadablePropertyMap, 
	     Distance,
	     DistanceMap extends GeneralConceptsP.ReadWritePropertyMap, 
	     PredecessorMap extends GeneralConceptsP.ReadWritePropertyMap, 
	     Combine extends GeneralConceptsP.BinaryFunction, 
	     Compare extends GeneralConceptsP.StrictWeakOrdering>
	  boolean relax(Edge e, WeightMap w, DistanceMap d, PredecessorMap pi, Combine combine, Compare compare) {
	    Distance dnew = (Distance)combine.op((Distance)d.get(e.source()), (Distance)w.get(e));
	    if (compare.less(dnew, d.get(e.target()))) {
	      d.set(e.target(), dnew);
	      pi.set(e.target(), e.source());
	      return true;
	    }
	    return false;
	  }
	}
	
	
}