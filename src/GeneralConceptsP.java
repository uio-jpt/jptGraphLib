/* Generelle konsepter for buffer, kø etc. */

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