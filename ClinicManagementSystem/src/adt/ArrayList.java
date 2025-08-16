package adt;

/**
 * Resizable-array implementation of the ListInterface. Implements all optional
 * list operations, and permits all elements, including null.
 * 
 * @param <T> the type of elements in this list
 */
public class ArrayList<T> implements ListInterface<T> {
  /** The default initial capacity. */
  private static final int DEFAULT_CAPACITY = 10;
  
  /** The number of elements in the list. */
  private int numberOfEntries;
  
  /** The array buffer into which the elements of the ArrayList are stored. */
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
   * @throws IllegalArgumentException if the specified initial capacity is negative
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
   * @return true (as specified by {@link Collection#add})
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements
   */
  @Override
  public boolean add(T newEntry) {
    if (newEntry == null) {
      throw new NullPointerException("Cannot add null element to the list");
    }
    if (isArrayFull()) {
      doubleArray();
    }
    array[numberOfEntries++] = newEntry;
    return true;
  }

  /**
   * Inserts the specified element at the specified position in this list.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * @param newPosition index at which the specified element is to be inserted
   * @param newEntry element to be inserted
   * @return true if the addition is successful
   * @throws IndexOutOfBoundsException if the index is out of range
   *         ({@code index < 1 || index > size() + 1})
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements
   */
  @Override
  public boolean add(int newPosition, T newEntry) {
    if (newEntry == null) {
      throw new NullPointerException("Cannot add null element to the list");
    }
    if (newPosition < 1 || newPosition > numberOfEntries + 1) {
      throw new IndexOutOfBoundsException("newPosition is out of bounds: " + newPosition);
    }
    if (isArrayFull()) {
      doubleArray();
    }
    // Shift elements to make space for the new element
    for (int i = numberOfEntries; i > newPosition - 1; i--) {
      array[i] = array[i - 1];
    }
    array[newPosition - 1] = newEntry;
    numberOfEntries++;
    return true;
  }

  /**
   * Removes the first occurrence of the specified element from this list,
   * if it is present. If the list does not contain the element, it is
   * unchanged.
   *
   * @param anEntry element to be removed from this list, if present
   * @return true if this list contained the specified element
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements
   */
  @Override
  public boolean remove(T anEntry) {
    if (anEntry == null) {
      throw new NullPointerException("Cannot remove null entry");
    }
    // Find the index of the element to remove
    int index = 0;
    while (index < numberOfEntries && !anEntry.equals(array[index])) {
      index++;
    }
    if (index == numberOfEntries) {
      return false; // Element not found
    }
    // Shift elements to fill the gap
    for (int i = index; i < numberOfEntries - 1; i++) {
      array[i] = array[i + 1];
    }
    array[numberOfEntries - 1] = null; // Clear to let GC do its work
    numberOfEntries--;
    return true;
  }

  /**
   * Removes the element at the specified position in this list.
   * Shifts any subsequent elements to the left (subtracts one from their
   * indices).
   *
   * @param givenPosition the index of the element to be removed
   * @return true if the element was removed successfully
   * @throws IndexOutOfBoundsException if the index is out of range
   *         ({@code index < 1 || index > size()})
   */
  @Override
  public boolean remove(int givenPosition) {
    if (givenPosition < 1 || givenPosition > numberOfEntries) {
      throw new IndexOutOfBoundsException("givenPosition is out of bounds: " + givenPosition);
    }
    // Shift elements to fill the gap
    for (int i = givenPosition - 1; i < numberOfEntries - 1; i++) {
      array[i] = array[i + 1];
    }
    array[--numberOfEntries] = null; // Clear to let GC do its work
    return true;
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param givenPosition index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range
   *         ({@code index < 1 || index > size()})
   */
  @Override
  public T getEntry(int givenPosition) {
    if (givenPosition < 1 || givenPosition > numberOfEntries) {
      throw new IndexOutOfBoundsException("givenPosition is out of bounds: " + givenPosition);
    }
    return array[givenPosition - 1];
  }

  /**
   * Returns true if this list contains the specified element.
   * More formally, returns true if and only if this list contains
   * at least one element e such that
   * (o==null ? e==null : o.equals(e)).
   *
   * @param anEntry element whose presence in this list is to be tested
   * @return true if this list contains the specified element
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements
   */
  @Override
  public boolean contains(T anEntry) {
    if (anEntry == null) {
      throw new NullPointerException("Cannot check for null element");
    }
    for (int i = 0; i < numberOfEntries; i++) {
      if (anEntry.equals(array[i])) {
        return true;
      }
    }
    return false;
  }

  /**
   * Replaces the element at the specified position in this list with
   * the specified element.
   *
   * @param givenPosition index of the element to replace
   * @param newEntry element to be stored at the specified position
   * @return true if the replacement is successful
   * @throws IndexOutOfBoundsException if the index is out of range
   *         ({@code index < 1 || index > size()})
   * @throws NullPointerException if the specified element is null and this
   *         list does not permit null elements
   */
  @Override
  public boolean replace(int givenPosition, T newEntry) {
    if (newEntry == null) {
      throw new NullPointerException("Cannot set null element");
    }
    if (givenPosition < 1 || givenPosition > numberOfEntries) {
      throw new IndexOutOfBoundsException("givenPosition is out of bounds: " + givenPosition);
    }
    array[givenPosition - 1] = newEntry;
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
 * Removes all of the elements from this list.
 * The list will be empty after this call returns.
 */
@Override
public void clear() {
    // Clear all elements to help garbage collection
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
 * Doubles the size of the internal array.
 * This method is called when the array is full and a new element is added.
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
    
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < numberOfEntries; i++) {
      sb.append(array[i]);
      if (i < numberOfEntries - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
