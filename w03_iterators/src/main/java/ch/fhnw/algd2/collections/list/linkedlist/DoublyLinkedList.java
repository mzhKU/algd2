package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.*;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class DoublyLinkedList<E> extends MyAbstractList<E> {
	// variable int modCount already declared by AbstractList<E>
	private int size = 0;
	private Node<E> first, last;

	@Override
	public boolean add(E e) {
		Node<E> newNode = new Node<>(e);

		// Case "Empty list"
		if (size == 0) {
			first = newNode;
		} else {
			last.next = newNode;
			newNode.prev = last;
		}
		last = newNode;
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		Node<E> newNode = new Node(element);
		if (index > size() || index < 0) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}

		// Case empty list.
		if (size() == 0) {
			first = newNode;
			last  = newNode;
			size++;
			return;
		}

		// Case first && only.
		if (size() == 1) {
			if (index == 0) { // Insert as start.
				first.prev = newNode;
				newNode.next = first;
				first = newNode;
			}
			if (index == 1) { // Insert as last.
				first.next = newNode;
				newNode.prev = first;
				last = newNode;
			}
			size++;
			return;
		}

		// Case inner && not last.
		if (size() > 1 && (index <= size()-1)) {
			Node<E> nodeBeforeInsertedNewNode = getNodeAt(index-1);
			newNode.next                   = nodeBeforeInsertedNewNode.next;
			nodeBeforeInsertedNewNode.next = newNode;
			newNode.next.prev              = newNode;
			newNode.prev                   = nodeBeforeInsertedNewNode;
			size++;
			return;
		}

		// Case after last.
		if (index == size()) {
			last.next = newNode;
			newNode.prev = last;
			last = newNode;
			size++;
			return;
		}
	}

	private Node<E> getNodeAt(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Out of bounds: " + index);
		}
		Node<E> current = null;
		int halfSize = size() / 2;
		if (index < halfSize) {
			// Start from beginning
			current = first;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
		} else {
			// Start from end
			current = last;
			for (int i = size() - 1; i > index; i--) {
				current = current.prev;
			}
		}
		return current;
	}

	@Override
	public boolean remove(Object o) {
		Node<E> currentNode = first;

		// Case: Empty list.
		if (size() == 0) {
			return false;
		}

		// Case: One node.
		if (size() == 1) {
			first = null;
			last = null;
			size--;
			return true;
		}

		// Case: First node.
		if (currentNode.elem.equals(o) && currentNode == first) {
		    first.next.prev = null;
			first = first.next;
			size--;
			return true;
		}

		// Case: Inner nodes.
		while (currentNode.next != null) {
			if (currentNode.elem.equals(o)) {
				currentNode.prev.next = currentNode.next;
				currentNode.next.prev = currentNode.prev;
				size--;
				return true;
			}
			currentNode = currentNode.next;
		}

		// Case: Last node.
		if (currentNode.elem.equals(o) && currentNode == last) {
			last = currentNode.prev;
			currentNode.prev.next = null;
			size--;
			return true;
		}
		return false;
	}

	@Override
	public E remove(int index) {
	    // Cases: First, inner, last node.
		// List has 'first' and 'last' references.
		// Node has 'prev'  and 'next' references.
		Node<E> currentNode = first;
		int currentIndex = 0;
		if (size() == 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Empty list.");
		}

		// Remove first and not empty list.
		if (index == 0 && currentNode != null) {
			E e = currentNode.elem;
			if (first.next != null) {                      // First has follower.
				first = currentNode.next;
				currentNode.next.prev = null;
			} else {                                       // First is only node.
				first = null;
			}
			size--;
			return e;
		}

		// Remove inner.
		while (currentNode != null) {
			E e = currentNode.elem;
			if (index == currentIndex) {
				currentNode.prev.next = currentNode.next;
				currentNode.next.prev = currentNode.prev;
				size--;
				return e;
			}
			currentNode = currentNode.next;
			currentIndex++;
		}
		throw new IndexOutOfBoundsException("Invalid index.");
	}

	@Override
	public boolean contains(Object o) {
	    Node<E> currentNode = first;
	    while (currentNode != null) {
			if (currentNode.elem.equals(o)) {
				// Element was found.
				return true;
			}
			currentNode = currentNode.next;
		}
	    // Element was not found.
	    return false;
	}

	@Override
	public E get(int index) {
		Node<E> currentNode = first;
		if (index > (size()-1) || index < 0 || size() == 0) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		int cnt = 0;
		while (currentNode != null) {
			if (cnt == index) {
				return currentNode.elem;
			}
			currentNode = currentNode.next;
			cnt++;
		}
		throw new NoSuchElementException("Element for index not available.");
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new MyListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		// throw new UnsupportedOperationException("Implement later");
		return new MyListIterator();
	}

	private static class Node<E> {
		private E elem;
		private Node<E> prev, next;

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(Node<E> prev, E elem, Node<E> next) {
			this.prev = prev;
			this.elem = elem;
			this.next = next;
		}
	}

	private class MyListIterator implements ListIterator<E> {

		private Node<E> next = first;
		private Node<E> prev = null;
		private boolean mayRemove = false;
		private int generation = modCount;
		private DoublyLinkedList thisList;

		public MyListIterator() {
		}

		public MyListIterator(DoublyLinkedList l) {
			thisList = l;
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public E next() {
			if (next == null) {
				throw new NoSuchElementException("Empty list.");
			}
			E e = next.elem;
			next = next.next;
			mayRemove = true;
			return e;
		}

		@Override
		public boolean hasPrevious() {
			return prev != null;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) { throw new NoSuchElementException("At start."); }
			E elem = prev.elem;

			next = prev;
			prev = prev.prev;

			return elem;
		}

		@Override
		public int nextIndex() {
			return 0;
		}

		@Override
		public int previousIndex() {
			return 0;
		}

		@Override
		public void remove() {
			if (!mayRemove)             { throw new IllegalStateException("Must call next() before remove."); }
			if (modCount != generation) { throw new ConcurrentModificationException("Changed."); }

			System.out.println("This element: " + next.elem);

			next.prev.next = next.next;
			next.next.prev = next.prev;

			System.out.println(this.thisList);

			generation++;
			modCount++;
			size--;
		}

		@Override
		public void set(E e) {

		}

		@Override
		public void add(E e) {

		}
	}

	public static void main(String[] args) {
		DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}

	@Override
	public Object[] toArray() {
		return arrayForDoublyLinkedList();
		// return arrayForCyclicDoublyLinkedList();
	}
	private Object[] arrayForDoublyLinkedList() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first;
		while (current != null) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}
	private Object[] arrayForCyclicDoublyLinkedList() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first.next;
		while (current != first) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}
}
