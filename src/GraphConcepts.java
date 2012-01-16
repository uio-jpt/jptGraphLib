/* Template'en GraphConcepts inneholder hovedkonseptene for en graf, 
   slik den er spesifisert i det opprinnelige biblioteket. Disse konseptene
   blir i Java realsert som interfaces.  

  Implementasjoner finnes i template'en GraphConceptImplementations.
*/


template GraphConcepts {
	
 	required type Vertex { }
	required type Edge { 
		Vertex source(); 
		Vertex target();
	}
	
	required type EdgeIterator extends java.util.Iterator<Edge>{}
	required type OutEdgeIterator extends java.util.Iterator<Edge>{}
	required type VertexIterator extends java.util.Iterator<Vertex>{}
	

	
	// An incidence graph is a directed graph that can be
	// represented by a list of all the outgoing edges for 
	// each vertex of the graph	
	required interface IncidenceGraph {
		OutEdgeIterator out_edges(Vertex v);
		int out_degree(Vertex v);
	}
	
	required interface EdgeListGraph  {
	    EdgeIterator edges();
	}
		
	required interface VertexListGraph {
	  VertexIterator vertices();
	  int num_vertices();
	}
	
	required interface VertexListAndIncidenceGraph 
	  extends 
	    VertexListGraph,
	    IncidenceGraph {}
		
	required interface VertexListAndIncidenceAndEdgeListGraph
	  extends 
	    VertexListAndIncidenceGraph,
	    EdgeListGraph {}
}
