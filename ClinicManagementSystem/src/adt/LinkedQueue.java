package adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A linked list implementation of a queue.
 * @author Tan Kai Hao
 */
public class LinkedQueue<T> implements QueueInterface<T>, Iterable<T> {

  private Node front;
  private Node back;
  private int size;

  private class Node {
    private T data;
    private Node next;

    public Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  public LinkedQueue() {
    this.front = null;
    this.back = null;
    this.size = 0;
  }

  @Override
  public boolean add(T newEntry) {
    Node newNode = new Node(newEntry);
    if (front == null) {
      front = newNode;
      back = newNode;
    } else {
      back.next = newNode;
      back = newNode;
    }
    size++;
    return true;
  }

  @Override
  public boolean offer(T newEntry) {
    return add(newEntry);
  }

  @Override
  public T remove() {
    if (front == null) {
      throw new NoSuchElementException("Queue is empty");
    }
    T data = front.data;
    front = front.next;
    size--;
    return data;
  }

  @Override
  public T poll() {
    if (front == null) {
      return null;
    }
    T data = front.data;
    front = front.next;
    size--;
    return data;
  }

  @Override
  public T element() {
    if (front == null) {
      throw new NoSuchElementException("Queue is empty");
    }
    return front.data;
  }

  @Override
  public T peek() {
    if (front == null) {
      return null;
    }
    return front.data;
  }
  
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void clear() {
    front = null;
    back = null;
    size = 0;
  }
  
  @Override
  public Iterator<T> iterator() {
    return new LinkedQueueIterator();
  }
  
  private class LinkedQueueIterator implements Iterator<T> {
    private Node current = front;
    private Node lastReturned = null;
    private Node beforeLastReturned = null;
    
    @Override
    public boolean hasNext() {
      return current != null;
    }
    
    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in the queue");
      }
      beforeLastReturned = lastReturned;
      lastReturned = current;
      T data = current.data;
      current = current.next;
      return data;
    }
    
    @Override
    public void remove() {
      if (lastReturned == null) {
        throw new IllegalStateException("next() must be called before remove()");
      }
      
      if (lastReturned == front) {
        front = front.next;
        if (front == null) {
          back = null;
        }
      } else {
        // Find the node before lastReturned
        Node prev = front;
        while (prev != null && prev.next != lastReturned) {
          prev = prev.next;
        }
        if (prev != null) {
          prev.next = lastReturned.next;
          if (lastReturned == back) {
            back = prev;
          }
        }
      }
      
      size--;
      lastReturned = null;
      current = beforeLastReturned != null ? beforeLastReturned.next : front;
    }
  }
}
