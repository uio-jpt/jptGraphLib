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

template GraphConceptImplementations {

	inst GraphConcepts with Edge <= adj_list_edge;
		/*EdgeIterator <= java.util.Iterator<adj_list_edge>, 
		OutEdgeIterator <= java.util.Iterator<adj_list_edge>,
		VertexIterator <= java.util.Iterator<Vertex>;*/
	
	class adj_list_edge {
	  private Vertex source_, target_;

	  assumed adj_list_edge(Vertex s, Vertex t);
	  public adj_list_edge(Vertex s, Vertex t) {
	    source_ = s;
	    target_ = t;
	  }

	  public Vertex source() {return source_;}
	  public Vertex target() {return target_;}

	  public java.lang.String toString() {
	    return "edge(" + source_ + " -> " + target_ + ")";
	  }

	  public int hashCode() {
	    return source_.hashCode() + target_.hashCode();
	  }

	  public boolean equals(java.lang.Object o) {
	    adj_list_edge oo = (adj_list_edge)(o);
	    if (oo == null) return false;
	    return (source_.equals(oo.source()) && target_.equals(oo.target()));
	  }
	}

	class adjacency_list 
	  implements VertexListAndIncidenceAndEdgeListGraph {
	
	  public adjacency_list() {
	    vertices_ = new java.util.ArrayList<Vertex>();
	    edges_ = new java.util.HashMap<Vertex, java.util.ArrayList<adj_list_edge> >();
	    all_edges_ = new java.util.ArrayList<adj_list_edge>();
	  }

	  private java.util.ArrayList<Vertex> vertices_;
	  public java.util.Iterator<Vertex> vertices() {
	    return vertices_.iterator();
	  }
	  public int num_vertices() {
	    return vertices_.size();
	  }

	  private java.util.HashMap<Vertex, java.util.ArrayList<adj_list_edge> > edges_;
	  private java.util.ArrayList<adj_list_edge> all_edges_;

	  public java.util.Iterator<adj_list_edge> out_edges(Vertex v) {
	    return edges_.get(v).iterator();
	  }
	  public int out_degree(Vertex v) {
	    return edges_.get(v).size();
	  }

	  public void add_vertex(Vertex v) {
	    vertices_.add(v);
	    edges_.put(v, new java.util.ArrayList<adj_list_edge>());
	  }

	  public void add_edge(Vertex u, Vertex v) {
	    adj_list_edge edge = new adj_list_edge(u,v);
	    edges_.get(u).add(edge);
	    all_edges_.add(edge);
	  }

	  public java.util.Iterator<adj_list_edge> edges() {
	    return all_edges_.iterator();
	  }
	}	
}