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

package Program {
	
	inst Algorithms	with 
	 	Vertex <= Integer, 
		Edge <= adj_list_edge;		
	
	inst GraphConceptImplementations;
					
	public class bfs_test {
	  public static void main(String[] args) {
	    adjacency_list g = new adjacency_list();
	    g.add_vertex(new Integer(3));
	    g.add_vertex(new Integer(4));
	    g.add_vertex(new Integer(5));
	    g.add_vertex(new Integer(6));
	    g.add_edge(new Integer(3), new Integer(6));
	    g.add_edge(new Integer(3), new Integer(5));
	    g.add_edge(new Integer(4), new Integer(5));
	    g.add_edge(new Integer(6), new Integer(4));
	    g.add_edge(new Integer(4), new Integer(3));

	    hash_property_map<Integer, Integer> color =
	      new hash_property_map<Integer, Integer>();

	    breadth_first_search.breadth_first_search(
	      g, 
	      new Integer(3), 
	      new printing_visitor(),
	      color);
	  }
	}
	
	public class dijkstra_test {
	  public static void main(String[] args) {
	    adjacency_list g = new adjacency_list();

	    hash_property_map<Integer,Integer> pred_map = new hash_property_map<Integer,Integer>();

	    hash_property_map<Integer,Double> distance_map = new hash_property_map<Integer,Double>();

	    hash_property_map<adj_list_edge,Double> weight_map = new hash_property_map<adj_list_edge,Double>();

	    g.add_vertex(new Integer(3));
	    distance_map.set(new Integer(3), new Double(0));
	    g.add_vertex(new Integer(4));
	    distance_map.set(new Integer(4), new Double(Double.POSITIVE_INFINITY));
	    g.add_vertex(new Integer(5));
	    distance_map.set(new Integer(5), new Double(Double.POSITIVE_INFINITY));
	    g.add_vertex(new Integer(6));
	    distance_map.set(new Integer(6), new Double(Double.POSITIVE_INFINITY));
	    g.add_edge(new Integer(3), new Integer(6));
	    weight_map.set(new adj_list_edge(new Integer(3), new Integer(6)), new Double(5));
	    g.add_edge(new Integer(3), new Integer(5));
	    weight_map.set(new adj_list_edge(new Integer(3), new Integer(5)), new Double(8));
	    g.add_edge(new Integer(4), new Integer(5));
	    weight_map.set(new adj_list_edge(new Integer(4), new Integer(5)), new Double(1));
	    g.add_edge(new Integer(6), new Integer(4));
	    weight_map.set(new adj_list_edge(new Integer(6), new Integer(4)), new Double(2));
	    g.add_edge(new Integer(4), new Integer(3));
	    weight_map.set(new adj_list_edge(new Integer(4), new Integer(3)), new Double(3));

	    dijkstra_shortest_paths.dijkstra_shortest_paths(
	      g, 
	      new Integer(3), 
	      pred_map,
	      distance_map,
	      weight_map,
	      new GeneralConceptsP.StrictWeakOrdering<Double>() {public boolean less(Double a, Double b) {return a.doubleValue()<b.doubleValue();}},
	      new GeneralConceptsP.BinaryFunction<Double>() {public Double op(Double a, Double b) {return new Double(a.doubleValue()+b.doubleValue());}},
	      new Double(Double.POSITIVE_INFINITY),
	      new Double(0.));

	    for (int i = 3; i < 7; ++i)
	      System.out.println("Dist(" + i + ")=" + distance_map.get(new Integer(i)) + ", Pred(" + i + ")=" + pred_map.get(new Integer(i)));
	  }
	}
	
	public class johnson_test {
	  public static void main(String[] args) {
	    adjacency_list g = new adjacency_list();

	    hash_property_map<Integer,Integer> pred_map = new hash_property_map<Integer,Integer>();

	    hash_property_map<Integer,Double> distance_map = new hash_property_map<Integer,Double>();

	    hash_property_map<adj_list_edge,Double> weight_map = new hash_property_map<adj_list_edge,Double>();
	    hash_property_map<adj_list_edge,Double> new_weight_map = new hash_property_map<adj_list_edge,Double>();

	    hash_property_map<Integer, hash_property_map<Integer, Double> > distance_matrix = new hash_property_map<Integer, hash_property_map<Integer, Double> >();

	    g.add_vertex(new Integer(3));
	    distance_matrix.set(new Integer(3), new hash_property_map<Integer,Double>());
	    distance_map.set(new Integer(3), new Double(0));
	    g.add_vertex(new Integer(4));
	    distance_matrix.set(new Integer(4), new hash_property_map<Integer,Double>());
	    distance_map.set(new Integer(4), new Double(Double.POSITIVE_INFINITY));
	    g.add_vertex(new Integer(5));
	    distance_matrix.set(new Integer(5), new hash_property_map<Integer,Double>());
	    distance_map.set(new Integer(5), new Double(Double.POSITIVE_INFINITY));
	    g.add_vertex(new Integer(6));
	    distance_matrix.set(new Integer(6), new hash_property_map<Integer,Double>());
	    distance_map.set(new Integer(6), new Double(Double.POSITIVE_INFINITY));

	    g.add_edge(new Integer(3), new Integer(6));
	    weight_map.set(new adj_list_edge(new Integer(3), new Integer(6)), new Double(5));
	    g.add_edge(new Integer(3), new Integer(5));
	    weight_map.set(new adj_list_edge(new Integer(3), new Integer(5)), new Double(8));
	    g.add_edge(new Integer(4), new Integer(5));
	    weight_map.set(new adj_list_edge(new Integer(4), new Integer(5)), new Double(-1));
	    g.add_edge(new Integer(6), new Integer(4));
	    weight_map.set(new adj_list_edge(new Integer(6), new Integer(4)), new Double(-2));
	    g.add_edge(new Integer(4), new Integer(3));
	    weight_map.set(new adj_list_edge(new Integer(4), new Integer(3)), new Double(3));

	    johnson_all_pairs_shortest_paths.johnson_all_pairs_shortest_paths(
	      g, 
	      weight_map,
	      new_weight_map,
	      distance_map,
	      distance_matrix,
	      new Double(0),
	      new Double(Double.POSITIVE_INFINITY),
	      new GeneralConceptsP.BinaryFunction<Double>() {public Double op(Double a, Double b) {return new Double((a.doubleValue() + b.doubleValue()));}},
	      new GeneralConceptsP.UnaryFunction<Double>() {public Double op(Double a) {return new Double((-a.doubleValue()));}},
	      new GeneralConceptsP.StrictWeakOrdering<Double>() {public boolean less(Double a, Double b) {return a.doubleValue()<b.doubleValue();}});

	    for (int i = 3; i < 7; ++i) {
	      System.out.print("" + i + ": ");
	      for (int j = 3; j < 7; ++j) {
		System.out.print("" + distance_matrix.get(new Integer(i)).get(new Integer(j)) + " ");
	      }
	      System.out.println("");
	    }

	    //System.out.println((VertexListGraph<Integer,Iterator<Integer>,Integer>)(g));
	  }
	}
	
	public class prim_test {
	  public static void main(String[] args) {
	    adjacency_list g = new adjacency_list();

	    hash_property_map<Integer,Integer> pred_map = new hash_property_map<Integer,Integer>();

	    hash_property_map<Integer,Double> distance_map = new hash_property_map<Integer,Double>();

	    hash_property_map<adj_list_edge,Double> weight_map = new hash_property_map<adj_list_edge,Double>();

	    g.add_vertex(new Integer(3));
	    distance_map.set(new Integer(3), new Double(0));
	    g.add_vertex(new Integer(4));
	    distance_map.set(new Integer(4), new Double(Double.POSITIVE_INFINITY));
	    g.add_vertex(new Integer(5));
	    distance_map.set(new Integer(5), new Double(Double.POSITIVE_INFINITY));
	    g.add_vertex(new Integer(6));
	    distance_map.set(new Integer(6), new Double(Double.POSITIVE_INFINITY));
	    g.add_edge(new Integer(3), new Integer(6));
	    weight_map.set(new adj_list_edge(new Integer(3), new Integer(6)), new Double(1));
	    g.add_edge(new Integer(6), new Integer(3));
	    weight_map.set(new adj_list_edge(new Integer(6), new Integer(3)), new Double(1));
	    g.add_edge(new Integer(3), new Integer(5));
	    weight_map.set(new adj_list_edge(new Integer(3), new Integer(5)), new Double(8));
	    g.add_edge(new Integer(5), new Integer(3));
	    weight_map.set(new adj_list_edge(new Integer(5), new Integer(3)), new Double(8));
	    g.add_edge(new Integer(4), new Integer(5));
	    weight_map.set(new adj_list_edge(new Integer(4), new Integer(5)), new Double(1));
	    g.add_edge(new Integer(5), new Integer(4));
	    weight_map.set(new adj_list_edge(new Integer(5), new Integer(4)), new Double(1));
	    g.add_edge(new Integer(6), new Integer(4));
	    weight_map.set(new adj_list_edge(new Integer(6), new Integer(4)), new Double(2));
	    g.add_edge(new Integer(4), new Integer(6));
	    weight_map.set(new adj_list_edge(new Integer(4), new Integer(6)), new Double(2));
	    g.add_edge(new Integer(4), new Integer(3));
	    weight_map.set(new adj_list_edge(new Integer(4), new Integer(3)), new Double(3));
	    g.add_edge(new Integer(3), new Integer(4));
	    weight_map.set(new adj_list_edge(new Integer(3), new Integer(4)), new Double(3));

	    prim_minimum_spanning_tree.prim_minimum_spanning_tree(
	      g, 
	      new Integer(3), 
	      pred_map,
	      distance_map,
	      weight_map,
	      new GeneralConceptsP.StrictWeakOrdering<Double>() {public boolean less(Double a, Double b) {return a.doubleValue()<b.doubleValue();}},
	      new Double(Double.POSITIVE_INFINITY),
	      new Double(0.));

	    for (int i = 3; i < 7; ++i)
	      System.out.println("Pred(" + i + ") = " + pred_map.get(new Integer(i)));
	  }
	}
	
	public class bellman_ford_test {
	  public static void main(String[] args) {
	    adjacency_list g = new adjacency_list();

	    hash_property_map<Integer,Integer> pred_map = new hash_property_map<Integer,Integer>();

	    hash_property_map<Integer,Double> distance_map = new hash_property_map<Integer,Double>();

	    hash_property_map<adj_list_edge,Double> weight_map = new hash_property_map<adj_list_edge,Double>();

	    g.add_vertex(new Integer(3));
	    distance_map.set(new Integer(3), new Double(0));
	    g.add_vertex(new Integer(4));
	    distance_map.set(new Integer(4), new Double(Double.POSITIVE_INFINITY));
	    g.add_vertex(new Integer(5));
	    distance_map.set(new Integer(5), new Double(Double.POSITIVE_INFINITY));
	    g.add_vertex(new Integer(6));
	    distance_map.set(new Integer(6), new Double(Double.POSITIVE_INFINITY));
	    g.add_edge(new Integer(3), new Integer(6));
	    weight_map.set(new adj_list_edge(new Integer(3), new Integer(6)), new Double(5));
	    g.add_edge(new Integer(3), new Integer(5));
	    weight_map.set(new adj_list_edge(new Integer(3), new Integer(5)), new Double(8));
	    g.add_edge(new Integer(4), new Integer(5));
	    weight_map.set(new adj_list_edge(new Integer(4), new Integer(5)), new Double(-1));
	    g.add_edge(new Integer(6), new Integer(4));
	    weight_map.set(new adj_list_edge(new Integer(6), new Integer(4)), new Double(-2));
	    g.add_edge(new Integer(4), new Integer(3));
	    weight_map.set(new adj_list_edge(new Integer(4), new Integer(3)), new Double(3));

	    bellman_ford_shortest_paths.bellman_ford_shortest_paths(
	      g, 
	      4,
	      weight_map,
	      pred_map,
	      distance_map,
	      new GeneralConceptsP.BinaryFunction<Double>() {public Double op(Double a, Double b) {return new Double((a.doubleValue() + b.doubleValue()));}},
	      new GeneralConceptsP.StrictWeakOrdering<Double>() {public boolean less(Double a, Double b) {return a.doubleValue()<b.doubleValue();}});

	    for (int i = 3; i < 7; ++i)
	      System.out.println("Dist(" + i + ")=" + distance_map.get(new Integer(i)) + ", Pred(" + i + ")=" + pred_map.get(new Integer(i)));

	  }
	}
}