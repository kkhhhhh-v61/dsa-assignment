package adt;

public interface StackInterface<T> {
  /**
   * Adds a new entry to the top of the stack.
   * 
   * @param newEntry The object to be added as a new entry
   */
  public void push(T newEntry);
  
  /**
   * Removes the entry at the top of the stack.
   * 
   * @return The entry that was removed
   */
  public T pop();
  
  /**
   * Retrieves the entry at the top of the stack.
   * 
   * @return The entry at the top of the stack
   */
  public T peek();
  
  /**
   * Tests whether the stack is empty.
   * 
   * @return true if the stack is empty, false otherwise
   */
  public boolean isEmpty();
  
  /**
   * Gets the current number of entries in the stack.
   * 
   * @return The integer number of entries in the stack
   */
  public int size();
  
  /**
   * Removes all entries from the stack.
   */
  public void clear();
}
