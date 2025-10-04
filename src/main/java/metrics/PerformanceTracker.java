package metrics;

import java.io.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Счётчик ключевых операций для эмпирического анализа.
 * Сбрасывается через reset(), пишет показатели в CSV (appendCsv).
 */
public class PerformanceTracker {
    private final AtomicLong comparisons = new AtomicLong();
    private final AtomicLong swaps = new AtomicLong();
    private final AtomicLong arrayAccesses = new AtomicLong();
    private final AtomicLong allocations = new AtomicLong();
    private final AtomicLong methodCalls = new AtomicLong();

    public void incComparisons(long v)   { comparisons.addAndGet(v); }
    public void incSwaps(long v)         { swaps.addAndGet(v); }
    public void incArrayAccesses(long v) { arrayAccesses.addAndGet(v); }
    public void incAllocations(long v)   { allocations.addAndGet(v); }
    public void incMethodCalls(long v)   { methodCalls.addAndGet(v); }

    public long getComparisons()   { return comparisons.get(); }
    public long getSwaps()         { return swaps.get(); }
    public long getArrayAccesses() { return arrayAccesses.get(); }
    public long getAllocations()   { return allocations.get(); }
    public long getMethodCalls()   { return methodCalls.get(); }

    public void reset() {
        comparisons.set(0);
        swaps.set(0);
        arrayAccesses.set(0);
        allocations.set(0);
        methodCalls.set(0);
    }

    /** Добавляет строку в CSV. Если файла нет — пишет заголовок. */
    public synchronized void appendCsv(String filePath, String label, int n, long millis) {
        File f = new File(filePath);
        boolean writeHeader = !f.exists();
        try (FileWriter fw = new FileWriter(f, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            if (writeHeader) {
                bw.write("label,n,time_ms,comparisons,swaps,array_accesses,allocations,method_calls\n");
            }
            bw.write(String.format("%s,%d,%d,%d,%d,%d,%d,%d\n", label, n, millis,
                    getComparisons(), getSwaps(), getArrayAccesses(), getAllocations(), getMethodCalls()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
