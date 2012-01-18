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
