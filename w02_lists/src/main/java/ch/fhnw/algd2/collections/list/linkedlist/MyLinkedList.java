package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.Arrays;
import java.util.Objects;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class MyLinkedList<E> extends MyAbstractList<E> {
	private int size = 0;
	private Node<E> first;

	@Override
	public boolean add(E e) {

		// Add new node to start of list.
		Node newNode = new Node(e);
		newNode.next = this.first;
		this.first   = newNode;
		size++;

		// Node newNode = new Node(e);
		// if (this.first != null) {
		// 	Node currentNode = this.first;
		// 	while (currentNode.next != null) {
		// 		currentNode = currentNode.next;
		// 	}
		// 	currentNode.next = newNode;
		// } else {
		// 	this.first = newNode;
		// }
		// size++;

		return true;

        /*
		// Add new node to end of list.
	    if (this.first == null) {
			this.first = new Node(e);
			size++;
			return true;
		}
        Node currentNode = this.first;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }

        // Not within loop because size stores size from previous adding.
		size++;

        // Insert at end of list.
		Node newNode = new Node(e);
		currentNode.next = newNode;


		// Insert at start of list.
        Node newNode = new Node(e);
        Node oldFirst = this.first;
        this.first = newNode;
        this.first.next = oldFirst;
*/
	}

	@Override
	public boolean contains(Object o) {
		if (this.size == 0) {
			return false;
		}
		if (this.first.elem == o) {
			return true;
		}
		Node currentNode = this.first;
		while (currentNode != null) {
			if (currentNode.elem == o) {
				return true;
			}
			currentNode = currentNode.next;
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		// TODO implement this operation (part C)
		throw new UnsupportedOperationException();
	}

	@Override
	public E get(int index) {
		// TODO implement this operation (part D)
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element) {
		// TODO implement this operation (part D)
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		// TODO implement this operation (part D)
		throw new UnsupportedOperationException();
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
