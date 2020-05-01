package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;
import java.util.Objects;

public class UnsortedBag<E> extends AbstractArrayCollection<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private E[] data;
	private int size = 0;

	public UnsortedBag() {
		this(DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public UnsortedBag(int capacity) {
		data = (E[])new Object[capacity];
	}

	private void checkRemainingCapacity() {
		if (size == data.length) {
			throw new IllegalStateException("Collection full");
		}
	}

	@Override
	public boolean add(E e) {
		Objects.requireNonNull(e);
		checkRemainingCapacity();
		addElement(e);
		return true;
	}

	private void addElement(E e) {
	    data[size] = e;
	    size++;
	}


	@Override
	public boolean remove(Object o) {
	    Objects.requireNonNull(o);
	    int index = indexOf(o);
	    if (index == -1) {
			return false;
		}
	    // Unsorted bag: just move the element
		// at the end to the place that gets emptied.
	    data[index] = data[size-1];
	    data[size-1] = null;
	    size--;
	    return true;
	}

	private int indexOf(Object o) {
		// We know that no null elements are present from specification.
		int i = 0;
		while (i < size && !o.equals(data[i])) {
			i++;
		}
		return i == size ? -1 : i;
	}

	// TODO:
	// - Why must the comparison be done with 'equals'?
	//   --> Because we care about if the elements have the same value
	//       not if it is the same object.
	@Override
	public boolean contains(Object o) {
		Objects.requireNonNull(o);
	    int i = 0;
	    while (i < size && !(o.equals(data[i]))) {
	    	i++;
		}
	    return i == size ? false : true;
	}

	@Override
	public Object[] toArray() {
		// O(n)
		return Arrays.copyOf(data, size());

		// return data would auch funktionieren mit O(1).
	}

	@Override
	public int size() {
		int size = 0;
	    for (int i = 0; i<this.data.length; i++) {
	    	if (this.data[i] != null) {
	    		size++;
			}
		}
	    return size;
	}

	public static void main(String[] args) {
		UnsortedBag<Integer> bag = new UnsortedBag<Integer>();
		bag.add(2);
		bag.add(1);
		System.out.println(Arrays.toString(bag.toArray()));
	}
}
