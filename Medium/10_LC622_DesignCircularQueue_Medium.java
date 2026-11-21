class MyCircularQueue {
    private final int[] buf;
    private final int capacity;
    private int front;
    private int size;

    public MyCircularQueue(int k) {
        this.buf = new int[k];
        this.capacity = k;
        this.front = 0;
        this.size = 0;
    }

    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        int rearIdx = (front + size) % capacity;
        buf[rearIdx] = value;
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        front = (front + 1) % capacity;
        size--;
        return true;
    }

    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return buf[front];
    }

    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        int rearIdx = (front + size - 1) % capacity;
        return buf[rearIdx];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
}
