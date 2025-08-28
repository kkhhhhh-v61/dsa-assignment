package adt;

import entity.Patient;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.Serializable;

/**
 *
 * @author Tan Kai Hao
 * 
 * Referenced from: 
 * [1] https://www.geeksforgeeks.org/dsa/introduction-to-red-black-tree
 * [2] https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html
 */
public class RedBlackTreeMap<K extends Comparable<K>, V> implements MapInterface<K, V>, Iterable<RedBlackTreeMap.Node<K, V>>, Serializable {
  private static final long serialVersionUID = 1L;

  private static final boolean RED = false;
  private static final boolean BLACK = true;

  @SuppressWarnings("hiding")
  public static class Node<K, V> implements Serializable {
    K key;
    V value;
    Node<K, V> left, right, parent;
    boolean color;

    public Node(K key, V value, Node<K, V> parent) {
      this.key = key;
      this.value = value;
      this.parent = parent;
      this.color = RED; // new nodes are RED by default
      this.left = this.right = null;
    }
  }

  Node<K, V> root;
  int size;

  public RedBlackTreeMap() {
    this.root = null;
    this.size = 0;
  }

  @Override
  public V put(K key, V value) {
    if (root == null) {
      root = new Node<K, V>(key, value, null);
      root.color = BLACK; // root node is black by default
      size = 1;
      return null;
    }

    Node<K, V> parent = null;
    Node<K, V> current = root;
    
    while (current != null) {
      parent = current;
      if (key.compareTo(current.key) < 0) {
        current = current.left;
      } else if (key.compareTo(current.key) > 0) {
        current = current.right;
      } else {
        V oldValue = current.value;
        current.value = value;
        return oldValue;
      }
    }
    
    Node<K, V> newNode = new Node<K, V>(key, value, parent);
    if (key.compareTo(parent.key) < 0) {
      parent.left = newNode;
    } else {
      parent.right = newNode;
    }

    fixAfterInsertion(newNode);
    size++;
    return null;
  }

  @Override
  public V get(Object key) {
    Node<K, V> node = getNode(key);
    return (node == null) ? null : node.value;
  }

  @Override
  public V remove(Object key) {
    Node<K, V> nodeToRemove = getNode(key);
    if (nodeToRemove == null) {
      return null;
    }
    
    V oldValue = nodeToRemove.value;
    deleteNode(nodeToRemove);
    size--;
    return oldValue;
  }

  @Override
  public boolean containsKey(Object key) {
    return getNode(key) != null;
  }

  @Override
  public boolean containsValue(Object value) {
    if (root == null) {
      return false;
    }

    LinkedQueue<Node<K, V>> queue = new LinkedQueue<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      Node<K, V> current = queue.poll();

      if (value == null) {
        if (current.value == null) {
          return true;
        }
      } else {
        if (value.equals(current.value)) {
          return true;
        }
      }

      if (current.left != null) {
        queue.offer(current.left);
      }
      if (current.right != null) {
        queue.offer(current.right);
      }
    }
    return false;
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
    root = null;
    size = 0;
  }

  @SuppressWarnings("unchecked")
  public Node<K, V> getNode(Object key) {
    if (key == null) {
      return null;
    }

    Node<K, V> current = root;
    while (current != null) {
      int cmp = ((K) key).compareTo(current.key);
      if (cmp < 0) {
        current = current.left;
      } else if (cmp > 0) {
        current = current.right;
      } else {
        return current;
      }
    }
    return null;
  }
  
  public void deleteNode(Node<K, V> node) {
    if (node.left != null && node.right != null) {
      Node<K, V> successor = node.right;
      while (successor.left != null) {
        successor = successor.left;
      }
      node.key = successor.key;
      node.value = successor.value;
      node = successor;
    }

    Node<K, V> replacement = (node.left != null) ? node.left : node.right;
    boolean isNodeBlack = node.color == BLACK;
    
    if (replacement != null) {
      replacement.parent = node.parent;
      if (node.parent == null) {
        root = replacement;
      } else if (node == node.parent.left) {
        node.parent.left = replacement;
      } else {
        node.parent.right = replacement;
      }
      
      if (isNodeBlack) {
        fixAfterDeletion(replacement);
      }
    } else if (node.parent == null) {
      root = null;
    } else {
      if (isNodeBlack) {
        fixAfterDeletion(node);
      }

      if (node.parent != null) {
        if (node == node.parent.left) {
          node.parent.left = null;
        } else if (node == node.parent.right) {
          node.parent.right = null;
        }
        node.parent = null;
      }
    }
  }
  
  public void rotateLeft(Node<K, V> node) {
    if (node == null || node.right == null) {
      return;
    }

    Node<K, V> rightChild = node.right;
    node.right = rightChild.left;

    if (rightChild.left != null) {
      rightChild.left.parent = node;
    }

    rightChild.parent = node.parent;
    
    if (node.parent == null) {
      this.root = rightChild;
    } else if (node == node.parent.left) {
      node.parent.left = rightChild;
    } else {
      node.parent.right = rightChild;
    }

    rightChild.left = node;
    node.parent = rightChild;
  }
  
  public void rotateRight(Node<K, V> node) {
    if (node == null || node.left == null) {
      return;
    }

    Node<K, V> leftChild = node.left;
    node.left = leftChild.right;

    if (leftChild.right != null) {
      leftChild.right.parent = node;
    }

    leftChild.parent = node.parent;

    if (node.parent == null) {
      this.root = leftChild;
    } else if (node == node.parent.left) {
      node.parent.left = leftChild;
    } else {
      node.parent.right = leftChild;
    }

    leftChild.right = node;
    node.parent = leftChild;
  }
  
  public ArrayList<K> getKeys() {
    ArrayList<K> keys = new ArrayList<>();

    if (root != null) {
      inOrderTraversal(root, keys);
    }

    return keys;
  }

  private void inOrderTraversal(Node<K, V> node, ListInterface<K> keys) {
    if (node == null) {
      return;
    }

    inOrderTraversal(node.left, keys);
    keys.add(node.key);
    inOrderTraversal(node.right, keys);
  }
  
  public void fixAfterInsertion(Node<K, V> node) {
    while (node.parent != null && node.parent.color == RED) {
      Node<K, V> parent = node.parent;
      Node<K, V> grandparent = parent.parent;

      if (parent == grandparent.left) {
        Node<K, V> uncle = grandparent.right;

        if (uncle != null && uncle.color == RED) {
          grandparent.color = RED;
          parent.color = BLACK;
          uncle.color = BLACK;
          node = grandparent;
        } else {
          if (node == parent.right) {
            rotateLeft(node);
            node = parent;
            parent = node.parent;
          }
          parent.color = BLACK;
          grandparent.color = RED;
          rotateRight(grandparent);
        }
      } else {
        Node<K, V> uncle = grandparent.left;

        if (uncle != null && uncle.color == RED) {
          grandparent.color = RED;
          parent.color = BLACK;
          uncle.color = BLACK;
          node = grandparent;
        } else {
          if (node == parent.left) {
            rotateRight(node);
            node = parent;
            parent = node.parent;
          }
          parent.color = BLACK;
          grandparent.color = RED;
          rotateLeft(grandparent);
        }
      }
    }
    root.color = BLACK;
  }

  public void fixAfterDeletion(Node<K, V> node) {
    while (node != root && (node == null || node.color == BLACK)) {
      if (node == null) {
        break;
      }
      
      if (node == node.parent.left) {
        Node<K, V> sibling = node.parent.right;
        
        if (sibling == null) {
          node = node.parent;
          continue;
        }

        if (sibling.color == RED) {
          sibling.color = BLACK;
          node.parent.color = RED;
          rotateLeft(node.parent);
          sibling = node.parent.right;
        }
        
        if (sibling == null) {
          node = node.parent;
          continue;
        }

        boolean leftBlack = (sibling.left == null) || (sibling.left.color == BLACK);
        boolean rightBlack = (sibling.right == null) || (sibling.right.color == BLACK);
        
        if (leftBlack && rightBlack) {
          sibling.color = RED;
          node = node.parent;
        } else {
          if (rightBlack) {
            if (sibling.left != null) {
              sibling.left.color = BLACK;
            }
            sibling.color = RED;
            rotateRight(sibling);
            sibling = node.parent.right;
          }
          
          if (sibling != null) {
            sibling.color = node.parent.color;
            if (sibling.right != null) {
              sibling.right.color = BLACK;
            }
          }
          
          node.parent.color = BLACK;
          rotateLeft(node.parent);
          node = root;
        }
      } else {
        Node<K, V> sibling = node.parent.left;
        
        if (sibling == null) {
          node = node.parent;
          continue;
        }

        if (sibling.color == RED) {
          sibling.color = BLACK;
          node.parent.color = RED;
          rotateRight(node.parent);
          sibling = node.parent.left;
        }
        
        if (sibling == null) {
          node = node.parent;
          continue;
        }

        boolean leftBlack = (sibling.left == null) || (sibling.left.color == BLACK);
        boolean rightBlack = (sibling.right == null) || (sibling.right.color == BLACK);
        
        if (leftBlack && rightBlack) {
          sibling.color = RED;
          node = node.parent;
        } else {
          if (leftBlack) {
            if (sibling.right != null) {
              sibling.right.color = BLACK;
            }
            sibling.color = RED;
            rotateLeft(sibling);
            sibling = node.parent.left;
          }
          
          if (sibling != null) {
            sibling.color = node.parent.color;
            if (sibling.left != null) {
              sibling.left.color = BLACK;
            }
          }
          
          node.parent.color = BLACK;
          rotateRight(node.parent);
          node = root;
        }
      }
    }
    
    if (node != null) {
      node.color = BLACK;
    }
  }
  
  // try to find replacement code for this
  public boolean containsUniqueValue(String attribute, String value) {
    if (root == null) {
      return false;
    }

    LinkedQueue<Node<K, V>> queue = new LinkedQueue<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      Node<K, V> current = queue.poll();

      if (current.value instanceof Patient) {
        Patient patient = (Patient) current.value;
        if (attribute.equals("icNumber")) {
          if (patient.getIcNumber().equals(value)) {
            return true;
          }
        }
      }

      if (current.left != null) {
          queue.offer(current.left);
      }
      if (current.right != null) {
          queue.offer(current.right);
      }
    }
    return false;
  }
  
  @Override
  public Iterator<Node<K, V>> iterator() {
    return new RedBlackTreeIterator();
  }
  
  private class RedBlackTreeIterator implements Iterator<Node<K, V>> {
    private Node<K, V> next;
    
    public RedBlackTreeIterator() {
      next = root;
      if (next == null) {
        return;
      }
      
      while (next.left != null) {
        next = next.left;
      }
    }
    
    @Override
    public boolean hasNext() {
      return next != null;
    }
    
    @Override
    public Node<K, V> next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in the tree");
      }
      
      Node<K, V> current = next;
      
      if (next.right != null) {
        next = next.right;
        while (next.left != null) {
          next = next.left;
        }
        return current;
      }
      
      while (true) {
        if (next.parent == null) {
          next = null;
          return current;
        }
        if (next.parent.left == next) {
          next = next.parent;
          return current;
        }
        next = next.parent;
      }
    }
    
    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() is not supported by this iterator");
    }
  }
}
