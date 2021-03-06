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