package adt;

/**
 * Hash table based implementation of the MapInterface with chaining for collision resolution.
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class HashMap<K, V> implements MapInterface<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    private Entry<K, V>[] table;
    private int size;
    private final float loadFactor;
    private int threshold;

    /**
     * Constructs an empty HashMap with the default initial capacity (16) and load factor (0.75).
     */
    public HashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty HashMap with the specified initial capacity and default load factor (0.75).
     * 
     * @param initialCapacity the initial capacity
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty HashMap with the specified initial capacity and load factor.
     * 
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     * @throws IllegalArgumentException if the initial capacity is negative or the load factor is nonpositive
     */
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

    /**
     * Node class to store key-value pairs in the hash table.
     */
    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;
        final int hash;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
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
        
        // Check if key already exists
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
    
    /**
     * Returns the entry associated with the specified key in the HashMap.
     * Returns null if the HashMap contains no mapping for the key.
     */
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
    
    /**
     * Adds a new entry with the specified key, value and hash code to the specified bucket.
     */
    private void addEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<>(hash, key, value, e);
        
        if (size++ >= threshold) {
            resize(2 * table.length);
        }
    }
    
    /**
     * Rehashes the contents of this map into a new array with a larger capacity.
     */
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
    
    /**
     * Transfers all entries from current table to new table.
     */
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
    
    /**
     * Returns index for hash code h.
     */
    private static int indexFor(int h, int length) {
        return h & (length - 1);
    }
    
    /**
     * Applies a supplemental hash function to a given hashCode, which
     * defends against poor quality hash functions.
     */
    private static int hash(Object key) {
        int h = key.hashCode();
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
}
