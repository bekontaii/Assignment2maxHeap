package cli;

import algorithms.MaxHeap;
import metrics.PerformanceTracker;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * CLI-бенчмарк MaxHeap.
 * Пример запуска:
 *   java -jar target/assignment2-maxheap-1.0.0.jar --n 10000 --runs 3 --csv results.csv --dist random
 */
public class BenchmarkRunner {
    public static void main(String[] args) {
        int n = 1000;
        int runs = 3;
        String csv = "heap-bench.csv";
        String dist = "random";

        // читаем аргументы
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--n"    -> n = Integer.parseInt(args[++i]);
                case "--runs" -> runs = Integer.parseInt(args[++i]);
                case "--csv"  -> csv = args[++i];
                case "--dist" -> dist = args[++i]; // random|sorted|reverse|nearly
            }
        }

        var rnd = new SecureRandom();
        int[] base = genData(n, dist, rnd);
        PerformanceTracker tracker = new PerformanceTracker();

        for (int t = 0; t < runs; t++) {
            int[] copy = Arrays.copyOf(base, base.length);

            // 1) build_heap
            tracker.reset();
            long start = System.currentTimeMillis();
            MaxHeap heap = new MaxHeap(copy, tracker);
            long elapsed = System.currentTimeMillis() - start;
            tracker.appendCsv(csv, "build_heap:" + dist, n, elapsed);

            // 2) insert_n
            tracker.reset();
            heap = new MaxHeap(16, tracker);
            start = System.currentTimeMillis();
            for (int v : copy) heap.insert(v);
            elapsed = System.currentTimeMillis() - start;
            tracker.appendCsv(csv, "insert_n:" + dist, n, elapsed);

            // 3) increase_key_batch
            tracker.reset();
            start = System.currentTimeMillis();
            int ops = Math.max(1, n / 10);
            for (int k = 0; k < ops; k++) {
                int idx = rnd.nextInt(heap.size());
                int curVal = heap.get(idx); // <-- теперь используем get()
                int newKey = curVal + rnd.nextInt(1000) + 1; // всегда больше
                heap.increaseKey(idx, newKey);
            }
            elapsed = System.currentTimeMillis() - start;
            tracker.appendCsv(csv, "increase_key_batch:" + dist, n, elapsed);

            // 4) extract_all
            tracker.reset();
            start = System.currentTimeMillis();
            long checksum = 0;
            while (!heap.isEmpty()) checksum += heap.extractMax();
            elapsed = System.currentTimeMillis() - start;
            tracker.appendCsv(csv, "extract_all:" + dist, n, elapsed);

            if (checksum == 42) System.out.print(""); // не даём оптимизировать
        }
        System.out.println("Done. Results -> " + csv);
    }

    // генерация входных массивов
    private static int[] genData(int n, String dist, SecureRandom rnd) {
        int[] base = new int[n];
        switch (dist) {
            case "sorted" -> {
                for (int i = 0; i < n; i++) base[i] = i;
            }
            case "reverse" -> {
                for (int i = 0; i < n; i++) base[i] = n - i;
            }
            case "nearly" -> {
                for (int i = 0; i < n; i++) base[i] = i;
                for (int i = 0; i < Math.max(1, n / 20); i++) {
                    int i1 = rnd.nextInt(n);
                    int i2 = Math.min(n - 1, i1 + rnd.nextInt(5));
                    int t = base[i1]; base[i1] = base[i2]; base[i2] = t;
                }
            }
            default -> { // random
                base = rnd.ints(n, Integer.MIN_VALUE, Integer.MAX_VALUE).toArray();
            }
        }
        return base;
    }
}
