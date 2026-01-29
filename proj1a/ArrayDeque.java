public class ArrayDeque<T> {

    private int size;
    private T[] array;
    private int front;//第一个元素索引
    private int rear;//最后一个元素后一个的索引
    private int capacity;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        capacity = 8;
        front = 0;
        rear = 0;
        size = 0;
    }

    private boolean isfull() {
        return size == capacity;
    }
    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        int index = front;
        for (int i = 0; i < size; i++) {
            newArray[i] = array[index];
            index = (index + 1) % capacity;
        }
        front = 0;
        rear = size;
        array = newArray;
        capacity = newCapacity;
    }
    private void update() {
        if (isfull()) {
            resize(capacity*2);
        } else if (size > 0 && capacity > 16 && size*4 <= capacity) {
            resize(Math.max(capacity / 2, 16));
        }
    }

    public void addFirst(T item) {
        front = (front - 1) % capacity;
        array[front] = item;
        size += 1;
        update();
    }

    public void addLast(T item) {
        array[rear] = item;
        rear = (rear + 1) % capacity;
        size += 1;
        update();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int index = front;
        while (index != rear) {//rear只能表示最后一个索引的后一位，否则循环条件不好表示
            System.out.print(array[index]);
            System.out.print(' ');
            index = (index + 1) % capacity;
        }
    }

    public T removeFirst() {
        T value = array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        size -= 1;
        update();
        return value;
    }

    public T removeLast() {
        rear = (rear - 1) % capacity;
        T value = array[rear];
        array[rear] = null;
        size -= 1;
        update();
        return value;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int i = front;
        for (int j = 0; j < index; j++) {
            i = (i + 1) % capacity;
        }
        return array[i];
    }
}
