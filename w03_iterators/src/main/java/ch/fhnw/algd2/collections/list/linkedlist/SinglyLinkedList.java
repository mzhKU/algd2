package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.*;
import ch.fhnw.algd2.collections.list.MyAbstractList;

public class SinglyLinkedList<E> extends MyAbstractList<E> {

	private int size = 0;

	private Node<E> first;
	private Node<E> last;

	@Override
	public boolean add(E item) {
		addTail(item);
		//generation++;
		return true;
	}

	@Override
	public void add(int index, E item) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Can not access index: "
					+ index);
		}
		if (index == 0) {
			// First position (O(1))
			addHead(item);
		} else if (index == size) {
			// Last position (O(1))
			addTail(item);
		} else {
			// In between (O(n))
			addElementAt(index, item);
		}
		//generation++;
	}

	private void addHead(E item) {
		Node<E> newElement = new Node<E>(item);
		if (isEmpty()) {
			last = newElement;
		} else {
			newElement.next = first;
		}
		first = newElement;
		updateAddInformation();
	}

	private void addTail(E item) {
		Node<E> newElement = new Node<E>(item);
		if (isEmpty()) {
			first = newElement;
		} else {
			last.next = newElement;
		}
		last = newElement;
		updateAddInformation();
	}

	private void addElementAt(int index, E item) {
		Node<E> current = getNodeAt(index - 1);
		Node<E> toAdd = new Node<E>(item);
		toAdd.next = current.next;
		current.next = toAdd;
		updateAddInformation();
	}

	@Override
	public boolean remove(Object o) {
		Node<E> current = first, before = null;
		while (current != null && !Objects.equals(o, current.elem)) {
			before = current;
			current = current.next;
		}

		if (current == null) {
			// Nothing to remove, reached end of list
			return false;
		}

		if (current == first) {
			removeFirstElement();
		} else {
			removeNextElement(before);
		}
		//generation++;
		return true;
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		}
		// Remove first element
		if (index == 0) {
			Node<E> toReturn = first;
			removeFirstElement();
			//generation++;
			return toReturn.elem;
		} else {
			Node<E> current = getNodeAt(index - 1);
			//generation++;
			return removeNextElement(current).elem;
		}
	}

	private Node<E> getNodeAt(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Out of bounds: " + index);
		}

		Node<E> current = first;
		for (int i = 1; i <= index; i++) {
			current = current.next;
		}
		return current;
	}

	private void removeFirstElement() {
		Node<E> headNext = first.next;
		if (last == first) {
			last = headNext;
		}
		first = headNext;
		updateRemoveInformation();
	}

	private Node<E> removeNextElement(Node<E> before) {
		if (before == null)
			throw new NullPointerException(
					"Can not remove element after null. Use removeFirstElement() for removing head element");
		Node<E> toRemove = before.next;
		Node<E> nextNext = before.next.next;

		// DONT FORGET: TAIL POINTER CHECK!
		if (last == toRemove) {
			last = before;
		}
		toRemove.next = null;
		before.next = nextNext;
		updateRemoveInformation();
		return toRemove;
	}

	@Override
	public boolean contains(Object o) {
		Node<E> current = first;
		while (current != null && !Objects.equals(o, current.elem)) {
			current = current.next;
		}
		return current != null;
	}


	@Override
	public E get(int index) {
		return getNodeAt(index).elem;
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
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	private void updateAddInformation() {
		++modCount;
		++size;
	}

	private void updateRemoveInformation() {
		++modCount;
		--size;
	}

	private static class Node<E> {
		private E elem;
		private Node<E> next = null;

		private Node(E value) {
			this.elem = value;
		}

		private Node(E value, Node<E> next) {
			this.elem = value;
			this.next = next;
		}
	}

	private class MyIterator implements Iterator<E> {

		private int     myGeneration = modCount;
		private boolean mayRemove    = false;
		private Node<E> nextNode = first;
		private Node<E> prev         = null;
		private Node<E> prevPrev     = null;

		@Override
		public boolean hasNext() {
		    return nextNode != null;
		}

		@Override
		public E next() {
			if (myGeneration != modCount) {
				throw new ConcurrentModificationException("List changed.");
			}
			if (nextNode == null) {
				throw new NoSuchElementException("Empty list.");
			}
			if (prev != null) {
				prevPrev = prev;
			}
			E e       = nextNode.elem;

			//  To prevent that prevPrev is referencing the just removed node
			if (mayRemove == true) {
				prevPrev = prev;
			}
			prev      = nextNode;
			nextNode = nextNode.next;
			mayRemove = true;
			return e;
		}

		@Override
		public void remove() {
			if (myGeneration != modCount) {
				throw new ConcurrentModificationException("List changed.");
			}
			if (!mayRemove) {
				throw new IllegalStateException("Cannot remove before nextNode() not called.");
			}

			// Case First && Only
			if (prevPrev == null && prev == first && nextNode == null) {
				prev = null;
				first = null;
				last = null;
			}
			// Case First && not only
			else if (prevPrev == null && prev == first && nextNode != null) {
				first = nextNode;
				prev = null;
			}
			// Case last.
			else if (prev == last) {
				prevPrev.next = null;
				last = prevPrev;
			} else {
				// Case innner
				prevPrev.next = nextNode;
			}
			mayRemove = false;
			size--;
			modCount++;
			myGeneration++;
		}
	}

	public static void main(String[] args) {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}

	/*
	Fragen:
	- Für CME: es ist nur erforderlich mit iterator.myGeneration mit modCount zu vergleichen?
	- nextNode: wieso previousPrevious = previous?
	- remove: wieso 'prev==null' erforderlich?
	 */
}
// Case last.
// if (nextNode == null) {
//     prev.next = null;
//     last = prev;
// } else {
//     // Not last.
// 	prev.next = nextNode.next;
// 	nextNode = nextNode.next;
// }

