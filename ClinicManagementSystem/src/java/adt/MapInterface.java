package adt;

/**
 * An interface for the ADT Map.
 * 
 * @param <K> The type of keys maintained by this map
 * @param <V> The type of mapped values
 */
public interface MapInterface<K, V> {
  /**
   * Returns true if this map contains a mapping for the specified key.
   * 
   * @param key The key whose presence in this map is to be tested
   * @return True if this map contains a mapping for the specified key
   * @throws NullPointerException if the specified key is null and this map does not permit null keys
   */
  public boolean containsKey(K key);

  /**
   * Returns true if this map maps one or more keys to the specified value.
   * 
   * @param value The value whose presence in this map is to be tested
   * @return True if this map maps one or more keys to the specified value
   * @throws NullPointerException if the specified value is null and this map does not permit null values
   */
  public boolean containsValue(V value);

  /**
   * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
   * 
   * @param key The key whose associated value is to be returned
   * @return The value to which the specified key is mapped, or null if this map contains no mapping for the key
   * @throws NullPointerException if the specified key is null and this map does not permit null keys
   */
  public V get(K key);

  /**
   * Associates the specified value with the specified key in this map.
   * 
   * @param key The key with which the specified value is to be associated
   * @param value The value to be associated with the specified key
   * @return The previous value associated with the key, or null if there was no mapping for the key
   * @throws NullPointerException if the specified key or value is null and this map does not permit null keys or values
   * @throws ClassCastException if the class of the specified key or value prevents it from being stored in this map
   * @throws IllegalArgumentException if some property of the specified key or value prevents it from being stored in this map
   */
  public V put(K key, V value);

  /**
   * Removes the mapping for a key from this map if it is present.
   * 
   * @param key The key whose mapping is to be removed from the map
   * @return The previous value associated with the key, or null if there was no mapping for the key
   * @throws NullPointerException if the specified key is null and this map does not permit null keys
   */
  public V remove(K key);

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
   * The map will be empty after this call returns.
   */
  public void clear();
}
