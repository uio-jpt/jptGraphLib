/* Denne template'en inneholder kun konsepet "Visitor", som ligner
   på den kjente design-pattern-varianten fra Gamma et al., men som
   her forekommer i en utgave spesialisert for graf-biblioteket.
*/

template AuxiliaryConcepts {
	inst GraphConcepts;
	required interface Graph { }
	
	required interface Visitor  {
	  void initialize_vertex(Vertex u, Graph g);
	  void discover_vertex(Vertex u, Graph g);
	  void examine_edge(Edge e, Graph g);
	  void tree_edge(Edge e, Graph g);
	  void non_tree_edge(Edge e, Graph g);
	  void gray_target(Edge e, Graph g);
	  void black_target(Edge e, Graph g);
	  void finish_vertex(Vertex u, Graph g);
	}
}