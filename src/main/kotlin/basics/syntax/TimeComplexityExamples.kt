/**
 * ============================================================================
 * TOPIC: Time Complexity Examples in Kotlin
 * DIFFICULTY: Intermediate
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * Time complexity describes how an algorithm's runtime scales with input size.
 * Understanding complexity is crucial for writing efficient code. This file
 * demonstrates various time complexities with practical examples.
 * 
 * KEY CONCEPTS:
 * 1. Big O notation basics
 * 2. O(1) - Constant time
 * 3. O(log n) - Logarithmic time
 * 4. O(n) - Linear time
 * 5. O(n log n) - Linearithmic time
 * 6. O(n²) - Quadratic time
 * 7. O(2ⁿ) - Exponential time
 * 
 * ============================================================================
 * BIG O NOTATION
 * ============================================================================
 * 
 * COMPLEXITY RANKING (Best to Worst):
 * O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(n³) < O(2ⁿ) < O(n!)
 * 
 * INPUT SIZE LIMITS (approx):
 * O(1), O(log n): Any size
 * O(n): Up to 10^8
 * O(n log n): Up to 10^6
 * O(n²): Up to 10^4
 * O(n³): Up to 500
 * O(2ⁿ): Up to 20
 * O(n!): Up to 11
 * 
 * ============================================================================
 */

package basics.syntax

import kotlin.math.sqrt

class TimeComplexityExamples {
    
    /**
     * O(1) - CONSTANT TIME
     * Runtime doesn't depend on input size
     */
    fun constantTimeExamples() {
        println("=== O(1) - Constant Time ===")
        
        // Array access
        fun getFirstElement(arr: IntArray): Int {
            return arr[0]  // Always one operation
        }
        
        // Hash map lookup
        fun mapLookup(map: Map<String, Int>, key: String): Int? {
            return map[key]  // Hash table lookup is O(1)
        }
        
        // Arithmetic operations
        fun add(a: Int, b: Int) = a + b  // One operation
        
        // Example
        val numbers = intArrayOf(1, 2, 3, 4, 5)
        println("First element: ${getFirstElement(numbers)}")
        println("Complexity: O(1) - always same time regardless of array size")
        
        println()
    }
    
    /**
     * O(log n) - LOGARITHMIC TIME
     * Halves problem size each iteration
     */
    fun logarithmicTimeExamples() {
        println("=== O(log n) - Logarithmic Time ===")
        
        // Binary search
        fun binarySearch(arr: IntArray, target: Int): Int {
            var left = 0
            var right = arr.size - 1
            
            while (left <= right) {
                val mid = left + (right - left) / 2
                when {
                    arr[mid] == target -> return mid
                    arr[mid] < target -> left = mid + 1
                    else -> right = mid - 1
                }
            }
            return -1
        }
        
        // Powers of 2
        fun countPowersOf2(n: Int): Int {
            var count = 0
            var num = 1
            while (num <= n) {
                count++
                num *= 2  // Halving the problem space
            }
            return count
        }
        
        // Example
        val sorted = intArrayOf(1, 3, 5, 7, 9, 11, 13, 15)
        println("Binary search for 7: ${binarySearch(sorted, 7)}")
        println("Powers of 2 up to 100: ${countPowersOf2(100)}")
        println("Complexity: O(log n) - very efficient for large inputs")
        
        println()
    }
    
    /**
     * O(n) - LINEAR TIME
     * Examines each element once
     */
    fun linearTimeExamples() {
        println("=== O(n) - Linear Time ===")
        
        // Sum of array
        fun sumArray(arr: IntArray): Int {
            var sum = 0
            for (num in arr) {  // Visits each element once
                sum += num
            }
            return sum
        }
        
        // Find maximum
        fun findMax(arr: IntArray): Int? {
            if (arr.isEmpty()) return null
            var max = arr[0]
            for (num in arr) {  // One pass through array
                if (num > max) max = num
            }
            return max
        }
        
        // Linear search
        fun linearSearch(arr: IntArray, target: Int): Int {
            for (i in arr.indices) {  // May check all elements
                if (arr[i] == target) return i
            }
            return -1
        }
        
        // Example
        val numbers = intArrayOf(5, 2, 8, 1, 9, 3)
        println("Sum: ${sumArray(numbers)}")
        println("Max: ${findMax(numbers)}")
        println("Search for 9: ${linearSearch(numbers, 9)}")
        println("Complexity: O(n) - scales linearly with input")
        
        println()
    }
    
    /**
     * O(n log n) - LINEARITHMIC TIME
     * Most efficient sorting algorithms
     */
    fun linearithmicTimeExamples() {
        println("=== O(n log n) - Linearithmic Time ===")
        
        // Helper function for merging - defined before use
        fun merge(left: IntArray, right: IntArray): IntArray {
            val result = IntArray(left.size + right.size)
            var i = 0
            var j = 0
            var k = 0
            
            while (i < left.size && j < right.size) {
                if (left[i] <= right[j]) {
                    result[k++] = left[i++]
                } else {
                    result[k++] = right[j++]
                }
            }
            
            while (i < left.size) result[k++] = left[i++]
            while (j < right.size) result[k++] = right[j++]
            
            return result
        }
        
        // Merge sort
        fun mergeSort(arr: IntArray): IntArray {
            if (arr.size <= 1) return arr
            
            val mid = arr.size / 2
            val left = mergeSort(arr.sliceArray(0 until mid))
            val right = mergeSort(arr.sliceArray(mid until arr.size))
            
            return merge(left, right)
        }
        
        // Example
        val unsorted = intArrayOf(5, 2, 8, 1, 9, 3)
        println("Unsorted: ${unsorted.contentToString()}")
        println("Sorted: ${mergeSort(unsorted).contentToString()}")
        println("Complexity: O(n log n) - optimal for comparison sorting")
        
        println()
    }
    
    /**
     * O(n²) - QUADRATIC TIME
     * Nested loops over input
     */
    fun quadraticTimeExamples() {
        println("=== O(n²) - Quadratic Time ===")
        
        // Bubble sort
        fun bubbleSort(arr: IntArray) {
            for (i in arr.indices) {  // Outer loop: n times
                for (j in 0 until arr.size - 1) {  // Inner loop: n times
                    if (arr[j] > arr[j + 1]) {
                        val temp = arr[j]
                        arr[j] = arr[j + 1]
                        arr[j + 1] = temp
                    }
                }
            }
        }
        
        // Find all pairs with sum
        fun findPairsWithSum(arr: IntArray, target: Int): List<Pair<Int, Int>> {
            val pairs = mutableListOf<Pair<Int, Int>>()
            for (i in arr.indices) {  // n iterations
                for (j in i + 1 until arr.size) {  // n-1, n-2, ... iterations
                    if (arr[i] + arr[j] == target) {
                        pairs.add(Pair(arr[i], arr[j]))
                    }
                }
            }
            return pairs
        }
        
        // Example
        val numbers = intArrayOf(5, 2, 8, 1, 9)
        val copy = numbers.copyOf()
        bubbleSort(copy)
        println("Bubble sorted: ${copy.contentToString()}")
        println("Pairs summing to 10: ${findPairsWithSum(numbers, 10)}")
        println("Complexity: O(n²) - slow for large inputs")
        
        println()
    }
    
    /**
     * O(n³) - CUBIC TIME
     * Triple nested loops
     */
    fun cubicTimeExamples() {
        println("=== O(n³) - Cubic Time ===")
        
        // Find triplets with sum
        fun findTripletsWithSum(arr: IntArray, target: Int): List<Triple<Int, Int, Int>> {
            val triplets = mutableListOf<Triple<Int, Int, Int>>()
            for (i in arr.indices) {
                for (j in i + 1 until arr.size) {
                    for (k in j + 1 until arr.size) {
                        if (arr[i] + arr[j] + arr[k] == target) {
                            triplets.add(Triple(arr[i], arr[j], arr[k]))
                        }
                    }
                }
            }
            return triplets
        }
        
        // Example
        val numbers = intArrayOf(1, 2, 3, 4, 5)
        println("Triplets summing to 9: ${findTripletsWithSum(numbers, 9)}")
        println("Complexity: O(n³) - very slow, avoid if possible")
        
        println()
    }
    
    /**
     * O(2ⁿ) - EXPONENTIAL TIME
     * Doubles with each additional input
     */
    fun exponentialTimeExamples() {
        println("=== O(2ⁿ) - Exponential Time ===")
        
        // Naive Fibonacci
        fun fibonacci(n: Int): Long {
            if (n <= 1) return n.toLong()
            return fibonacci(n - 1) + fibonacci(n - 2)  // Two recursive calls
        }
        
        // Power set (all subsets)
        fun powerSet(nums: List<Int>): List<List<Int>> {
            val result = mutableListOf<List<Int>>()
            
            fun backtrack(index: Int, current: MutableList<Int>) {
                result.add(current.toList())
                
                for (i in index until nums.size) {
                    current.add(nums[i])
                    backtrack(i + 1, current)
                    current.removeAt(current.size - 1)
                }
            }
            
            backtrack(0, mutableListOf())
            return result
        }
        
        // Example (small n only!)
        println("Fibonacci(10): ${fibonacci(10)}")
        val set = listOf(1, 2, 3)
        println("Power set of $set: ${powerSet(set)}")
        println("Complexity: O(2ⁿ) - only feasible for small n (< 20)")
        
        println()
    }
    
    /**
     * SPACE COMPLEXITY EXAMPLES
     */
    fun spaceComplexityExamples() {
        println("=== Space Complexity ===")
        
        // O(1) space - constant
        fun swapInPlace(arr: IntArray, i: Int, j: Int) {
            val temp = arr[i]  // Only one extra variable
            arr[i] = arr[j]
            arr[j] = temp
        }
        
        // O(n) space - linear
        fun copyArray(arr: IntArray): IntArray {
            return arr.copyOf()  // New array of size n
        }
        
        // O(n) space - recursion
        fun factorial(n: Int): Long {
            if (n <= 1) return 1
            return n * factorial(n - 1)  // n stack frames
        }
        
        println("Space O(1): In-place swap")
        println("Space O(n): Array copy")
        println("Space O(n): Recursive factorial (stack depth)")
        
        println()
    }
    
    /**
     * PRACTICAL COMPARISONS
     */
    fun practicalComparison() {
        println("=== Practical Comparison ===")
        println("Input size: 1000 elements\n")
        
        // Generate test data
        val arr = IntArray(1000) { (it + 1) }
        
        // O(1) - constant
        var start = System.nanoTime()
        val first = arr[0]
        var time = System.nanoTime() - start
        println("O(1) - Get first element: ${time}ns")
        
        // O(n) - linear
        start = System.nanoTime()
        var sum = 0
        for (num in arr) sum += num
        time = System.nanoTime() - start
        println("O(n) - Sum all elements: ${time}ns")
        
        // O(n log n) - merge sort
        val toSort = arr.copyOf()
        toSort.shuffle()
        start = System.nanoTime()
        toSort.sort()
        time = System.nanoTime() - start
        println("O(n log n) - Sort array: ${time}ns")
        
        // O(n²) - nested loops
        start = System.nanoTime()
        var pairs = 0
        for (i in 0 until minOf(100, arr.size)) {
            for (j in 0 until minOf(100, arr.size)) {
                pairs++
            }
        }
        time = System.nanoTime() - start
        println("O(n²) - Count pairs (100x100): ${time}ns")
        
        println("\nAs n grows: O(1) stays constant, O(n) grows linearly,")
        println("O(n log n) grows slowly, O(n²) grows quickly")
        
        println()
    }
}

/**
 * ============================================================================
 * TIME COMPLEXITY CHEAT SHEET
 * ============================================================================
 * 
 * COMMON DATA STRUCTURES:
 * 
 * Array:
 * - Access: O(1)
 * - Search: O(n)
 * - Insert/Delete: O(n)
 * 
 * ArrayList/Dynamic Array:
 * - Access: O(1)
 * - Append: O(1) amortized
 * - Insert/Delete: O(n)
 * 
 * HashMap:
 * - Search/Insert/Delete: O(1) average, O(n) worst
 * 
 * Binary Search Tree (balanced):
 * - Search/Insert/Delete: O(log n)
 * 
 * COMMON ALGORITHMS:
 * 
 * Sorting:
 * - Bubble/Insertion/Selection Sort: O(n²)
 * - Merge/Quick/Heap Sort: O(n log n)
 * - Counting/Radix Sort: O(n+k) or O(nk)
 * 
 * Searching:
 * - Linear Search: O(n)
 * - Binary Search: O(log n) [on sorted data]
 * 
 * ============================================================================
 * OPTIMIZATION TIPS
 * ============================================================================
 * 
 * 1. Use hash maps for O(1) lookups instead of arrays
 * 2. Sort once and use binary search instead of repeated linear search
 * 3. Avoid nested loops when possible
 * 4. Use appropriate data structures
 * 5. Consider space-time tradeoffs
 * 6. Memoization for recursive algorithms
 * 7. Two pointers technique to avoid nested loops
 * 8. Sliding window for subarray problems
 * 
 * ============================================================================
 */

fun main() {
    val demo = TimeComplexityExamples()
    
    println("=== Time Complexity Examples ===\n")
    
    demo.constantTimeExamples()
    demo.logarithmicTimeExamples()
    demo.linearTimeExamples()
    demo.linearithmicTimeExamples()
    demo.quadraticTimeExamples()
    demo.cubicTimeExamples()
    demo.exponentialTimeExamples()
    demo.spaceComplexityExamples()
    demo.practicalComparison()
    
    println("=== Key Takeaways ===")
    println("✓ Big O describes worst-case scaling")
    println("✓ Lower complexity = better performance")
    println("✓ Choose right algorithm for your constraints")
    println("✓ O(1) < O(log n) < O(n) < O(n log n) < O(n²)")
    println("✓ Consider both time AND space complexity")
}
