package mylist;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    transient Object[] array;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.array = new Object[initialCapacity];
            size = initialCapacity;
        } else if (initialCapacity == 0) {
            this.array = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    public MyArrayList() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public Iterator<T> iterator() {
        return new MyListIterator<>(this);
    }

    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new RuntimeException("Method not supported yet");
    }

    public boolean add(T t) {
        add(size, t);
        return true;
    }

    public boolean remove(Object o) {
        throw new RuntimeException("Method not supported yet");
    }

    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException("Method not supported yet");
    }

    public boolean addAll(Collection<? extends T> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
            return false;
        Object[] elementData;
        final int s;
        if (numNew > (elementData = this.array).length - (s = size))
            grow(s + numNew);
        System.arraycopy(a, 0, elementData, s, numNew);
        size = s + numNew;
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new RuntimeException("Method not supported yet");
    }

    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("Method not supported yet");
    }

    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("Method not supported yet");
    }

    public void clear() {
        throw new RuntimeException("Method not supported yet");
    }

    public T get(int index) {
        return (T) array[index];
    }

    public T set(int index, T element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("You have passed an illegal argument");
        }
        T oldValue = (T) array[index];
        array[index] = element;
        return oldValue;
    }

    public void add(int index, T element) {
        rangeCheckForAdd(index);
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.array).length)
            grow();
        System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        elementData[index] = element;
        size = s + 1;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Invalid index " + index);
    }

    public T remove(int index) {
        throw new RuntimeException("Method not supported yet");
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (array[i] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(array[i]))
                    return i;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (array[i] == null)
                    return i;
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(array[i]))
                    return i;
        }
        return -1;
    }

    private void grow(int minCapacity) {
        int oldCapacity = array.length;
        //like JDK
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        //skip max_size case
        array = Arrays.copyOf(array, newCapacity);
    }

    private void grow() {
        grow(size + 1);
    }

    public ListIterator<T> listIterator() {
        return new MyListIterator<>(this);
    }

    public ListIterator<T> listIterator(int index) {
        return new MyListIterator<>(this, index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("Method not supported yet");
    }

    private class MyListIterator<T> implements ListIterator<T> {
        private int pos;
        private MyArrayList<T> list;

        public MyListIterator(MyArrayList<T> list) {
            this.list = list;
            this.pos = 0;
        }

        public MyListIterator(MyArrayList<T> list, int index) {
            if (index <= 0 || index >= list.size()) {
                throw new IllegalArgumentException("Invalid index passed");
            }

            this.list = list;
            this.pos = index;
        }

        public boolean hasNext() {
            return pos != list.size();
        }

        public T next() {
            if (pos >= list.size())
                throw new NoSuchElementException();

            return list.get(pos++);
        }

        public boolean hasPrevious() {
            return pos >= 1;
        }

        public T previous() {
            if (pos == 0)
                throw new NoSuchElementException();

            return list.get(pos--);
        }

        public void set(T t) {
            list.set(pos - 1, t);
        }

        public int nextIndex() {
            throw new RuntimeException("Method not supported yet");
        }

        public int previousIndex() {
            throw new RuntimeException("Method not supported yet");
        }

        public void remove() {
            throw new RuntimeException("Method not supported yet");
        }

        public void add(T t) {
            throw new RuntimeException("Method not supported yet");
        }
    }
}