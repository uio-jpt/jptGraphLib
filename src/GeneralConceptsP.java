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

package GeneralConceptsP {

	public interface Buffer<Value> {
	  void push(Value v);
	  Value pop();
	  boolean empty();
	}

	public interface ReadablePropertyMap<Key,Value> {
	  Value get(Key k);
	}

	public interface ReadWritePropertyMap<Key, Value> extends ReadablePropertyMap<Key,Value> {
	  void set(Key k, Value v);
	}

	public interface MutableQueue<Vertex> extends Buffer<Vertex> {
	  void update(Vertex v);
	}

	public interface StrictWeakOrdering<T> {
	  boolean less(T a, T b);
	}

	public interface UnaryFunction<T> {
	  T op(T a);
	}

	public interface BinaryFunction<T> {
	  T op(T a, T b);
	}
}