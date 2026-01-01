# Sorting Algorithms Comparison

## Table of Contents
1. [Overview](#overview)
2. [Algorithm Summaries](#summaries)
3. [Complexity Comparison](#complexity)
4. [Detailed Analysis](#detailed)
5. [When to Use Which](#when-to-use)
6. [Visual Comparisons](#visual)
7. [Code Examples](#code-examples)

---

## Overview {#overview}

Sorting is one of the most fundamental operations in computer science. Different algorithms have different trade-offs in terms of time complexity, space complexity, and stability.

### Key Metrics
- **Time Complexity**: How runtime scales with input size
- **Space Complexity**: Extra memory needed
- **Stability**: Preserves relative order of equal elements
- **In-place**: Doesn't require extra array space
- **Adaptive**: Performs better on partially sorted data

---

## Algorithm Summaries {#summaries}

### 1. Bubble Sort
**Idea**: Repeatedly swap adjacent elements if they're in wrong order. Largest element "bubbles" to the end.

**Characteristics**:
- Simple to implement
- Stable
- In-place
- Adaptive (with optimization)
- Poor performance on large datasets

### 2. Selection Sort
**Idea**: Find minimum element and swap with first unsorted position. Repeat.

**Characteristics**:
- Simple to implement
- Not stable (standard version)
- In-place
- Not adaptive
- Performs many comparisons

### 3. Insertion Sort
**Idea**: Build sorted array one element at a time by inserting into correct position.

**Characteristics**:
- Simple to implement
- Stable
- In-place
- Adaptive (good for nearly sorted data)
- Efficient for small datasets

### 4. Merge Sort
**Idea**: Divide array in half, recursively sort halves, merge sorted halves.

**Characteristics**:
- Divide and conquer approach
- Stable
- Not in-place (requires O(n) space)
- Not adaptive
- Guaranteed O(n log n) time

### 5. Quick Sort
**Idea**: Pick pivot, partition around it, recursively sort partitions.

**Characteristics**:
- Divide and conquer approach
- Not stable (standard version)
- In-place
- Not adaptive
- Fastest in practice
- Worst case O(n²) with bad pivots

### 6. Heap Sort
**Idea**: Build max heap, repeatedly extract maximum.

**Characteristics**:
- Uses heap data structure
- Not stable
- In-place
- Not adaptive
- Guaranteed O(n log n) time
- Good worst-case performance

---

## Complexity Comparison {#complexity}

### Time Complexity

| Algorithm | Best Case | Average Case | Worst Case |
|-----------|-----------|--------------|------------|
| Bubble Sort | O(n) | O(n²) | O(n²) |
| Selection Sort | O(n²) | O(n²) | O(n²) |
| Insertion Sort | O(n) | O(n²) | O(n²) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) |

### Space Complexity

| Algorithm | Space Complexity | In-Place? |
|-----------|------------------|-----------|
| Bubble Sort | O(1) | Yes |
| Selection Sort | O(1) | Yes |
| Insertion Sort | O(1) | Yes |
| Merge Sort | O(n) | No |
| Quick Sort | O(log n) | Yes |
| Heap Sort | O(1) | Yes |

### Other Properties

| Algorithm | Stable? | Adaptive? | Online? |
|-----------|---------|-----------|---------|
| Bubble Sort | Yes | Yes* | No |
| Selection Sort | No** | No | No |
| Insertion Sort | Yes | Yes | Yes |
| Merge Sort | Yes | No | No |
| Quick Sort | No | No | No |
| Heap Sort | No | No | No |

\* With early termination optimization  
\** Can be made stable with modifications

---

## Detailed Analysis {#detailed}

### Bubble Sort

#### How It Works
```
[5, 2, 8, 1, 9]
Pass 1: [2, 5, 1, 8, 9]  → 9 in position
Pass 2: [2, 1, 5, 8, 9]  → 8 in position
Pass 3: [1, 2, 5, 8, 9]  → 5 in position
Pass 4: [1, 2, 5, 8, 9]  → Already sorted
```

#### Pros & Cons
**Pros:**
- Simple to understand and implement
- Stable sort
- In-place (O(1) space)
- Can detect if already sorted

**Cons:**
- Very slow: O(n²) comparisons and swaps
- Not practical for large datasets
- Many unnecessary comparisons

#### Best Used For:
- Educational purposes
- Small datasets (< 10 elements)
- Nearly sorted data (with optimization)

---

### Selection Sort

#### How It Works
```
[5, 2, 8, 1, 9]
Find min (1): [1, 2, 8, 5, 9]
Find min (2): [1, 2, 8, 5, 9]
Find min (5): [1, 2, 5, 8, 9]
Find min (8): [1, 2, 5, 8, 9]
```

#### Pros & Cons
**Pros:**
- Simple to implement
- In-place (O(1) space)
- Minimum number of swaps: O(n)
- Performs well on small arrays

**Cons:**
- Always O(n²) time
- Not adaptive
- Not stable
- Many comparisons even on sorted data

#### Best Used For:
- When memory writes are expensive
- Small datasets
- When simplicity is priority

---

### Insertion Sort

#### How It Works
```
[5, 2, 8, 1, 9]
Insert 2: [2, 5, 8, 1, 9]
Insert 8: [2, 5, 8, 1, 9]  (already in place)
Insert 1: [1, 2, 5, 8, 9]
Insert 9: [1, 2, 5, 8, 9]  (already in place)
```

#### Pros & Cons
**Pros:**
- Simple and intuitive
- Stable sort
- In-place
- Adaptive: O(n) for nearly sorted data
- Online: can sort as data arrives
- Efficient for small datasets

**Cons:**
- O(n²) worst case
- Slow for large random datasets

#### Best Used For:
- Small datasets (< 50 elements)
- Nearly sorted data
- Online sorting (streaming data)
- As part of hybrid algorithms (Timsort)

---

### Merge Sort

#### How It Works
```
[5, 2, 8, 1, 9]
       |
   [5, 2, 8] [1, 9]
      |         |
  [5, 2] [8]  [1] [9]
    |           
  [5] [2]
   
Merge: [2, 5] [8] → [2, 5, 8]
Merge: [1] [9] → [1, 9]
Merge: [2, 5, 8] [1, 9] → [1, 2, 5, 8, 9]
```

#### Pros & Cons
**Pros:**
- Guaranteed O(n log n) time
- Stable sort
- Predictable performance
- Parallelizable
- Good for linked lists

**Cons:**
- Requires O(n) extra space
- Not in-place
- Slower than quicksort in practice
- More memory usage

#### Best Used For:
- When stability is required
- Large datasets
- External sorting (data doesn't fit in memory)
- Linked lists
- When worst-case guarantee needed

---

### Quick Sort

#### How It Works
```
[5, 2, 8, 1, 9]  pivot = 9
Partition: [5, 2, 8, 1] 9 []
           pivot = 1
Partition: [] 1 [5, 2, 8]
                pivot = 8
Partition: [5, 2] 8 []
           pivot = 2
Final: [1, 2, 5, 8, 9]
```

#### Pros & Cons
**Pros:**
- Fastest in practice
- In-place (O(log n) space)
- Cache-friendly
- Simple to implement
- Average case O(n log n)

**Cons:**
- Worst case O(n²) (with bad pivots)
- Not stable
- Not adaptive
- Performance depends on pivot selection

#### Best Used For:
- General-purpose sorting
- Large datasets
- When average-case performance matters
- Arrays (not lists)
- When extra space is limited

---

### Heap Sort

#### How It Works
```
1. Build max heap: [9, 8, 5, 2, 1]
2. Extract max (9): [8, 2, 5, 1] | 9
3. Extract max (8): [5, 2, 1] | 8, 9
4. Extract max (5): [2, 1] | 5, 8, 9
5. Extract max (2): [1] | 2, 5, 8, 9
6. Extract max (1): [] | 1, 2, 5, 8, 9
```

#### Pros & Cons
**Pros:**
- Guaranteed O(n log n) time
- In-place (O(1) space)
- No worst-case degradation
- No recursion stack needed

**Cons:**
- Not stable
- Not adaptive
- Not cache-friendly
- Slower than quicksort in practice
- Complex to implement

#### Best Used For:
- When O(n log n) guarantee needed
- When O(1) space required
- Real-time systems (predictable performance)
- When quicksort's worst case is concern

---

## When to Use Which {#when-to-use}

### Decision Tree

```
Is dataset small (< 50)?
├─ Yes → Use Insertion Sort
└─ No → Continue

Is stability required?
├─ Yes → Use Merge Sort or Timsort
└─ No → Continue

Is extra space available?
├─ No → Use Quick Sort (with good pivot) or Heap Sort
└─ Yes → Use Merge Sort

Is data nearly sorted?
├─ Yes → Use Insertion Sort or Timsort
└─ No → Use Quick Sort

Worst-case guarantee needed?
├─ Yes → Use Merge Sort or Heap Sort
└─ No → Use Quick Sort
```

### Recommendations by Scenario

| Scenario | Best Choice | Why |
|----------|-------------|-----|
| General purpose | Quick Sort | Fastest average case |
| Need stability | Merge Sort | Stable + O(n log n) |
| Limited memory | Heap Sort | In-place + O(n log n) |
| Small dataset | Insertion Sort | Simple + efficient for small n |
| Nearly sorted | Insertion Sort | O(n) best case |
| Linked list | Merge Sort | No random access needed |
| External sorting | Merge Sort | Sequential access pattern |
| Real-time system | Heap Sort | Predictable performance |

---

## Visual Comparisons {#visual}

### Comparisons for n=10,000

```
Algorithm       Time (ms)    Space      Stable   Adaptive
═══════════════════════════════════════════════════════════
Bubble Sort     ~1200        O(1)       Yes      Yes*
Selection Sort  ~800         O(1)       No       No
Insertion Sort  ~600         O(1)       Yes      Yes
Merge Sort      ~15          O(n)       Yes      No
Quick Sort      ~8           O(log n)   No       No
Heap Sort       ~20          O(1)       No       No
```

\* With optimization

### Swaps vs Comparisons

```
Algorithm       Comparisons   Swaps      Total Ops
═══════════════════════════════════════════════════
Bubble Sort     O(n²)        O(n²)      O(n²)
Selection Sort  O(n²)        O(n)       O(n²)
Insertion Sort  O(n²)        O(n²)      O(n²)
Merge Sort      O(n log n)   O(n log n) O(n log n)
Quick Sort      O(n log n)   O(n log n) O(n log n)
Heap Sort       O(n log n)   O(n log n) O(n log n)
```

---

## Code Examples {#code-examples}

### Quick Comparison Code (Kotlin)

```kotlin
// Bubble Sort
fun bubbleSort(arr: IntArray) {
    for (i in arr.indices) {
        for (j in 0 until arr.size - 1) {
            if (arr[j] > arr[j + 1]) {
                arr.swap(j, j + 1)
            }
        }
    }
}

// Insertion Sort
fun insertionSort(arr: IntArray) {
    for (i in 1 until arr.size) {
        val key = arr[i]
        var j = i - 1
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j]
            j--
        }
        arr[j + 1] = key
    }
}

// Quick Sort
fun quickSort(arr: IntArray, low: Int, high: Int) {
    if (low < high) {
        val pi = partition(arr, low, high)
        quickSort(arr, low, pi - 1)
        quickSort(arr, pi + 1, high)
    }
}

// Merge Sort
fun mergeSort(arr: IntArray, left: Int, right: Int) {
    if (left < right) {
        val mid = (left + right) / 2
        mergeSort(arr, left, mid)
        mergeSort(arr, mid + 1, right)
        merge(arr, left, mid, right)
    }
}
```

---

## Key Takeaways

1. ✓ No single "best" sorting algorithm for all cases
2. ✓ Quick Sort is fastest general-purpose algorithm
3. ✓ Merge Sort when stability or worst-case guarantee needed
4. ✓ Insertion Sort for small or nearly sorted data
5. ✓ Heap Sort when in-place + O(n log n) required
6. ✓ Consider trade-offs: time, space, stability
7. ✓ Hybrid algorithms (Timsort) combine best of multiple algorithms
8. ✓ Know your data characteristics and requirements

---

## Further Reading

- Timsort (Python's default): Hybrid of Merge + Insertion
- Introsort (C++ STL): Hybrid of Quick + Heap
- Radix Sort: Non-comparison sort for integers
- Counting Sort: O(n+k) for limited range
- External sorting algorithms
- Parallel sorting algorithms
