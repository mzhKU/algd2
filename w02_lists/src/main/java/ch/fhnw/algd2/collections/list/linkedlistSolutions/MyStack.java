package ch.fhnw.algd2.collections.list.linkedlistSolutions;

import java.util.EmptyStackException;

import ch.fhnw.algd2.collections.list.linkedlist.MyLinkedList;
import ch.fhnw.algd2.collections.list.stack.IStack;

public class MyStack<E> implements IStack<E> {

	private MyLinkedList<E> list = new MyLinkedList<>();

	@Override
	public E pop() {
		if (empty()) {
			throw new EmptyStackException();
		}
		return list.remove(0);
	}

	@Override
	public E push(E item) {
		list.add(0, item);
		return item;
	}

	@Override
	public E peek() {
		if (empty()) {
			throw new EmptyStackException();
		}
		return list.get(0);
	}

	@Override
	public boolean empty() {
		return list.isEmpty();
	}

	public static void main(String[] args) {
		MyStack<Integer> stack = new MyStack<>();
		System.out.println("Pushing numbers to stack (0 to 9)");
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		System.out.println("Pop numbers from stack");
		while (!stack.empty()) {
			System.out.println(stack.pop());
		}
	}

}
