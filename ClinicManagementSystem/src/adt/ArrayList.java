package adt;

import java.io.Serializable;

/**
 *
 * @author Tan Kai Hao
 */
public class ArrayList<T> implements ListInterface<T>, Serializable {
  
  private static final int DEFAULT_CAPACITY = 10;
  private int numberOfEntries;
  private T[] array;

  @SuppressWarnings("unchecked")
  public ArrayList() {
    this.numberOfEntries = 0;
    this.array = (T[]) new Object[DEFAULT_CAPACITY];
  }

  @SuppressWarnings("unchecked")
  public ArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
      this.numberOfEntries = 0;
      this.array = (T[]) new Object[initialCapacity];
    } else {
      throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
    }
  }

  @Override
  public boolean add(T newEntry) {
    if (newEntry != null) {
      if (isArrayFull()) {
        doubleArray();
      }
      array[numberOfEntries] = newEntry;
      numberOfEntries++;
      return true;
    } else {
      throw new NullPointerException("Cannot add null entry to list");
    }
  }

  @Override
  public boolean add(int newPosition, T newEntry) {
    if (newEntry != null) {
      if (newPosition >= 1 && newPosition <= numberOfEntries + 1) {
        if (isArrayFull()) {
          doubleArray();
        }
        for (int i = numberOfEntries; i >= newPosition; i--) {
          array[i] = array[i - 1];
        }
        array[newPosition - 1] = newEntry;
        numberOfEntries++;
        return true;
      } else {
        throw new IndexOutOfBoundsException("Position is out of bounds: " + newPosition);
      }
    } else {
      throw new NullPointerException("Cannot add null entry to list");
    }
  }

  @Override
  public T remove(int givenPosition) {
    if (givenPosition >= 1 && givenPosition <= numberOfEntries) {
      T result = array[givenPosition - 1];
      for (int i = givenPosition; i < numberOfEntries; i++) {
        array[i - 1] = array[i];
      }
      numberOfEntries--;
      return result;
    } else {
      throw new IndexOutOfBoundsException("Position is out of bounds: " + givenPosition);
    }
  }

  @Override
  public boolean remove(Object anEntry) {
    if (anEntry != null) {
      int position = getEntryPosition(anEntry);
      if (position != -1) {
        remove(position);
        return true;
      }
    } else {
      throw new NullPointerException("Cannot remove null entry from list");
    }
    return false;
  }

  @Override
  public T get(int givenPosition) {
    if (givenPosition >= 1 && givenPosition <= numberOfEntries) {
      return array[givenPosition - 1];
    } else {
      throw new IndexOutOfBoundsException("Position is out of bounds: " + givenPosition);
    }
  }

  @Override
  public boolean contains(Object anEntry) {
    if (anEntry != null) {
      return getEntryPosition(anEntry) != -1;
    } else {
      throw new NullPointerException("Cannot check for null entry in list");
    }
  }

  @Override
  public boolean replace(int givenPosition, T newEntry) {
    if (newEntry != null) {
      if (givenPosition >= 1 && givenPosition <= numberOfEntries) {
        array[givenPosition - 1] = newEntry;
        return true;
      } else {
        throw new IndexOutOfBoundsException("Position is out of bounds: " + givenPosition);
      }
    } else {
      throw new NullPointerException("Cannot replace null entry in list");
    }
  }

  @Override
  public int size() {
    return numberOfEntries;
  }

  @Override
  public boolean isEmpty() {
    return numberOfEntries == 0;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void clear() {
    numberOfEntries = 0;
    array = (T[]) new Object[DEFAULT_CAPACITY];
  }

  private int getEntryPosition(Object anEntry) {
    for (int i = 0; i < numberOfEntries; i++) {
      if (array[i].equals(anEntry)) {
        return i;
      }
    }
    return -1;
  }

  private boolean isArrayFull() {
    return numberOfEntries == array.length;
  }

  @SuppressWarnings("unchecked")
  private void doubleArray() {
    T[] newArray = (T[]) new Object[array.length * 2];
    for (int i = 0; i < numberOfEntries; i++) {
      newArray[i] = array[i];
    }
    array = newArray;
  }
}
