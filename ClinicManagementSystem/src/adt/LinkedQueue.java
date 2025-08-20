package adt;

import java.util.NoSuchElementException;

/**
 * A linked list implementation of the QueueInterface. This implementation provides
 * O(1) time complexity for enqueue and dequeue operations.
 * 
 * @param <T> the type of elements held in this queue
 * @author khtan
 */
public class LinkedQueue<T> implements QueueInterface<T> {
  /**
   * A private inner class representing a node in the linked list.
   */
  private class Node {
    private T data;
    private Node next;
      
    private Node(T data) {
      this(data, null);
    }
      
    private Node(T data, Node next) {
      this.data = data;
      this.next = next;
    }
  }
  
  /** Reference to the front node of the queue. */
  private Node frontNode;
  
  /** Reference to the back node of the queue. */
  private Node backNode;
  
  /** The number of elements in the queue. */
  private int size;
  
  /**
   * Creates an empty queue.
   */
  public LinkedQueue() {
    frontNode = null;
    backNode = null;
    size = 0;
  }
  
  /**
   * Adds a new entry to the back of this queue.
   *
   * @param newEntry the object to be added as a new entry
   * @throws ClassCastException if the class of the specified element prevents it from being added to this queue
   * @throws NullPointerException if the specified element is null and this queue does not permit null elements
   * @throws IllegalArgumentException if some property of this element prevents it from being added to this queue
   */
  @Override
  public void enqueue(T newEntry) {
    if (newEntry == null) {
        throw new NullPointerException("Cannot enqueue null element");
    }
    
    Node newNode = new Node(newEntry);
    
    if (isEmpty()) {
        frontNode = newNode;
    } else {
        backNode.next = newNode;
    }
    
    backNode = newNode;
    size++;
  }

  /**
   * Removes and returns the entry at the front of this queue.
   *
   * @return the object at the front of the queue
   * @throws NoSuchElementException if this queue is empty
   */
  @Override
  public T dequeue() {
    if (isEmpty()) {
        throw new NoSuchElementException("Queue is empty");
    }
    
    T front = frontNode.data;
    frontNode = frontNode.next;
    
    if (frontNode == null) {
        backNode = null;
    }
    
    size--;
    return front;
  }

  /**
   * Retrieves the entry at the front of this queue without removing it.
   *
   * @return the object at the front of the queue
   * @throws NoSuchElementException if this queue is empty
   */
  @Override
  public T getFront() {
    if (isEmpty()) {
        throw new NoSuchElementException("Queue is empty");
    }
    return frontNode.data;
  }

  /**
   * Detects whether this queue is empty.
   *
   * @return true if the queue is empty, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return frontNode == null;
  }

  /**
   * Gets the current number of entries in this queue.
   *
   * @return the integer number of entries currently in the queue
   */
  @Override
  public int size() {
    return size;
  }
  
  /**
   * Removes all entries from this queue.
   */
  @Override
  public void clear() {
    frontNode = null;
    backNode = null;
    size = 0;
  }
}
