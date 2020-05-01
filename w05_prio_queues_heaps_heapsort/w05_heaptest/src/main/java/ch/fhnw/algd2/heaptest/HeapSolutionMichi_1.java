package ch.fhnw.algd2.heaptest;

/* Heap als Implementierung einer Priority Queue */
//class Heap<K> implements PriorityQueue<K> {
//	private HeapNode<K>[] heap; // Array to store the heap elements
//	private int size; // Position where inserted LAST (0 means no elements)
//
//	/**
//	 * Construct the binary heap.
//	 *
//	 * @param capacity
//	 *            how many items the heap can store
//	 */
//	Heap(int capacity) {
//		// We need one more extra space
//		heap = new HeapNode[capacity + 1];
//	}
//
//	/**
//	 * Returns the number of elements in this priority queue.
//	 *
//	 * @return the number of elements in this priority queue.
//	 */
//	@Override
//	public int size() {
//		return size;
//	}
//
//	/**
//	 * Check whether the heap is empty.
//	 *
//	 * @return true if there are no items in the heap.
//	 */
//	@Override
//	public boolean isEmpty() {
//		return size == 0;
//	}
//
//	/**
//	 * Check whether the heap is full.
//	 *
//	 * @return true if no further elements can be inserted into the heap.
//	 */
//	@Override
//	public boolean isFull() {
//		return size + 1 == heap.length;
//	}
//
//	/**
//	 * Make the heap (logically) empty.
//	 */
//	@Override
//	public void clear() {
//		heap = new HeapNode[heap.length];
//		size = 0;
//	}
//
//	/**
//	 * Add to the priority queue, maintaining heap order. Duplicates and null
//	 * values are allowed. Small values of the argument priority means high
//	 * priority, Large values means low priority.
//	 *
//	 * @param element
//	 *            the item to insert
//	 * @param priority
//	 *            the priority to be assigned to the item element
//	 * @exception QueueFullException
//	 *                if the heap is full
//	 */
//	@Override
//	public void add(K element, long priority) throws QueueFullException {
//		if (isFull()) {
//			throw new QueueFullException();
//		}
//		++size;
//		heap[size] = new HeapNode<>(element, priority);
//		siftUp(size);
//	}
//
//	/**
//	 * Removes and returns the item with highest priority (smallest priority
//	 * value) from the queue, maintaining heap order.
//	 *
//	 * @return the item with highest priority (smallest priority value)
//	 * @throws QueueEmptyException
//	 *             if the queue is empty
//	 */
//	@Override
//	public K removeMin() throws QueueEmptyException {
//		if (isEmpty())
//			throw new QueueEmptyException();
//		// First element
//		K res = heap[1].element;
//		heap[1] = heap[size];
//		heap[size] = null;
//		--size;
//		siftDown(1);
//		return res;
//	}
//
//	/**
//	 * Internal method to let an element sift down in the heap.
//	 *
//	 * @param current
//	 *            the index at which the percolate begins
//	 */
//	private void siftDown(int current) {
//		int child = indexOfSmallerChild(current);
//		while (child <= size && heap[current].priority > heap[child].priority) {
//			swapElements(current, child);
//			current = child;
//			child = indexOfSmallerChild(child);
//		}
//	}
//
//	private void swapElements(int a, int b) {
//		HeapNode<K> n = heap[a];
//		heap[a] = heap[b];
//		heap[b] = n;
//	}
//
//	private int indexOfSmallerChild(int current) {
//		int child = 2 * current;
//		if (child + 1 <= size
//				&& heap[child].priority > heap[child + 1].priority) {
//			child++;
//		}
//		return child;
//	}
//
//	/**
//	 * Internal method to let an element sift up in the heap.
//	 *
//	 * @param current
//	 *            the index at which the percolate begins
//	 */
//	private void siftUp(int current) {
//		int parent = parentIndex(current);
//		// Solange current nicht auf die Wurzel referenziert. UND
//		// Solange die Priority vom Parent groesser ist als vom current wird vertauscht
//		while (current > 1 && heap[current].priority < heap[parent].priority) {
//			swapElements(current, parent);
//			current = parent;
//			parent = parentIndex(parent);
//		}
//	}
//
//	private int parentIndex(int current) {
//		return current / 2;
//	}
//
//	/**
//	 * Erzeugt ein neues long[] Array und kopiert die Werte der Prioritäten aus
//	 * dem Heaparray dort hinein. Die Grösse des zurückgegebenen Arrays soll der
//	 * Anzahl Elemente in der Queue entsprechen (= size()). An der Position 0
//	 * soll die kleinste Priorität (= Priorität des Wurzelelementes) stehen.
//	 *
//	 * @return Array mit allen Prioritäten
//	 */
//	@Override
//	public long[] toLongArray() {
//		// Start from index 1
//		long[] l = new long[size];
//		for (int i = 0; i < size; i++)
//			l[i] = heap[i+1].priority;
//		return l;
//	}
//
//	private static class HeapNode<K> {
//		private final K element;
//		private final long priority;
//
//		HeapNode(K element, long priority) {
//			this.element = element;
//			this.priority = priority;
//		}
//	}
//}
