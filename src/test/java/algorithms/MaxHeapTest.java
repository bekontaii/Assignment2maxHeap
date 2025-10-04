package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для MaxHeap
 */
class MaxHeapTest {

    @Test
    void emptyHeap_ops() {
        PerformanceTracker tr = new PerformanceTracker();
        MaxHeap h = new MaxHeap(0, tr);

        assertTrue(h.isEmpty(), "Пустая куча должна быть пустой");
        assertEquals(0, h.size(), "Размер пустой кучи = 0");

        assertThrows(NoSuchElementException.class, h::max, "max() на пустой куче должно кидать исключение");
        assertThrows(NoSuchElementException.class, h::extractMax, "extractMax() на пустой куче должно кидать исключение");
    }

    @Test
    void singleElement_insertAndExtract() {
        MaxHeap h = new MaxHeap(1, new PerformanceTracker());

        h.insert(42);
        assertFalse(h.isEmpty(), "После вставки куча не должна быть пустой");
        assertEquals(1, h.size(), "Размер должен быть 1");
        assertEquals(42, h.max(), "max() должен вернуть вставленный элемент");
        assertEquals(42, h.extractMax(), "extractMax() должен вернуть вставленный элемент");
        assertTrue(h.isEmpty(), "После извлечения куча снова пустая");
    }

    @Test
    void multipleElements_order() {
        MaxHeap h = new MaxHeap(4, new PerformanceTracker());

        h.insert(5);
        h.insert(7);
        h.insert(3);
        h.insert(7); // дубликат

        assertEquals(4, h.size());
        assertEquals(7, h.max(), "Максимум должен быть 7");

        assertEquals(7, h.extractMax());
        assertEquals(7, h.extractMax()); // дубликат
        assertEquals(5, h.extractMax());
        assertEquals(3, h.extractMax());
        assertTrue(h.isEmpty());
    }

    @Test
    void buildFromArray_heapifyWorks() {
        int[] arr = {3, 1, 6, 5, 2, 4};
        MaxHeap h = new MaxHeap(arr, new PerformanceTracker());

        assertEquals(6, h.size());
        assertEquals(6, h.max(), "Максимум должен быть 6");
    }

    @Test
    void increaseKey_increasesAndSiftsUp() {
        MaxHeap h = new MaxHeap(4, new PerformanceTracker());

        h.insert(10);
        h.insert(20);
        h.insert(5);

        // нельзя уменьшить ключ
        assertThrows(IllegalArgumentException.class, () -> h.increaseKey(2, 1));

        // увеличиваем
        h.increaseKey(2, 30);
        assertEquals(30, h.max(), "Теперь максимум должен быть 30");
    }

    @Test
    void randomStress_smallHeap() {
        Random rnd = new Random(0);
        MaxHeap h = new MaxHeap(0, new PerformanceTracker());

        int n = 1000;
        int max = Integer.MIN_VALUE;

        // вставляем случайные значения
        for (int i = 0; i < n; i++) {
            int v = rnd.nextInt();
            h.insert(v);
            max = Math.max(max, v);
        }

        // проверяем, что максимум совпадает
        assertEquals(max, h.max());

        // проверяем порядок убывания при извлечении
        int prev = Integer.MAX_VALUE;
        while (!h.isEmpty()) {
            int cur = h.extractMax();
            assertTrue(cur <= prev, "Элементы должны извлекаться в порядке невозрастания");
            prev = cur;
        }
    }
}
