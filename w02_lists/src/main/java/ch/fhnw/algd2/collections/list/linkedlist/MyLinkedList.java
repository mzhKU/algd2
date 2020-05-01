package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.Arrays;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class MyLinkedList<E> extends MyAbstractList<E> {
	private int size = 0;
	private Node<E> first;
	private Node<E> last;

	@Override
	public boolean add(E e) {
		// Add new node to end of list.
		Node<E> newNode = new Node<E>(e);

		if (first != null) {
			last.next  = newNode;
			last       = newNode;
		} else {
			first = newNode;
			last  = newNode;
		}
		size++;
		return true;
	}

	@Override
	public boolean contains(Object o) {
	    // Sequential search.
		Node currentNode = this.first;
		while (currentNode != null) {
			if (o != null && o.equals(currentNode.elem)) {
				return true;
			} else {
				currentNode = currentNode.next;
			}
		}
		return false;
	}


	@Override
	public boolean remove(Object o) {
		// Invariant: previousNode.next == currentNode
		Node currentNode  = this.first;
		Node previousNode = null;

		while (currentNode != null) {
			if (currentNode.elem.equals(o)) {
				// It is not the first element.
				if (previousNode != null) {
					previousNode.next = currentNode.next;
				} else {
					// It is the first element.
					first = currentNode.next;
				}
				// It is the last element.
				if (currentNode.next == null) {
					last = previousNode;
				}
				size--;
				return true;
			} else {
				// Advance the search.
				previousNode = currentNode;
				currentNode  = currentNode.next;
			}
		}
		return false;
	}


	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *         ({@code index < 0 || index >= size()})
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		int i = 0;
		Node n = this.first;
		while (i < index) {
			n = n.next;
			i++;
		}
		return (E) n.elem;
	}

	/**
	 * Inserts the specified element at the specified position in this list
	 * (optional operation).  Shifts the element currently at that position
	 * (if any) and any subsequent elements to the right (adds one to their
	 * indices).
	 *
	 * @param index index at which the specified element is to be inserted
	 * @param element element to be inserted
	 * @throws UnsupportedOperationException if the {@code add} operation
	 *         is not supported by this list
	 * @throws ClassCastException if the class of the specified element
	 *         prevents it from being added to this list
	 * @throws NullPointerException if the specified element is null and
	 *         this list does not permit null elements
	 * @throws IllegalArgumentException if some property of the specified
	 *         element prevents it from being added to this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *         ({@code index < 0 || index > size()})
	 */
	@Override
	public void add(int index, E element) {
		// Insert Element 'element' at position 'index'.
		if (index < 0) {
			throw new IndexOutOfBoundsException("Illegal index.");
		}
		if (index > size) {
			throw new IndexOutOfBoundsException("Illegal index.");
		}

		if (index == size) {
			add(element);
		} else if (index == 0) {
			first = new Node(element, first);
			size++;
		} else {

			Node n = first;

			for (int i = 0; i < index; i++) {
				n = n.next;
			}

			n.next = new Node(element);

			size++;
		}
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Negative index.");
		}
		if (index >= size) {
			throw new IllegalArgumentException("Illegal index.");
		}
		E removedElement;
		if (index > 0) {
			Node<E> n = first;
			for (int i = 0; i < index-1; i++) {
				n = n.next;
			}
			removedElement = n.next.elem;
			n.next = n.next.next;
			if (n.next == null) {
				last = n;
			}
		} else {
			removedElement = first.elem;
			first = first.next;
			if (first == null) {
				last = null;
			}
		}
		size--;
		return removedElement;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first;
		while (current != null) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public int size() {
		return size;
	}

	private static class Node<E> {
		private final E elem;
		private Node<E> next;

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(E elem, Node<E> next) {
			this.elem = elem;
			this.next = next;
		}
	}

	public static void main(String[] args) {
		MyLinkedList<Integer> list = new MyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}
}
