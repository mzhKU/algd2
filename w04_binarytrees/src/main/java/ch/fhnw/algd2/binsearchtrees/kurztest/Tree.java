package ch.fhnw.algd2.binsearchtrees.kurztest;

public class Tree<K extends Comparable<? super K>> {

    private Node<K> node;

    private static class Node<K> {
        final K key = (K) new Object();
        Node<K> left, right;
    }

    private Node<K> root;

    public K minKey() {
        Node<K> n = root;
        if (n == null) {
            throw new IllegalArgumentException("Empty Tree.");
        }
        while (n.left != null) {
            n = n.left;
        }
        return n.key;
    }


    public int size() {
    }


    public Node getChildren(Node node) {
        return node.left;
    }

}
