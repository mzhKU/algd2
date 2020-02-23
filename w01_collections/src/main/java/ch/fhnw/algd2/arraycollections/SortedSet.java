package ch.fhnw.algd2.arraycollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SortedSet<E extends Comparable<? super E>> extends AbstractArrayCollection<E> implements Set<E> {
	public static final int DEFAULT_CAPACITY = 100;
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
		boolean containsE = this.contains(e);
		if (!containsE) {
			int nextFreeIndex = this.size();
			boolean hasSpace = nextFreeIndex < (this.data.length);
			if (hasSpace) {
				this.data[nextFreeIndex] = e;
				Arrays.sort(this.data, 0, nextFreeIndex+1);
				return true;
			} else {
				throw new IllegalStateException("Cannot add existing element.");
			}
		} else {
		    return false;
		}
	}

	@Override
	public boolean remove(Object o) {
		boolean isNotNull = o != null;
		if (isNotNull) {
			boolean containsO = this.contains(o);
			if (containsO) {
				int indexO = 0;
				int numberOfElements = this.size();
				int indexFound = 0;
				for (int i = 0; i < numberOfElements; i++) {
					if (this.data[i] == o) {
						this.data[i] = null;
						indexFound = i;
					}
				}
				for (int i = indexFound; i<numberOfElements; i++) {
				    if (i < numberOfElements-1) {
						this.data[i] = this.data[i + 1];
					} else {
				    	this.data[i] = null;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(Object o) {
	    for (int i = 0; i<this.data.length; i++) {
	    	if (o.equals(this.data[i])) {
	    		return true;
			}
		}
	    return false;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size());
	}

	@Override
	public int size() {
		int cnt = 0;
	    for(int i = 0; i < this.data.length; i++) {
	    	if(this.data[i] != null) {
	    		cnt += 1;
			}
		}
	    return cnt;
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
