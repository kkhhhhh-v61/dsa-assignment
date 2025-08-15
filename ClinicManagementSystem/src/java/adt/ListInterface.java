package adt;

/**
 * An interface for the ADT List.
 * 
 * @param <T> The type of elements in this list
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
   * Adds a new entry at a specified position within this list.
   * 
   * @param newPosition An integer that specifies the position of the new entry
   * @param newEntry The object to be added as a new entry
   * @return True if the addition is successful, false if not
   * @throws IndexOutOfBoundsException if newPosition is less than 0 or greater than size()
   */
  public boolean add(int newPosition, T newEntry);
  
  /**
   * Removes the first occurrence of the specified entry from this list, if it is present.
   * 
   * @param anEntry The entry to be removed
   * @return True if the entry was found and removed, false otherwise
   */
  public boolean remove(T anEntry);

  /**
   * Removes the entry at the given position in this list.
   * 
   * @param givenPosition The position of the entry to be removed
   * @return True if the removal is successful, false otherwise
   * @throws IndexOutOfBoundsException if givenPosition is less than 0 or greater than or equal to size()
   */
  public boolean remove(int givenPosition);

  /**
   * Retrieves the entry at the given position in this list.
   * 
   * @param givenPosition The position of the desired entry
   * @return The entry at the given position
   * @throws IndexOutOfBoundsException if givenPosition is less than 0 or greater than or equal to size()
   */
  public T getEntry(int givenPosition);

  /**
   * Determines whether this list contains a given entry.
   * 
   * @param anEntry The entry to locate in the list
   * @return True if the list contains the entry, false otherwise
   */
  public boolean contains(T anEntry);

  /**
   * Replaces the entry at the given position in this list with the new entry.
   * 
   * @param givenPosition The position of the entry to be replaced
   * @param newEntry The new entry to be stored at the given position
   * @return True if the replacement is successful, false otherwise
   * @throws IndexOutOfBoundsException if givenPosition is less than 0 or greater than or equal to size()
   */
  public boolean replace(int givenPosition, T newEntry);

  /**
   * Gets the current number of entries in this list.
   * 
   * @return The integer number of entries currently in the list
   */
  public int size();

  /**
   * Detects whether this list is empty.
   * 
   * @return True if the list is empty, false otherwise
   */
  public boolean isEmpty();
  
  /**
   * Removes all entries from this list.
   */
  public void clear();
}
