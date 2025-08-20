package adt;

/**
 *
 * @author khtan
 */
public interface MapInterface<K, V> {

  public V put(K key, V value);

  public V get(Object key);

  public V remove(Object key);

  public boolean containsKey(Object key);

  public boolean containsValue(Object value);

  public int size();

  public boolean isEmpty();

  public void clear();
}
