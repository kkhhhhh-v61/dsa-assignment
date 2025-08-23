package adt;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A priority queue implementation using a min-heap.
 * Elements are ordered according to their natural ordering.
 * 
 * @author Tan Kai Hao
 * @param <T> the type of elements in this queue, must implement Comparable
 */
public class PriorityQueue<T extends Comparable<T>> implements QueueInterface<T> {
    private final ArrayList<T> heap;

    /**
     * Constructs an empty priority queue.
     */
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
        
        // Add the new element to the end of the heap
        heap.add(newEntry);
        
        // Bubble up the new element to maintain heap property
        int index = heap.size() - 1;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap.get(parentIndex);
            
            if (newEntry.compareTo(parent) >= 0) {
                break;
            }
            
            // Swap with parent
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
        
        // Get the root (minimum) element
        T min = heap.get(0);
        
        if (heap.size() == 1) {
            heap.remove(0);
            return min;
        }
        
        // Move the last element to the root
        T last = heap.remove(heap.size() - 1);
        heap.set(0, last);
        
        // Sink down the new root to maintain heap property
        int index = 0;
        int size = heap.size();
        
        while (true) {
            int leftChildIdx = 2 * index + 1;
            int rightChildIdx = 2 * index + 2;
            int smallest = index;
            
            // Find the smallest among current node and its children
            if (leftChildIdx < size && 
                heap.get(leftChildIdx).compareTo(heap.get(smallest)) < 0) {
                smallest = leftChildIdx;
            }
            
            if (rightChildIdx < size && 
                heap.get(rightChildIdx).compareTo(heap.get(smallest)) < 0) {
                smallest = rightChildIdx;
            }
            
            // If the smallest is the current node, heap property is satisfied
            if (smallest == index) {
                break;
            }
            
            // Swap with the smallest child
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
    
    /**
     * Returns the number of elements in this queue.
     * 
     * @return the number of elements in this queue
     */
    public int size() {
        return heap.size();
    }
    
    /**
     * Returns true if this queue contains no elements.
     * 
     * @return true if this queue contains no elements
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    /**
     * Removes all elements from this queue.
     */
    public void clear() {
        heap.clear();
    }
}
