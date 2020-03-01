package collections_solutions;

import ch.fhnw.algd2.arraycollections.AbstractArrayCollection;

import java.util.Arrays;
import java.util.Objects;

public class SortedSet<E extends Comparable<? super E>> extends AbstractArrayCollection<E> {

	public static final int DEFAULT_CAPACITY = 100;

	private int size = 0;
	private E[] data;

	public SortedSet() {
		this(DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public SortedSet(int capacity) {
		data = (E[])new Comparable[capacity];
	}

	@Override
	public boolean add(E e) {
		Objects.requireNonNull(e);
		int index = indexOf(e);
		if (index >= 0) {
			return false;
		} else {
			checkRemainingCapacity();
			addElement(e, -index -1);
			return true;
		}
	}

	private void addElement(E element, int index) {
		moveElementsRight(index);
		data[index] = element;
		++size;
	}

	private void checkRemainingCapacity() {
		if (size == data.length) {
			throw new IllegalStateException(
					"Can not add more elements than capacity");
		}
	}

	@Override
	public boolean remove(Object o) {
		Objects.requireNonNull(o);
		int index = indexOf(o);
		if (index < 0) {
			return false;
		}
		moveElementsLeft(index);
		// remove last Element
		data[size - 1] = null;
		--size;
		return true;
	}

	private void moveElementsLeft(int index) {
		for (int i = index; i < size - 1; i++) {
			data[i] = data[i + 1];
		}
	}

	private void moveElementsRight(int index) {
		for (int i = size; i > index; i--) {
			data[i] = data[i - 1];
		}
	}

	@Override
	public boolean contains(Object o) {
		Objects.requireNonNull(o);
		return indexOf(o) >= 0;
	}

	/**
	 * Looks for given object in the data
	 *
	 * @param o
	 *            Object to look for
	 * @return position of element (>= 0), otherwise (-(insertion point) - 1).
	 */
	public int indexOf(Object o) {
		return Arrays.binarySearch(data, 0, size, o);
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size);
	}

	@Override
	public int size() {
		return size;
	}

}
