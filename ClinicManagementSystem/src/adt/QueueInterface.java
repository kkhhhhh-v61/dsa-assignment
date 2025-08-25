package adt;

/**
 *
 * @author Tan Kai Hao
 * 
 * Referenced from: https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html
 */
public interface QueueInterface<T> {
  
  /**
   * Adds a new entry to the end of this queue.
   * 
   * @param newEntry The object to be added as a new entry
   * @return True if the addition is successful, false if not
   * @throws IllegalStateException if the queue is full
   */
  public boolean add(T newEntry);

  /**
   * Adds a new entry to the end of this queue.
   * 
   * @param newEntry The object to be added as a new entry
   * @return True if the addition is successful, false if not
   */
  public boolean offer(T newEntry);

  /**
   * Retrives and removes the entry at the front of this queue.
   * 
   * @return The entry that was removed
   * @throws NoSuchElementException if the queue is empty
   */
  public T remove();

  /**
   * Retrives and removes the entry at the front of this queue.
   * 
   * @return The entry that was removed, or null if the queue is empty
   */
  public T poll();

  /**
   * Retrives the entry at the front of this queue.
   * 
   * @return The entry at the front of this queue
   * @throws NoSuchElementException if the queue is empty
   */
  public T element();

  /**
   * Retrives but do not remove the entry at the front of this queue.
   * 
   * @return The entry at the front of this queue, or null if the queue is empty
   */
  public T peek();
}
