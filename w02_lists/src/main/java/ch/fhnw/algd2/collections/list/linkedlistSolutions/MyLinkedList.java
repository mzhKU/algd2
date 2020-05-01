package ch.fhnw.algd2.collections.list.linkedlistSolutions;

import java.util.Arrays;
import java.util.Objects;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class MyLinkedList<E> extends MyAbstractList<E> {
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
        Node<E> newElement = new Node<>(item);
        if (isEmpty()) {
            last = newElement;
        } else {
            newElement.next = first;
        }
        first = newElement;
        ++size;
    }

    private void addTail(E item) {
        Node<E> newElement = new Node<>(item);
        if (isEmpty()) {
            first = newElement;
        } else {
            last.next = newElement;
        }
        last = newElement;
        ++size;
    }

    private void addElementAt(int index, E item) {
        Node<E> current = getNodeAt(index - 1);
        Node<E> toAdd = new Node<>(item);
        toAdd.next = current.next;
        current.next = toAdd;
        ++size;
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
        --size;
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
        --size;
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

    private static class Node<E> {
        private final E elem;
        private Node<E> next = null;

        private Node(E value) {
            this.elem = value;
        }

        private Node(E value, Node<E> next) {
            this.elem = value;
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
