package adt;

/**
 *
 * @author Tan Kai Hao
 */

/*
 * Reference from: https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
 */
public interface MapInterface<K, V> {
  
  /**
   * Associates the specified value with the specified key in this map.
   * 
   * @param key The key with which the specified value is to be associated
   * @param value The value to be associated with the specified key
   * @return The previous value associated with key, or null if there was no mapping for key
   */
  public V put(K key, V value);

  /**
   * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
   * 
   * @param key The key whose associated value is to be returned
   * @return The value to which the specified key is mapped, or null if this map contains no mapping for the key
   */
  public V get(Object key);

  /**
   * Removes the mapping for a key from this map if it is present.
   * 
   * @param key The key whose mapping is to be removed from the map
   * @return The previous value associated with key, or null if there was no mapping for key
   */
  public V remove(Object key);

  /**
   * Returns true if this map contains a mapping for the specified key.
   * 
   * @param key The key whose presence in this map is to be tested
   * @return True if this map contains a mapping for the specified key
   */
  public boolean containsKey(Object key);

  /**
   * Returns true if this map maps one or more keys to the specified value.
   * 
   * @param value The value whose presence in this map is to be tested
   * @return True if this map maps one or more keys to the specified value
   */
  public boolean containsValue(Object value);

  /**
   * Returns the number of key-value mappings in this map.
   * 
   * @return The number of key-value mappings in this map
   */
  public int size();

  /**
   * Returns true if this map contains no key-value mappings.
   * 
   * @return True if this map contains no key-value mappings
   */
  public boolean isEmpty();

  /**
   * Removes all of the mappings from this map.
   */
  public void clear();
}
