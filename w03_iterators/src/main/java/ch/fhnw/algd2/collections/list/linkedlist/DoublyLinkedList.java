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
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		Node<E> currentNode = first;
		int currentIndex = 0;
		if (size() == 0) {
			throw new NoSuchElementException("Empty list.");
		}
		while (currentNode.next != null) {
			if (currentNode.elem.equals(o)) {
				remove(currentIndex);
			}
			currentNode = currentNode.next;
			currentIndex++;
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
		if (size() == 0) {
			throw new NoSuchElementException("Empty list.");
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

		// Remove last.
		if ((index == size()-1) && currentNode != null) {
			E e = currentNode.elem;
			last.prev.next = null;
			last = last.prev;
			size--;
			return e;
		}

		throw new NoSuchElementException("Node for index not found.");
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
		throw new UnsupportedOperationException("Implement later");
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

	private class MyListIterator implements Iterator<E> {

		private Node<E> next = first;
		private Node<E> prev = null;
		private boolean mayRemove = false;
		private int generation = modCount;

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
		public void remove() {
			if (!mayRemove) { throw new IllegalStateException("Must call next() before remove."); }
			if (modCount != generation) { throw new ConcurrentModificationException("Changed."); }

			Node<E> currentNode = first;

			// Remove first.


			// Remove inner.
			next.prev.next = next.next;
			next.next.prev = next.prev;
			mayRemove = false;
			generation++;
			modCount++;
			size--;
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
