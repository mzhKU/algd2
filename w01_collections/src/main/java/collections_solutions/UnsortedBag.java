package collections_solutions;

import ch.fhnw.algd2.arraycollections.AbstractArrayCollection;

import java.util.Arrays;
import java.util.Objects;

public class UnsortedBag<E> extends AbstractArrayCollection<E> {

    public static final int DEFAULT_CAPACITY = 100;

    private int size = 0;
    private Object[] data;

    public UnsortedBag() {
        this(DEFAULT_CAPACITY);
    }

    public UnsortedBag(int capacity) {
        data = new Object[capacity];
    }

    @Override
    public boolean add(E e) {
        Objects.requireNonNull(e);
        checkRemainingCapacity();
        addElement(e);
        return true;
    }

    private void addElement(E element) {
        data[size++] = element;
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
        if (index == -1) {
            return false;
        }
        data[index] = data[size-1];
        // remove last Element
        data[size - 1] = null;
        --size;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        Objects.requireNonNull(o);
        return indexOf(o) != -1;
    }

    /**
     * Looks for given object in the data
     *
     * @param o
     *            Object to look for
     * @return -1 if element was not found or the first index where the element
     *         was placed
     */
    private int indexOf(Object o) {
        int current = 0;
        while (current < size && !(data[current].equals(o))) {
            ++current;
        }
        return current == size ? -1 : current;
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
