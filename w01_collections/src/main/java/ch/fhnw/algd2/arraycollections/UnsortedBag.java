package ch.fhnw.algd2.arraycollections;

import java.util.Arrays;

public class UnsortedBag<E> extends AbstractArrayCollection<E> {
	public static final int DEFAULT_CAPACITY = 100;
	private E[] data;

	public UnsortedBag() {
		this(DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public UnsortedBag(int capacity) {
		data = (E[])new Object[capacity];
	}

	@Override
	public boolean add(E e) {
		int lastIndex = size()-1;

		// 'null' cannot be added.
		if(e == null) {
			throw new NullPointerException("Adding null.");
		}

		if (lastIndex+1 == this.data.length) {
			throw new IllegalStateException("Cannot add to full collection.");
		} else {
			this.data[lastIndex+1] = e;
		}
        return true;
	}

	@Override
	public boolean remove(Object o) {
		int position = 0;
		if (this.contains(o)) {
			int numberOfElements = size();
	        for(int i = 0; i < numberOfElements; i++) {
	        	E tmp = this.data[i];
	            if (tmp == o) {
	            	position = i;
		    	}
		    }
	        for(int i = position; i<numberOfElements; i++) {
	        	// If the last element is removed, manually set the
				// position that became available to 'null'.
	        	if (i == numberOfElements-1) {
	        		this.data[i] = null;
				} else {
					this.data[i] = this.data[i + 1];
				}
		    }
	        return true;
		}
		return false;
	}

	// TODO:
	// - Why must the comparison be done with 'equals'?
	@Override
	public boolean contains(Object o) {
	    int numberOfElements = this.size();
	    for (int i = 0; i < numberOfElements; i++) {
			if (this.data[i].getClass() != o.getClass()) {
				return false;
			}
			if (this.data[i].equals(o)) {
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
