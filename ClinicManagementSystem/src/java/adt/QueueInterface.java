package adt;

public interface QueueInterface<T> {
  /**
   * Adds a new entry to the end of the queue.
   * 
   * @param newEntry The object to be added as a new entry
   */
  public void enqueue(T newEntry);
  
  /**
   * Removes the entry at the front of the queue.
   * 
   * @return The entry that was removed
   */
  public T dequeue();
  
  /**
   * Retrieves the entry at the front of the queue.
   * 
   * @return The entry at the front of the queue
   */
  public T getFront();
  
  /**
   * Tests whether the queue is empty.
   * 
   * @return true if the queue is empty, false otherwise
   */
  public boolean isEmpty();
  
  /**
   * Gets the current number of entries in the queue.
   * 
   * @return The integer number of entries in the queue
   */
  public int size();
  
  /**
   * Removes all entries from the queue.
   */
  public void clear();
}
