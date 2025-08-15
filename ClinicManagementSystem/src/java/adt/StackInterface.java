package adt;

/**
 * An interface for the ADT Stack.
 * 
 * @param <T> The type of elements in this stack
 */
public interface StackInterface<T> {
  /**
   * Adds a new entry to the top of this stack.
   * 
   * @param newEntry The object to be added as a new entry
   */
  public void push(T newEntry);

  /**
   * Removes and returns the top entry from this stack.
   * 
   * @return The object at the top of the stack
   * @throws java.util.EmptyStackException if the stack is empty
   */
  public T pop();

  /**
   * Retrieves the top entry of this stack without removing it.
   * 
   * @return The object at the top of the stack
   * @throws java.util.EmptyStackException if the stack is empty
   */
  public T peek();

  /**
   * Detects whether this stack is empty.
   * 
   * @return True if the stack is empty, false otherwise
   */
  public boolean isEmpty();
  
  /**
   * Gets the current number of entries in this stack.
   * 
   * @return The integer number of entries currently in the stack
   */
  public int size();
  
  /**
   * Removes all entries from this stack.
   */
  public void clear();
}
