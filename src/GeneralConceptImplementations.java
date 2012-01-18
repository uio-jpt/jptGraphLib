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

template GeneralConceptImplementations {
	

	public class project2nd<T> implements GeneralConceptsP.BinaryFunction<T> {
	  assumed project2nd();	
	  public T op(T a, T b) {
	    return b;
	  }
	}
	
	public class ColorValue {
	  private static final Integer white_const = new Integer(0);
	  private static final Integer gray_const = new Integer(1);
	  private static final Integer black_const = new Integer(2);
	  public static Integer white() {return white_const;}
	  public static Integer gray() {return gray_const;}
	  public static Integer black() {return black_const;}
	}
	

	
	public class queue<T> implements GeneralConceptsP.Buffer<T> {
	  private java.util.LinkedList<T> data;
	  
	  assumed queue();
	
	  public queue() {
	    data = new java.util.LinkedList<T>();
	  }

	  public void push(T v) {
	    data.addLast(v);
	  }

	  public T pop() {
	    return data.removeFirst();
	  }

	  public boolean empty() {
	    return data.isEmpty();
	  }
	}
	
	public class indirect_cmp<
	  Vertex,
	  Distance,
	  DistanceMap extends GeneralConceptsP.ReadWritePropertyMap<Vertex, Distance>,
	  DistanceCompare extends GeneralConceptsP.StrictWeakOrdering<Distance> > 
	    implements GeneralConceptsP.StrictWeakOrdering<Vertex> {
	  private DistanceMap m_distance;
	  private DistanceCompare m_compare;

	  public indirect_cmp(DistanceMap distance, DistanceCompare compare) {
	    m_distance = distance;
	    m_compare = compare;
	  }
	
	  assumed indirect_cmp(DistanceMap distance, DistanceCompare compare);

	  public boolean less(Vertex a, Vertex b) {
	    return m_compare.less(m_distance.get(a), m_distance.get(b));
	  }
	}
	
	
	public class hash_property_map<A,B> implements GeneralConceptsP.ReadWritePropertyMap<A,B> {
	  private java.util.HashMap<A,B> data;

	  public hash_property_map() {
	    data = new java.util.HashMap<A,B>();
	  }
	  assumed hash_property_map();

	  public void set(A a, B b) {
	    data.put(a,b);
	  }

	  public B get(A a) {
	    return data.get(a);
	  }
	}
	
	public class null_property_map<A,B> implements GeneralConceptsP.ReadWritePropertyMap<A,B> {
	  assumed null_property_map();
	  public void set(A a, B b) {}
	  public B get(A a) {return null;}
	}
}