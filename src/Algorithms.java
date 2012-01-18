/* Copyright 2012 © The SWAT project, OMS Group, Department of Informatics, 
   University of Oslo.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   -----------------------------------------------------------------------
   
   This product includes a modified version of software developed 
   by the Open Systems Laboratory at Indiana University.  For 
   further information contact Andrew Lumsdaine at lums@osl.iu.edu.

   Copyright 2003 © The Trustees of Indiana University.  All rights
   reserved.

   See LICENSE_Indiana_University.txt for the full license for the
   original code.
*/

template Algorithms {
	
	inst GeneralConceptImplementations;
	
	inst AuxiliaryConceptImplementations with Graph => IncidenceGraph;
	inst GraphConcepts;


	public class breadth_first_search {
	  public static <
	    ColorMap extends GeneralConceptsP.ReadWritePropertyMap<Vertex,java.lang.Integer > >
	  void breadth_first_search(VertexListAndIncidenceGraph g, Vertex s, Visitor vis, ColorMap color) {
	    queue<Vertex> Q = new queue<Vertex>();
	    for(VertexIterator u_iter = g.vertices(); u_iter.hasNext(); ) {
	      Vertex u = u_iter.next();
		      vis.initialize_vertex(u, g);
	      color.set(u, ColorValue.white());
	    }
	    graph_search.graph_search(g,s,vis,color,Q);
	  }
	}
	
	public class graph_search {
	  public static <
    	ColorMap extends  GeneralConceptsP.ReadWritePropertyMap<Vertex,java.lang.Integer>,
	    QueueType extends  GeneralConceptsP.Buffer<Vertex> >
	  void graph_search(VertexListAndIncidenceGraph g, Vertex s, Visitor vis, ColorMap color, QueueType Q) {
	    vis.discover_vertex(s, g);
	    color.set(s, ColorValue.gray());
	    Q.push(s);
	    while (!Q.empty()) {
	      Vertex u =  Q.pop();
	      vis.discover_vertex(u, g);
	      for(OutEdgeIterator e_iter = g.out_edges(u); e_iter.hasNext(); ) {
		Edge e = e_iter.next();
		Vertex v = e.target();
		vis.examine_edge(e, g);
		if (color.get(v).equals(ColorValue.white())) {
		  vis.tree_edge(e, g);
		  color.set(v, ColorValue.gray());
		  Q.push(v);
		} else {
		  vis.non_tree_edge(e, g);
		  if (color.get(v).equals(ColorValue.gray())) {
		    vis.gray_target(e, g);
		  } else {
		    vis.black_target(e, g);
		  }
		}
	      }
	      vis.finish_vertex(u, g);
	      color.set(u, ColorValue.black());
	    }
	  }
	}

	public class prim_minimum_spanning_tree {
	  public static <
	    WeightMap extends  GeneralConceptsP.ReadablePropertyMap<Edge,Distance>,
	    DistanceMap extends  GeneralConceptsP.ReadWritePropertyMap<Vertex,Distance>,
	    Distance,
	    DistanceCompare extends  GeneralConceptsP.StrictWeakOrdering<Distance>,
	    PredecessorMap extends  GeneralConceptsP.ReadWritePropertyMap<Vertex,Vertex> >

	  void prim_minimum_spanning_tree(VertexListAndIncidenceGraph g, Vertex s, PredecessorMap predecessor, DistanceMap distance, WeightMap weight, DistanceCompare compare, Distance inf, Distance zero) {
	    project2nd<Distance> combine = new project2nd<Distance>();
	    dijkstra_shortest_paths.dijkstra_shortest_paths(g,s,predecessor,distance,weight,compare,combine,inf,zero);
	  }
	}

	public class johnson_all_pairs_shortest_paths {
	  public static
	    <Distance,
	     WeightMap extends  GeneralConceptsP.ReadablePropertyMap,
	     WeightMap2 extends  GeneralConceptsP.ReadWritePropertyMap,
	     DistanceMap extends  GeneralConceptsP.ReadWritePropertyMap,
	     DistanceMatrixElement extends  GeneralConceptsP.ReadWritePropertyMap,
	     DistanceMatrix extends  GeneralConceptsP.ReadablePropertyMap,
	     DistanceCombine extends  GeneralConceptsP.BinaryFunction,
	     DistanceCompare extends  GeneralConceptsP.StrictWeakOrdering,
	     DistanceNegate extends  GeneralConceptsP.UnaryFunction>
	  boolean johnson_all_pairs_shortest_paths(VertexListAndIncidenceAndEdgeListGraph g, WeightMap w, WeightMap2 w2, DistanceMap distance, final DistanceMatrix distance_matrix, Distance zero, Distance inf, DistanceCombine combine, DistanceNegate negate, DistanceCompare compare) {
	    // Set distance to all vertices to zero (emulates special "s" vertex used in CLR)
	    for (VertexIterator vi = g.vertices(); vi.hasNext();) {
	      Vertex v = vi.next();
	      distance.set(v, zero);
	    }

	    // Do Bellman-Ford to get factors for reweighting
	    boolean bf_worked = bellman_ford_shortest_paths.bellman_ford_shortest_paths(
	      g, 
	      g.num_vertices(),
	      w, 
	      new null_property_map<Vertex,Vertex>(), 
	      distance, 
	      combine,
	      compare);

	    if (!bf_worked) return false; // Negative-weight cycle

	    // Set up reweighting
	    for (EdgeIterator ei = g.edges(); ei.hasNext(); ) {
	      Edge e = ei.next();
	      w2.set(e, combine.op(combine.op(w.get(e), distance.get(e.source())), negate.op(distance.get(e.target()))));
	    }

	    // Do Dijkstra from each vertex
	    for (VertexIterator vi = g.vertices(); vi.hasNext(); ) {
	      final Vertex v = vi.next();
	      final  GeneralConceptsP.ReadWritePropertyMap<Vertex,Distance> dm = ( GeneralConceptsP.ReadWritePropertyMap)distance_matrix.get(v);
	      dijkstra_shortest_paths.dijkstra_shortest_paths(
		g, 
		v,
		new null_property_map<Vertex,Vertex>(),
		dm,
		w2,
		compare,
		combine,
		inf,
		zero);

	      // Adjust returned distances to account for reweighting
	      for (VertexIterator vi2 = g.vertices(); vi2.hasNext(); ) {
		Vertex v2 = vi2.next();
		dm.set(v2, (Distance)combine.op(combine.op(dm.get(v2), distance.get(v2)), negate.op(distance.get(v))));
	      }
	    }
	    return true;
	  }
	}

	public class dijkstra_shortest_paths {
	  public static <
	    WeightMap extends  GeneralConceptsP.ReadablePropertyMap<Edge,Distance>,
	    DistanceMap extends  GeneralConceptsP.ReadWritePropertyMap<Vertex,Distance>,
	    Distance,
	    PredecessorMap extends  GeneralConceptsP.ReadWritePropertyMap<Vertex,Vertex>,
	    DistanceCombine extends  GeneralConceptsP.BinaryFunction<Distance>,
	    DistanceCompare extends  GeneralConceptsP.StrictWeakOrdering<Distance> >
	  void dijkstra_shortest_paths(VertexListAndIncidenceGraph g, Vertex s, PredecessorMap predecessor, DistanceMap distance, WeightMap weight, DistanceCompare compare, DistanceCombine combine, Distance inf, Distance zero) {
	    for(VertexIterator u_iter = g.vertices(); u_iter.hasNext(); ) {
	      Vertex u = u_iter.next();
	      distance.set(u, inf);
	      predecessor.set(u, u);
	    }
	    distance.set(s, zero);

	    indirect_cmp<Vertex, Distance, DistanceMap, DistanceCompare> icmp 
	      = new indirect_cmp<Vertex, Distance, DistanceMap, DistanceCompare>(distance, compare);
	    mutable_queue<indirect_cmp<Vertex, Distance, DistanceMap, DistanceCompare> > Q 
	      = new mutable_queue<indirect_cmp<Vertex, Distance, DistanceMap, DistanceCompare> >(icmp);

//		Visitor vis 	      = new dijkstra_visitor<mutable_queue<indirect_cmp<Vertex, Distance, DistanceMap, DistanceCompare> >, WeightMap, PredecessorMap, DistanceMap, DistanceCombine, DistanceCompare, Distance>(Q, weight, predecessor, distance, combine, compare, zero);
	    dijkstra_visitor<mutable_queue<indirect_cmp<Vertex, Distance, DistanceMap, DistanceCompare> >, WeightMap, PredecessorMap, DistanceMap, DistanceCombine, DistanceCompare, Distance> vis 
	      = new dijkstra_visitor<mutable_queue<indirect_cmp<Vertex, Distance, DistanceMap, DistanceCompare> >, WeightMap, PredecessorMap, DistanceMap, DistanceCombine, DistanceCompare, Distance>(Q, weight, predecessor, distance, combine, compare, zero);

	    hash_property_map<Vertex, java.lang.Integer> color
	      = new hash_property_map<Vertex, java.lang.Integer>();

	    // Initialize color map
	    for (VertexIterator i = g.vertices(); i.hasNext();) {
	      Vertex v = i.next();
	      color.set(v, ColorValue.white());
	    }

	    graph_search.graph_search(
	      g,
	      s,
	      vis,
	      color,
	      Q);
	  }
	}

	public class bellman_ford_shortest_paths {
	  public static
	    <Distance,
	     WeightMap extends  GeneralConceptsP.ReadablePropertyMap,
	     PredecessorMap extends  GeneralConceptsP.ReadWritePropertyMap,
	     DistanceMap extends  GeneralConceptsP.ReadWritePropertyMap,
	     DistanceCombine extends  GeneralConceptsP.BinaryFunction,
	     DistanceCompare extends  GeneralConceptsP.StrictWeakOrdering>
	  boolean bellman_ford_shortest_paths(EdgeListGraph g, int size, WeightMap w, PredecessorMap predecessor, DistanceMap distance, DistanceCombine combine, DistanceCompare compare) {
	    boolean any_relaxed;
	    for (int i = 0; i < size; ++i) {
	      any_relaxed = false; // Optimization from BGL
	      for (EdgeIterator e_iter = g.edges(); e_iter.hasNext();) {
		Edge e = e_iter.next();
		if(relax.relax(e, w, distance, predecessor, combine, compare)) {
		  any_relaxed = true;
		}
	      }
	      if (!any_relaxed) break;
	    }
	    for (EdgeIterator e_iter = g.edges(); e_iter.hasNext();) {
	      Edge e = e_iter.next();
	      if (compare.less(combine.op(w.get(e), distance.get(e.source())), distance.get(e.target()))) {
		return false;
	      }
	    }
	    return true;
	  }
	}
}

