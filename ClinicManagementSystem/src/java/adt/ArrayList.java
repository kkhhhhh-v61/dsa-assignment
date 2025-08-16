package adt;

/**
 * Resizable-array implementation of the ListInterface. Implements all optional
 * list operations, and permits all elements, including null.
 *
 * @param <T> the type of elements in this list
 */
public class ArrayList<T> implements ListInterface<T> {

    /**
     * The default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The number of elements in the list.
     */
    private int numberOfEntries;

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     */
    private T[] array;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.numberOfEntries = 0;
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is
     * negative
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.numberOfEntries = 0;
        this.array = (T[]) new Object[initialCapacity];
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param newEntry element to be appended to this list
     * @return true (as specified by Collection.add)
     */
    @Override
    public boolean add(T newEntry) {
        if (isArrayFull()) {
            doubleArray();
        }
        array[numberOfEntries] = newEntry;
        numberOfEntries++;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * @param newPosition index at which the specified element is to be
     * inserted
     * @param newEntry element to be inserted
     * @return true if the addition is successful, false if not
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0
     * || index > size())
     */
    @Override
    public boolean add(int newPosition, T newEntry) {
        if (newPosition < 0 || newPosition > numberOfEntries) {
            throw new IndexOutOfBoundsException("newPosition is out of bounds: " + newPosition);
        }
        if (isArrayFull()) {
            doubleArray();
        }
        // Shift elements to the right
        for (int i = numberOfEntries; i > newPosition; i--) {
            array[i] = array[i - 1];
        }
        array[newPosition] = newEntry;
        numberOfEntries++;
        return true;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if
     * it is present. If the list does not contain the element, it is
     * unchanged.
     *
     * @param anEntry element to be removed from this list, if present
     * @return true if this list contained the specified element
     */
    @Override
    public boolean remove(T anEntry) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i].equals(anEntry)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the element at the specified position in this list. Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     *
     * @param givenPosition the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0
     * || index >= size())
     */
    @Override
    public boolean remove(int givenPosition) {
        if (givenPosition < 0 || givenPosition >= numberOfEntries) {
            throw new IndexOutOfBoundsException("givenPosition is out of bounds: " + givenPosition);
        }
        // Shift elements to the left
        for (int i = givenPosition; i < numberOfEntries - 1; i++) {
            array[i] = array[i + 1];
        }
        array[numberOfEntries - 1] = null; // Clear the last element
        numberOfEntries--;
        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param givenPosition index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0
     * || index >= size())
     */
    @Override
    public T getEntry(int givenPosition) {
        // CORRECTED THIS LINE
        if (givenPosition < 0 || givenPosition >= numberOfEntries) {
            throw new IndexOutOfBoundsException("givenPosition is out of bounds: " + givenPosition);
        }
        return array[givenPosition];
    }

    /**
     * Returns true if this list contains the specified element.
     *
     * @param anEntry element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    @Override
    public boolean contains(T anEntry) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i].equals(anEntry)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param givenPosition index of the element to replace
     * @param newEntry element to be stored at the specified position
     * @return true if the replacement is successful, false otherwise
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0
     * || index >= size())
     */
    @Override
    public boolean replace(int givenPosition, T newEntry) {
        if (givenPosition < 0 || givenPosition >= numberOfEntries) {
            throw new IndexOutOfBoundsException("givenPosition is out of bounds: " + givenPosition);
        }
        array[givenPosition] = newEntry;
        return true;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return numberOfEntries;
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    /**
     * Removes all of the elements from this list. The list will be empty after
     * this call returns.
     */
    @Override
    public void clear() {
        for (int i = 0; i < numberOfEntries; i++) {
            array[i] = null;
        }
        numberOfEntries = 0;
    }

    /**
     * Returns true if the internal array is full.
     *
     * @return true if the array is full, false otherwise
     */
    private boolean isArrayFull() {
        return numberOfEntries == array.length;
    }

    /**
     * Doubles the size of the internal array. This method is called when the
     * array is full and a new element is added.
     */
    @SuppressWarnings("unchecked")
    private void doubleArray() {
        int newCapacity = array.length * 2;
        if (newCapacity < 0) { // Handle potential overflow
            newCapacity = Integer.MAX_VALUE;
        }
        T[] newArray = (T[]) new Object[newCapacity];
        // Copy elements to the new array
        for (int i = 0; i < numberOfEntries; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    /**
     * Returns a string representation of this list. The string representation
     * consists of a list of the list's elements in the order they are returned
     * by its iterator, enclosed in square brackets ("[]"). Adjacent elements
     * are separated by the characters ", " (comma and space).
     *
     * @return a string representation of this list
     */
    @Override
    public String toString() {
        if (numberOfEntries == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < numberOfEntries; i++) {
            sb.append(array[i]);
            if (i < numberOfEntries - 1) {
                sb.append(',').append(' ');
            }
        }
        return sb.append(']').toString();
    }
}
