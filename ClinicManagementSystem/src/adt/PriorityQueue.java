package adt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Tan Kai Hao
 */
public class PriorityQueue<T extends Comparable<T>> implements QueueInterface<T>, Iterable<T> {
  private final ArrayList<T> heap;

  public PriorityQueue() {
    this.heap = new ArrayList<>();
  }

  @Override
  public boolean add(T newEntry) {
    return offer(newEntry);
  }

  @Override
  public boolean offer(T newEntry) {
    if (newEntry == null) {
      throw new NullPointerException("Cannot add null to the queue");
    }
    
    heap.add(newEntry);
    
    int index = heap.size() - 1;
    while (index > 0) {
      int parentIndex = (index - 1) / 2;
      T parent = heap.get(parentIndex);
      
      if (newEntry.compareTo(parent) >= 0) {
        break;
      }
      
      heap.set(index, parent);
      heap.set(parentIndex, newEntry);
      index = parentIndex;
    }
    
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
    
    T min = heap.get(0);
    
    if (heap.size() == 1) {
      heap.remove(0);
      return min;
    }
    
    T last = heap.remove(heap.size() - 1);
    heap.set(0, last);
    
    int index = 0;
    int size = heap.size();
    
    while (true) {
      int leftChildIdx = 2 * index + 1;
      int rightChildIdx = 2 * index + 2;
      int smallest = index;
      
      if (leftChildIdx < size && 
          heap.get(leftChildIdx).compareTo(heap.get(smallest)) < 0) {
        smallest = leftChildIdx;
      }
      
      if (rightChildIdx < size && 
          heap.get(rightChildIdx).compareTo(heap.get(smallest)) < 0) {
        smallest = rightChildIdx;
      }
      
      if (smallest == index) {
        break;
      }
      
      T temp = heap.get(index);
      heap.set(index, heap.get(smallest));
      heap.set(smallest, temp);
      
      index = smallest;
    }
    
    return min;
  }

  @Override
  public T element() {
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }
    return heap.get(0);
  }

  @Override
  public T peek() {
    return isEmpty() ? null : heap.get(0);
  }

  @Override
  public int size() {
    return heap.size();
  }

  @Override
  public boolean isEmpty() {
    return heap.isEmpty();
  }

  @Override
  public void clear() {
    heap.clear();
  }

  @Override
  public Iterator<T> iterator() {
    return new ArrayList<>(heap).iterator();
  }
}
