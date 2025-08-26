package adt;

import java.util.NoSuchElementException;

/**
 * A generic queue implementation using a linked list.
 * 
 * @author Tan Kai Hao
 * @param <T> The type of elements in this queue
 */
public class Queue<T> implements QueueInterface<T> {
    private Node<T> firstNode; // References node at front of queue
    private Node<T> lastNode;  // References node at back of queue
    private int size;

    public Queue() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    // Node class for linked list
    private static class Node<T> {
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
}
