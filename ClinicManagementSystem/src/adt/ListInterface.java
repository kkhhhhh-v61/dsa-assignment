package adt;

/**
 *
 * @author Tan Kai Hao
 * 
 * Referenced from: https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 */
public interface ListInterface<T> {
  
  /**
   * Adds a new entry to the end of this list.
   * 
   * @param newEntry The object to be added as a new entry
   * @return True if the addition is successful, false if not
   */
  public boolean add(T newEntry);

  /**
   * Adds a new entry at the specified position in this list.
   * 
   * @param newPosition The position at which the new entry should be added
   * @param newEntry The object to be added as a new entry
   * @return True if the addition is successful, false if not
   */
  public boolean add(int newPosition, T newEntry);

  /**
   * Removes the entry at the specified position from this list.
   * 
   * @param givenPosition The position of the entry to be removed
   * @return The entry that was removed
   */
  public T remove(int givenPosition);
  
  /**
   * Removes the first entry that matches the given entry from this list.
   * 
   * @param anEntry The object to be removed
   * @return True if the removal is successful, false if not
   */
  public boolean remove(Object anEntry);

  /**
   * Returns the entry at the specified position in this list.
   * 
   * @param givenPosition The position of the entry to be retrieved
   * @return The entry at the specified position
   */
  public T get(int givenPosition);

  /**
   * Returns true if this list contains the given entry.
   * 
   * @param anEntry The entry to be searched for
   * @return True if the list contains the given entry, false if not
   */
  public boolean contains(Object anEntry);

  /**
   * Replaces the entry at the specified position in this list.
   * 
   * @param givenPosition The position of the entry to be replaced
   * @param newEntry The object to be used as the replacement
   * @return True if the replacement is successful, false if not
   */
  public boolean replace(int givenPosition, T newEntry);

  /**
   * Gets the current number of entries in this list.
   * 
   * @return The number of entries in this list
   */
  public int size();

  /**
   * Detects whether this list is empty.
   * 
   * @return True if this list is empty, false if not
   */
  public boolean isEmpty();

  /**
   * Removes all entries from this list.
   */
  public void clear();
}
