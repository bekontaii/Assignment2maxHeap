# Assignment 2 – Algorithmic Analysis and Peer Code Review
**Pair 4 — Student B (Max-Heap Implementation)**  
**Author:** Uzbekbayev Bekarys  
**Group:** SE-2438

---

## 📘 Project Overview
This project implements and analyzes the **Max-Heap** data structure as part of the *Algorithmic Analysis and Peer Code Review* assignment.  
The implementation is written in **Java 17** and follows Maven standards for testing and benchmarking.  
It includes both theoretical and empirical performance evaluation according to the assignment specification.

---

## ⚙️ Features

- **Algorithms:**
    - `insert(key)` – inserts an element, maintaining heap property.
    - `extractMax()` – removes and returns the maximum element.
    - `increaseKey(i, newKey)` – increases the key and rebalances heap.
    - `buildHeap(array)` – bottom-up heap construction (O(n)).

- **Performance Tracker:**
    - Measures comparisons, swaps, array accesses, allocations, and method calls.

- **Benchmark Runner:**
    - CLI-based benchmarking for multiple input sizes (n = 100, 1000, 10000, 100000).
    - Supports input distributions: `random`, `sorted`, `reverse`, `nearly-sorted`.

- **Testing:**
    - Comprehensive **JUnit 5** tests covering:
        - Empty heap cases
        - Single and duplicate elements
        - Increase-key correctness
        - Random stress tests (n = 1000)

---


---

## 🧮 Complexity Analysis

| Operation     | Best (Ω) | Average (Θ) | Worst (O) | Space |
|----------------|-----------|--------------|------------|--------|
| insert         | Ω(1)      | Θ(log n)     | O(log n)   | O(1)  |
| extractMax     | Ω(1)      | Θ(log n)     | O(log n)   | O(1)  |
| increaseKey    | Ω(1)      | Θ(log n)     | O(log n)   | O(1)  |
| buildHeap      | Ω(n)      | Θ(n)         | O(n)       | O(1)  |

---

## 📊 Empirical Evaluation

Benchmarks were performed using random input distributions for input sizes:
n = 100, 1000, 10000, 100000



# 🧾 Report

📄 Assignment2_MaxHeap_Report_Uzbekbayev_Bekarys.pdf

Contains:

Algorithm overview

Complexity analysis

Code review of partner’s (Min-Heap) implementation

Empirical validation (results & graph)

Final conclusions

# Key Takeaways

Max-Heap operations follow the expected O(log n) behavior.

Empirical measurements align with theoretical complexity.

Implementation satisfies all assignment requirements:

correctness,

performance tracking,

clean Git workflow,

and documentation.


#  Conclusion

This project demonstrates both theoretical and empirical mastery of heap-based algorithms.
The implementation is efficient, modular, and fully tested — meeting all performance, documentation, and analysis criteria.