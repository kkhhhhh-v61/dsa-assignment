package adt;

public class ArrayList<T> implements ListInterface<T> {
  private static final int DEFAULT_CAPACITY = 10;
  private int numberOfEntries;
  private T[] array;

  public ArrayList() {
    this.numberOfEntries = 0;
    this.array = (T[]) new Object[DEFAULT_CAPACITY];
  }

  public ArrayList(int initialCapacity) {
    this.numberOfEntries = 0;
    this.array = (T[]) new Object[initialCapacity];
  }

  @Override
  public boolean add(T newEntry) {
    if (isArrayFull()) {
      doubleArray();
    }
    array[numberOfEntries++] = newEntry;
    return true;
  }

  @Override
  public boolean add(int newPosition, T newEntry) {
    if (newPosition >= 1 && newPosition <= numberOfEntries + 1) {
      if (isArrayFull()) {
        doubleArray();
      }
      for (int i = numberOfEntries; i > newPosition - 1; i--) {
        array[i] = array[i - 1];
      }
      array[newPosition - 1] = newEntry;
      numberOfEntries++;
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(T anEntry) {
    int index = 0;
    while (index < numberOfEntries && !array[index].equals(anEntry)) {
      index++;
    }
    if (index == numberOfEntries) {
      return false;
    }
    for (int i = index; i < numberOfEntries - 1; i++) {
      array[i] = array[i + 1];
    }
    numberOfEntries--;
    return true;
  }

  @Override
  public boolean remove(int givenPosition) {
    if (givenPosition >= 1 && givenPosition <= numberOfEntries) {
      for (int i = givenPosition - 1; i < numberOfEntries - 1; i++) {
        array[i] = array[i + 1];
      }
      numberOfEntries--;
      return true;
    }
    return false;
  }

  @Override
  public T getEntry(int givenPosition) {
    if (givenPosition >= 1 && givenPosition <= numberOfEntries) {
      return array[givenPosition - 1];
    }
    return null;
  }

  @Override
  public boolean contains(T anEntry) {
    for (int i = 0; i < numberOfEntries; i++) {
      if (array[i].equals(anEntry)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean replace(int givenPosition, T newEntry) {
    if (givenPosition >= 1 && givenPosition <= numberOfEntries) {
      array[givenPosition - 1] = newEntry;
      return true;
    }
    return false;
  }

  @Override
  public int size() {
    return numberOfEntries;
  }

  @Override
  public boolean isEmpty() {
    return numberOfEntries == 0;
  }

  @Override
  public void clear() {
    numberOfEntries = 0;
    array = (T[]) new Object[DEFAULT_CAPACITY];
  }

  private boolean isArrayFull() {
    return numberOfEntries == array.length;
  }

  private void doubleArray() {
    T[] newArray = (T[]) new Object[array.length * 2];
    for (int i = 0; i < numberOfEntries; i++) {
      newArray[i] = array[i];
    }
    array = newArray;
  }

  public String toString() {
    String result = "";
    for (int i = 0; i < numberOfEntries; i++) {
      result += array[i] + (i == numberOfEntries - 1 ? "" : ", ");
    }
    return result;
  }
}
