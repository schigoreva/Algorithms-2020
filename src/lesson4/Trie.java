package lesson4;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        Map<Character, Node> children = new LinkedHashMap<>();
    }

    private Node root = new Node();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Сложная
     */
    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

    public class TrieIterator implements Iterator<String> {
        private LinkedList<Iterator<Map.Entry<Character, Node>>> queue = new LinkedList<>();
        private String currentString;
        private StringBuilder nextString;

        private TrieIterator() {
            nextString = new StringBuilder();
            if (size > 0) {
                queue.add(root.children.entrySet().iterator());
                findNext();
            }
        }

        private void findNext() {
            Iterator<Map.Entry<Character, Node>> nextStringNode = queue.getLast();
            while (nextStringNode != null && !nextStringNode.hasNext()) {
                queue.removeLast();
                if (queue.isEmpty()) {
                    nextStringNode = null;
                } else {
                    nextString.deleteCharAt(nextString.length() - 1);
                    nextStringNode = queue.getLast();
                }
            }
            if (nextStringNode != null) {
                while (nextStringNode.hasNext()) {
                    Map.Entry<Character, Node> child = nextStringNode.next();
                    nextStringNode = new HashMap<>(child.getValue().children).entrySet().iterator();
                    queue.addLast(nextStringNode);
                    nextString.append(child.getKey());
                }
                if (nextString.charAt(nextString.length() - 1) != (char) 0) {
                    findNext();
                } else {
                    nextString.deleteCharAt(nextString.length() - 1);
                    queue.removeLast();
                }
            }
        }

        @Override
        // Трудоемкость - О(1) ; Ресурсоемкость - О(1)
        public boolean hasNext() {
            return nextString.length() != 0;
        }

        @Override
        public String next() throws IllegalStateException {
            // Трудоемкость - О(n) ; Ресурсоемкость - О(n), где n длина строки
            if (!hasNext()) {
                throw new IllegalStateException();
            } else {
                currentString = nextString.toString();
                findNext();
                return currentString;
            }
        }

        @Override
        public void remove() throws IllegalStateException {
            // Трудоемкость - О(n), Ресурсоемкость - О(1), где n длина строки
            if (currentString == null) {
                throw new IllegalStateException();
            } else {
                Node node = findNode(currentString);
                if (node != null) {
                    Trie.this.remove(currentString);
                    currentString = null;
                }
            }
        }
    }
}
