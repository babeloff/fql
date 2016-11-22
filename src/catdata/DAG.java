package catdata;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/********************************************************************************
* Copyright (c) 2006 IBM Corporation and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     IBM Corporation - initial API and implementation
*******************************************************************************/

public final class DAG<N> {
	/**
	 * Multimap, supports <code>null key, but not null values.
	 */
	private static final class MultiMap<K,V> {
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((fMap == null) ? 0 : fMap.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MultiMap<?,?> other = (MultiMap<?,?>) obj;
			if (fMap == null) {
				if (other.fMap != null)
					return false;
			} else if (!fMap.equals(other.fMap))
				return false;
			return true;
		}

		private final Map<K, Set<V>> fMap= new LinkedHashMap<>();
		
		public MultiMap() {
			
		}
		
		public MultiMap(MultiMap<K,V> x) {
			for (K k : x.keySet()) {
				fMap.put(k, new HashSet<>(x.get(k)));
			}
		}

		/**
		 * Adds <code>val to the values mapped to by key. If
		 * <code>val is null, key is added to the key set of
		 * the multimap.
		 * 
		 * @param key the key
		 * @param val the value
		 */
		public void put(K key, V val) {
			Set<V> values = fMap.get(key);
			if (values == null) {
				values = new LinkedHashSet<>();
				fMap.put(key, values);
			}
			if (val != null) {
				values.add(val);
			}
		}

		/**
		 * Returns all mappings for the given key, an empty set if there are no mappings.
		 * 
		 * @param key the key
		 * @return the mappings for <code>key
		 */
		public Set<V> get(K key) {
			Set<V> values = fMap.get(key);
			return values == null ? Collections.emptySet() : values;
		}

		public Set<K> keySet() {
			return fMap.keySet();
		}

		/**
		 * Removes all mappings for <code>key and removes key from the key
		 * set.
		 * 
		 * @param key the key to remove
		 * @return the removed mappings
		 */
		public Set<V> removeAll(K key) {
			Set<V> values = fMap.remove(key);
			return values == null ? Collections.emptySet() : values;
		}

		/**
		 * Removes a mapping from the multimap, but does not remove the <code>key from the
		 * key set.
		 * 
		 * @param key the key
		 * @param val the value
		 */
		public void remove(K key, V val) {
			Set<V> values= fMap.get(key);
			if (values != null) {
				values.remove(val);
			}
		}
		
		public String toString() {
			return fMap.toString();
		}
	}

	public final MultiMap<N,N> fOut, fIn; 
	
	/**
	 * Adds a directed edge from <code>origin to target. The vertices are not
	 * required to exist prior to this call - if they are not currently contained by the graph, they are
	 * automatically added.
	 * 
	 * @param origin the origin vertex of the dependency
	 * @param target the target vertex of the dependency
	 * @return <code>true if the edge was added, false if the
	 *         edge was not added because it would have violated the acyclic nature of the
	 *         receiver.
	 */
	public boolean addEdge(N origin, N target) {
		if (origin == null || target == null) {
			throw new RuntimeException("Anomaly: please report");
		}

		if (hasPath(target, origin)) {
			return false;
		}

		fOut.put(origin, target);
		fOut.put(target, null);
		fIn.put(target, origin);
		fIn.put(origin, null);
		return true;
	}

	/**
	 * Adds a vertex to the graph. If the vertex does not exist prior to this call, it is added with
	 * no incoming or outgoing edges. Nothing happens if the vertex already exists.
	 * 
	 * @param vertex the new vertex
	 */
	public void addVertex(N vertex) {
		if (vertex == null) {
			throw new RuntimeException("Anomaly: please report");
		}
		fOut.put(vertex, null);
		fIn.put(vertex, null);
	}

	/**
	 * Removes a vertex and all its edges from the graph.
	 *
	 * @param vertex the vertex to remove
	 */
	public void removeVertex(N vertex) {
		Set<N> targets = fOut.removeAll(vertex);
		for (Iterator<N> it = targets.iterator(); it.hasNext();) {
			fIn.remove(it.next(), vertex);
		}
		Set<N> origins = fIn.removeAll(vertex);
		for (Iterator<N> it = origins.iterator(); it.hasNext();) {
			fOut.remove(it.next(), vertex);
		}
	}

	/**
	 * Returns the sources of the receiver. A source is a vertex with no incoming edges. The
	 * returned set's iterator traverses the nodes in the order they were added to the graph.
	 * 
	 * @return the sources of the receiver
	 */
	public Set<N> getSources() {
		return computeZeroEdgeVertices(fIn);
	}

	/**
	 * Returns the sinks of the receiver. A sink is a vertex with no outgoing edges. The returned
	 * set's iterator traverses the nodes in the order they were added to the graph.
	 * 
	 * @return the sinks of the receiver
	 */
	public Set<N> getSinks() {
		return computeZeroEdgeVertices(fOut);
	}

	private Set<N> computeZeroEdgeVertices(MultiMap<N,N> map) {
		Set<N> candidates = map.keySet();
		Set<N> roots = new LinkedHashSet<>(candidates.size());
		for (Iterator<N> it = candidates.iterator(); it.hasNext();) {
			N candidate= it.next();
			if (map.get(candidate).isEmpty()) {
				roots.add(candidate);
			}
		}
		return roots;
	}

	/**
	 * Returns the direct children of a vertex. The returned {@link Set} is unmodifiable.
	 * 
	 * @param vertex the parent vertex
	 * @return the direct children of <code>vertex
	 */
	public Set<N> getChildren(N vertex) {
		return Collections.unmodifiableSet(fOut.get(vertex));
	}

	public boolean hasPath(N start, N end) {
		if (start.equals(end)) {
			return true;
		}

		Set<N> children = fOut.get(start);
		for (Iterator<N> it= children.iterator(); it.hasNext();) {
			if (hasPath(it.next(), end)){
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "Out edges: " + fOut.toString(); // + " In edges: " + fIn.toString(); 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fIn == null) ? 0 : fIn.hashCode());
		result = prime * result + ((fOut == null) ? 0 : fOut.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		DAG<N> other = (DAG<N>) obj;
		if (!other.vertices().equals(vertices())) {
			return false;
		}
		for (N n : vertices()) {
			for (N m : vertices()) {
				if (other.hasPath(n, m) && !hasPath(n, m)) {
					return false;
				}
				if (!other.hasPath(n, m) && hasPath(n, m)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Set<N> vertices() {
		return fIn.keySet();
	}
	
	public DAG() {
		fOut= new MultiMap<N,N>();
		fIn= new MultiMap<N,N>();
	}
	
	public DAG(DAG<N> g) {
		fIn = new MultiMap<>(g.fIn);
		fOut = new MultiMap<>(g.fOut);
	}

	public List<N> topologicalSort() {
		DAG<N> g = new DAG<>(this);
		List<N> ret = new LinkedList<>();
		
		while (!g.vertices().isEmpty()) {
			N n = Util.get0X(g.getSources());
			g.removeVertex(n);
			ret.add(n);
		} 
		return ret;
	}

	
	
}