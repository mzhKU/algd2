package ch.fhnw.algd2.graphedit;

import java.util.*;
import java.util.stream.Collectors;

public final class AdjListGraph<K> extends AbstractGraph<K> {

	private static class Vertex<K> {
		final K id;
		private boolean visited;
		List<Vertex<K>> adjList = new LinkedList<Vertex<K>>();

		Vertex(K id) { this.id = id; }

		boolean addEdgeTo(Vertex<K> to) {
			return (adjList.contains(to)) ? false : adjList.add(to);
		}
	}

	private Map<K, Vertex<K>> vertices;
	private int numEdges = 0;

	public AdjListGraph() { // default constructor
		this(false);
	}

	public AdjListGraph(boolean directed) {
		super(directed);
		vertices = new HashMap<K, Vertex<K>>();
	}

	// Copy constructor.
	public AdjListGraph(AdjListGraph<K> orig) {
		this();
		// this(orig.isDirected());
	    // this.numEdges = orig.numEdges;
	    // orig.vertices.keySet().forEach(this::addVertex);
	    // for (Vertex<K> v : orig.vertices.values()) {
	    // 	for (Vertex<K> w : v.adjList) {
	    // 		addEdge(v.id, w.id);
		// 	}
		// }
	}

	@Override
	public boolean addVertex(K id) {
		if (id != null && !vertices.containsKey(id)) {
			vertices.put(id, new Vertex(id));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addEdge(K from, K to) {
		Vertex<K> vf = vertices.get(from);
		Vertex<K> vt = vertices.get(to);
		if (vf != null && vt != null && !vf.adjList.contains(vt)) {

			vf.addEdgeTo(vt);
			// AUCH OK?
			// vf.adjList.add(vt);

			if (!isDirected()) {
				vt.addEdgeTo(vf);
			}

			numEdges++;
			System.out.println(this.numEdges);

			// Wieso doppelte Anzahl Vertices?
			System.out.println(vertices.keySet().stream()
				.mapToInt(id -> vertices.get(id).adjList.size())
				.sum());

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeEdge(K from, K to) {
		Vertex<K> vf = vertices.get(from);
		Vertex<K> vt = vertices.get(to);
		if (vf != null && vt != null && vf.adjList.contains(vt)) {
			vf.adjList.remove(vt);
			if(!isDirected()) {
				vt.adjList.remove(vf);
			}
		    numEdges--;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getNofVertices() { return vertices.size(); }

	@Override
	public int getNofEdges() {
		return this.numEdges;
	}

	@Override
	public Set<K> getVertices() {
		return new HashSet<K>(vertices.keySet());
	}

	@Override
	public Set<K> getAdjacentVertices(K id) {
	    Vertex<K> vertex = vertices.get(id);
	    if (vertex == null) { return Set.of(); }
		return vertex.adjList.stream().map(v -> v.id)
				.collect(Collectors.toSet());
	}

	@Override
	public Object clone() {
		return new AdjListGraph<K>(this);
	}
}
