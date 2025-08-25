package adt;

/**
 *
 * @author Tan Kai Hao
 * 
 * Referenced from: https://docs.oracle.com/javase/8/docs/api/java/util/Stack.html
 */
public interface StackInterface<T> {
  
  /**
   * Adds a new entry to the top of this stack.
   * 
   * @param newEntry The object to be added as a new entry
   * @return True if the addition is successful, false if not
   */
  public boolean push(T newEntry);

  /**
   * Removes the entry at the top of this stack.
   * 
   * @return The entry that was removed
   */
  public T pop();

  /**
   * Looks at the entry at the top of this stack without removing it.
   * 
   * @return The entry at the top of this stack
   */
  public T peek();

  /**
   * Returns the 1-based position where an object is on this stack.
   * 
   * @param anEntry The object to be searched for
   * @return The 1-based position where anEntry is found, or -1 if not found
   */
  public int search(Object anEntry);

  /**
   * Tests if this stack is empty.
   * 
   * @return True if this stack is empty, false if not
   */
  public boolean empty();
}
