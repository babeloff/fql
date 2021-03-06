package catdata;

import java.io.Serializable;

/**
 * @author Ryan Wisnesky
 */
@SuppressWarnings("serial")
public class Pair<T1, T2> implements Comparable<Pair<T1, T2>>, Serializable {

	public  T1 first; //TODO aql make these final.  Same for Triple
	public  T2 second;
	
	public void setFirst(T1 x) {
		first = x;
		hashCode2();
	}
	public void setSecond(T2 x) {
		second = x;
		hashCode2();
	}

	public Pair(T1 value, T2 value2) {
		first = value;
		second = value2;
		hashCode2();
	}
	private int hashCode;
	
	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
		
	}

	@Override
	public int hashCode() {
		return hashCode;
	}
	
	public void hashCode2() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		hashCode = result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair<?,?> other = (Pair<?,?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}
	
	public Pair<T2, T1> reverse() {
		return new Pair<>(second, first);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int compareTo(Pair<T1, T2> o) {
		Comparable x = (Comparable) o.first;
		Comparable y = (Comparable) first;
		int c = x.compareTo(y);
		if (c == 0) {
			Comparable a = (Comparable) o.second;
			Comparable b = (Comparable) second;
			return a.compareTo(b);
		} else {
			return c;
		}
	}
	
}
