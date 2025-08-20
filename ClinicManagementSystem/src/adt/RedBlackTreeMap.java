package adt;

import java.io.Serializable;

public class RedBlackTreeMap<K extends Comparable<K>, V> implements MapInterface<K, V>, Serializable {
  private static final boolean RED = false;
  private static final boolean BLACK = true;

  @SuppressWarnings("hiding")
  public class Node<K, V> {
    K key;
    V value;
    Node<K, V> left, right, parent;
    boolean color;

    Node(K key, V value, Node<K, V> parent) {
      this.key = key;
      this.value = value;
      this.parent = parent;
      this.color = RED; // new nodes are RED by default
      this.left = this.right = null;
    }
  }

  private Node<K, V> root;
  private int size;

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

    QueueInterface<Node<K, V>> queue = new LinkedQueue<>();
    queue.enqueue(root);

    while (!queue.isEmpty()) {
      Node<K, V> current = queue.dequeue();

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
        queue.enqueue(current.left);
      }
      if (current.right != null) {
        queue.enqueue(current.right);
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

  // additional methods
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
    if (replacement != null) {
      replacement.parent = node.parent;
      if (node.parent == null) {
        root = replacement;
      } else if (node == node.parent.left) {
        node.parent.left = replacement;
      } else {
        node.parent.right = replacement;
      }
    } else if (node.parent == null) {
      root = null;
    } else {
      if (node.color == BLACK) {
        fixAfterDeletion(node);
      }

      if (node.parent.left == node) {
        node.parent.left = null;
      } else {
        node.parent.right = null;
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
  
  public ListInterface<K> getKeys() {
    ListInterface<K> keys = new ArrayList<>();

    if (root != null) {
      inOrderTraversal(root, keys);
    }

    return keys;
  }
  
  // recursion
  private void inOrderTraversal(Node<K, V> node, ListInterface<K> keys) {
    if (node == null) {
      return;
    }
    
    inOrderTraversal(node.left, keys);
    keys.add(node.key);
    inOrderTraversal(node.right, keys);
  }

  // fix helper methods
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
    while (node != root && node.color == BLACK) {
      if (node == node.parent.left) {
        Node<K, V> sibling = node.parent.right;
        
        if (sibling.color == RED) {
          sibling.color = BLACK;
          node.parent.color = RED;
          rotateLeft(node.parent);
          sibling = node.parent.right;
        }

        if (sibling.left.color == BLACK && sibling.right.color == BLACK) {
          sibling.color = RED;
          node = node.parent;
        } else {
          if (sibling.right.color == BLACK) {
            sibling.left.color = BLACK;
            sibling.color = RED;
            rotateRight(sibling);
            sibling = node.parent.right;
          }

          sibling.color = node.parent.color;
          node.parent.color = BLACK;
          sibling.right.color = BLACK;
          rotateLeft(node.parent);
          node = root;
        }
      } else {
        Node<K, V> sibling = node.parent.left;

        if (sibling.color == RED) {
          sibling.color = BLACK;
          node.parent.color = RED;
          rotateRight(node.parent);
          sibling = node.parent.left;
        }

        if (sibling.right.color == BLACK && sibling.left.color == BLACK) {
          sibling.color = RED;
          node = node.parent;
        } else {
          if (sibling.left.color == BLACK) {
            sibling.right.color = BLACK;
            sibling.color = RED;
            rotateLeft(sibling);
            sibling = node.parent.left;
          }

          sibling.color = node.parent.color;
          node.parent.color = BLACK;
          sibling.left.color = BLACK;
          rotateRight(node.parent);
          node = root;
        }
      }
    }
    node.color = BLACK;
  }
}
