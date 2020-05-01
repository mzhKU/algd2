package defaultSolutions;


import java.util.*;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class DoublyLinkedListSolutionMichi<E> extends MyAbstractList<E> {

	private int size = 0;

	private Node<E> first;
	private Node<E> last;

	@Override
	public boolean add(E item) {
		addTail(item);
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
	}

	private void addHead(E item) {
		Node<E> newElement = new Node<E>(item);
		if (isEmpty()) {
			last = newElement;
		} else {
			newElement.next = first;
			first.prev = newElement;
		}
		first = newElement;
		updateAddInformation();
	}

	private void addTail(E item) {
		Node<E> newElement = new Node<>(item);
		if (isEmpty()) {
			first = newElement;
		} else {
			last.next = newElement;
			newElement.prev = last;
		}
		last = newElement;
		updateAddInformation();
	}

	private void addElementAt(int index, E item) {
		Node<E> current = getNodeAt(index - 1);
		Node<E> toAdd = new Node<E>(item);
		toAdd.next = current.next;
		toAdd.next.prev = toAdd;
		current.next = toAdd;
		toAdd.prev = current;
		updateAddInformation();
	}

	@Override
	public boolean remove(Object o) {
		// Removed Schleppzeiger
		Node<E> current = first;
		while (current != null && !Objects.equals(o, current.elem)) {
			current = current.next;
		}
		if (current == null) {
			return false;
		}
		// Tail pointer check
		if (current == first) {
			removeFirstElement();
		} else {
			removeNextNode(current.prev);
		}
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
			return toReturn.elem;
		} else {
			Node<E> current = getNodeAt(index - 1);
			return removeNextNode(current).elem;
		}
	}

	private void removeFirstElement() {
		Node<E> headNext = first.next;
		if (last == first) {
			last = headNext;
		} else {
			first.next.prev = null;
		}
		first.next = null;
		first.prev = null;
		first = headNext;
		updateRemoveInformation();
	}

	private Node<E> removeNextNode(Node<E> before) {
		Node<E> toRemove = before.next;
		// DONT FORGET: TAIL POINTER CHECK!
		if (toRemove == last) {
			last = before;
			last.next = null;
		} else {
			toRemove.next.prev = toRemove.prev;
			toRemove.prev.next = toRemove.next;
		}
		toRemove.next = null;
		toRemove.prev = null;
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
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Out of bounds: " + index);
		}
		return getNodeAt(index).elem;
	}

	@Override
	public int size() {
		return size;
	}

	private void addNodeAfter(Node<E> node, E item) {
		Node<E> toAdd = new Node<E>(item);
		if (node == last) {
			last = toAdd;
		} else {
			node.next.prev = toAdd;
		}
		toAdd.next = node.next;
		toAdd.prev = node;
		node.next = toAdd;
		updateAddInformation();
	}

	@Override
	public Object[] toArray() {
		return arrayForDoublyLinkedList();
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

	@Override
	public Iterator<E> iterator() {
		return listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new MyListIterator(getNodeAt(index));
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
	public ListIterator<E> listIterator() {
		return new MyListIterator();
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
		private Node<E> next;
		private Node<E> prev;

		private Node(E value) {
			this.elem = value;
		}

		private Node(Node<E> prev, E value, Node<E> next) {
			this.prev = prev;
			this.elem = value;
			this.next = next;
		}
	}

	private class MyListIterator implements ListIterator<E> {

		private int itModCount;
		private Node<E> next = first;
		private Node<E> lastReturned = null;
		private int nextIndex = 0;

		public MyListIterator() {
			this.itModCount = modCount;
		}

		public MyListIterator(Node<E> node) {
			this.itModCount = modCount;
			next = node;
		}

		@Override
		public boolean hasNext() {
			return nextIndex < size();
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			checkModification();
			lastReturned = next;
			next = next.next;
			++nextIndex;
			return lastReturned.elem;
		}

		@Override
		public boolean hasPrevious() {
			return nextIndex > 0;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			checkModification();
			if (next == null) {
				lastReturned = last;
			} else {
				lastReturned = next.prev;
			}
			next = lastReturned;
			--nextIndex;
			return lastReturned.elem;
		}

		@Override
		public int nextIndex() {
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			return nextIndex - 1;
		}

		@Override
		public void remove() {
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			checkModification();

			// check if last call was previous()
			// Because the removed element is placed after the current
			// next-pointer (nextIndex position) this pointer stays.
			if (lastReturned == next) {
				next = next.next;
			} else {
				--nextIndex;
			}

			// check if first or last removed and adapt head / tail pointer
			if (lastReturned == first) {
				removeFirstElement();
			}
			else {
				removeNextNode(lastReturned.prev);
			}
			lastReturned.next = null;
			lastReturned.prev = null;
			lastReturned = null;
			++itModCount;
		}

		private void checkModification() {
			if (itModCount != modCount) {
				throw new ConcurrentModificationException();
			}
		}

		@Override
		public void set(E e) {
			checkModification();
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			lastReturned.elem = e;
		}

		@Override
		public void add(E e) {
			checkModification();
			// Check first
			if (next == first) {
				addHead(e);
			} else if (lastReturned == last) {
				addTail(e);
			} else {
				addNodeAfter(next.prev, e);
			}
			++itModCount;
			++nextIndex;
		}

	}
}
