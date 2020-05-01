package ch.fhnw.algd2.arraycollections;

import java.util.*;

public class SortedSet<E extends Comparable<? super E>> extends AbstractArrayCollection<E> implements Set<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private E[] data;
	private int size = 0;

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
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		Objects.requireNonNull(o);
		// int i = 0;
		// while (i < size && !(o.equals(data[i]))) {
		// 	i++;
		// }
		// return i == size ? false : true;
        return Arrays.binarySearch(data, 0, size, o) >= 0;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size());
	}

	@Override
	public int size() {
		return -1;
	}

	public static void main(String[] args) {
		SortedSet<Integer> bag = new SortedSet<Integer>();
		long start = System.currentTimeMillis();
		bag.add(2);
		System.out.println("Time [add]: " + (System.currentTimeMillis() - start));

		long start2 = System.currentTimeMillis();
		bag.contains(2);
		System.out.println("Time [contains]: " + (System.currentTimeMillis() - start));

		System.out.println(Arrays.toString(bag.toArray()));
	}
}
