package adt;

import java.util.NoSuchElementException;

/**
 *
 * @author Tan Kai Hao
 */
public class LinkedQueue<T> implements QueueInterface<T> {

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
}
