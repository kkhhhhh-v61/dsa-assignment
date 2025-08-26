package adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Tan Kai Hao
 */
public class HashMap<K, V> implements MapInterface<K, V>, Iterable<HashMap.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    private Entry<K, V>[] table;
    private int size;
    private final float loadFactor;
    private int threshold;

    public HashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        
        this.loadFactor = loadFactor;
        this.table = (Entry<K, V>[]) new Entry[initialCapacity > 0 ? initialCapacity : DEFAULT_CAPACITY];
        this.threshold = (int) (this.table.length * loadFactor);
    }

    public static class Entry<K, V> {
        public final K key;
        public V value;
        public Entry<K, V> next;
        final int hash;

        public Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        public K getKey() {
            return key;
        }
        
        public V getValue() {
            return value;
        }
        
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        }
        
        for (Entry<K, V> entry : table) {
            for (; entry != null; entry = entry.next) {
                if (value.equals(entry.value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        
        Entry<K, V> entry = getEntry(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("Neither key nor value can be null");
        }
        
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (key == e.key || key.equals(e.key))) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        
        // Add new entry
        addEntry(hash, key, value, index);
        return null;
    }

    @Override
    public V remove(Object key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        Entry<K, V> prev = null;
        Entry<K, V> e = table[index];
        
        while (e != null) {
            if (e.hash == hash && (key == e.key || key.equals(e.key))) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
            prev = e;
            e = e.next;
        }
        
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }
    
    private Entry<K, V> getEntry(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (key == e.key || key.equals(e.key))) {
                return e;
            }
        }
        return null;
    }
    
    private void addEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<>(hash, key, value, e);
        
        if (size++ >= threshold) {
            resize(2 * table.length);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] oldTable = table;
        int oldCapacity = oldTable.length;
        
        if (oldCapacity >= (1 << 30)) {  // Maximum capacity
            threshold = Integer.MAX_VALUE;
            return;
        }
        
        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }
    
    private void transfer(Entry<K, V>[] newTable) {
        int newCapacity = newTable.length;
        
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int index = indexFor(entry.hash, newCapacity);
                entry.next = newTable[index];
                newTable[index] = entry;
                entry = next;
            }
        }
    }
    
    private static int indexFor(int h, int length) {
        return h & (length - 1);
    }
    
    private static int hash(Object key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }
    
    private class HashMapIterator implements Iterator<Entry<K, V>> {
        private Entry<K, V> next;
        private int index;
        private Entry<K, V> current;
        
        HashMapIterator() {
            current = null;
            next = null;
            index = 0;
            // Find first non-empty bucket
            if (size > 0) {
                while (index < table.length && (next = table[index]) == null) {
                    index++;
                }
            }
        }
        
        @Override
        public boolean hasNext() {
            return next != null;
        }
        
        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            Entry<K, V> e = next;
            next = e.next;
            
            if (next == null) {
                index++;
                while (index < table.length && (next = table[index]) == null) {
                    index++;
                }
            }
            
            current = e;
            return e;
        }
        
        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException("next() must be called before remove()");
            }
            
            K k = current.key;
            current = null;
            HashMap.this.remove(k);
        }
    }
}
