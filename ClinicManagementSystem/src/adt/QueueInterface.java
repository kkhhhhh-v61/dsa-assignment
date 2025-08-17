package adt;

/**
 *
 * @author khtan
 */

/**
 * An interface for the ADT Queue.
 * 
 * @param <T> The type of elements in this queue
 */
public interface QueueInterface<T> {
  /**
   * Adds a new entry to the back of this queue.
   * 
   * @param newEntry The object to be added as a new entry
   * @throws ClassCastException if the class of the specified element prevents it from being added to this queue
   * @throws NullPointerException if the specified element is null and this queue does not permit null elements
   * @throws IllegalArgumentException if some property of this element prevents it from being added to this queue
   */
  public void enqueue(T newEntry);

  /**
   * Removes and returns the entry at the front of this queue.
   * 
   * @return The object at the front of the queue
   * @throws java.util.NoSuchElementException if this queue is empty
   */
  public T dequeue();

  /**
   * Retrieves the entry at the front of this queue without removing it.
   * 
   * @return The object at the front of the queue
   * @throws java.util.NoSuchElementException if this queue is empty
   */
  public T getFront();

  /**
   * Detects whether this queue is empty.
   * 
   * @return True if the queue is empty, false otherwise
   */
  public boolean isEmpty();

  /**
   * Gets the current number of entries in this queue.
   * 
   * @return The integer number of entries currently in the queue
   */
  public int size();
  
  /**
   * Removes all entries from this queue.
   * The queue will be empty after this call returns.
   */
  public void clear();
}
