import java.util.HashMap;
import java.util.Map;

class LFUCache {
    private static final class Node {
        final int key;
        int value;
        int freq;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    private static final class DoublyLinkedList {
        private final Node head;
        private final Node tail;
        private int size;

        DoublyLinkedList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addFirst(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
            size--;
        }

        Node removeLast() {
            if (size == 0) {
                return null;
            }
            Node last = tail.prev;
            remove(last);
            return last;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }

    private final int capacity;
    private int minFreq;
    private final Map<Integer, Node> keyMap;
    private final Map<Integer, DoublyLinkedList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.keyMap = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    public int get(int key) {
        Node node = keyMap.get(key);
        if (node == null) {
            return -1;
        }
        promote(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        Node existing = keyMap.get(key);
        if (existing != null) {
            existing.value = value;
            promote(existing);
            return;
        }

        if (keyMap.size() == capacity) {
            DoublyLinkedList minList = freqMap.get(minFreq);
            Node evicted = minList.removeLast();
            if (evicted != null) {
                keyMap.remove(evicted.key);
            }
        }

        Node newNode = new Node(key, value);
        keyMap.put(key, newNode);
        freqMap.computeIfAbsent(1, k -> new DoublyLinkedList()).addFirst(newNode);
        minFreq = 1;
    }

    private void promote(Node node) {
        int oldFreq = node.freq;
        DoublyLinkedList oldList = freqMap.get(oldFreq);
        oldList.remove(node);

        if (oldFreq == minFreq && oldList.isEmpty()) {
            minFreq++;
        }

        node.freq = oldFreq + 1;
        freqMap.computeIfAbsent(node.freq, k -> new DoublyLinkedList()).addFirst(node);
    }
}
