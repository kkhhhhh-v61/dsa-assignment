package adt;

public interface ListInterface<T> {
  /**
   * Adds a new entry to the end of the list.
   * 
   * @param newEntry The object to be added as a new entry
   * @return true if the addition is successful, false otherwise
   */
  public boolean add(T newEntry);

  /**
   * Adds a new entry at a specified position within the list.
   * 
   * @param newPosition An integer that specifies the desired position of the new entry
   * @param newEntry The object to be added as a new entry
   * @return true if the addition is successful, false otherwise
   * @throws IndexOutOfBoundsException if newPosition is less than 0 or greater than the list size
   */
  public boolean add(int newPosition, T newEntry);
  
  /**
   * Removes the first occurrence of the specified entry from the list.
   * 
   * @param anEntry The entry to be removed
   * @return true if the entry was found and removed, false otherwise
   */
  public boolean remove(T anEntry);

  /**
   * Removes the entry at the given position in the list.
   * 
   * @param givenPosition An integer that indicates the position of the entry to be removed
   * @return true if the removal is successful, false otherwise
   * @throws IndexOutOfBoundsException if givenPosition is less than 0 or greater than or equal to the list size
   */
  public boolean remove(int givenPosition);

  /**
   * Retrieves the entry at the given position in the list.
   * 
   * @param givenPosition An integer that indicates the position of the desired entry
   * @return The entry at the given position
   * @throws IndexOutOfBoundsException if givenPosition is less than 0 or greater than or equal to the list size
   */
  public T getEntry(int givenPosition);

  /**
   * Tests whether the list contains a given entry.
   * 
   * @param anEntry The entry to locate
   * @return true if the list contains the entry, false otherwise
   */
  public boolean contains(T anEntry);

  /**
   * Replaces the entry at the given position in the list.
   * 
   * @param givenPosition An integer that indicates the position of the entry to be replaced
   * @param newEntry The object that will replace the entry at the given position
   * @return true if the replacement is successful, false otherwise
   * @throws IndexOutOfBoundsException if givenPosition is less than 0 or greater than or equal to the list size
   */
  public boolean replace(int givenPosition, T newEntry);

  /**
   * Gets the current number of entries in the list.
   * 
   * @return The integer number of entries in the list
   */
  public int size();

  /**
   * Tests whether the list is empty.
   * 
   * @return true if the list is empty, false otherwise
   */
  public boolean isEmpty();
  
  /**
   * Removes all entries from the list.
   */
  public void clear();
}
