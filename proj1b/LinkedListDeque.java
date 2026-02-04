public class LinkedListDeque<T> implements Deque<T> {

    private static class Node<T> {
        private Node<T> prev;
        private final T item;
        private Node<T> next;

        public Node() {
            prev = null;
            item = null;
            next = null;
        }

        public Node(Node<T> p, T i, Node<T> n) {
            prev = p;
            item = i;
            next = n;
        }

        public Node(T i) {
            prev = null;
            item = i;
            next = null;
        }
    }

    private final Node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node<>();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }
    /*
    public LinkedListDeque(T i) {
        size = 0;
        sentinel = new Node<>();
        Node<T> newNode = new Node<>(i);
        sentinel.next = newNode;
        sentinel.prev = newNode;
        newNode.prev = sentinel;
        newNode.next = sentinel;
    }
    */
    @Override
    public void addFirst(T item) {
        Node<T> newNode = new Node<>(sentinel, item, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;

        size += 1;
    }
    @Override
    public void addLast(T item) {
        Node<T> newNode = new Node<>(sentinel.prev, item, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;

        size += 1;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        Node<T> p = sentinel;
        while (p.next != sentinel) {
            System.out.print(p.next.item);
            System.out.print(' ');
            p = p.next;
        }
    }
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T value = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return value;
        }
    }
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T value = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return value;
        }
    }
    @Override
    public T get(int index) {
        if (index + 1 > size || size < 0) {
            return null;
        }
        Node<T> p = sentinel;
        for (int i = 0; i <= index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index + 1 > size || size < 0) {
            return null;
        }
        return getRecursiveHelper(sentinel, index);
    }
    private T getRecursiveHelper(Node<T> p, int index) {
        if (index == 0) {
            return p.next.item;
        } else {
            return getRecursiveHelper(p.next, index - 1);
        }
    }

}
