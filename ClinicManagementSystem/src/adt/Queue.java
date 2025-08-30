package adt;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Tan Kai Hao
 */
public class Queue<T> implements QueueInterface<T>, Iterable<T>, Serializable {
  private static final long serialVersionUID = 1L;
  private Node<T> firstNode;
  private Node<T> lastNode;
  private int size;

  public Queue() {
    firstNode = null;
    lastNode = null;
    size = 0;
  }

  private static class Node<T> implements Serializable {
    private T data;
    private Node<T> next;

    private Node(T data) {
      this(data, null);
    }

    private Node(T data, Node<T> next) {
      this.data = data;
      this.next = next;
    }
  }

  @Override
  public boolean add(T newEntry) {
    if (newEntry == null) {
      throw new NullPointerException("Cannot add null to the queue");
    }
    return offer(newEntry);
  }

  @Override
  public boolean offer(T newEntry) {
    Node<T> newNode = new Node<>(newEntry);
    
    if (isEmpty()) {
      firstNode = newNode;
    } else {
      lastNode.next = newNode;
    }
    
    lastNode = newNode;
    size++;
    return true;
  }

  @Override
  public T remove() {
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }
    return poll();
  }

  @Override
  public T poll() {
    if (isEmpty()) {
      return null;
    }
        
    T result = firstNode.data;
    firstNode = firstNode.next;
    size--;
        
    if (isEmpty()) {
      lastNode = null;
    }
        
    return result;
  }

  @Override
  public T element() {
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }
    return firstNode.data;
  }

  @Override
  public T peek() {
    return (firstNode == null) ? null : firstNode.data;
  }

  public T peekLast() {
    return (lastNode == null) ? null : lastNode.data;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return firstNode == null;
  }

  @Override
  public void clear() {
    firstNode = null;
    lastNode = null;
    size = 0;
  }
    
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    Node<T> current = firstNode;
    while (current != null) {
      sb.append(current.data);
      if (current.next != null) {
        sb.append(", ");
      }
      current = current.next;
    }
    sb.append("]");
    return sb.toString();
  }
    
  @Override
  public Iterator<T> iterator() {
    return new QueueIterator();
  }
    
  private class QueueIterator implements Iterator<T> {
    private Node<T> current = firstNode;
    private Node<T> lastReturned = null;
    private Node<T> beforeLastReturned = null;
        
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
            
      if (lastReturned == firstNode) {
        firstNode = firstNode.next;
        if (firstNode == null) {
          lastNode = null;
        }
      } else {
        Node<T> prev = firstNode;
        while (prev != null && prev.next != lastReturned) {
          prev = prev.next;
        }
        if (prev != null) {
          prev.next = lastReturned.next;
          if (lastReturned == lastNode) {
            lastNode = prev;
          }
        }
      }
            
      size--;
      lastReturned = null;
      current = beforeLastReturned != null ? beforeLastReturned.next : firstNode;
    }
  }
}
