package algorithms;

import metrics.PerformanceTracker;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Бинарная Max-куча (array-backed).
 * Поддерживает операции insert, max, extractMax, increaseKey.
 */
public class MaxHeap {
    private int[] a;
    private int size;
    private final PerformanceTracker tr;

    public MaxHeap(int capacity, PerformanceTracker tracker) {
        if (capacity < 0) throw new IllegalArgumentException("capacity < 0");
        this.a = new int[Math.max(1, capacity)];
        this.size = 0;
        this.tr = tracker;
        if (tr != null) tr.incAllocations(1);
    }

    public MaxHeap(int[] data, PerformanceTracker tracker) {
        this.a = Arrays.copyOf(data, data.length);
        this.size = data.length;
        this.tr = tracker;
        if (tr != null) {
            tr.incAllocations(1);
            tr.incArrayAccesses(data.length * 2); // чтение+запись при копировании
        }
        buildHeap();
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public int max() {
        if (size == 0) throw new NoSuchElementException("heap is empty");
        if (tr != null) tr.incArrayAccesses(1);
        return a[0];
    }

    /** Простой геттер для тестов и increaseKey */
    public int get(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException();
        if (tr != null) tr.incArrayAccesses(1);
        return a[i];
    }

    public void insert(int key) {
        ensureCapacity(size + 1);
        a[size] = key;
        if (tr != null) tr.incArrayAccesses(1);
        size++;
        siftUp(size - 1);
    }

    public void increaseKey(int i, int newKey) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException();
        int cur = a[i];
        if (newKey < cur) throw new IllegalArgumentException("newKey < current key");
        a[i] = newKey;
        if (tr != null) tr.incArrayAccesses(2);
        siftUp(i);
    }

    public int extractMax() {
        if (size == 0) throw new NoSuchElementException("heap is empty");
        int max = a[0];
        if (tr != null) tr.incArrayAccesses(1);
        swap(0, size - 1);
        size--;
        siftDown(0);
        return max;
    }

    // ===== внутренние методы =====

    private void buildHeap() {
        for (int i = parent(size - 1); i >= 0; i--) siftDown(i);
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = parent(i);
            if (tr != null) tr.incComparisons(1);
            if (a[p] < a[i]) {
                swap(p, i);
                i = p;
            } else break;
        }
    }

    private void siftDown(int i) {
        while (left(i) < size) {
            int l = left(i), r = right(i);
            int largest = i;
            if (l < size && a[l] > a[largest]) largest = l;
            if (r < size && a[r] > a[largest]) largest = r;
            if (largest != i) {
                swap(i, largest);
                i = largest;
            } else break;
        }
    }

    private void ensureCapacity(int minCap) {
        if (minCap <= a.length) return;
        a = Arrays.copyOf(a, a.length * 2);
        if (tr != null) tr.incAllocations(1);
    }

    private void swap(int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
        if (tr != null) tr.incSwaps(1);
    }

    private static int parent(int i) { return (i - 1) / 2; }
    private static int left(int i)   { return 2 * i + 1; }
    private static int right(int i)  { return 2 * i + 2; }
}
